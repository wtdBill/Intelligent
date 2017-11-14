package com.muxin.asus.arg.monitorlist;

import android.support.annotation.NonNull;

import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.base.inter.BasePresenter;
import com.muxin.asus.arg.base.inter.BaseView;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.MonitorResponse;

public interface MonitorListContract {
	interface View extends BaseView {
		void initListView(@NonNull MonitorResponse[] monitorBeans);

		void toStation(@NonNull MonitorResponse monitorBean);

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
