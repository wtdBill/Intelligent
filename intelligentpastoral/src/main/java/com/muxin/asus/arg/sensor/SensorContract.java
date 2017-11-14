package com.muxin.asus.arg.sensor;

import android.support.annotation.NonNull;

import com.muxin.asus.arg.base.inter.BasePresenter;
import com.muxin.asus.arg.base.inter.BaseView;
import com.muxin.asus.arg.bean.SensorValueBean;
import com.muxin.asus.arg.bean.StationResponse;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月30日
 * @Description:
 * 
 */
public interface SensorContract {
	interface View extends BaseView {
		void updateUI(@NonNull SensorValueBean[] sensorBeans);

		String getStationId();

		StationResponse getStaionResponse();
	}

	interface Presenter extends BasePresenter {

		void resume();

		void pause();

	}
}
