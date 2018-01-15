package com.wallpaper.bing.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * author GaoPC
 * date 2018-01-15 14:56
 * description 使用自定义字体
 */

public class BingTextView extends AppCompatTextView {

    public BingTextView(Context context) {
        super(context);
    }

    public BingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }
}
