package com.hellohuandian.apps.testlibrary.core;

import com.hellohuandian.apps.testlibrary.core.action.RwAction;
import com.hellohuandian.apps.testlibrary.core.action.WatchAction;

import android_serialport_api.SerialPortConfig;
import android_serialport_api.SerialPortDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
public final class TestManager
{
    private static final TestManager TEST_MANAGER = new TestManager();

    private final TaskFormater taskFormater = new TaskFormater();
    private final SerialPortDispatcher serialPortDispatcher = new SerialPortDispatcher(taskFormater);

    private TestManager()
    {

    }

    public static TestManager getInstance()
    {
        return TEST_MANAGER;
    }

    public void start()
    {
        try
        {
            SerialPortDevice serialPortDevice = new SerialPortDevice(new SerialPortConfig());
            CmdSerialPortDispatcher.getInstance().init(serialPortDevice);
            serialPortDispatcher.init(serialPortDevice);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        serialPortDispatcher.start();
    }

    public void stop()
    {
        if (serialPortDispatcher != null)
        {
            serialPortDispatcher.stop();
        }
    }

    public void setRwCallBack(RwAction rwCallBack)
    {
        taskFormater.setRwAction(rwCallBack);
    }

    public void setWatchAction(WatchAction watchAction)
    {
        serialPortDispatcher.setWatchAction(watchAction);
    }

    SerialPortDispatcher getSerialPortDispatcher()
    {
        return serialPortDispatcher;
    }
}
