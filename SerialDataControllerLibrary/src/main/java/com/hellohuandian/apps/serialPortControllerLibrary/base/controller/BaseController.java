package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import com.android.SerialPort.SerialPortDevice;

import java.io.IOException;

import androidx.core.util.Consumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description: 读取控制，每个实现控制都可能有自己独立的地址访问方式
 */
public abstract class BaseController<T>
{
    private final SerialPortDevice serialPortDevice;

    private final byte[] emptyData = new byte[0];

    public BaseController(SerialPortDevice serialPortDevice)
    {
        this.serialPortDevice = serialPortDevice;
    }

    protected void write(byte[] bytes)
    {
        if (serialPortDevice != null)
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

    protected final void syncExecute(Consumer<T> consumer, T t)
    {
        SyncExecuter.getInstance().execute(consumer, t);
    }

    private static final class SyncExecuter<T>
    {
        private static final SyncExecuter SYNC_EXECUTER = new SyncExecuter();

        static SyncExecuter getInstance()
        {
            return SYNC_EXECUTER;
        }

        synchronized void execute(Consumer<T> consumer, T t)
        {
            if (consumer != null)
            {
                consumer.accept(t);
            }
        }
    }
}
