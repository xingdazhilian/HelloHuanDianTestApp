package com.HelloHuanDian.apps.test.service;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-01
 * Description:
 */
public interface BatteryInfoWatcher
{
    void onWatch(FormatBatteryInfo formatBatteryInfo);

    void onControl(String controlData);
}
