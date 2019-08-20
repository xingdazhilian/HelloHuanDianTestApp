package com.hellohuandian.apps.ControllerLibrary.base.strategy;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-15
 * Description:
 */
class TimeStrategy extends IdStrategy
{
    //延迟时间，激活485的时候需要延迟的时间
    public long delayTime = 3 * 1000;

    //每条指令等待执行的间隔时间，保证写完之后，延迟，在读
    public long intervalTime = 300;

    public TimeStrategy(int id)
    {
        super(id);
    }

    public void setDelayTime(long delayTime)
    {
        this.delayTime = delayTime;
    }

    public void setIntervalTime(long intervalTime)
    {
        this.intervalTime = intervalTime;
    }
}
