package com.hellohuandian.apps.deviceparserlibrary.version;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-30
 * Description:
 */
public final class BatteryVersion
{
    private String SerialNumber;
    private String BatteryCapacitySpecification;
    private String BMS_Factory;
    private String Pack_Factory;
    private String ProductionLine;
    private String Batteries_Factory;
    private String Year;
    private String Month;
    private String Day;
    private String EnterpriseIdentificationCode;
    private String RatedOutput;

    private String VersionCode;

    public String getSerialNumber()
    {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        SerialNumber = serialNumber;
    }

    public String getBatteryCapacitySpecification()
    {
        return BatteryCapacitySpecification;
    }

    public void setBatteryCapacitySpecification(String batteryCapacitySpecification)
    {
        BatteryCapacitySpecification = batteryCapacitySpecification;
    }

    public String getBMS_Factory()
    {
        return BMS_Factory;
    }

    public void setBMS_Factory(String BMS_Factory)
    {
        this.BMS_Factory = BMS_Factory;
    }

    public String getPack_Factory()
    {
        return Pack_Factory;
    }

    public void setPack_Factory(String pack_Factory)
    {
        Pack_Factory = pack_Factory;
    }

    public String getProductionLine()
    {
        return ProductionLine;
    }

    public void setProductionLine(String productionLine)
    {
        ProductionLine = productionLine;
    }

    public String getBatteries_Factory()
    {
        return Batteries_Factory;
    }

    public void setBatteries_Factory(String batteries_Factory)
    {
        Batteries_Factory = batteries_Factory;
    }

    public String getYear()
    {
        return Year;
    }

    public void setYear(String year)
    {
        Year = year;
    }

    public String getMonth()
    {
        return Month;
    }

    public void setMonth(String month)
    {
        Month = month;
    }

    public String getDay()
    {
        return Day;
    }

    public void setDay(String day)
    {
        Day = day;
    }

    public String getEnterpriseIdentificationCode()
    {
        return EnterpriseIdentificationCode;
    }

    public void setEnterpriseIdentificationCode(String enterpriseIdentificationCode)
    {
        EnterpriseIdentificationCode = enterpriseIdentificationCode;
    }

    public String getRatedOutput()
    {
        return RatedOutput;
    }

    public void setRatedOutput(String ratedOutput)
    {
        RatedOutput = ratedOutput;
    }

    public String getVersionCode()
    {
        return VersionCode;
    }

    public void setVersionCode(String versionCode)
    {
        VersionCode = versionCode;
    }
}
