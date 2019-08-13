package com.HelloHuanDian.apps.test.modules.main;

import android.content.Intent;
import android.os.Bundle;

import com.HelloHuanDian.apps.test.R;
import com.HelloHuanDian.apps.test.base.activities.AppBaseActivity;
import com.HelloHuanDian.apps.test.modules.main.viewmodel.BatteryViewModel;
import com.HelloHuanDian.apps.test.service.SerialPortWatchService;

import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppBaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelProviders.of(this).get(BatteryViewModel.class);
        startService(new Intent(this, SerialPortWatchService.class));

    }
}
