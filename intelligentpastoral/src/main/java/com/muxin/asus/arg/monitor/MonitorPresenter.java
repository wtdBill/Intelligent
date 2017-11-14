package com.muxin.asus.arg.monitor;

import android.content.Context;
import android.view.View;

import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.common.inter.MonitorI;
import com.muxin.asus.arg.common.impl.MonitorImpl;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;

/**
 * Author:   Lianwei Bu
 * Date:     2016/7/2
 * Description:
 */
public class MonitorPresenter implements MonitorContract.Presenter {
    private Context mContext;
    private MonitorI mModel;
    private MonitorContract.View mView;

    public MonitorPresenter( MonitorContract.View view) {
        mView = view;
        mContext = view.getContext();
        mModel = new MonitorImpl(mContext);
        connect();
    }

    @Override
    public void connect() {
        mView.showLoading();
        mModel.loginDevice(mView.getMonitorResponse(), new ModelAfter() {
            @Override
            public void success(Object o) {

                mModel.priview(new ModelAfter() {
                    @Override
                    public void success(final Object o) {
                        mView.hideLoading();
                        mView.updateUI((PlaySurfaceView[]) o);
                        mView.setMonitorName(((PlaySurfaceView[]) o)[0].getChanName());
                    }

                    @Override
                    public void error() {
                        mView.hideLoading();
                        showError();
                    }
                },  mView.getViewPager().getWidth(), mView.getViewPager().getHeight());
            }

            @Override
            public void error() {
                mView.hideLoading();
                showError();
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void unsubscribe() {
        ((BaseModel) mModel).unsubscribe();
    }

    private void showError() {
        if (mView.getViewPager() != null || mView.getDefaultTextView() != null) {
            mView.getViewPager().setVisibility(View.GONE);
            mView.getDefaultTextView().setVisibility(View.VISIBLE);
        }
    }
}
