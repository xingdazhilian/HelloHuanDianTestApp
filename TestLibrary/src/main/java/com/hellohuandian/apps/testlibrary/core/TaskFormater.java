package com.hellohuandian.apps.testlibrary.core;

import com.hellohuandian.apps.ControllerLibrary.base.strategy.TaskStrategy;
import com.hellohuandian.apps.ControllerLibrary.base.strategyController.action.StrategyAction;
import com.hellohuandian.apps.SerialPortDataLibrary.models.data.SerialPortData;
import com.hellohuandian.apps.configstrategylibrary.BatteryStrategyTable;
import com.hellohuandian.apps.configstrategylibrary.ControlPanelStrategyTable;
import com.hellohuandian.apps.testlibrary.core.action.RwAction;
import com.hellohuandian.apps.utillibrary.StringFormatHelper;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-17
 * Description:
 */
public final class TaskFormater implements StrategyAction
{
    private final String writeFormat = ">";
    private final String readFormat = "<";

    private final SerialPortData serialPortData = new SerialPortData();

    private RwAction rwAction;

    @Override
    public void onStrategyWrite(TaskStrategy taskStrategy)
    {
        String cmdWrite = writeFormat + ":";

        switch (taskStrategy.id)
        {
            case ControlPanelStrategyTable.ID._485:
                serialPortData.reset();
                cmdWrite += ":激活485转发模式";
                break;
            case BatteryStrategyTable.ID.BATTERY_TEMPERATURE:
                cmdWrite += "电池温度";
                break;
            case BatteryStrategyTable.ID.BATTERY_TOTAL_VOLTAGE:
                cmdWrite += "电池总电压";
                break;
            case BatteryStrategyTable.ID.REAL_TIME_CUTTENT:
                cmdWrite += "实时电流";
                break;
            case BatteryStrategyTable.ID.RELATIVE_CAPACITY_PERCENT:
                cmdWrite += "相对容量";
                break;
            case BatteryStrategyTable.ID.ABSOLUTE_CAPACITY_PERCENT:
                cmdWrite += "绝对容量";
                break;
            case BatteryStrategyTable.ID.REMAINING_CAPACITY:
                cmdWrite += "剩余容量";
                break;
            case BatteryStrategyTable.ID.FULL_CAPACITY:
                cmdWrite += "满电容量";
                break;
            case BatteryStrategyTable.ID.LOOP_COUNT:
                cmdWrite += "循环次数";
                break;
            case BatteryStrategyTable.ID.BATTERY_VOLTAGE_1__7:
                cmdWrite += "电压1-7";
                break;
            case BatteryStrategyTable.ID.BATTERY_VOLTAGE_8__15:
                cmdWrite += "电压8-15";
                break;
            case BatteryStrategyTable.ID.BATTERY_ID_CODE:
                cmdWrite += "电池ID条码";
                break;
            case BatteryStrategyTable.ID.SOH:
                cmdWrite += "SOH";
                break;
            case BatteryStrategyTable.ID.MANUFACTURER:
                cmdWrite += "制造商";
                break;
            case BatteryStrategyTable.ID.VERSION:
                cmdWrite += "版本";
                break;
            case BatteryStrategyTable.ID.BATTERY_ID_CHECK_CODE:
                cmdWrite += "电池ID校验码";
                break;
            case BatteryStrategyTable.ID.BATTERY_DATA_08_09_0a_0d_7E:
                cmdWrite += "综合数据";
                break;
        }

        cmdWrite += StringFormatHelper.getInstance().toHexString(taskStrategy.taskBytes);
        System.out.println(cmdWrite);

        if (rwAction != null)
        {
            rwAction.onWrite(cmdWrite);
        }
    }

    @Override
    public void onStrategyRead(TaskStrategy taskStrategy, byte[] data)
    {
        String cmdRead = readFormat + ":";

        switch (taskStrategy.id)
        {
            case ControlPanelStrategyTable.ID._485:
                break;
            case BatteryStrategyTable.ID.BATTERY_TEMPERATURE:
                serialPortData.setBatteryTemperature(data);
                break;
            case BatteryStrategyTable.ID.BATTERY_TOTAL_VOLTAGE:
                serialPortData.setBatteryTotalVoltage(data);
                break;
            case BatteryStrategyTable.ID.REAL_TIME_CUTTENT:
                serialPortData.setRealTimeCurrent(data);
                break;
            case BatteryStrategyTable.ID.RELATIVE_CAPACITY_PERCENT:
                serialPortData.setRelativeCapatityPercent(data);
                break;
            case BatteryStrategyTable.ID.ABSOLUTE_CAPACITY_PERCENT:
                serialPortData.setAbsoluteCapatityPercent(data);
                break;
            case BatteryStrategyTable.ID.REMAINING_CAPACITY:
                serialPortData.setRemainingCapatity(data);
                break;
            case BatteryStrategyTable.ID.FULL_CAPACITY:
                serialPortData.setFullCapatity(data);
                break;
            case BatteryStrategyTable.ID.LOOP_COUNT:
                serialPortData.setLoopCount(data);
                break;
            case BatteryStrategyTable.ID.BATTERY_VOLTAGE_1__7:
                serialPortData.setBatteryVoltage_1_7(data);
                break;
            case BatteryStrategyTable.ID.BATTERY_VOLTAGE_8__15:
                serialPortData.setBatteryVoltage_8_15(data);
                break;
            case BatteryStrategyTable.ID.BATTERY_ID_CODE:
                serialPortData.setBatteryIdCode(data);
                break;
            case BatteryStrategyTable.ID.SOH:
                serialPortData.setSoh(data);
                break;
            case BatteryStrategyTable.ID.MANUFACTURER:
                serialPortData.setManufacturer(data);
                break;
            case BatteryStrategyTable.ID.VERSION:
                serialPortData.setVersion(data);
                break;
            case BatteryStrategyTable.ID.BATTERY_ID_CHECK_CODE:
                serialPortData.setBatteryIdCheckCode(data);
                break;
            case BatteryStrategyTable.ID.BATTERY_DATA_08_09_0a_0d_7E:
                serialPortData.setBatteryData_08_09_0a_0d_7E(data);
                break;
        }

        cmdRead += StringFormatHelper.getInstance().toHexString(data);
        System.out.println(cmdRead);
        if (rwAction != null)
        {
            rwAction.onRead(cmdRead);
        }
    }

    public SerialPortData getSerialPortData()
    {
        return serialPortData;
    }

    public void setRwAction(RwAction rwAction)
    {
        this.rwAction = rwAction;
    }
}
