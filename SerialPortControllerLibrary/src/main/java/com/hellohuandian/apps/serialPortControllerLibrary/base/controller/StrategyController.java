package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import android_serialport_api.SerialPortDevice;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-08
 * Description: 对策略对象数据做支持
 */
public abstract class StrategyController<T extends TaskStrategy> extends TimeStrategyController<T>
{
    protected final int controllerAddressId;

    private final Map<Integer, T> controlStrategyMap = new HashMap<>();
    private final List<T> taskStrategyList = new ArrayList<>();

    public StrategyController(SerialPortDevice serialPortDevice, int controllerAddressId)
    {
        super(serialPortDevice);
        this.controllerAddressId = controllerAddressId;
    }

    public int getControllerAddressId()
    {
        return controllerAddressId;
    }

    protected void addControlStrategy(T taskStrategy)
    {
        controlStrategyMap.put(taskStrategy.strategyId, taskStrategy);
    }

    protected void addBatteryStrategy(T taskStrategy)
    {
        taskStrategyList.add(taskStrategy);
    }

    public abstract void execute();

    protected final void executeStrategy(int controlStrategyId)
    {
        syncExecute(controlStrategyMap.get(controlStrategyId));
    }

    protected final void executeStrategyAll()
    {
        int i = 0;
        final int count = taskStrategyList.size() - 1;
        for (; i < count; i++)
        {
            syncExecute(taskStrategyList.get(i));
        }

        // TODO: 2019-08-13 最后一条策略需要将延迟流程一起做同步处理
        final int lastIndex = taskStrategyList.size() - 1;
        if (i == lastIndex)
        {
            syncExecuteAndDelayed(taskStrategyList.get(i));
        }
    }
}
