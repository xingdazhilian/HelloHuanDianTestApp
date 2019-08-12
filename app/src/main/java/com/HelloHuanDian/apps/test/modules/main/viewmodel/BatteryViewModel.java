package com.HelloHuanDian.apps.test.modules.main.viewmodel;

import android.app.Application;

import com.HelloHuanDian.apps.test.service.BatteryInfoWatcher;
import com.HelloHuanDian.apps.test.service.FormatBatteryInfo;
import com.HelloHuanDian.apps.test.service.SerialPortWatchService;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-01
 * Description:
 */
public class BatteryViewModel extends AndroidViewModel implements BatteryInfoWatcher
{
    public final MutableLiveData<FormatBatteryInfo> batteryMonitorLiveData = new MutableLiveData<>();
    public final MutableLiveData<String> batteryControllerDataLiveData = new MutableLiveData<>();

    public BatteryViewModel(@NonNull Application application)
    {
        super(application);
        SerialPortWatchService.BatteryWatcherRegisters.register(BatteryViewModel.this);
    }

    @Override
    public void onWatch(FormatBatteryInfo formatBatteryInfo)
    {
        if (formatBatteryInfo != null)
        {
            batteryMonitorLiveData.setValue(formatBatteryInfo);
        }
    }

    @Override
    public void onControl(String controlData)
    {
        batteryControllerDataLiveData.setValue(controlData);
    }

    @Override
    protected void onCleared()
    {
        SerialPortWatchService.BatteryWatcherRegisters.unRegister(BatteryViewModel.this);
    }
}
