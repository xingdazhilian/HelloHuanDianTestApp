package com.hellohuandian.apps.deviceparserlibrary;

import android.text.TextUtils;

import com.hellohuandian.apps.deviceparserlibrary.base.BaseSerialDataParser;
import com.hellohuandian.apps.deviceparserlibrary.parser.CommonSerialDataParser;
import com.hellohuandian.apps.deviceparserlibrary.parser.ParserVersionTable;
import com.hellohuandian.apps.deviceparserlibrary.version.BatteryVersion;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-07-25
 * Description: 串口解析器提供者，提供不同版本的解析器
 */
public final class SerialPortParserProvider
{
    private static final Map<String, BaseSerialDataParser> DATA_PARSER_MAP = new HashMap<>(ParserVersionTable.VERSION_COUNT);

    public static BaseSerialDataParser match(BatteryVersion batteryVersion)
    {
        if (batteryVersion != null && !TextUtils.isEmpty(batteryVersion.getVersionCode()))
        {
            BaseSerialDataParser serialDataParser = createParser(batteryVersion.getVersionCode());
            if (serialDataParser != null)
            {
                serialDataParser.setBatteryVersion(batteryVersion);
            }
            return serialDataParser;
        }
        return null;
    }

    private static BaseSerialDataParser createParser(String versionCode)
    {
        // TODO: 2019-07-31 为了测试临时赋值写死。
        versionCode = ParserVersionTable.MMM;
        BaseSerialDataParser parser = DATA_PARSER_MAP.get(versionCode);
        if (parser == null)
        {
            switch (versionCode)
            {
                case ParserVersionTable.MMM:
                    parser = new CommonSerialDataParser();
                    DATA_PARSER_MAP.put(versionCode, parser);
                    break;
            }
        }

        return parser;
    }


    public static void finish()
    {
        DATA_PARSER_MAP.clear();
    }
}
