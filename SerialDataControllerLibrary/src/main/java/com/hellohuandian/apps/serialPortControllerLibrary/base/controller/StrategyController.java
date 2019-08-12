package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import android.util.SparseArray;

import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.configstrategylibrary.table.ControlAddressTable;
import com.hellohuandian.apps.datalibrary.models.readSerialData.SerialPortBytes;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-08
 * Description: 基本命令读写控制
 */
public abstract class StrategyController<T extends TaskStrategy> extends BaseController<T>
{
    private final ControlAddressTable.Address address;

    private final SparseArray<T> controlStrategySparseArray = new SparseArray<>();
    protected final List<T> TaskStrategyList = new ArrayList<>();

    public StrategyController(SerialPortDevice serialPortDevice, ControlAddressTable.Address address)
    {
        super(serialPortDevice);
        this.address = address;
    }

    public int getControllerId()
    {
        return address != null ? address.id : -1;
    }

    public abstract SerialPortBytes execute();

    protected void addControlStrategy(T taskStrategy)
    {
        controlStrategySparseArray.put(taskStrategy.strategyId, taskStrategy);
    }

    protected T obtainControlStrategy(int controlStrategyId)
    {
        return controlStrategySparseArray.get(controlStrategyId);
    }

    /**
     * 添加命令策略
     *
     * @param taskStrategy
     */
    protected void addBatteryStrategy(T taskStrategy)
    {
        TaskStrategyList.add(taskStrategy);
    }
}
