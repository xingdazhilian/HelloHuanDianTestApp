package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import android_serialport_api.SerialPortDevice;

import java.io.IOException;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public abstract class BaseController
{
    private final SerialPortDevice serialPortDevice;

    private final byte[] emptyData = new byte[0];

    public BaseController(SerialPortDevice serialPortDevice)
    {
        this.serialPortDevice = serialPortDevice;
    }

    protected void write(byte[] bytes)
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

    protected byte[] read()
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
