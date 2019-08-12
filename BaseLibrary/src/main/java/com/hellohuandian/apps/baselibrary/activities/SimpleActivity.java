package com.hellohuandian.apps.baselibrary.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-07
 * Description:
 */
public class SimpleActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        if (isHideActionBar() && getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
    }

    protected boolean isHideActionBar()
    {
        return true;
    }
}
