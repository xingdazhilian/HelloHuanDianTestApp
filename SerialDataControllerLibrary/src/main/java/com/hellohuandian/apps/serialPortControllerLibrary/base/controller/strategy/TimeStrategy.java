package com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-08
 * Description:
 */
class TimeStrategy
{
    public final long outTime;
    public final long waitTime;

    public TimeStrategy(long outTime, long waitTime)
    {
        this.outTime = outTime;
        this.waitTime = waitTime;
    }
}
