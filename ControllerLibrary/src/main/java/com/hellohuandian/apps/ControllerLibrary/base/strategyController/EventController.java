package com.hellohuandian.apps.ControllerLibrary.base.strategyController;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;
import com.hellohuandian.apps.ControllerLibrary.base.strategyController.action.StrategyAction;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description: 事件控制器，对控制器回调事件做支持
 */
public abstract class EventController<T extends TaskStrategy> extends ExecuteController<T>
{
    private StrategyAction strategyAction;

    public EventController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    public void setStrategyAction(StrategyAction strategyAction)
    {
        this.strategyAction = strategyAction;
    }


    protected final void strategyWrite(T taskStrategy)
    {
        if (strategyAction != null)
        {
            if (taskStrategy != null)
            {
                write(taskStrategy.taskBytes);
            }
            strategyAction.onStrategyWrite(taskStrategy);
        }
    }

    protected final void strategyRead(T taskStrategy)
    {
        if (strategyAction != null)
        {
            strategyAction.onStrategyRead(taskStrategy, read());
        }
    }
}
