package com.hellohuandian.apps.deviceparserlibrary.base;

import com.hellohuandian.apps.datalibrary.models.BatteryData.BatteryInfo;
import com.hellohuandian.apps.datalibrary.models.readSerialData.SerialPortBytes;
import com.hellohuandian.apps.deviceparserlibrary.version.BatteryVersion;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public abstract class BaseSerialDataParser
{
    protected final BatteryVersion batteryVersion = new BatteryVersion();
    protected final BatteryInfo batteryInfo = new BatteryInfo();

    public void setBatteryVersion(BatteryVersion batteryVersion)
    {
        this.batteryVersion.setVersionCode(batteryVersion.getVersionCode());
        this.batteryVersion.setDay(batteryVersion.getDay());
        this.batteryVersion.setRatedOutput(batteryVersion.getRatedOutput());
        this.batteryVersion.setEnterpriseIdentificationCode(batteryVersion.getEnterpriseIdentificationCode());
        this.batteryVersion.setMonth(batteryVersion.getMonth());
        this.batteryVersion.setYear(batteryVersion.getYear());
        this.batteryVersion.setBatteries_Factory(batteryVersion.getBatteries_Factory());
        this.batteryVersion.setProductionLine(batteryVersion.getProductionLine());
        this.batteryVersion.setBMS_Factory(batteryVersion.getBMS_Factory());
        this.batteryVersion.setPack_Factory(batteryVersion.getPack_Factory());
        this.batteryVersion.setBatteryCapacitySpecification(batteryVersion.getBatteryCapacitySpecification());
        this.batteryVersion.setSerialNumber(batteryVersion.getSerialNumber());
    }

    public BatteryVersion getBatteryVersion()
    {
        return batteryVersion;
    }

    public abstract BatteryInfo parse(SerialPortBytes serialPortBytes);
}
