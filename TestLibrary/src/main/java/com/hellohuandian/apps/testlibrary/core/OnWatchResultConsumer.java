package com.hellohuandian.apps.testlibrary.core;

import com.hellohuandian.apps.datalibrary.models.BatteryData.BatteryInfo;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-12
 * Description:
 */
public interface OnWatchResultConsumer
{
    void onWatch(BatteryInfo batteryInfo);
}
