package com.hellohuandian.apps.testlibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hellohuandian.apps.testlibrary.core.OnEventConsumer;
import com.hellohuandian.apps.testlibrary.core.OnWatchResultConsumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
public abstract class TestService extends Service implements OnWatchResultConsumer, OnEventConsumer
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

        if (TestManager.getInstance().getSerialPortDispatcher() != null)
        {
            TestManager.getInstance().getSerialPortDispatcher().setOnEventConsumer(this);
            TestManager.getInstance().getSerialPortDispatcher().setOnWatchResultConsumer(this);
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
