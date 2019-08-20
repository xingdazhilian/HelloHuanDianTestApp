package com.hellohuandian.apps.SerialPortDataLibrary.models.info;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-24
 * Description: 电池信息
 */
public class BatteryInfo implements Cloneable
{
    public transient static final int LOADING = 0;
    public transient static final int FINISH = 1;

    @IntDef({LOADING, FINISH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATUS
    {
    }

    //ID用来区分控制器的标记
    private int controllerAddress;
    private int status = FINISH;

    private String batteryTemperature;
    private String batteryTotalVoltage;
    private String realTimeCurrent;
    private String relativeCapatityPercent;
    private String absoluteCapatityPercent;
    private String remainingCapatity;
    private String fullCapatity;
    private String loopCount;
    private String batteryVoltage_1_7;
    private String batteryVoltage_8_15;
    private String soh;
    private String batteryIdCode;
    private String manufacturer;
    private String version;
    private String batteryIdCheckCode;
    private String batteryComprehensiveInfo;

    @Override
    public BatteryInfo clone()
    {
        Object obj;
        try
        {
            obj = super.clone();
            return obj instanceof BatteryInfo ? (BatteryInfo) obj : null;
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public int getControllerAddress()
    {
        return controllerAddress;
    }

    public void setControllerAddress(int controllerAddress)
    {
        this.controllerAddress = controllerAddress;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(@STATUS int status)
    {
        this.status = status;
    }

    public String getBatteryTemperature()
    {
        return batteryTemperature;
    }

    public void setBatteryTemperature(String batteryTemperature)
    {
        this.batteryTemperature = batteryTemperature;
    }

    public String getBatteryTotalVoltage()
    {
        return batteryTotalVoltage;
    }

    public void setBatteryTotalVoltage(String batteryTotalVoltage)
    {
        this.batteryTotalVoltage = batteryTotalVoltage;
    }

    public String getRealTimeCurrent()
    {
        return realTimeCurrent;
    }

    public void setRealTimeCurrent(String realTimeCurrent)
    {
        this.realTimeCurrent = realTimeCurrent;
    }

    public String getRelativeCapatityPercent()
    {
        return relativeCapatityPercent;
    }

    public void setRelativeCapatityPercent(String relativeCapatityPercent)
    {
        this.relativeCapatityPercent = relativeCapatityPercent;
    }

    public String getAbsoluteCapatityPercent()
    {
        return absoluteCapatityPercent;
    }

    public void setAbsoluteCapatityPercent(String absoluteCapatityPercent)
    {
        this.absoluteCapatityPercent = absoluteCapatityPercent;
    }

    public String getRemainingCapatity()
    {
        return remainingCapatity;
    }

    public void setRemainingCapatity(String remainingCapatity)
    {
        this.remainingCapatity = remainingCapatity;
    }

    public String getFullCapatity()
    {
        return fullCapatity;
    }

    public void setFullCapatity(String fullCapatity)
    {
        this.fullCapatity = fullCapatity;
    }

    public String getLoopCount()
    {
        return loopCount;
    }

    public void setLoopCount(String loopCount)
    {
        this.loopCount = loopCount;
    }

    public String getBatteryVoltage_1_7()
    {
        return batteryVoltage_1_7;
    }

    public void setBatteryVoltage_1_7(String batteryVoltage_1_7)
    {
        this.batteryVoltage_1_7 = batteryVoltage_1_7;
    }

    public String getBatteryVoltage_8_15()
    {
        return batteryVoltage_8_15;
    }

    public void setBatteryVoltage_8_15(String batteryVoltage_8_15)
    {
        this.batteryVoltage_8_15 = batteryVoltage_8_15;
    }

    public String getSoh()
    {
        return soh;
    }

    public void setSoh(String soh)
    {
        this.soh = soh;
    }

    public String getBatteryIdCode()
    {
        return batteryIdCode;
    }

    public void setBatteryIdCode(String batteryIdCode)
    {
        this.batteryIdCode = batteryIdCode;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getBatteryIdCheckCode()
    {
        return batteryIdCheckCode;
    }

    public void setBatteryIdCheckCode(String batteryIdCheckCode)
    {
        this.batteryIdCheckCode = batteryIdCheckCode;
    }

    public String getBatteryComprehensiveInfo()
    {
        return batteryComprehensiveInfo;
    }

    public void setBatteryComprehensiveInfo(String batteryComprehensiveInfo)
    {
        this.batteryComprehensiveInfo = batteryComprehensiveInfo;
    }

    public void reset()
    {
        status = FINISH;

        batteryTemperature
                = batteryTotalVoltage
                = realTimeCurrent
                = relativeCapatityPercent
                = absoluteCapatityPercent
                = remainingCapatity
                = fullCapatity
                = loopCount
                = batteryVoltage_1_7
                = batteryVoltage_8_15
                = soh
                = batteryIdCode
                = manufacturer
                = version
                = batteryIdCheckCode
                = batteryComprehensiveInfo
                = "";
    }
}
