package com.ajman.ded.ae.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.Keep;

/**
 * Created by Ahmed on 12/2/2017.
 */

public class SlidingFrameLayout extends FrameLayout {
    private static final String TAG = SlidingFrameLayout.class.getName();

    public SlidingFrameLayout(Context context) {
        super(context);
    }

    public SlidingFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public @Keep
    float getXFraction() {
        int width = getWidth();
        return (width == 0) ? 0 : getX() / (float) width;
    }

    public @Keep
    void setXFraction(float xFraction) {
        int width = getWidth();
        setX((width > 0) ? (xFraction * width) : 0);
    }
}