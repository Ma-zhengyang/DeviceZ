package com.android.mazhengyang.device_z;

import android.content.Context;

/**
 * Created by mazhengyang on 18-11-13.
 */

public class Utils {

    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

}
