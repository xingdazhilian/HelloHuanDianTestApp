package com.hellohuandian.apps.configstrategylibrary.table;

import android.util.SparseArray;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description: 电池命令策略
 */
public final class BatteryStrategy
{
    private static final SparseArray<byte[]> CMDS = new SparseArray<>();

    static
    {
        CMDS.put(ID.BATTERY_TEMPERATURE, CMD.BATTERY_TEMPERATURE);
        CMDS.put(ID.BATTERY_TOTAL_VOLTAGE, CMD.BATTERY_TOTAL_VOLTAGE);
        CMDS.put(ID.REAL_TIME_CUTTENT, CMD.REAL_TIME_CUTTENT);
        CMDS.put(ID.RELATIVE_CAPACITY_PERCENT, CMD.RELATIVE_CAPACITY_PERCENT);
        CMDS.put(ID.ABSOLUTE_CAPACITY_PERCENT, CMD.ABSOLUTE_CAPACITY_PERCENT);
        CMDS.put(ID.REMAINING_CAPACITY, CMD.REMAINING_CAPACITY);
        CMDS.put(ID.FULL_CAPACITY, CMD.FULL_CAPACITY);
        CMDS.put(ID.LOOP_COUNT, CMD.LOOP_COUNT);
        CMDS.put(ID.BATTERY_VOLTAGE_1__7, CMD.BATTERY_VOLTAGE_1__7);
        CMDS.put(ID.BATTERY_VOLTAGE_8__15, CMD.BATTERY_VOLTAGE_8__15);
        CMDS.put(ID.SOH, CMD.SOH);
        CMDS.put(ID.BATTERY_ID_CODE, CMD.BATTERY_ID_CODE);
        CMDS.put(ID.VERSION, CMD.VERSION);
        CMDS.put(ID.MANUFACTURER, CMD.MANUFACTURER);
        CMDS.put(ID.BATTERY_ID_CHECK_CODE, CMD.BATTERY_ID_CHECK_CODE);
        CMDS.put(ID.BATTERY_DATA_08_09_0a_0d_7E, CMD.BATTERY_DATA_08_09_0a_0d_7E);

    }

    public static class ID
    {
        //电池组内部温度
        public static final int BATTERY_TEMPERATURE = 1;
        //电池组总电压
        public static final int BATTERY_TOTAL_VOLTAGE = 2;
        //实时电流
        public static final int REAL_TIME_CUTTENT = 3;
        //相对容量百分比
        public static final int RELATIVE_CAPACITY_PERCENT = 4;
        //绝对容量百分比
        public static final int ABSOLUTE_CAPACITY_PERCENT = 5;
        //剩余容量
        public static final int REMAINING_CAPACITY = 6;
        //满电容量
        public static final int FULL_CAPACITY = 7;
        //电池循环次数
        public static final int LOOP_COUNT = 8;
        //电池电压1-7节
        public static final int BATTERY_VOLTAGE_1__7 = 9;
        //电池电压8-15节
        public static final int BATTERY_VOLTAGE_8__15 = 10;
        public static final int SOH = 11;
        //电池组ID条码
        public static final int BATTERY_ID_CODE = 12;
        public static final int VERSION = 13;
        //制造商
        public static final int MANUFACTURER = 14;
        //电池 ID 校验码
        public static final int BATTERY_ID_CHECK_CODE = 15;
        //综合数据读取
        public static final int BATTERY_DATA_08_09_0a_0d_7E = 16;

    }

