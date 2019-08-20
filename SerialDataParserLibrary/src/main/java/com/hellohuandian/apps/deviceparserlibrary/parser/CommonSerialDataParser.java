package com.hellohuandian.apps.deviceparserlibrary.parser;

import com.hellohuandian.apps.SerialPortDataLibrary.models.info.BatteryInfo;
import com.hellohuandian.apps.SerialPortDataLibrary.models.data.SerialPortData;
import com.hellohuandian.apps.deviceparserlibrary.base.BaseSerialDataParser;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-30
 * Description: 解析器
 */
public class CommonSerialDataParser extends BaseSerialDataParser
{
    private final byte[] START_CONDITION_CODE = {0x3A, 0x16};
    private final byte[] LAST_CONDITION_CODE = {0x0D, 0x0A};
    //数据最小长度必须是9
    private final int MIN_DATA_LEN = 9;

    private StringBuilder stringBuilder = new StringBuilder();

    private boolean checkConditionCode(byte[] serialPortData)
    {
        if (serialPortData != null && serialPortData.length >= MIN_DATA_LEN)
        {
            for (int i = 0; i < START_CONDITION_CODE.length; i++)
            {
                if (START_CONDITION_CODE[i] != (serialPortData[i] & 0xFF))
                {
                    return false;
                }
            }
            for (int i = 0; i < LAST_CONDITION_CODE.length; i++)
            {
                if (LAST_CONDITION_CODE[i] != (serialPortData[serialPortData.length - LAST_CONDITION_CODE.length + i] & 0xFF))
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

            int contentLen = serialPortData[3] & 0xFF;//index=3代表数据内容的长度
            if (contentLen >= 1)
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

    @Override
    public BatteryInfo parse(SerialPortData serialPortBytes)
    {
        batteryInfo.setBatteryTemperature(parseBatteryTemperature(serialPortBytes.getBatteryTemperature()));
        batteryInfo.setBatteryTotalVoltage(parseBatteryTotalVoltage(serialPortBytes.getBatteryTotalVoltage()));
        batteryInfo.setRealTimeCurrent(parseRealTimeCurrent(serialPortBytes.getRealTimeCurrent()));
        batteryInfo.setAbsoluteCapatityPercent(parseRelativeCapatityPercent(serialPortBytes.getAbsoluteCapatityPercent()));
        batteryInfo.setRelativeCapatityPercent(parseAbsoluteCapatityPercent(serialPortBytes.getRelativeCapatityPercent()));
        batteryInfo.setRemainingCapatity(parseRemainingCapatity(serialPortBytes.getRemainingCapatity()));
        batteryInfo.setFullCapatity(parseFullCapatity(serialPortBytes.getFullCapatity()));
        batteryInfo.setLoopCount(parseLoopCount(serialPortBytes.getLoopCount()));
        batteryInfo.setBatteryVoltage_1_7(parseBatteryVoltage_1_7(serialPortBytes.getBatteryVoltage_1_7()));
        batteryInfo.setBatteryVoltage_8_15(parseBatteryVoltage_8_15(serialPortBytes.getBatteryVoltage_8_15()));
        batteryInfo.setSoh(parseSOH(serialPortBytes.getSoh()));
        if (batteryVersion != null)
        {
            batteryInfo.setBatteryIdCode(batteryVersion.getSerialNumber());
        }
        batteryInfo.setManufacturer(parseManufacturer(serialPortBytes.getManufacturer()));
        batteryInfo.setVersion(parseVersion(serialPortBytes.getVersion()));
        batteryInfo.setBatteryIdCheckCode(parseBatteryIdCheckCode(serialPortBytes.getBatteryIdCheckCode()));
        batteryInfo.setBatteryComprehensiveInfo(parseBatteryComprehensiveInfo(serialPortBytes.getBatteryData_08_09_0a_0d_7E()));

        return batteryInfo.clone();
    }


    /**
     * 解析温度
     * 读取温度命令，返回数据区前 2 字节组成的整数字为温度数据，即 byte0、byte1 代表电池表面温 度，单位为开尔文的温度数值，分辨率 0.1K。
     * 转换为摄氏度的算法如下实例
     * 如读取的数据为 0xb90 0xb90=2960 2960-2731=229=22.9 度
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseBatteryTemperature(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 2)
            {
                int value = ((serialPortData[4] & 0xFF)) + (serialPortData[5] << 8 & 0xFFFF);
                value -= 2731;
                stringBuilder.append((float) value / 10);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析电压
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseBatteryTotalVoltage(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen > 0)
            {
                int value = 0;
                for (int i = 4, j = 0; j < dataContentLen; i++, j++)
                {
                    value += (serialPortData[i] << (j * 8) & (0xFF << j * 8));
                }
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 解析电流
     * 读取电流命令，返回数据区前 2 字节的整数字为电流数值，该数值为有符号的数据，最高位是符 号位。单位为 mA。
     * 如，放电电流 20A 20000=0x4e20 其补码为 ~0x4e20+1=0xB1E0 注:
     * 该电流数值为之前 5.12S 时间范围的电流数值的平均值。
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseRealTimeCurrent(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 2)
            {
                int value = (serialPortData[4] << 8 & 0xFFFF) + ((serialPortData[5] & 0xFF));
                if (value >= 0x4e20)
                {
                    value = (~value + 1);
                }
                stringBuilder.append((short) value);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析荷电态
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseRelativeCapatityPercent(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 1)
            {
                stringBuilder.append(serialPortData[4] & 0xFF);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析荷电态
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseAbsoluteCapatityPercent(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 1)
            {
                stringBuilder.append(serialPortData[4] & 0xFF);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析剩余容量
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseRemainingCapatity(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 2)
            {
                stringBuilder.append((serialPortData[4] & 0xFF) + (serialPortData[5] << 8 & 0xFFFF));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析满充容量
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseFullCapatity(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 2)
            {
                stringBuilder.append((serialPortData[4] & 0xFF) + (serialPortData[5] << 8 & 0xFFFF));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析电池循环次数
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseLoopCount(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 2)
            {
                stringBuilder.append(((serialPortData[4] & 0xFF) << 8) + ((serialPortData[5] & 0xFFFF)));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析电芯电压1-7节
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseBatteryVoltage_1_7(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 14)
            {
                final int loopCount = dataContentLen / 2 - 1;
                int i = 4;
                for (int j = 0; j < loopCount; i += 2, j++)
                {
                    int value = ((serialPortData[i] & 0xFF)) + ((serialPortData[i + 1] << 8 & 0xFFFF));
                    stringBuilder.append(value);
                    stringBuilder.append("-");
                }

                int value = ((short) (serialPortData[i] & 0xFF)) + (serialPortData[i + 1] << 8 & 0xFFFF);
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析电芯电压8-15节
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseBatteryVoltage_8_15(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 16)
            {
                final int loopCount = dataContentLen / 2 - 1;
                int i = 4;
                for (int j = 0; j < loopCount; i += 2, j++)
                {
                    int value = ((serialPortData[i] & 0xFF)) + (serialPortData[i + 1] << 8 & 0xFFFF);
                    stringBuilder.append(value);
                    stringBuilder.append("-");
                }

                int value = ((serialPortData[i] & 0xFF)) + (serialPortData[i + 1] << 8 & 0xFFFF);
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析电池健康百分比
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseSOH(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen >= 1)
            {
                stringBuilder.append(serialPortData[4] & 0xFF);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析制造商
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseManufacturer(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            int contentLen = serialPortData[3] & 0xFF;
            for (int i = 4, j = 0; j < contentLen; i++, j++)
            {
                stringBuilder.append((char) serialPortData[i]);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 解析版本
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseVersion(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            int softrwareVersion = serialPortData[5];
            stringBuilder.append("softrwareVersion V" + (softrwareVersion & 0xFF));
            stringBuilder.append(",");
            int headwareVersion = serialPortData[6];
            stringBuilder.append("hardwareVersion V" + (headwareVersion & 0xFF));

        }
        return stringBuilder.toString();
    }

    /**
     * 解析电池 ID 校验码
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseBatteryIdCheckCode(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            for (int i = 4, j = 0; j < dataContentLen; i++, j++)
            {
                stringBuilder.append(serialPortData[i] & 0xFF);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * 解析电池 ID 校验码
     *
     * @param serialPortData
     *
     * @return
     */
    private String parseBatteryComprehensiveInfo(byte[] serialPortData)
    {
        stringBuilder.setLength(0);
        if (checkConditionCode(serialPortData) && checkSum(serialPortData))
        {
            final int dataContentLen = serialPortData[3] & 0xFF;
            if (dataContentLen == 23)
            {
                //解析温度
                int value = (serialPortData[4] & 0xFF) + (serialPortData[5] << 8 & 0xFFFF) - 2731;
                stringBuilder.append((float) value / 10).append("(°C)").append("-");

                //解析电压
                value = (serialPortData[6] & 0xFF) + (serialPortData[7] << 8 & 0xFFFF);
                stringBuilder.append(value).append("(mV)").append("-");

                //解析电流
                value = (serialPortData[8] & 0xFF) + (serialPortData[9] << 8 & 0xFFFF);
                stringBuilder.append(value).append("(mA)").append("-");

                //解析SOC
                value = serialPortData[10] & 0xFF;
                stringBuilder.append(value).append("%").append("-");

                //解析条码
                for (int i = 11, j = 0; j < 16; i++, j++)
                {
                    stringBuilder.append((char) serialPortData[i]);
                }
            }
        }
        return stringBuilder.toString();
    }
}
