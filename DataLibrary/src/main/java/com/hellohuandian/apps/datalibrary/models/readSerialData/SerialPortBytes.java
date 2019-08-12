package com.hellohuandian.apps.datalibrary.models.readSerialData;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public class SerialPortBytes extends BaseSerialPortBytes
{
    //控制器地址ID用来区分控制器的标记
    private int controllerAddressId;

    private byte[] batteryTemperature;
    private byte[] batteryTotalVoltage;
    private byte[] realTimeCurrent;
    private byte[] relativeCapatityPercent;
    private byte[] absoluteCapatityPercent;
    private byte[] remainingCapatity;
    private byte[] fullCapatity;
    private byte[] loopCount;
    private byte[] batteryVoltage_1_7;
    private byte[] batteryVoltage_8_15;
    private byte[] soh;
    //电池组 ID 条码字段在父类定义
    private byte[] manufacturer;
    private byte[] version;
    private byte[] batteryIdCheckCode;
    private byte[] batteryData_08_09_0a_0d_7E;

    public int getControllerAddressId()
    {
        return controllerAddressId;
    }

    public void setControllerAddressId(int controllerAddressId)
    {
        this.controllerAddressId = controllerAddressId;
    }

    public byte[] getBatteryIdCheckCode()
    {
        return batteryIdCheckCode;
    }

    public void setBatteryIdCheckCode(byte[] batteryIdCheckCode)
    {
        this.batteryIdCheckCode = batteryIdCheckCode;
    }

    public byte[] getSoh()
    {
        return soh;
    }

    public void setSoh(byte[] soh)
    {
        this.soh = soh;
    }

    public byte[] getBatteryVoltage_1_7()
    {
        return batteryVoltage_1_7;
    }

    public void setBatteryVoltage_1_7(byte[] batteryVoltage_1_7)
    {
        this.batteryVoltage_1_7 = batteryVoltage_1_7;
    }

    public byte[] getBatteryVoltage_8_15()
    {
        return batteryVoltage_8_15;
    }

    public void setBatteryVoltage_8_15(byte[] batteryVoltage_8_15)
    {
        this.batteryVoltage_8_15 = batteryVoltage_8_15;
    }

    public byte[] getLoopCount()
    {
        return loopCount;
    }

    public void setLoopCount(byte[] loopCount)
    {
        this.loopCount = loopCount;
    }

    public byte[] getFullCapatity()
    {
        return fullCapatity;
    }

    public void setFullCapatity(byte[] fullCapatity)
    {
        this.fullCapatity = fullCapatity;
    }

    public byte[] getRemainingCapatity()
    {
        return remainingCapatity;
    }

    public void setRemainingCapatity(byte[] remainingCapatity)
    {
        this.remainingCapatity = remainingCapatity;
    }

    public byte[] getAbsoluteCapatityPercent()
    {
        return absoluteCapatityPercent;
    }

    public void setAbsoluteCapatityPercent(byte[] absoluteCapatityPercent)
    {
        this.absoluteCapatityPercent = absoluteCapatityPercent;
    }

    public byte[] getRelativeCapatityPercent()
    {
        return relativeCapatityPercent;
    }

    public void setRelativeCapatityPercent(byte[] relativeCapatityPercent)
    {
        this.relativeCapatityPercent = relativeCapatityPercent;
    }

    public byte[] getRealTimeCurrent()
    {
        return realTimeCurrent;
    }

    public void setRealTimeCurrent(byte[] realTimeCurrent)
    {
        this.realTimeCurrent = realTimeCurrent;
    }

    public byte[] getBatteryTotalVoltage()
    {
        return batteryTotalVoltage;
    }

    public void setBatteryTotalVoltage(byte[] batteryTotalVoltage)
    {
        this.batteryTotalVoltage = batteryTotalVoltage;
    }

    public byte[] getBatteryTemperature()
    {
        return batteryTemperature;
    }

    public void setBatteryTemperature(byte[] batteryTemperature)
    {
        this.batteryTemperature = batteryTemperature;
    }

    public byte[] getBatteryData_08_09_0a_0d_7E()
    {
        return batteryData_08_09_0a_0d_7E;
    }

    public void setBatteryData_08_09_0a_0d_7E(byte[] batteryData_08_09_0a_0d_7E)
    {
        this.batteryData_08_09_0a_0d_7E = batteryData_08_09_0a_0d_7E;
    }

    public byte[] getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(byte[] manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public byte[] getVersion()
    {
        return version;
    }

    public void setVersion(byte[] version)
    {
        this.version = version;
    }
}
