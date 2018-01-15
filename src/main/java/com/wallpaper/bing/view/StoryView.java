package com.wallpaper.bing.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * author GaoPC
 * date 2017-12-13 16:01
 * description
 */

public class StoryView extends LinearLayout {
    private float startY;
    private int sizeWidth;
    private int sizeHeight;

    public StoryView(Context context) {
        super(context);
    }

    public StoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float a = event.getY()-startY;
                //setMeasuredDimension(sizeWidth, (int) (sizeHeight+a));
                layout((int)getX(),(int)(getTop()-a),(int)(getX()+getWidth()),getBottom());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                break;
        }

        return true;
    }


}
