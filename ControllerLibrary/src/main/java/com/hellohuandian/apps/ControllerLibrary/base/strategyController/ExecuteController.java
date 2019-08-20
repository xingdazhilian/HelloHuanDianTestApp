package com.hellohuandian.apps.ControllerLibrary.base.strategyController;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description:
 */
abstract class ExecuteController<T extends TaskStrategy> extends TimeController
{
    public ExecuteController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    /**
     * 策略执行，执行细节由最终控制器实现
     *
     * @param taskStrategy
     */
    public abstract void execute(T taskStrategy);
}
