package com.muxin.asus.arg.common.inter;

import android.support.annotation.NonNull;

import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.MonitorResponse;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public interface MonitorI {
    void loginDevice(@NonNull ModelAfter after);

    void loginDevice(@NonNull MonitorResponse monitorResponse, @NonNull ModelAfter after);

    void logoutDevice();

    void priview(@NonNull ModelAfter after);

    void priview(@NonNull final ModelAfter after,  @NonNull final int width, @NonNull final int height);

    void stopPriview();

    int getLogId();

    int getPlayId();

    void destroy();
}
