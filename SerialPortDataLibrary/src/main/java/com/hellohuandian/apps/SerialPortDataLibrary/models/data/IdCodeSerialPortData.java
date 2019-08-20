package com.hellohuandian.apps.SerialPortDataLibrary.models.data;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public class IdCodeSerialPortData
{
    protected final byte[] EMPTY_Data = new byte[0];

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

    protected void reset()
    {
        batteryIdCode = EMPTY_Data;
    }
}
