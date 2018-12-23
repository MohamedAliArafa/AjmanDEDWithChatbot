package com.ajman.ded.ae.utility.slidingrootnav.util;

/**
 * on 25.03.2017.
 */

public abstract class SideNavUtils {

    public static float evaluate(float fraction, float startValue, float endValue) {
        return startValue + fraction * (endValue - startValue);
    }
}
