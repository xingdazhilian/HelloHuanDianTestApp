package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import android_serialport_api.SerialPortDevice;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

import java.util.List;

import androidx.core.util.Consumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-12
 * Description: 执行控制器，提供同步操作
 */
public abstract class ExecuteController<T extends TaskStrategy> extends BaseController
{
    private final Consumer<T> executeConsumer = t -> onSyncExecute(t);
    private final Consumer<List<T>> executeMoreConsumer = tList -> {
        if (tList != null)
        {
            for (T t : tList)
            {
                ExecuteController.this.onSyncExecute(t);
            }
        }
    };

    public ExecuteController(SerialPortDevice serialPortDevice)
    {
        super(serialPortDevice);
    }

    protected abstract void onSyncExecute(T t);

    protected final void syncExecute(T t)
    {
        SyncExecuter.SYNC_EXECUTER.execute(executeConsumer, t, null, null);
    }

    protected final void syncExecute(Consumer<T> executeConsumer, T t)
    {
        SyncExecuter.SYNC_EXECUTER.execute(executeConsumer, t, null, null);
    }

    protected final void syncExecute(List<T> tList)
    {
        SyncExecuter.SYNC_EXECUTER.execute(null, null, executeMoreConsumer, tList);
    }

    private static final class SyncExecuter<T>
    {
        static final SyncExecuter SYNC_EXECUTER = new SyncExecuter();

        synchronized void execute(Consumer<T> consumer, T t, Consumer<List<T>> executeMoreConsumer, List<T> tList)
        {
            if (consumer != null)
            {
                consumer.accept(t);
            }
            if (executeMoreConsumer != null)
            {
                executeMoreConsumer.accept(tList);
            }
        }
    }
}
