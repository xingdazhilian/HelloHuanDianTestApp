package com.hellohuandian.apps.configstrategylibrary.table;

import android.util.SparseArray;

import com.hellohuandian.apps.serialportutillibrary.CrcUtil;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description: 控制器电路板策略
 */
public final class ControllerStrategy
{
    private static final SparseArray<byte[]> CMDS = new SparseArray<>();
    private static final byte ADDRESS = 0;

    static
    {
        CMDS.put(ID._485, CMD._485);
    }

    public static class ID
    {
        public static final int _485 = 0;
    }

    private static class CMD
    {
        static final byte[] _485 = {ADDRESS, 0x05, 0x00, 0x0B, 0x00, 0x01, 0x00, 0x00};
    }

    /**
     * 匹配存在的控制板地址定义
     *
     * @param address
     *
     * @return
     */
    public static SparseArray<byte[]> match(ControlAddressTable.Address address)
    {
        final int size = CMDS.size();
        if (size >= 1)
        {
            byte[] cmd, crcData;
            for (int i = 0; i < size; i++)
            {
                cmd = CMDS.valueAt(i);
                if (cmd != null && cmd.length >= 6)
                {
                    cmd[0] = address.value;

                    crcData = CrcUtil.crc16(cmd, 6);
                    if (crcData != null && crcData.length == 2)
                    {
                        cmd[cmd.length - 2] = crcData[0];
                        cmd[cmd.length - 1] = crcData[1];
                    }
                }
            }
        }

        return CMDS;
    }
}
