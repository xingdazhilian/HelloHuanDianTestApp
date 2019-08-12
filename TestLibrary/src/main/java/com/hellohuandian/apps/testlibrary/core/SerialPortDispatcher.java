package com.hellohuandian.apps.testlibrary.core;

import com.android.SerialPort.SerialPortConfig;
import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.datalibrary.models.BatteryData.BatteryInfo;
import com.hellohuandian.apps.datalibrary.models.readSerialData.SerialPortBytes;
import com.hellohuandian.apps.deviceparserlibrary.SerialPortParserProvider;
import com.hellohuandian.apps.deviceparserlibrary.base.BaseSerialDataParser;
import com.hellohuandian.apps.deviceparserlibrary.base.callBack.SerialDataListener;
import com.hellohuandian.apps.deviceparserlibrary.version.BatteryVersion;
import com.hellohuandian.apps.deviceparserlibrary.version.VersionParser;
import com.hellohuandian.apps.serialPortControllerLibrary.SerialPortControllerProvider;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.EventStrategyController;
import com.hellohuandian.apps.testlibrary.core.observer.IgnoreErrorObserver;
import com.hellohuandian.apps.utillibrary.LogTracer;

import java.util.ArrayList;
import java.util.List;

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
    private SerialPortConfig serialPortConfig;

    private SerialDataListener serialDataListener;
    private OnRwEventConsumer onRwEventConsumer;

    private final Consumer<String> writeConsumer = new Consumer<String>()
    {
        @Override
        public void accept(String s)
        {
            if (onRwEventConsumer != null)
            {
                onRwEventConsumer.onWriteEvent(s);
            }
        }
    };
    private final Consumer<String> readConsumer = new Consumer<String>()
    {
        @Override
        public void accept(String s)
        {
            if (onRwEventConsumer != null)
            {
                onRwEventConsumer.onReadEvent(s);
            }
        }
    };

    private Disposable loopDeviceDataDisposable;

    private final List<EventStrategyController> readControllerList = new ArrayList<>();

    private final VersionParser versionParser = new VersionParser();

    public void init(@NonNull SerialPortDevice serialPortDevice, @NonNull SerialPortConfig serialPortConfig)
    {
        stop();

        this.serialPortDevice = serialPortDevice;
        this.serialPortConfig = serialPortConfig;

        readControllerList.addAll(SerialPortControllerProvider.loadController(serialPortDevice));
        for (EventStrategyController eventStrategyController : readControllerList)
        {
            eventStrategyController.registerWriteEventConsumer(writeConsumer);
            eventStrategyController.registerReadEventConsumer(readConsumer);
        }
        LogTracer.outI(TAG, "串口Read控制器初始化完成，数量:" + readControllerList.size());
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
                .map(eventStrategyController -> eventStrategyController)
                .map(baseReadController -> {
                    if (onRwEventConsumer != null)
                    {
                        onRwEventConsumer.onOutEvent("↓↓↓↓↓↓↓电池号(" + baseReadController.getControllerId() + ")↓↓↓↓↓↓↓\n");
                    }
                    return baseReadController.execute();
                })
                .subscribe(new IgnoreErrorObserver<SerialPortBytes>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        loopDeviceDataDisposable = d;
                    }

                    @Override
                    protected void on_Next(SerialPortBytes serialPortBytes)
                    {
//                        SerialPortLog.outI("电池信息", "------------------------------------------------");
//                        SerialPortLog.outI("制造商", StringFormatHelper.getInstance().toHexString(serialPortBytes.getManufacturer()));
//                        SerialPortLog.outI("版本", StringFormatHelper.getInstance().toHexString(serialPortBytes.getVersion()));
//                        SerialPortLog.outI("电池ID校验码", StringFormatHelper.getInstance().toHexString(serialPortBytes.getBatteryIdCheckCode()));
//                        SerialPortLog.outI("电池ID条码", StringFormatHelper.getInstance().toHexString(serialPortBytes.getBatteryIdCode()));
//                        SerialPortLog.outI("SOH", StringFormatHelper.getInstance().toHexString(serialPortBytes.getSoh()));
//                        SerialPortLog.outI("电池电压1-7", StringFormatHelper.getInstance().toHexString(serialPortBytes.getBatteryVoltage_1_7()));
//                        SerialPortLog.outI("电池电压8-15", StringFormatHelper.getInstance().toHexString(serialPortBytes.getBatteryVoltage_8_15()));
//                        SerialPortLog.outI("电池循环次数", StringFormatHelper.getInstance().toHexString(serialPortBytes.getLoopCount()));
//                        SerialPortLog.outI("电池满电容量", StringFormatHelper.getInstance().toHexString(serialPortBytes.getFullCapatity()));
//                        SerialPortLog.outI("电池剩余容量", StringFormatHelper.getInstance().toHexString(serialPortBytes.getRemainingCapatity()));
//                        SerialPortLog.outI("电池绝对容量百分比", StringFormatHelper.getInstance().toHexString(serialPortBytes.getAbsoluteCapatityPercent()));
//                        SerialPortLog.outI("电池相对容量百分比", StringFormatHelper.getInstance().toHexString(serialPortBytes.getRelativeCapatityPercent()));
//                        SerialPortLog.outI("电池实时电流", StringFormatHelper.getInstance().toHexString(serialPortBytes.getRealTimeCurrent()));
//                        SerialPortLog.outI("电池组总电压", StringFormatHelper.getInstance().toHexString(serialPortBytes.getBatteryTotalVoltage()));
//                        SerialPortLog.outI("电池温度", StringFormatHelper.getInstance().toHexString(serialPortBytes.getBatteryTemperature()));
//                        SerialPortLog.outI("电池综合数据", StringFormatHelper.getInstance().toHexString(serialPortBytes.getBatteryData_08_09_0a_0d_7E()));

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
                                    serialDataListener.onWatch(batteryInfo);
                                }
                            }
                        }

                        int count = 10;
                        while (count > 0)
                        {
                            if (onRwEventConsumer != null)
                            {
                                onRwEventConsumer.onOutEvent("倒计时" + count + "s...");
                            }
                            count--;
                            sleep(1000);
                        }

                        if (onRwEventConsumer != null)
                        {
                            onRwEventConsumer.onOutEvent("clear");
                            sleep(1000);
                        }
                    }
                });
    }

    private void sleep(final long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        SerialPortParserProvider.finish();
    }

    public void setSerialDataListener(SerialDataListener serialDataListener)
    {
        this.serialDataListener = serialDataListener;
    }

    public void setOnRwEventConsumer(OnRwEventConsumer onRwEventConsumer)
    {
        this.onRwEventConsumer = onRwEventConsumer;
    }
}
