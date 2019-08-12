package com.android.SerialPort;

import android_serialport_api.SerialPort;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public class SerialPortDevice extends SerialPort
{
    private final String serialPortPath;

    public SerialPortDevice(SerialPortConfig serialPortConfig) throws Exception
    {
        super(serialPortConfig);
        serialPortPath = serialPortConfig.getSerialPortPath();
    }

}
