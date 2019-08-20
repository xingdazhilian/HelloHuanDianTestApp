package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import android_serialport_api.SerialPortDevice;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

import androidx.core.util.Consumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-12
 * Description:
 */
public abstract class TimeStrategyController<T extends TaskStrategy> extends ExecuteController<T>
{
    private static long delayedTime = 1000 * 3;

    private Consumer<Long> countdownConsumer;

    private final Consumer<T> delayedConsumer = t -> {
        // TODO: 2019-08-13 同步执行策略->执行之后->执行延时时间
        onSyncExecute(t);
        onDelayedBefore();
        executeDelayed();
    };

    public TimeStrategyController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    /**
     * 执行延迟之前调用
     */
    protected abstract void onDelayedBefore();

    /**
     * 执行延迟
     */
    private final void executeDelayed()
    {
        if (countdownConsumer != null)
        {
            long count = delayedTime / 1000;
            long countdownTime = 1000;
            while (count > 0)
            {
                countdownConsumer.accept(countdownTime * count);
                count--;
                sleep(countdownTime);
            }
            countdownTime = delayedTime % 1000;
            if (delayedTime > 0)
            {
                sleep(countdownTime);
                countdownConsumer.accept(countdownTime);
            }
            //0L代表执行延时结束
            countdownConsumer.accept(0L);
        }
    }

    protected final void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 同步执行并且延时
     *
     * @param t
     */
    protected final void syncExecuteAndDelayed(T t)
    {
        syncExecute(delayedConsumer, t);
    }

    public final void setDelayedTime(long delayedTime)
    {
        this.delayedTime = delayedTime;
    }

    /**
     * 注册倒计时监听
     *
     * @param countdownConsumer
     */
    public void registerCountdownConsumer(Consumer<Long> countdownConsumer)
    {
        this.countdownConsumer = countdownConsumer;
    }
}
