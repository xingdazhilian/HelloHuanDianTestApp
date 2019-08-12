package com.hellohuandian.apps.deviceparserlibrary.version;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-31
 * Description: 电池序列号表
 */
public final class BatteryVersionTable
{
    public static String match(int id, char key)
    {
        switch (id)
        {
            case BatteryCapacitySpecification.ID:
                return BatteryCapacitySpecification.DATA.get(key);
            case BMS_Factory.ID:
                return BMS_Factory.DATA.get(key);
            case Pack_Factory.ID:
                return Pack_Factory.DATA.get(key);
            case ProductionLine.ID:
                return ProductionLine.DATA.get(key);
            case Batteries_Factory.ID:
                return Batteries_Factory.DATA.get(key);
            case Year.ID:
                return Year.DATA.get(key);
            case Month.ID:
                return Month.DATA.get(key);
            case EnterpriseIdentificationCode.ID:
                return EnterpriseIdentificationCode.DATA.get(key);
            case RatedOutput.ID:
                return RatedOutput.DATA.get(key);
        }
        return null;
    }

    /**
     * 电池规格
     */
    static class BatteryCapacitySpecification
    {
        static final int ID = 0;
        static final Map<Character, String> DATA = new HashMap<>();

        static
        {
            DATA.put('A', "48/12");
            DATA.put('B', "48/12.5");
            DATA.put('C', "48/13");
            DATA.put('D', "48/13.5");
            DATA.put('E', "48/14");
            DATA.put('F', "48/14.5");
            DATA.put('G', "48/15");
            DATA.put('H', "48/15.5");
            DATA.put('I', "48/16");
            DATA.put('J', "48/16.5");
            DATA.put('K', "48/17");
            DATA.put('L', "48/17.5");
            DATA.put('M', "60/16");
        }
    }

    /**
     * BMS生产厂家
     */
    static class BMS_Factory
    {
        static final int ID = 1;
        static final Map<Character, String> DATA = new HashMap<>();

        static
        {
            DATA.put('A', "艾启");
            DATA.put('B', "博强");
            DATA.put('C', "超力源");
            DATA.put('D', "诺万");
            DATA.put('K', "捷敏科");
        }
    }

    /**
     * pack生产厂家
     */
    static class Pack_Factory
    {
        static final int ID = 2;
        static final Map<Character, String> DATA = new HashMap<>();

        static
        {
            DATA.put('A', "华富");
            DATA.put('B', "南都");
            DATA.put('C', "国轩");
            DATA.put('D', "芯驰");
            DATA.put('E', "乐嘉");
            DATA.put('F', "鹏辉");
            DATA.put('G', "沃泰通");
            DATA.put('K', "洁能劲");
        }
    }

    /**
     * 生产线识别码
     */
    static class ProductionLine
    {
        static final int ID = 3;
        static final Map<Character, String> DATA = new HashMap<>();

        static
        {
            DATA.put('A', "华富");
            DATA.put('B', "南都");
            DATA.put('C', "国轩");
            DATA.put('D', "芯驰");
            DATA.put('E', "乐嘉");
            DATA.put('F', "鹏辉");
            DATA.put('G', "沃泰通");
            DATA.put('K', "洁能劲");
        }
    }

    /**
     * 电芯生产厂家
     */
    static class Batteries_Factory
    {
        static final int ID = 4;
        static final Map<Character, String> DATA = new HashMap<>();

        static
        {
            DATA.put('A', "华富");
            DATA.put('B', "南都");
            DATA.put('C', "国轩");
            DATA.put('D', "芯驰");
            DATA.put('E', "乐嘉");
            DATA.put('F', "鹏辉");
            DATA.put('G', "沃泰通");
            DATA.put('K', "洁能劲");
        }
    }

    static class Year
    {
        static final int ID = 5;
        static final Map<Character, String> DATA = new HashMap<>('J' - 'F' + 1);

        static
        {
            for (int i = 'F', y = 2018; i <= 'J'; i++, y++)
            {
                DATA.put(Character.valueOf((char) i), String.valueOf(y));
            }
        }
    }

    static class Month
    {
        static final int ID = 6;
        static final Map<Character, String> DATA = new HashMap<>('L' - 'A' + 1);

        static
        {
            for (int i = 'A'; i <= 'L'; i++)
            {
                DATA.put(Character.valueOf((char) i), String.valueOf(i % 64));
            }
        }
    }

    static class Day
    {
        static final int ID = 7;
    }

    /**
     * 企业识别码
     */
    static class EnterpriseIdentificationCode
    {
        static final int ID = 9;
        static final Map<Character, String> DATA = new HashMap<>(1);

        static
        {
            DATA.put(Character.valueOf('A'), "哈喽换电");
        }
    }

    /**
     * 额定输出(60V/16A电池)
     */
    static class RatedOutput
    {
        static final int ID = 10;
        static final int ID2 = 11;
        static final Map<Character, String> DATA = new HashMap<>(1);

        static
        {
            DATA.put(Character.valueOf('M'), "60V/16A电池");
        }
    }
}
