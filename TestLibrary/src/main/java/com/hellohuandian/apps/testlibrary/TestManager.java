package com.hellohuandian.apps.testlibrary;

import com.android.SerialPort.SerialPortConfig;
import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.testlibrary.core.SerialPortDispatcher;
import com.hellohuandian.apps.testlibrary.core.SerialPortControllerDevice;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
public final class TestManager
{
    private static final TestManager TEST_MANAGER = new TestManager();

    private SerialPortDevice mSerialPortDevice;
    private final SerialPortDispatcher mSerialPortController = new SerialPortDispatcher();

    private TestManager()
    {
    }

    public static TestManager getInstance()
    {
        return TEST_MANAGER;
    }

    void start()
    {
        final SerialPortConfig serialPortConfig = new SerialPortConfig();
        try
        {
            mSerialPortDevice = new SerialPortControllerDevice(serialPortConfig);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            stop();
        }

        if (mSerialPortDevice != null)
        {
            mSerialPortController.init(mSerialPortDevice, serialPortConfig);
            try
            {
                mSerialPortController.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    void stop()
    {
        if (mSerialPortDevice != null)
        {
            mSerialPortDevice.closeSerial();
            mSerialPortDevice = null;
        }

        if (mSerialPortController != null)
        {
            mSerialPortController.stop();
        }
    }

    public SerialPortDispatcher getSerialPortController()
    {
        return mSerialPortController;
    }
}
