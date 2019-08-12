package com.hellohuandian.apps.testlibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hellohuandian.apps.deviceparserlibrary.base.callBack.SerialDataListener;
import com.hellohuandian.apps.testlibrary.core.OnRwEventConsumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
public abstract class TestService extends Service implements SerialDataListener, OnRwEventConsumer
{
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        if (TestManager.getInstance().getSerialPortController() != null)
        {
            TestManager.getInstance().getSerialPortController().setSerialDataListener(this);
            TestManager.getInstance().getSerialPortController().setOnRwEventConsumer(this);
        }

        try
        {
            TestManager.getInstance().start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        TestManager.getInstance().stop();
    }
}
