package com.hellohuandian.apps.testlibrary.core;

import com.android.SerialPort.SerialPortConfig;
import com.android.SerialPort.SerialPortDevice;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-09
 * Description:
 */
public class SerialPortControllerDevice extends SerialPortDevice
{
    private ReentrantLock reentrantLock = new ReentrantLock(true);
    private Condition condition = reentrantLock.newCondition();

    public SerialPortControllerDevice(SerialPortConfig serialPortConfig) throws Exception
    {
        super(serialPortConfig);
    }

    @Override
    public void write(byte[] data) throws IOException
    {
        super.write(data);
    }

    @Override
    public byte[] read() throws IOException
    {
        return super.read();
    }
}
