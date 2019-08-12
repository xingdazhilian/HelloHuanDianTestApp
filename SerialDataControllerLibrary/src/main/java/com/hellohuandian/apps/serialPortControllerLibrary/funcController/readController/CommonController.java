package com.hellohuandian.apps.serialPortControllerLibrary.funcController.readController;

import android.util.SparseArray;

import com.android.SerialPort.SerialPortDevice;
import com.hellohuandian.apps.configstrategylibrary.table.BatteryStrategy;
import com.hellohuandian.apps.configstrategylibrary.table.ControlAddressTable;
import com.hellohuandian.apps.configstrategylibrary.table.ControllerStrategy;
import com.hellohuandian.apps.datalibrary.models.readSerialData.SerialPortBytes;
import com.hellohuandian.apps.serialPortControllerLibrary.base.controller.EventStrategyController;
import com.hellohuandian.apps.serialportutillibrary.SerialPortLog;
import com.hellohuandian.apps.utillibrary.StringFormatHelper;

import androidx.core.util.Consumer;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description: 通用读取控制器
 */
public class CommonController extends EventStrategyController<CommonStrategy>
{
    private final String writeFormat = ">";
    private final String readFormat = "<";

    private final String TAG = "串口请求";

    private final SerialPortBytes commonSerialPortBytes = new SerialPortBytes();

    private final Consumer<CommonStrategy> commonStrategyConsumer = commonStrategy -> {
        onSwitchWrite(commonStrategy);
        sleep(commonStrategy.waitTime);
        onSwitchRead(commonStrategy);
    };

    public CommonController(SerialPortDevice serialPortDevice, ControlAddressTable.Address address)
    {
        super(serialPortDevice, address);

        commonSerialPortBytes.setControllerAddressId(address.id);

        // TODO: 2019-07-27 设置控制器对应控制板的访问地址
        SparseArray<byte[]> controlCmds = ControllerStrategy.match(address);
        if (controlCmds != null)
        {
            final int size = controlCmds.size();
            for (int i = 0; i < size; i++)
            {
                addControlStrategy(new CommonStrategy(controlCmds.keyAt(i), controlCmds.valueAt(i)));
            }
        }

        // TODO: 2019-07-27 设置电池访问串口命令
        SparseArray<byte[]> batteryCmds = BatteryStrategy.match();
        if (batteryCmds != null)
        {
            final int size = batteryCmds.size();
            for (int i = 0; i < size; i++)
            {
                addBatteryStrategy(new CommonStrategy(batteryCmds.keyAt(i), batteryCmds.valueAt(i)));
            }
        }
    }

    @Override
    public SerialPortBytes execute()
    {
        // TODO: 2019-07-26 需要先激活485转发模式
        CommonStrategy _485CmdStrategy = obtainControlStrategy(ControllerStrategy.ID._485);

        if (_485CmdStrategy != null && _485CmdStrategy.cmdBytes != null && _485CmdStrategy.cmdBytes.length > 0)
        {
            syncExecute(commonStrategyConsumer, _485CmdStrategy);
        }

        // TODO: 2019-07-26 循环执行策略指令，读取串口数据
        if (TaskStrategyList != null)
        {
            for (CommonStrategy batteryStrategy : TaskStrategyList)
            {
                syncExecute(commonStrategyConsumer, batteryStrategy);
            }
        }

        return commonSerialPortBytes;
    }