    /**
     * 2.3 数据帧格式
     * 通讯数据帧包含起始标识、地址标识、通讯命令、数据长度、数据内容、校验数据、结束标识等 7 部分组成。其中，
     * 起始标识:单字节，内容为 0X3A，为固定值。
     * 地址标识:单字节，内容为 0X16，为固定值。 通讯命令:单字节，内容为各个通讯命令，具体命令参见第 3 项通讯命令内容。
     * 数据长度: 单字节，内容为该通讯数据帧内数据缓冲区内的数据长度。主机发送数据时，如果不
     * 包含其他数据则统一设置为 1。 数据内容:多字节，内容为具体各个命令对应的数据字节，字节数是不固定的，数量由数据长度
     * 部分的数值确定。主机发送数据时，如无特别命令要求，建议设置为 0。 校验资料:两字节，内容为通讯数据的累加校验和数据，包括从地址标识、命令标识、数据长
     * 度、数据内容的等内容的累加和，低字节在前，高字节在后。 结束标识:两字节，内容为结束标识 1(为固定值 0X0D)和结束标识 2(为固定值 0X0A)。 2.4 主机发送数据帧示例说明
     * 主机向电池组发送读取最大充电时间的命令的数据帧(十六进制)为:
     * 3A 16 08 01 00 1F 00 0D 0A
     * 其中，
     * 3A，为起始标识，单字节，为固定值
     * 16，为地址标识，单字节，代表电池组地址编码
     * 08，为通讯命令，单字节，代表电池温度查询
     * 01, 为数据长度，单字节，代表该通讯数据帧内数据缓冲区内的数据长度，一般统一设置为 1，除
     * 非带着改写命令的信息。 00，为数据内容，多字节，代表数据区内容，如无具体命令的要求，数据区的第 1 字节数值为 0 1F 00，为累加校验和，两字节，低字节为 1F，高字节为 00。累加校验和的具体的计算公式为
     * 16+48+01+00= 001F (十六进制)
     * 0D0A，分别为结束标识 1 和结束标识 2，两字节，为固定值。
     */
    private static class CMD
    {
        static final byte[] BATTERY_TEMPERATURE = {0x3A, 0x16, 0x08, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] BATTERY_TOTAL_VOLTAGE = {0x3A, 0x16, 0x09, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] REAL_TIME_CUTTENT = {0x3A, 0x16, 0x0A, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] RELATIVE_CAPACITY_PERCENT = {0x3A, 0x16, 0x0D, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] ABSOLUTE_CAPACITY_PERCENT = {0x3A, 0x16, 0x0E, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] REMAINING_CAPACITY = {0x3A, 0x16, 0x0F, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] FULL_CAPACITY = {0x3A, 0x16, 0x10, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] LOOP_COUNT = {0x3A, 0x16, 0x17, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] BATTERY_VOLTAGE_1__7 = {0x3A, 0x16, 0x24, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] BATTERY_VOLTAGE_8__15 = {0x3A, 0x16, 0x25, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] SOH = {0x3A, 0x16, 0x0C, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] BATTERY_ID_CODE = {0x3A, 0x16, (byte) 0x7E, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] MANUFACTURER = {0x3A, 0x16, (byte) 0x80, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] VERSION = {0x3A, 0x16, 0x7F, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] BATTERY_ID_CHECK_CODE = {0x3A, 0x16, (byte) 0x81, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
        static final byte[] BATTERY_DATA_08_09_0a_0d_7E = {0x3A, 0x16, (byte) 0xA0, 0x01, 0x00, 0x00, 0x00, 0x0D, 0x0A};
    }

    /**
     * 更新校验和
     */
    public static SparseArray<byte[]> match()
    {
        final int size = CMDS.size();
        if (size > 1)
        {
            byte[] v;
            for (int i = 0; i < size; i++)
            {
                v = CMDS.valueAt(i);
                if (v != null && v.length >= 9)
                {
                    short sum = (short) (v[1] + v[2] + v[3]);
                    final int dataContentLen = v[3];

                    //校验位数组最小下标
                    int sumIndex = 5;
                    if (dataContentLen > 1)
                    {
                        for (int k = 4, j = 0; j < dataContentLen; k++, j++, sumIndex++)
                        {
                            sum += v[k];
                        }
                    }

                    byte sumL = (byte) (sum & 0x00FF);
                    byte sumH = (byte) (sum & 0xFF00);
                    v[sumIndex++] = sumL;
                    v[sumIndex] = sumH;
                }
            }
        }

        return CMDS;
    }
}
