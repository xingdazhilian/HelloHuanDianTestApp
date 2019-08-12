package com.hellohuandian.apps.utillibrary;

import java.util.List;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description:
 */
public final class StringFormatHelper
{
    private static StringFormatHelper stringFormatHelper;

    private StringBuilder stringBuilder;

    private StringFormatHelper()
    {
        stringBuilder = new StringBuilder();
    }

    public static StringFormatHelper getInstance()
    {
        if (stringFormatHelper == null)
        {
            stringFormatHelper = new StringFormatHelper();
        }
        return stringFormatHelper;
    }

    /**
     * 字节数组转换为16进制字符串
     *
     * @param data
     *
     * @return
     */
    public String toHexString(byte[] data)
    {
        if (stringBuilder != null)
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.delete(0, stringBuilder.length());
            }

            final int len = data.length;
            if (data != null && len > 0)
            {
                for (int i = 0; i < len; i++)
                {
                    String hex = Integer.toHexString(data[i] & 0xFF);
                    if (hex.length() == 1)
                    {
                        hex = '0' + hex;
                    }
                    stringBuilder.append("[").append(hex.toUpperCase()).append("]");
                }
            }

            return stringBuilder.toString();
        }

        return null;
    }

    public String toHexString(List<Byte> data)
    {
        if (stringBuilder != null)
        {
            if (stringBuilder.length() > 0)
            {
                stringBuilder.delete(0, stringBuilder.length());
            }

            final int len = data.size();
            if (data != null && len > 0)
            {
                for (int i = 0; i < len; i++)
                {
                    String hex = Integer.toHexString(data.get(i) & 0xFF);
                    if (hex.length() == 1)
                    {
                        hex = '0' + hex;
                    }
                    stringBuilder.append("[").append(hex.toUpperCase()).append("]");
                }
            }

            return stringBuilder.toString();
        }

        return null;
    }
}
