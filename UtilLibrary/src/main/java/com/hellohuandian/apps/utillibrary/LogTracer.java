package com.hellohuandian.apps.utillibrary;

import android.util.Log;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-27
 * Description:
 */
public class LogTracer
{
    private static String LogTracer_TAG = "Log：";

    public static void outE(String TAG, String logText)
    {
        Log.e(TAG, logText);
    }

    public static void outI(String TAG, String logText)
    {
        Log.i(TAG, logText);
    }

    public static void outD(String TAG, String logText)
    {
        Log.d(TAG, logText);
    }

    public static void outE(String logText)
    {
        Log.e(LogTracer_TAG, logText);
    }

    public static void outI(String logText)
    {
        Log.i(LogTracer_TAG, logText);
    }

    public static void outD(String logText)
    {
        Log.d(LogTracer_TAG, logText);
    }
}
