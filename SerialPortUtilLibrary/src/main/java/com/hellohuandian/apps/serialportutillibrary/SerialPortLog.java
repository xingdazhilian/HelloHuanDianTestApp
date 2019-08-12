package com.hellohuandian.apps.serialportutillibrary;

import android.text.TextUtils;

import com.hellohuandian.apps.utillibrary.LogTracer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-27
 * Description:
 */
public class SerialPortLog
{
    private static final String TAG = " 串口日志-> ";

    public static final void outI(String tag, String text)
    {
        LogTracer.outI(TAG + tag, TextUtils.isEmpty(text) ? " " : text);
    }
}
