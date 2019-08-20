package com.hellohuandian.apps.testlibrary.core.action;

import com.hellohuandian.apps.SerialPortDataLibrary.models.info.BatteryInfo;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-17
 * Description:
 */
public interface WatchAction
{
    void onWatch(BatteryInfo batteryInfo);
}
