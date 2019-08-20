package com.hellohuandian.apps.ControllerLibrary.base.strategyController;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description:
 */
class TimeController extends DeviceController
{
    public TimeController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    protected final void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
