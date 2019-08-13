package com.hellohuandian.apps.serialPortControllerLibrary.base.controller;

import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.datalibrary.models.readSerialData.SerialPortBytes;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.strategy.TaskStrategy;

import androidx.core.util.Consumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-10
 * Description: 对一些事件注册回调做支持
 */
public abstract class EventStrategyController<T extends TaskStrategy> extends StrategyController<T>
{
    private Consumer<Integer> idIndicatorConsumer;
    private Consumer<String> writeConsumer;
    private Consumer<String> readConsumer;

    private Consumer<SerialPortBytes> serialPortBytesConsumer;

    public EventStrategyController(SerialPortDevice serialPortDevice, int controllerAddressId)
    {
        super(serialPortDevice, controllerAddressId);
    }

    protected void onIndicate(Integer position)
    {
        if (idIndicatorConsumer != null)
        {
            idIndicatorConsumer.accept(position);
        }
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

    protected void onReadFinish(SerialPortBytes serialPortBytes)
    {
        if (serialPortBytesConsumer != null)
        {
            serialPortBytesConsumer.accept(serialPortBytes);
        }
    }

    public void registerIndicatorConsumer(Consumer<Integer> idIndicatorConsumer)
    {
        this.idIndicatorConsumer = idIndicatorConsumer;
    }

    public void registerWriteEventConsumer(Consumer<String> writeConsumer)
    {
        this.writeConsumer = writeConsumer;
    }

    public void registerReadEventConsumer(Consumer<String> readConsumer)
    {
        this.readConsumer = readConsumer;
    }

    public final void registerSerialPortBytesConsumer(Consumer<SerialPortBytes> serialPortBytesConsumer)
    {
        this.serialPortBytesConsumer = serialPortBytesConsumer;
    }
}
