package com.HelloHuanDian.apps.test.modules.main.fragments;

import android.os.Bundle;

import com.HelloHuanDian.apps.test.R;
import com.HelloHuanDian.apps.test.modules.main.fragments.adapter.BatteryInfoAdapter;
import com.HelloHuanDian.apps.test.base.fragments.AppBaseFragment;
import com.HelloHuanDian.apps.test.modules.main.viewmodel.BatteryViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-07
 * Description: 电池表格监视器，显示电池格式化的信息
 */
public class BatteryMonitorFragment extends AppBaseFragment
{
    @BindView(R.id.rv_BatteryInfos)
    RecyclerView rvBatteryInfos;

    private BatteryInfoAdapter batteryInfoAdapter;

    @Override
    protected int getLayoutID()
    {
        return R.layout.fragment_battery_monitor;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        batteryInfoAdapter = new BatteryInfoAdapter(getContext());
        rvBatteryInfos.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvBatteryInfos.setAdapter(batteryInfoAdapter);

        BatteryViewModel batteryViewModel = ViewModelProviders.of(getActivity()).get(BatteryViewModel.class);

        if (batteryViewModel.batteryMonitorLiveData != null)
        {
            batteryViewModel.batteryMonitorLiveData.observe(this, batteryData -> {
                System.out.println("控制板ID：" + batteryData.getBatteryData().getControllerAddressId());
                System.out.println("当前温度：" + batteryData.getBatteryData().getBatteryTemperature() + "°C");
                System.out.println("当前电压：" + batteryData.getBatteryData().getBatteryTotalVoltage() + "mV");
                System.out.println("当前电流：" + batteryData.getBatteryData().getRealTimeCurrent() + "mA");
                System.out.println("电池荷电态相对百分比：" + batteryData.getBatteryData().getRelativeCapatityPercent() + "%");
                System.out.println("电池荷电态绝对百分比：" + batteryData.getBatteryData().getAbsoluteCapatityPercent() + "%");
                System.out.println("电池剩余容量：" + batteryData.getBatteryData().getRemainingCapatity() + "mAh");
                System.out.println("电池满冲容量：" + batteryData.getBatteryData().getFullCapatity() + "mAh");
                System.out.println("电池循环次数：" + batteryData.getBatteryData().getLoopCount());
                System.out.println("电芯电压1-7：" + batteryData.getBatteryData().getBatteryVoltage_1_7());
                System.out.println("电芯电压8-15：" + batteryData.getBatteryData().getBatteryVoltage_8_15());
                System.out.println("SOH：" + batteryData.getBatteryData().getSoh() + "%");
                System.out.println("制造商：" + batteryData.getBatteryData().getManufacturer());
                System.out.println("电池ID条码：" + batteryData.getBatteryData().getBatteryIdCode());
                System.out.println("版本：" + batteryData.getBatteryData().getVersion());
                System.out.println("电池 ID 校验码：" + batteryData.getBatteryData().getBatteryIdCheckCode());
                System.out.println("综合数据：" + batteryData.getBatteryData().getBatteryComprehensiveInfo());

                batteryInfoAdapter.addElement(batteryData);
            });
        }
    }
}
