package com.HelloHuanDian.apps.test.base.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hellohuandian.apps.baselibrary.fragments.FunctionFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-07
 * Description:
 */
public abstract class AppBaseFragment extends FunctionFragment
{
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if (unbinder != null)
        {
            unbinder.unbind();
        }
    }
}
