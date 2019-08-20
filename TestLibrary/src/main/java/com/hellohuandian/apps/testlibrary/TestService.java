package com.hellohuandian.apps.testlibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.hellohuandian.apps.testlibrary.core.TestManager;
import com.hellohuandian.apps.testlibrary.core.action.RwAction;
import com.hellohuandian.apps.testlibrary.core.action.WatchAction;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
public abstract class TestService extends Service implements RwAction, WatchAction
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

        TestManager.getInstance().setRwCallBack(this);
        TestManager.getInstance().setWatchAction(this);

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
