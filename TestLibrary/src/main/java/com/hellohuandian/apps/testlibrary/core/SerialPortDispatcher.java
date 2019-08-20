package com.hellohuandian.apps.testlibrary.core;

import com.hellohuandian.apps.ControllerLibrary.StrategyDispatcher;
import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;
import com.hellohuandian.apps.ControllerLibrary.base.strategyController.TaskController;
import com.hellohuandian.apps.ControllerLibrary.base.strategyController.action.TaskAction;
import com.hellohuandian.apps.ControllerLibrary.strategyController.battery.BatteryController;
import com.hellohuandian.apps.ControllerLibrary.strategyController.battery.BatteryStrategy;
import com.hellohuandian.apps.ControllerLibrary.strategyController.cmd.CmdStrategy;
import com.hellohuandian.apps.ControllerLibrary.strategyController.controlPanel._485Controller;
import com.hellohuandian.apps.ControllerLibrary.strategyController.controlPanel._485Strategy;
import com.hellohuandian.apps.SerialPortDataLibrary.models.data.SerialPortData;
import com.hellohuandian.apps.SerialPortDataLibrary.models.info.BatteryInfo;
import com.hellohuandian.apps.configstrategylibrary.BatteryStrategyTable;
import com.hellohuandian.apps.configstrategylibrary.ControlPanelStrategyTable;
import com.hellohuandian.apps.deviceparserlibrary.SerialPortParserProvider;
import com.hellohuandian.apps.deviceparserlibrary.base.BaseSerialDataParser;
import com.hellohuandian.apps.deviceparserlibrary.version.BatteryVersion;
import com.hellohuandian.apps.deviceparserlibrary.version.VersionParser;
import com.hellohuandian.apps.testlibrary.core.action.WatchAction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-15
 * Description:
 */
final class SerialPortDispatcher extends ConcurrentLinkedQueue<CmdStrategy>
{
    private SerialPortDevice serialPortDevice;
    private final StrategyDispatcher<TaskStrategy> strategyDispatcher = new StrategyDispatcher<>();
    private final TaskFormater taskFormater;

    private final VersionParser versionParser = new VersionParser();

    private final Map<Integer, BatteryInfo> batteryInfoMap = new HashMap<>();

    private volatile boolean isPolling;

    private WatchAction watchAction;

    private final TaskAction taskAction = new TaskAction()
    {
        @Override
        public void onTaskExecuteBefore(int taskAddress)
        {
            if (watchAction != null)
            {
                BatteryInfo batteryInfo = batteryInfoMap.get(taskAddress);
                if (batteryInfo != null)
                {
                    batteryInfo.setStatus(BatteryInfo.LOADING);
                    watchAction.onWatch(batteryInfo);
                }
            }
        }

        @Override
        public void onTaskExecuteAfter(int taskAddress)
        {
            if (watchAction != null && taskFormater != null)
            {
                SerialPortData serialPortData = taskFormater.getSerialPortData();
                if (serialPortData != null)
                {
                    BatteryInfo batteryInfo = null;
                    BatteryVersion batteryVersion = versionParser.match(serialPortData);
                    if (batteryVersion != null)
                    {
                        BaseSerialDataParser serialDataParser = SerialPortParserProvider.match(batteryVersion);
                        if (serialDataParser != null)
                        {
                            batteryInfo = serialDataParser.parse(serialPortData);
                            if (batteryInfo != null)
                            {
                                batteryInfo.setControllerAddress(taskAddress);
                            }
                        }
                    }

                    if (batteryInfo == null)
                    {
                        batteryInfo = batteryInfoMap.get(taskAddress);
                        if (batteryInfo != null)
                        {
                            batteryInfo.reset();
                        }
                    }

                    if (watchAction != null)
                    {
                        watchAction.onWatch(batteryInfo);
                    }
                }
            }
        }
    };

    public SerialPortDispatcher(TaskFormater taskFormater)
    {
        this.taskFormater = taskFormater;
    }

    void init(SerialPortDevice serialPortDevice)
    {
        /**
         * 策略结构：
         * groupIndex1-策略1(groupIndex1)-策略2(groupIndex1)-策略3(groupIndex1)-策略4(groupIndex1)
         * -groupIndex2-策略1(groupIndex2)-策略2(groupIndex2)-策略3(groupIndex2)-策略4(groupIndex2)-.......
         * ...........
         * .........
         * .......
         */
        if (serialPortDevice != null)
        {
            this.serialPortDevice = serialPortDevice;

            if (ControlPanelStrategyTable.ADDRS != null)
            {
                TaskController taskController;
                TaskStrategy taskStrategy;
                int groupIndex = 0;

                for (int index = 0, taskId = 1, size = ControlPanelStrategyTable.ADDRS.size(); index < size; index++, taskId++)
                {
                    // TODO: 2019-08-17 添加485转发策略
                    taskController = new _485Controller(serialPortDevice);
                    taskController.setTaskGroupId(groupIndex);
                    taskController.setTaskAddress(ControlPanelStrategyTable.ADDRS.get(index)[ControlPanelStrategyTable.ADDRESS_INDEX]);
                    taskController.setStrategyAction(taskFormater);
                    taskController.setTaskAction(taskAction);

                    taskStrategy = new _485Strategy(ControlPanelStrategyTable.ID._485, ControlPanelStrategyTable.ADDRS.get(index));
                    taskStrategy.setTaskController(taskController);
                    strategyDispatcher.add(taskStrategy);

                    BatteryInfo batteryInfo = new BatteryInfo();
                    batteryInfo.setControllerAddress(taskController.getTaskAddress());
                    fullData(batteryInfo);

                    if (BatteryStrategyTable.CMDS != null)
                    {
                        taskController = new BatteryController(serialPortDevice);
                        taskController.setTaskGroupId(groupIndex);
                        taskController.setTaskAddress(ControlPanelStrategyTable.ADDRS.get(index)[ControlPanelStrategyTable.ADDRESS_INDEX]);
                        taskController.setStrategyAction(taskFormater);
                        taskController.setTaskAction(taskAction);

                        for (Map.Entry<Integer, byte[]> entry : BatteryStrategyTable.CMDS.entrySet())
                        {
                            // TODO: 2019-08-17 添加电池策略
                            taskStrategy = new BatteryStrategy(entry.getKey(), entry.getValue());
                            taskStrategy.setTaskController(taskController);
                            strategyDispatcher.add(taskStrategy);
                        }

                        // TODO: 2019-08-17 标记为最后一条策略
                        taskStrategy.setLast(true);

                        groupIndex = strategyDispatcher.size();
                    }
                }
            }
        }
    }

    void fullData(BatteryInfo batteryInfo)
    {
        batteryInfoMap.put(batteryInfo.getControllerAddress(), batteryInfo);

        if (watchAction != null)
        {
            watchAction.onWatch(batteryInfo);
        }
    }

    void start()
    {
        isPolling = true;

        new Thread(() -> {
            while (isPolling)
            {
                CmdStrategy cmdStrategy = poll();
                if (cmdStrategy == null)
                {
                    strategyDispatcher.next();
                    // TODO: 2019-08-15 继续执行任务
                } else
                {
                    // TODO: 2019-08-15 执行cmd任务
                    strategyDispatcher.next(cmdStrategy);
                }
            }
        }).start();
    }

    void stop()
    {
        isPolling = false;
        if (serialPortDevice != null)
        {
            serialPortDevice.closeSerial();
        }
    }

    void setWatchAction(WatchAction watchAction)
    {
        this.watchAction = watchAction;
    }
}
