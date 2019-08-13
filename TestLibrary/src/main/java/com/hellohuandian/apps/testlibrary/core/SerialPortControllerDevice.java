package com.hellohuandian.apps.testlibrary.core;

import com.android.SerialPort.SerialPortConfig;
import com.android.SerialPort.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-09
 * Description:
 */
public class SerialPortControllerDevice extends SerialPortDevice
{
    public SerialPortControllerDevice(SerialPortConfig serialPortConfig) throws Exception
    {
        super(serialPortConfig);
    }
}
