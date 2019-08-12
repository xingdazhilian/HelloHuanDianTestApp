package com.hellohuandian.apps.configstrategylibrary.table;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-27
 * Description: 维护控制板地址，控制板地址版本，和对应ID(匹配电池仓号)
 */
public final class ControlAddressTable
{
    /**
     * 控制板ID
     */
    public class AddressID
    {
        public static final int _1 = 1;
        public static final int _2 = 2;
        public static final int _3 = 3;
        public static final int _4 = 4;
        public static final int _5 = 5;
        public static final int _6 = 6;
        public static final int _7 = 7;
        public static final int _8 = 8;
        public static final int _9 = 9;
    }

    /**
     * 控制板地址
     */
    public static class AddressValue
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

    /**
     * 地址控制板的命令版本，用此标记。不过需要做好对应的实现，去匹配控制板版本
     */
    public static class AddressVersion
    {
        public static final int code_1 = 1;
        public static final int code_2 = 2;
    }

    public static class Address
    {
        public final int id;
        public final byte value;
        public final int version;

        public Address(int id, byte value, int version)
        {
            this.id = id;
            this.value = value;
            this.version = version;
        }
    }

    public static final List<Address> ADDRESSES = new ArrayList<>(AddressValue.class.getDeclaredFields().length);

    static
    {
        ADDRESSES.add(new Address(AddressID._1, AddressValue._1, AddressVersion.code_1));
        ADDRESSES.add(new Address(AddressID._2, AddressValue._2, AddressVersion.code_1));
        ADDRESSES.add(new Address(AddressID._3, AddressValue._3, AddressVersion.code_1));
//        ADDRESSES.add(new Address(AddressID._4, AddressValue._4, AddressVersion.code_1));
//        ADDRESSES.add(new Address(AddressID._5, AddressValue._5, AddressVersion.code_1));
//        ADDRESSES.add(new Address(AddressID._6, AddressValue._6, AddressVersion.code_1));
//        ADDRESSES.add(new Address(AddressID._7, AddressValue._7, AddressVersion.code_1));
//        ADDRESSES.add(new Address(AddressID._8, AddressValue._8, AddressVersion.code_1));
//        ADDRESSES.add(new Address(AddressID._9, AddressValue._9, AddressVersion.code_1));
    }
}
