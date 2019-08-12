package com.HelloHuanDian.apps.test.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-01
 * Description:
 */
public abstract class BaseRecycleAdapter<D, VH extends BaseRecycleAdapter.BaseViewHolder<D>> extends RecyclerView.Adapter<VH>
{
    private List<D> dataArrayList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public BaseRecycleAdapter(Context context)
    {
        if (context != null)
        {
            mContext = context.getApplicationContext();
        }
    }

    public LayoutInflater getLayoutInflater()
    {
        if (mLayoutInflater == null && mContext != null)
        {
            mLayoutInflater = LayoutInflater.from(mContext);
        }

        return mLayoutInflater;
    }

    public void addElement(D element)
    {
        if (element != null)
        {
            this.dataArrayList.add(element);
        }
    }

    public void clear()
    {
        this.dataArrayList.clear();
    }

    public void addData(List<D> dataArrayList)
    {
        if (dataArrayList != null)
        {
            this.dataArrayList.addAll(dataArrayList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<D> dataArrayList, boolean isClearOldData)
    {
        if (isClearOldData)
        {
            this.dataArrayList.clear();
        }
        addData(dataArrayList);
    }

    public List<D> getDataArrayList()
    {
        return dataArrayList;
    }

    public D getItem(int position)
    {
        if (position >= 0 && position < dataArrayList.size())
        {
            return dataArrayList.get(position);
        } else
        {
            return null;
        }
    }

    @Override
    public int getItemCount()
    {
        return dataArrayList != null ? dataArrayList.size() : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position)
    {
        holder.update(getItem(position), position);
    }

    public abstract static class BaseViewHolder<VHD> extends RecyclerView.ViewHolder
    {
        public BaseViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }

        protected abstract void update(VHD model, int position);
    }
}
