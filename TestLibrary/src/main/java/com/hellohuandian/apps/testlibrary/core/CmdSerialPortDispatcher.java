package com.hellohuandian.apps.testlibrary.core;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;
import com.hellohuandian.apps.ControllerLibrary.base.strategyController.action.StrategyAction;
import com.hellohuandian.apps.ControllerLibrary.base.strategyController.action.TaskAction;
import com.hellohuandian.apps.ControllerLibrary.strategyController.cmd.CmdController;
import com.hellohuandian.apps.ControllerLibrary.strategyController.cmd.CmdStrategy;
import com.hellohuandian.apps.configstrategylibrary.ControlPanelStrategyTable;
import com.hellohuandian.apps.utillibrary.StringFormatHelper;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-20
 * Description:
 */
public final class CmdSerialPortDispatcher
{
    private static final CmdSerialPortDispatcher CMD_SERIAL_PORT_DISPATCHER = new CmdSerialPortDispatcher();

    private CmdController cmdController;

    private final TaskAction taskAction = new TaskAction()
    {
        @Override
        public void onTaskExecuteBefore(int taskAddress)
        {

        }

        @Override
        public void onTaskExecuteAfter(int taskAddress)
        {

        }
    };

    private final StrategyAction strategyAction = new StrategyAction()
    {
        @Override
        public void onStrategyWrite(TaskStrategy taskStrategy)
        {
            String cmdWrite = ">";
            switch (taskStrategy.id)
            {
                case ControlPanelStrategyTable.ID.PUSH_ROD:
                    cmdWrite += StringFormatHelper.getInstance().toHexString(taskStrategy.taskBytes);
                    break;
            }
            System.out.println(cmdWrite);
        }

        @Override
        public void onStrategyRead(TaskStrategy taskStrategy, byte[] data)
        {
            String cmdRead = ">";
            switch (taskStrategy.id)
            {
                case ControlPanelStrategyTable.ID.PUSH_ROD:
                    cmdRead += StringFormatHelper.getInstance().toHexString(data);
                    break;
            }
            System.out.println(cmdRead);
        }
    };

    private CmdSerialPortDispatcher()
    {
    }

    public static CmdSerialPortDispatcher getInstance()
    {
        return CMD_SERIAL_PORT_DISPATCHER;
    }

    void init(SerialPortDevice serialPortDevice)
    {
        cmdController = new CmdController(serialPortDevice);
        cmdController.setTaskAction(taskAction);
        cmdController.setStrategyAction(strategyAction);
    }

    public class CmdStrategyId
    {
        public static final int PUSH_ROD = ControlPanelStrategyTable.ID.PUSH_ROD;
    }

    public void dispatch(int cmdStrategyId, int controlAddress)
    {
        final int index = ControlPanelStrategyTable.findIndexByAddr(controlAddress);
        if (index >= 0 && index < ControlPanelStrategyTable.PUSH_RODS.size())
        {
            CmdStrategy cmdStrategy = null;
            switch (cmdStrategyId)
            {
                case CmdStrategyId.PUSH_ROD:
                    cmdStrategy = new CmdStrategy(cmdStrategyId, ControlPanelStrategyTable.PUSH_RODS.get(index));
                    cmdStrategy.setIntervalTime(10 * 1000);
                    break;
            }

            dispatch(cmdStrategy);
        }
    }

    private void dispatch(CmdStrategy cmdStrategy)
    {
        if (TestManager.getInstance().getSerialPortDispatcher() != null)
        {
            cmdStrategy.setTaskController(cmdController);
            TestManager.getInstance().getSerialPortDispatcher().add(cmdStrategy);
        }
    }
}
