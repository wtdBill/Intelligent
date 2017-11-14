package com.muxin.asus.arg.common.inter;

import android.support.annotation.NonNull;

import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.StationResponse;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public interface SensorI {
    void showSensor(@NonNull ModelAfter after);
    void handleSensorAndCurrentData(StationResponse stationResponse, @NonNull final ModelAfter after);
}
