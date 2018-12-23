package com.ajman.ded.ae.libs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.roughike.bottombar.BottomBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/*
 * Created by root on 7/27/17.
 */

public class ScrollingBehavior extends CoordinatorLayout.Behavior<BottomBar> {

    public ScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomBar fab, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, BottomBar fab, View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = fab.getHeight() + fabBottomMargin;
            fab.setTranslationY(-distanceToScroll);
        }
        return true;
    }
}