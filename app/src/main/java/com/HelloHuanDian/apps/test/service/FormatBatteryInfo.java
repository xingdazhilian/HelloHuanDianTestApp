package com.HelloHuanDian.apps.test.service;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.hellohuandian.apps.SerialPortDataLibrary.models.info.BatteryInfo;

import androidx.annotation.ColorInt;

import static com.hellohuandian.apps.SerialPortDataLibrary.models.info.BatteryInfo.FINISH;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-01
 * Description:
 */
public class FormatBatteryInfo
{
    private BatteryInfo batteryData;
    private final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();

    private int controllerAddress;
    private int status = FINISH;

    private final int TITLE_COLOR = Color.BLACK;
    private final int EMPTY_DATA_COLOR = Color.RED;
    private final int DATA_COLOR = Color.GREEN;

    public BatteryInfo getBatteryData()
    {
        return batteryData;
    }

    public int getControllerAddress()
    {
        return controllerAddress;
    }

    public int getStatus()
    {
        return status;
    }

    public void format(BatteryInfo batteryData)
    {
        this.batteryData = batteryData;
        spannableStringBuilder.clear();

        if (batteryData != null)
        {
            this.status = batteryData.getStatus();

            this.controllerAddress = batteryData.getControllerAddress();

            addColorSpan(("控制板ID：" + batteryData.getControllerAddress() + "\n"), TITLE_COLOR, 1.2f, true);

            boolean isEmpty = TextUtils.isEmpty(batteryData.getBatteryTemperature());
            String text = isEmpty ? "当前温度：***\n" : "当前温度：" + batteryData.getBatteryTemperature() + "°C\n";
            int color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getBatteryTotalVoltage());
            text = isEmpty ? "当前电压：***\n" : "当前电压：" + batteryData.getBatteryTotalVoltage() + "mV\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getRealTimeCurrent());
            text = isEmpty ? "当前电流：***\n" : "当前电流：" + batteryData.getRealTimeCurrent() + "mA\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getRelativeCapatityPercent());
            text = isEmpty ? "电池荷电态相对百分比：***\n" : "电池荷电态相对百分比：" + batteryData.getRelativeCapatityPercent() + "%\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getAbsoluteCapatityPercent());
            text = isEmpty ? "电池荷电态绝对百分比：***\n" : "电池荷电态绝对百分比：" + batteryData.getAbsoluteCapatityPercent() + "%\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getRemainingCapatity());
            text = isEmpty ? "电池剩余容量：***\n" : "电池剩余容量：" + batteryData.getRemainingCapatity() + "mAh\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getFullCapatity());
            text = isEmpty ? "电池满冲容量：***\n" : "电池满冲容量：" + batteryData.getFullCapatity() + "mAh\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getLoopCount());
            text = isEmpty ? "电池循环次数：***\n" : "电池循环次数：" + batteryData.getLoopCount() + "\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getBatteryVoltage_1_7());
            text = isEmpty ? "电芯电压1-7：***\n" : "电芯电压1-7：" + batteryData.getBatteryVoltage_1_7() + "\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getBatteryVoltage_8_15());
            text = isEmpty ? "电芯电压8-15：***\n" : "电芯电压8-15：" + batteryData.getBatteryVoltage_8_15() + "\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getSoh());
            text = isEmpty ? "SOH：***\n" : "SOH：" + batteryData.getSoh() + "%\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getManufacturer());
            text = isEmpty ? "制造商：***\n" : "制造商：" + batteryData.getManufacturer() + "\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getBatteryIdCode());
            text = isEmpty ? "电池ID条码：***\n" : "电池ID条码：" + batteryData.getBatteryIdCode() + "\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getVersion());
            text = isEmpty ? "版本：***\n" : "版本：" + batteryData.getVersion() + "\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getBatteryIdCheckCode());
            text = isEmpty ? "电池ID校验码：***\n" : "电池ID校验码：" + batteryData.getBatteryIdCheckCode() + "\n";
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);

            isEmpty = TextUtils.isEmpty(batteryData.getBatteryComprehensiveInfo());
            text = isEmpty ? "综合数据：***\n" : "综合数据：" + batteryData.getBatteryComprehensiveInfo();
            color = isEmpty ? EMPTY_DATA_COLOR : DATA_COLOR;
            addColorSpan(text, color);
        }
    }

    private void addColorSpan(String text, @ColorInt int color)
    {
        addColorSpan(text, color, 0);
    }

    private void addColorSpan(String text, @ColorInt int color, float textSize)
    {
        addColorSpan(text, color, textSize, false);
    }

    private void addColorSpan(String text, @ColorInt int color, float textSize, boolean isBold)
    {
        SpannableString spanString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (textSize > 0)
        {
            RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(textSize);
            spanString.setSpan(relativeSizeSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (isBold)
        {
            StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
            spanString.setSpan(styleSpan_B, 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        spannableStringBuilder.append(spanString);
    }


    public SpannableStringBuilder formatString()
    {
        return spannableStringBuilder;
    }
}
