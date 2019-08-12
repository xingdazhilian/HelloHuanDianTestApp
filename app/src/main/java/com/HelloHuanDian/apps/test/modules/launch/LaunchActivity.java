package com.HelloHuanDian.apps.test.modules.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.HelloHuanDian.apps.test.R;
import com.HelloHuanDian.apps.test.base.activities.AppBaseActivity;
import com.HelloHuanDian.apps.test.modules.main.MainActivity;

import androidx.annotation.Nullable;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-07
 * Description:
 */
public class LaunchActivity extends AppBaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        new Handler().postDelayed(() -> startActivity(new Intent(LaunchActivity.this, MainActivity.class)), 3000);
    }

    @Override
    public void onBackPressed()
    {
    }
}
