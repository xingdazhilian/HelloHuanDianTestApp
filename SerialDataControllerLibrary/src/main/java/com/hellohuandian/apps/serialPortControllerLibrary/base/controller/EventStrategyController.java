package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.configstrategylibrary.table.ControlAddressTable;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

import androidx.core.util.Consumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-10
 * Description:
 */
public abstract class EventStrategyController<T extends TaskStrategy> extends StrategyController<T>
{
    private Consumer<String> writeConsumer;
    private Consumer<String> readConsumer;

    public EventStrategyController(SerialPortDevice serialPortDevice, ControlAddressTable.Address address)
    {
        super(serialPortDevice, address);
    }

    protected void onWriteEvent(String event)
    {
        if (writeConsumer != null)
        {
            writeConsumer.accept(event);
        }
    }

    protected void onReadEvent(String event)
    {
        if (readConsumer != null)
        {
            readConsumer.accept(event);
        }
    }

    public void registerWriteEventConsumer(Consumer<String> writeConsumer)
    {
        this.writeConsumer = writeConsumer;
    }

    public void registerReadEventConsumer(Consumer<String> readConsumer)
    {
        this.readConsumer = readConsumer;
    }
}
