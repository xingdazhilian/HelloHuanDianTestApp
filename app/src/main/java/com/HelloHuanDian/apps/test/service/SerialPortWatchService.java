package com.HelloHuanDian.apps.test.service;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.SparseArray;

import com.hellohuandian.apps.SerialPortDataLibrary.models.info.BatteryInfo;
import com.hellohuandian.apps.testlibrary.TestService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public class SerialPortWatchService extends TestService
{
    private final Handler mWatcherHandle = new Handler();

    private volatile boolean isWatch = true;

    private final SparseArray<FormatBatteryInfo> formatBatteryInfoSparseArray = new SparseArray<>();

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onWatch(BatteryInfo batteryData)
    {
//        System.out.println("当前温度：" + batteryData.getBatteryTemperature() + "°C");
//        System.out.println("当前电压：" + batteryData.getBatteryTotalVoltage() + "mV");
//        System.out.println("当前电流：" + batteryData.getRealTimeCurrent() + "mA");
//        System.out.println("电池荷电态相对百分比：" + batteryData.getRelativeCapatityPercent() + "%");
//        System.out.println("电池荷电态绝对百分比：" + batteryData.getAbsoluteCapatityPercent() + "%");
//        System.out.println("电池剩余容量：" + batteryData.getRemainingCapatity() + "mAh ");
//        System.out.println("电池满冲容量：" + batteryData.getFullCapatity() + "mAh ");
//        System.out.println("电池循环次数：" + batteryData.getLoopCount());
//        System.out.println("电芯电压1-7：" + batteryData.getBatteryVoltage_1_7());
//        System.out.println("电芯电压8-15：" + batteryData.getBatteryVoltage_8_15());
//        System.out.println("SOH：" + batteryData.getSoh() + "%");
//        System.out.println("制造商：" + batteryData.getManufacturer());
//        System.out.println("版本：" + batteryData.getVersion());
//        System.out.println("电池 ID 校验码：" + batteryData.getBatteryIdCheckCode());

        if (isWatch)
        {
            try
            {
                Observable.just(batteryData)
                        .map(batteryInfo -> {
                            FormatBatteryInfo formatBatteryInfo = formatBatteryInfoSparseArray.get(batteryData.getControllerAddress());
                            if (formatBatteryInfo == null)
                            {
                                formatBatteryInfo = new FormatBatteryInfo();
                                formatBatteryInfoSparseArray.put(batteryData.getControllerAddress(), formatBatteryInfo);
                            }
                            return formatBatteryInfo;
                        }).subscribe(formatBatteryInfo -> {
                    formatBatteryInfo.format(batteryData);

                    mWatcherHandle.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            BatteryWatcherRegisters.onWatch(formatBatteryInfo);
                            postString("clear");
                        }
                    });
                });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void postString(String str)
    {
        if (isMainThread())
        {
            BatteryWatcherRegisters.onWatch(str);
        } else
        {
            mWatcherHandle.post(() -> BatteryWatcherRegisters.onWatch(str));
        }
    }

    @Override
    public void onWrite(String data)
    {
        postString(data);
    }

    @Override
    public void onRead(String data)
    {
        postString(data);
    }

    public static class BatteryWatcherRegisters
    {
        private static final Set<BatteryInfoWatcher> BATTERY_INFO_WATCHERS = new HashSet<>();

        static void onWatch(FormatBatteryInfo batteryData)
        {
            Iterator<BatteryInfoWatcher> infoWatcherIterator = BATTERY_INFO_WATCHERS.iterator();
            while (infoWatcherIterator.hasNext())
            {
                BatteryInfoWatcher batteryInfoWatcher = infoWatcherIterator.next();
                if (batteryInfoWatcher != null)
                {
                    batteryInfoWatcher.onWatch(batteryData);
                }
            }
        }

        static void onWatch(String controlData)
        {
            Iterator<BatteryInfoWatcher> infoWatcherIterator = BATTERY_INFO_WATCHERS.iterator();
            while (infoWatcherIterator.hasNext())
            {
                BatteryInfoWatcher batteryInfoWatcher = infoWatcherIterator.next();
                if (batteryInfoWatcher != null)
                {
                    batteryInfoWatcher.onControl(controlData);
                }
            }
        }

        public static void register(BatteryInfoWatcher batteryInfoWatcher)
        {
            if (batteryInfoWatcher != null)
            {
                BATTERY_INFO_WATCHERS.add(batteryInfoWatcher);
            }
        }

        public static void unRegister(BatteryInfoWatcher batteryInfoWatcher)
        {
            if (batteryInfoWatcher != null)
            {
                BATTERY_INFO_WATCHERS.remove(batteryInfoWatcher);
            }
        }
    }

    private boolean isMainThread()
    {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        isWatch = false;
        mWatcherHandle.removeCallbacksAndMessages(null);
        BatteryWatcherRegisters.BATTERY_INFO_WATCHERS.clear();
    }
}
