package com.hellohuandian.apps.ControllerLibrary.base.strategyController;

import java.io.IOException;

import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-15
 * Description:
 */
abstract class DeviceController
{
    private final SerialPortDevice serialPortDevice;

    private final byte[] emptyData = new byte[0];

    public DeviceController(SerialPortDevice serialPortDevice)
    {
        this.serialPortDevice = serialPortDevice;
    }

    protected final void write(byte[] bytes)
    {
        if (serialPortDevice != null && bytes != null)
        {
            try
            {
                serialPortDevice.write(bytes);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected final byte[] read()
    {
        if (serialPortDevice != null)
        {
            try
            {
                return serialPortDevice.read();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return emptyData;
    }
}
