package com.hellohuandian.apps.configstrategylibrary;

import com.hellohuandian.apps.serialportutillibrary.CrcUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-16
 * Description: 控制板策略表
 */
public final class ControlPanelStrategyTable
{
    public static class ID
    {
        public static final int _485 = 0;
        public static final int PUSH_ROD = 100;
    }

    private static class ADDR
    {
        public static final byte _1 = 0x05;
        public static final byte _2 = 0x06;
        public static final byte _3 = 0x07;
        public static final byte _4 = 0x08;
        public static final byte _5 = 0x09;
        public static final byte _6 = 0x0A;
        public static final byte _7 = 0x0B;
        public static final byte _8 = 0x0C;
        public static final byte _9 = 0x0D;
    }

    public static int findIndexByAddr(int addr)
    {
        switch (addr)
        {
            case ADDR._1:
                return 0;
            case ADDR._2:
                return 1;
            case ADDR._3:
                return 2;
            case ADDR._4:
                return 3;
            case ADDR._5:
                return 4;
            case ADDR._6:
                return 5;
            case ADDR._7:
                return 6;
            case ADDR._8:
                return 7;
            case ADDR._9:
                return 8;
        }
        return -1;
    }

    private static class CMD
    {
        private static final byte[] _485 = {ADDRESS_INDEX, 0x05, 0x00, 0x0B, 0x00, 0x01, 0x00, 0x00};
        private static final byte[] PUSH_ROD = {ADDRESS_INDEX, 0x05, 0x00, 0x09, 0x00, 0x03, 0x00, 0x00};
    }

    public static final List<byte[]> ADDRS = new ArrayList<>(ADDR.class.getDeclaredFields().length);
    public static final List<byte[]> PUSH_RODS = new ArrayList<>(ADDR.class.getDeclaredFields().length);
    public static final int ADDRESS_INDEX = 0;

    static
    {
        Field[] addrFieldes = ADDR.class.getDeclaredFields();
        if (addrFieldes != null)
        {
            try
            {
                for (int i = 0, len = addrFieldes.length; i < len; i++)
                {
                    CMD._485[ADDRESS_INDEX] = addrFieldes[i].getByte(ADDR.class);
                    CMD.PUSH_ROD[ADDRESS_INDEX] = addrFieldes[i].getByte(ADDR.class);

                    ADDRS.add(CMD._485.clone());
                    PUSH_RODS.add(CMD.PUSH_ROD.clone());
                }
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }

            crcAddrsFull();
            crcPushRodFull();
        }
    }

    private static void crcAddrsFull()
    {
        if (ADDRS != null)
        {
            byte[] crcData;

            for (byte[] bytes : ADDRS)
            {
                if (bytes != null && bytes.length >= 6)
                {
                    crcData = CrcUtil.crc16(bytes, 6);
                    if (crcData != null && crcData.length == 2)
                    {
                        bytes[bytes.length - 2] = crcData[0];
                        bytes[bytes.length - 1] = crcData[1];
                    }
                }
            }
        }
    }

    private static void crcPushRodFull()
    {
        if (PUSH_RODS != null)
        {
            byte[] crcData;

            for (byte[] bytes : PUSH_RODS)
            {
                if (bytes != null && bytes.length >= 6)
                {
                    crcData = CrcUtil.crc16(bytes, 6);
                    if (crcData != null && crcData.length == 2)
                    {
                        bytes[bytes.length - 2] = crcData[0];
                        bytes[bytes.length - 1] = crcData[1];
                    }
                }
            }
        }
    }
}
