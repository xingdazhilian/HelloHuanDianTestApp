package com.hellohuandian.apps.testlibrary;

import com.android.SerialPort.SerialPortConfig;
import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.testlibrary.core.SerialPortControllerDevice;
import com.hellohuandian.apps.testlibrary.core.SerialPortDispatcher;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
final class TestManager
{
    private static final TestManager TEST_MANAGER = new TestManager();

    private SerialPortDevice mSerialPortDevice;
    private final SerialPortDispatcher mSerialPortController = new SerialPortDispatcher();

    private TestManager()
    {
    }

    static TestManager getInstance()
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
            mSerialPortController.init(mSerialPortDevice);
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

    SerialPortDispatcher getSerialPortDispatcher()
    {
        return mSerialPortController;
    }
}
