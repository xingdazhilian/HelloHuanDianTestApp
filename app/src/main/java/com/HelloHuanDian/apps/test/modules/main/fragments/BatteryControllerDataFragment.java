package com.HelloHuanDian.apps.test.modules.main.fragments;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import com.HelloHuanDian.apps.test.R;
import com.HelloHuanDian.apps.test.base.fragments.AppBaseFragment;
import com.HelloHuanDian.apps.test.modules.main.viewmodel.BatteryViewModel;
import com.HelloHuanDian.apps.test.widget.VerticalScrollTextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-07
 * Description: 控制板和电池的应答数据的显示
 */
public class BatteryControllerDataFragment extends AppBaseFragment
{
    @BindView(R.id.tv_BatteryControllerData)
    VerticalScrollTextView tvBatteryControllerData;

    @Override
    protected int getLayoutID()
    {
        return R.layout.fragment_controller_data;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        tvBatteryControllerData.setMovementMethod(ScrollingMovementMethod.getInstance());

        BatteryViewModel batteryViewModel = ViewModelProviders.of(getActivity()).get(BatteryViewModel.class);
        if (batteryViewModel.batteryControllerDataLiveData != null)
        {
            batteryViewModel.batteryControllerDataLiveData.observe(this, controlData -> {
                if (controlData.equals("clear"))
                {
                    tvBatteryControllerData.clear();
                } else
                {
                    tvBatteryControllerData.appendText(controlData + "\n");
                }
            });
        }
    }
}
