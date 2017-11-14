package com.muxin.asus.arg.main;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.muxin.asus.arg.base.inter.BasePresenter;
import com.muxin.asus.arg.base.inter.BaseView;
import com.muxin.asus.arg.bean.SensorValueBean;
import com.muxin.asus.arg.bean.StationResponse;

import java.util.List;
import java.util.Map;


public interface MainContract {
    interface View extends BaseView{
        void updateSensorUI(@NonNull SensorValueBean[] sensorBeans);
        void updateUI(@NonNull List<Map<String,Integer>> lists);

        @NonNull
        android.view.View getMonitorLayout();

        @NonNull
        TextView getDefaultTextView();

        void setMonitorStation(@NonNull String monitorStation);

        void setSensorStation(@NonNull String sensorStation);
    }

    interface Presenter extends BasePresenter {

        void getSensorData();

        void preview();

        void logoutDevice();

        void loginDevice();

        void destory();

        void resume();

        void pause();

        void handleSensor(@NonNull StationResponse stationResponse);
    }

}
