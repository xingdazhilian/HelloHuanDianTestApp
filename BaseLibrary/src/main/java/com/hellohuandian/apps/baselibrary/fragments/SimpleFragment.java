package com.hellohuandian.apps.baselibrary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-07
 * Description:
 */
public abstract class SimpleFragment extends Fragment
{
    private View self;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (self == null)
        {
            self = inflater.inflate(getLayoutID(), container, false);
        } else
        {
            if (self.getParent() != null)
            {
                ((ViewGroup) self.getParent()).removeView(self);
            }
        }

        return self;
    }

    protected abstract int getLayoutID();
}
