package com.HelloHuanDian.apps.test.modules.main.fragments.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.HelloHuanDian.apps.test.R;
import com.HelloHuanDian.apps.test.base.adapter.BaseRecycleAdapter;
import com.HelloHuanDian.apps.test.service.FormatBatteryInfo;
import com.hellohuandian.apps.SerialPortDataLibrary.models.info.BatteryInfo;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-01
 * Description:
 */
public class BatteryInfoAdapter extends BaseRecycleAdapter<FormatBatteryInfo, BatteryInfoAdapter.ViewHolder>
{
    private Consumer<FormatBatteryInfo> formatBatteryInfoConsumer;

    private final HashMap<Integer, Integer> indexMap = new HashMap<>();

    public BatteryInfoAdapter(Context context)
    {
        super(context);
    }

    @Override
    public void addElement(FormatBatteryInfo element)
    {
        if (element != null)
        {
            final int id = element.getControllerAddress();
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

    public void setFormatBatteryInfoConsumer(Consumer<FormatBatteryInfo> formatBatteryInfoConsumer)
    {
        this.formatBatteryInfoConsumer = formatBatteryInfoConsumer;
    }

    class ViewHolder extends BaseRecycleAdapter.BaseViewHolder<FormatBatteryInfo>
    {
        @BindView(R.id.tv_BatteryInfo)
        TextView tvBatteryInfo;
        AlphaAnimation alphaAnimation;
        FormatBatteryInfo model;

        public ViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnTouchListener(new OnDoubleClickListener(new OnDoubleClickListener.DoubleClickCallback()
            {
                @Override
                public void onDoubleClick()
                {
                    if (formatBatteryInfoConsumer != null)
                    {
                        formatBatteryInfoConsumer.accept(model);
                    }
                }
            }));
            ButterKnife.bind(this, itemView);
            alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(itemView.getContext(), R.anim.alpha);
        }

        @Override
        protected void update(FormatBatteryInfo model, int position)
        {
            if (model != null)
            {
                this.model = model;

                switch (model.getStatus())
                {
                    case BatteryInfo.LOADING:
                        if (alphaAnimation != null)
                        {
                            itemView.setAnimation(alphaAnimation);
                        }
                        break;
                    case BatteryInfo.FINISH:
                        if (itemView.getAnimation() != null && !itemView.getAnimation().hasEnded())
                        {
                            itemView.getAnimation().cancel();
                        }
                        break;
                }

                SpannableStringBuilder spannableStringBuilder = model.formatString();
                tvBatteryInfo.setText(spannableStringBuilder != null ? spannableStringBuilder : "");
            }
        }
    }
}