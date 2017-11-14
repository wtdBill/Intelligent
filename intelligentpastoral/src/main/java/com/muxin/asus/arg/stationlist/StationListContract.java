package com.muxin.asus.arg.stationlist;

import android.support.annotation.NonNull;

import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.base.inter.BasePresenter;
import com.muxin.asus.arg.base.inter.BaseView;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.StationResponse;


public interface StationListContract {
	interface View extends BaseView {
		void initListView(@NonNull StationResponse[] stationBeans);


		void toStation(@NonNull StationResponse stationBean);

		int getCurPage();
	}

	interface Presenter extends BasePresenter {
		void loadData();
		void loadMoreData();
	}

	interface Model {
		void loadData(@NonNull ModelAfter after);
		void loadMoreData(@NonNull RequestData requestData,@NonNull ModelAfter after);
	}
}
