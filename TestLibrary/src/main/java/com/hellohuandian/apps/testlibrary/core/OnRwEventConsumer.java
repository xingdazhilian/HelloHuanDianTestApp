package com.hellohuandian.apps.testlibrary.core;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-10
 * Description:
 */
public interface OnRwEventConsumer
{
    void onReadEvent(String event);

    void onOutEvent(String event);

    void onWriteEvent(String event);
}
