package com.HelloHuanDian.apps.test.modules.main.fragments.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.HelloHuanDian.apps.test.R;
import com.HelloHuanDian.apps.test.base.adapter.BaseRecycleAdapter;
import com.HelloHuanDian.apps.test.service.FormatBatteryInfo;

import java.util.HashMap;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-01
 * Description:
 */
public class BatteryInfoAdapter extends BaseRecycleAdapter<FormatBatteryInfo, BatteryInfoAdapter.ViewHolder>
{
    private final HashMap<Integer, Integer> indexMap = new HashMap<>();

    public BatteryInfoAdapter(Context context)
    {
        super(context);
    }

    @Override
    public void addElement(FormatBatteryInfo element)
    {
        if (element != null && element.getBatteryData() != null)
        {
            final int id = element.getBatteryData().getControllerAddressId();
            Integer index = indexMap.get(id);
            if (index == null)
            {
                int newIndex = getItemCount();
                indexMap.put(id, newIndex);
                super.addElement(element);
                notifyItemRangeChanged(newIndex, getItemCount());
            } else
            {
                if (index >= 0 && index < getItemCount())
                {
                    getDataArrayList().set(index, element);
                    notifyItemChanged(index);
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(getLayoutInflater().inflate(R.layout.item_battery_info, parent, false));
    }

    class ViewHolder extends BaseRecycleAdapter.BaseViewHolder<FormatBatteryInfo>
    {
        @BindView(R.id.tv_BatteryInfo)
        TextView tvBatteryInfo;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void update(FormatBatteryInfo model, int position)
        {
            if (model != null)
            {
                SpannableStringBuilder spannableStringBuilder = model.formatString();
                tvBatteryInfo.setText(spannableStringBuilder != null ? spannableStringBuilder : "");
            }
        }
    }
}