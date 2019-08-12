package com.HelloHuanDian.apps.test;

import android.app.Application;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-26
 * Description:
 */
public class TestApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
//        setRxJavaErrorHandler();
    }

    private void setRxJavaErrorHandler()
    {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>()
        {
            @Override
            public void accept(Throwable throwable) throws Exception
            {
                System.out.println("错误了");
            }
        });
    }
}
