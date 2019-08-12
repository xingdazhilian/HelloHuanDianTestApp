package com.hellohuandian.apps.deviceparserlibrary.version;

import android.text.TextUtils;

import com.hellohuandian.apps.datalibrary.models.readSerialData.BaseSerialPortBytes;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-29
 * Description: 版本解析器，先解析电池版本数据，为了匹配对应的数据解析器
 */
public final class VersionParser
{
    private final byte[] START_CONDITION_CODE = {0x3A, 0x16};
    private final byte[] LAST_CONDITION_CODE = {0x0D, 0x0A};
    //数据最小长度必须是9
    private final int MIN_DATA_LEN = 9;

    private BatteryVersion batteryVersion = new BatteryVersion();
    private final StringBuilder stringBuilder = new StringBuilder();

    public BatteryVersion match(BaseSerialPortBytes baseSerialPortBytes)
    {
        if (baseSerialPortBytes != null)
        {
            byte[] batteryIdCode = baseSerialPortBytes.getBatteryIdCode();
            if (batteryIdCode != null && checkConditionCode(batteryIdCode) && checkSum(batteryIdCode))
            {
                if (isDataContentLen16(batteryIdCode))
                {
                    parse(batteryIdCode);
                    return batteryVersion;
                }
            }
        }

        return null;
    }

    private void parse(byte[] data)
    {
        stringBuilder.setLength(0);

        char v;
        for (int i = 4, j = 0; j < 16; i++, j++)
        {
            v = (char) data[i];
            stringBuilder.append(v);

            switch (j)
            {
                case BatteryVersionTable.BatteryCapacitySpecification.ID:
                    batteryVersion.setBatteryCapacitySpecification(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.BMS_Factory.ID:
                    batteryVersion.setBMS_Factory(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.Pack_Factory.ID:
                    batteryVersion.setPack_Factory(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.ProductionLine.ID:
                    batteryVersion.setProductionLine(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.Batteries_Factory.ID:
                    batteryVersion.setBatteries_Factory(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.Year.ID:
                    batteryVersion.setYear(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.Month.ID:
                    batteryVersion.setMonth(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.Day.ID:
                    batteryVersion.setDay(String.valueOf(v));
                    break;
                case BatteryVersionTable.EnterpriseIdentificationCode.ID:
                    batteryVersion.setEnterpriseIdentificationCode(BatteryVersionTable.match(j, v));
                    break;
                case BatteryVersionTable.RatedOutput.ID:
//                case BatteryVersionTable.RatedOutput.ID2:
                    batteryVersion.setRatedOutput(BatteryVersionTable.match(j, v));
                    break;
            }
        }

        final String serialNumber = stringBuilder.toString();
        batteryVersion.setSerialNumber(serialNumber);
        if (!TextUtils.isEmpty(serialNumber) && serialNumber.length() == 16)
        {
            stringBuilder.setLength(0);
            stringBuilder.append(serialNumber.charAt(BatteryVersionTable.BatteryCapacitySpecification.ID))
                    .append(serialNumber.charAt(BatteryVersionTable.RatedOutput.ID))
                    .append(serialNumber.charAt(BatteryVersionTable.RatedOutput.ID2));
            batteryVersion.setVersionCode(stringBuilder.toString());
        }
    }

    private boolean isDataContentLen16(byte[] data)
    {
        return (data != null && data.length > 3) ? data[3] == 16 : false;
    }

    private boolean checkConditionCode(byte[] serialPortData)
    {
        if (serialPortData != null && serialPortData.length >= MIN_DATA_LEN)
        {
            for (int i = 0; i < START_CONDITION_CODE.length; i++)
            {
                if (START_CONDITION_CODE[i] != serialPortData[i])
                {
                    return false;
                }
            }
            for (int i = 0; i < LAST_CONDITION_CODE.length; i++)
            {
                if (LAST_CONDITION_CODE[i] != serialPortData[serialPortData.length - LAST_CONDITION_CODE.length + i])
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    private boolean checkSum(byte[] serialPortData)
    {
        if (serialPortData != null && serialPortData.length >= MIN_DATA_LEN)
        {
            short sum = 0;
            for (int i = 1; i <= 3; i++)
            {
                sum += (short) (serialPortData[i] & 0xFF);
            }

            //校验位数组最小下标
            int sumIndex = 4;

            int contentLen = serialPortData[3];//index=3代表数据内容的长度
            if (contentLen > 1)
            {
                for (int k = 4, j = 0; j < contentLen; k++, j++, sumIndex++)
                {
                    sum += (serialPortData[k] & 0xFF);
                }

                byte sumL = (byte) (sum & 0x00FF);
                byte sumH = (byte) (sum >> 8);
                if (serialPortData[sumIndex++] == sumL && serialPortData[sumIndex] == sumH)
                {
                    return true;
                }
            }

        }
        return false;
    }
}
