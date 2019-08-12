package com.HelloHuanDian.apps.test.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Author:      Lee Yeung
 * Create Date: 2019-08-07
 * Description: 垂直滚动文本控件
 */
public class VerticalScrollTextView extends AppCompatTextView
{
    private Scroller scroller;
    private boolean isVerticalCenter = true;

    public VerticalScrollTextView(Context context)
    {
        super(context);
        init();
    }

    public VerticalScrollTextView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public VerticalScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);

        if (isVerticalCenter && changed)
        {
            //修正top和bottom的padding距离，保证居中显示
            final int selfHeight = bottom - top;
            final int lineHeight = getLineHeight();
            if (lineHeight > 0)
            {
                final int t_b_height = selfHeight % lineHeight;
                setPadding(getPaddingLeft(), t_b_height / 2, getPaddingRight(), t_b_height / 2);
            }
        }
    }

    @Override
    public void computeScroll()
    {
        if (scroller.computeScrollOffset())
        {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
        }
    }

    private void startScroll()
    {
        int offset = getLineCount() * getLineHeight();
        if (offset > getHeight())
        {
            scroller.startScroll(0, offset - getHeight(), 0, getLineHeight());
        }
    }

    /**
     * 追加文本信息
     *
     * @param text
     */
    public void appendText(String text)
    {
        append(text);
        if (scroller.isFinished())
        {
            startScroll();
        }
    }

    public void clear()
    {
        getEditableText().clear();
        scroller.startScroll(0, 0, 0, 0);
    }
}
