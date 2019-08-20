package com.hellohuandian.apps.ControllerLibrary.strategyController.battery;

import com.hellohuandian.apps.ControllerLibrary.base.strategyController.TaskController;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description:
 */
public class BatteryController extends TaskController<BatteryStrategy>
{
    public BatteryController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    @Override
    public void execute(BatteryStrategy taskStrategy)
    {
        if (taskStrategy != null)
        {
            strategyWrite(taskStrategy);

            if (taskStrategy.intervalTime > 0)
            {
                sleep(taskStrategy.intervalTime);
            }

            strategyRead(taskStrategy);

            if (taskStrategy.isLast())
            {
                taskExecuteAfter();
            }
        }
    }
}