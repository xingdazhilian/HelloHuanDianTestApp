package com.hellohuandian.apps.deviceparserlibrary.base.callBack;

import com.hellohuandian.apps.datalibrary.models.BatteryData.BatteryInfo;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public interface SerialDataListener<T extends BatteryInfo>
{
    void onWatch(T batteryData);
}
