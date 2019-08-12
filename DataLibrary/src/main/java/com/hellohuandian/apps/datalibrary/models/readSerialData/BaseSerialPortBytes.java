package com.hellohuandian.apps.datalibrary.models.readSerialData;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public class BaseSerialPortBytes
{
    //电池组 ID 条码
    private byte[] batteryIdCode;

    public byte[] getBatteryIdCode()
    {
        return batteryIdCode;
    }

    public void setBatteryIdCode(byte[] batteryIdCode)
    {
        this.batteryIdCode = batteryIdCode;
    }
}
