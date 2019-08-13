package com.hellohuandian.apps.testlibrary.core;

import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.datalibrary.models.BatteryData.BatteryInfo;
import com.hellohuandian.apps.datalibrary.models.readSerialData.SerialPortBytes;
import com.hellohuandian.apps.deviceparserlibrary.SerialPortParserProvider;
import com.hellohuandian.apps.deviceparserlibrary.base.BaseSerialDataParser;
import com.hellohuandian.apps.deviceparserlibrary.version.BatteryVersion;
import com.hellohuandian.apps.deviceparserlibrary.version.VersionParser;
import com.hellohuandian.apps.serialPortControllerLibrary.SerialPortControllerProvider;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.EventStrategyController;
import com.hellohuandian.apps.testlibrary.core.observer.IgnoreErrorObserver;
import com.hellohuandian.apps.utillibrary.LogTracer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.util.Consumer;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description:
 */
public final class SerialPortDispatcher
{
    private static final String TAG = "串口控制器:";

    private SerialPortDevice serialPortDevice;

    private final Map<Integer, BatteryInfo> batteryInfoMap = new HashMap<>();

    private OnWatchResultConsumer onWatchResultConsumer;
    private OnEventConsumer onEventConsumer;

    private final Consumer<Integer> idIndicatorConsumer = new Consumer<Integer>()
    {
        @Override
        public void accept(Integer integer)
        {
            if (onEventConsumer != null)
            {
                onEventConsumer.onIdIndicate(integer);
            }
        }
    };
    private final Consumer<String> writeConsumer = new Consumer<String>()
    {
        @Override
        public void accept(String s)
        {
            if (onEventConsumer != null)
            {
                onEventConsumer.onWriteEvent(s);
            }
        }
    };
    private final Consumer<String> readConsumer = new Consumer<String>()
    {
        @Override
        public void accept(String s)
        {
            if (onEventConsumer != null)
            {
                onEventConsumer.onReadEvent(s);
            }
        }
    };
    private final Consumer<BatteryInfo> watchConsumer = new Consumer<BatteryInfo>()
    {
        @Override
        public void accept(BatteryInfo batteryInfo)
        {
            if (onWatchResultConsumer != null)
            {
                onWatchResultConsumer.onWatch(batteryInfo);
            }
        }
    };
    private final Consumer<SerialPortBytes> serialPortBytesConsumer = new Consumer<SerialPortBytes>()
    {
        @Override
        public void accept(SerialPortBytes serialPortBytes)
        {
            // TODO: 2019-07-29 选择串口解析器
            BatteryVersion batteryVersion = versionParser.match(serialPortBytes);
            if (batteryVersion != null)
            {
                BaseSerialDataParser serialDataParser = SerialPortParserProvider.match(batteryVersion);
                if (serialDataParser != null)
                {
                    BatteryInfo batteryInfo = serialDataParser.parse(serialPortBytes);
                    if (batteryInfo != null)
                    {
                        batteryInfo.setControllerAddressId(serialPortBytes.getControllerAddressId());
                        watchConsumer.accept(batteryInfo);
                    }
                }
            } else
            {
                BatteryInfo batteryInfo = batteryInfoMap.get(serialPortBytes.getControllerAddressId());
                if (batteryInfo != null)
                {
                    batteryInfo.reset();
                }
                watchConsumer.accept(batteryInfo);
            }
        }
    };
    private final Consumer<Long> delayedConsumer = new Consumer<Long>()
    {
        @Override
        public void accept(Long l)
        {
            if (onEventConsumer != null)
            {
                onEventConsumer.onOutEvent("延迟剩余" + (l / 1000) + "s...");
            }
        }
    };

    private Disposable loopDeviceDataDisposable;

    private final List<EventStrategyController> readControllerList = new ArrayList<>();

    private final VersionParser versionParser = new VersionParser();

    public void init(@NonNull SerialPortDevice serialPortDevice)
    {
        stop();

        this.serialPortDevice = serialPortDevice;

        readControllerList.addAll(SerialPortControllerProvider.loadController(serialPortDevice));
        for (EventStrategyController eventStrategyController : readControllerList)
        {
            eventStrategyController.registerIndicatorConsumer(idIndicatorConsumer);
            eventStrategyController.registerWriteEventConsumer(writeConsumer);
            eventStrategyController.registerReadEventConsumer(readConsumer);
            eventStrategyController.registerSerialPortBytesConsumer(serialPortBytesConsumer);
            eventStrategyController.registerCountdownConsumer(delayedConsumer);
        }

        notifyInitData();

        LogTracer.outI(TAG, "串口Read控制器初始化完成，数量:" + readControllerList.size());
    }

    /**
     * 通知初始化数据
     */
    private void notifyInitData()
    {
        BatteryInfo batteryInfo;
        for (EventStrategyController eventStrategyController : readControllerList)
        {
            batteryInfo = new BatteryInfo();
            batteryInfo.setControllerAddressId(eventStrategyController.getControllerAddressId());
            batteryInfoMap.put(eventStrategyController.getControllerAddressId(), batteryInfo);
            watchConsumer.accept(batteryInfo);
        }
    }

    public void start()
    {
        if (serialPortDevice == null)
        {
            return;
        }

        if (loopDeviceDataDisposable != null)
        {
            if (!loopDeviceDataDisposable.isDisposed())
            {
                loopDeviceDataDisposable.dispose();
            }
        }

        Observable.fromIterable(readControllerList)
                .subscribeOn(Schedulers.single())
                .repeat()
                .filter(eventStrategyController -> eventStrategyController != null)
                .subscribe(new IgnoreErrorObserver<EventStrategyController>()
                {
                    @Override
                    protected void on_Next(EventStrategyController eventStrategyController)
                    {
                        if (onEventConsumer != null)
                        {
                            onEventConsumer.onOutEvent("clear");
                        }

                        eventStrategyController.execute();
                    }

                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        loopDeviceDataDisposable = d;
                    }
                });
    }

    public void stop()
    {
        SerialPortParserProvider.finish();
    }

    public void setOnEventConsumer(OnEventConsumer onEventConsumer)
    {
        this.onEventConsumer = onEventConsumer;
    }

    public void setOnWatchResultConsumer(OnWatchResultConsumer onWatchResultConsumer)
    {
        this.onWatchResultConsumer = onWatchResultConsumer;
    }
}
