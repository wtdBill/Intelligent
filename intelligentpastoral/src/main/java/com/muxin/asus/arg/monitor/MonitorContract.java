package com.muxin.asus.arg.monitor;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.muxin.asus.arg.base.inter.BasePresenter;
import com.muxin.asus.arg.base.inter.BaseView;
import com.muxin.asus.arg.bean.MonitorResponse;
import com.muxin.asus.arg.common.LazyViewPager;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;

/**
 * Author:   Lianwei Bu
 * Date:     2016/7/2
 * Description:
 */
public interface MonitorContract {
    interface View extends BaseView{

        void updateUI(@NonNull PlaySurfaceView[] views);

        void setMonitorName(@NonNull String name);

        @NonNull
        LazyViewPager getViewPager();

        @NonNull
        TextView getDefaultTextView();

        @NonNull
        MonitorResponse getMonitorResponse();
    }

    interface Presenter extends BasePresenter {
        void connect();
    }
}