    private void onSwitchWrite(CommonStrategy batteryStrategy)
    {
        write(batteryStrategy.cmdBytes);
        String cmdWrite = writeFormat + batteryStrategy.strategyId + ":";

        switch (batteryStrategy.strategyId)
        {
            case ControllerStrategy.ID._485:
                cmdWrite += ":激活485转发模式";
                break;
            case BatteryStrategy.ID.BATTERY_TEMPERATURE:
                cmdWrite += "电池温度";
                break;
            case BatteryStrategy.ID.BATTERY_TOTAL_VOLTAGE:
                cmdWrite += "电池总电压";
                break;
            case BatteryStrategy.ID.REAL_TIME_CUTTENT:
                cmdWrite += "实时电流";
                break;
            case BatteryStrategy.ID.RELATIVE_CAPACITY_PERCENT:
                cmdWrite += "相对容量";
                break;
            case BatteryStrategy.ID.ABSOLUTE_CAPACITY_PERCENT:
                cmdWrite += "绝对容量";
                break;
            case BatteryStrategy.ID.REMAINING_CAPACITY:
                cmdWrite += "剩余容量";
                break;
            case BatteryStrategy.ID.FULL_CAPACITY:
                cmdWrite += "满电容量";
                break;
            case BatteryStrategy.ID.LOOP_COUNT:
                cmdWrite += "循环次数";
                break;
            case BatteryStrategy.ID.BATTERY_VOLTAGE_1__7:
                cmdWrite += "电压1-7";
                break;
            case BatteryStrategy.ID.BATTERY_VOLTAGE_8__15:
                cmdWrite += "电压8-15";
                break;
            case BatteryStrategy.ID.BATTERY_ID_CODE:
                cmdWrite += "电池ID条码";
                break;
            case BatteryStrategy.ID.SOH:
                cmdWrite += "SOH";
                break;
            case BatteryStrategy.ID.MANUFACTURER:
                cmdWrite += "制造商";
                break;
            case BatteryStrategy.ID.VERSION:
                cmdWrite += "版本";
                break;
            case BatteryStrategy.ID.BATTERY_ID_CHECK_CODE:
                cmdWrite += "电池ID校验码";
                break;
            case BatteryStrategy.ID.BATTERY_DATA_08_09_0a_0d_7E:
                cmdWrite += "综合数据";
                break;
        }

        cmdWrite += StringFormatHelper.getInstance().toHexString(batteryStrategy.cmdBytes);
        onWriteEvent(cmdWrite);
        SerialPortLog.outI(TAG, cmdWrite);
    }

    private void onSwitchRead(CommonStrategy batteryStrategy)
    {
        byte[] _readBytes = read();
        String cmdRead = readFormat + batteryStrategy.strategyId + ":";

        switch (batteryStrategy.strategyId)
        {
            case ControllerStrategy.ID._485:
                break;
            case BatteryStrategy.ID.BATTERY_TEMPERATURE:
                commonSerialPortBytes.setBatteryTemperature(_readBytes);
                break;
            case BatteryStrategy.ID.BATTERY_TOTAL_VOLTAGE:
                commonSerialPortBytes.setBatteryTotalVoltage(_readBytes);
                break;
            case BatteryStrategy.ID.REAL_TIME_CUTTENT:
                commonSerialPortBytes.setRealTimeCurrent(_readBytes);
                break;
            case BatteryStrategy.ID.RELATIVE_CAPACITY_PERCENT:
                commonSerialPortBytes.setRelativeCapatityPercent(_readBytes);
                break;
            case BatteryStrategy.ID.ABSOLUTE_CAPACITY_PERCENT:
                commonSerialPortBytes.setAbsoluteCapatityPercent(_readBytes);
                break;
            case BatteryStrategy.ID.REMAINING_CAPACITY:
                commonSerialPortBytes.setRemainingCapatity(_readBytes);
                break;
            case BatteryStrategy.ID.FULL_CAPACITY:
                commonSerialPortBytes.setFullCapatity(_readBytes);
                break;
            case BatteryStrategy.ID.LOOP_COUNT:
                commonSerialPortBytes.setLoopCount(_readBytes);
                break;
            case BatteryStrategy.ID.BATTERY_VOLTAGE_1__7:
                commonSerialPortBytes.setBatteryVoltage_1_7(_readBytes);
                break;
            case BatteryStrategy.ID.BATTERY_VOLTAGE_8__15:
                commonSerialPortBytes.setBatteryVoltage_8_15(_readBytes);
                break;
            case BatteryStrategy.ID.BATTERY_ID_CODE:
                commonSerialPortBytes.setBatteryIdCode(_readBytes);
                break;
            case BatteryStrategy.ID.SOH:
                commonSerialPortBytes.setSoh(_readBytes);
                break;
            case BatteryStrategy.ID.MANUFACTURER:
                commonSerialPortBytes.setManufacturer(_readBytes);
                break;
            case BatteryStrategy.ID.VERSION:
                commonSerialPortBytes.setVersion(_readBytes);
                break;
            case BatteryStrategy.ID.BATTERY_ID_CHECK_CODE:
                commonSerialPortBytes.setBatteryIdCheckCode(_readBytes);
                break;
            case BatteryStrategy.ID.BATTERY_DATA_08_09_0a_0d_7E:
                commonSerialPortBytes.setBatteryData_08_09_0a_0d_7E(_readBytes);
                break;
        }

        cmdRead += StringFormatHelper.getInstance().toHexString(_readBytes);
        onReadEvent(cmdRead);
        SerialPortLog.outI(TAG, cmdRead);
    }

}
