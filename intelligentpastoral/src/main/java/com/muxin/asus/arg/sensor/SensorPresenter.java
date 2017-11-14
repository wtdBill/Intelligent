package com.muxin.asus.arg.sensor;

import android.content.Context;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.SensorValueBean;
import com.muxin.asus.arg.common.impl.SensorImpl;
import com.muxin.asus.arg.common.inter.SensorI;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/22
 * Description:
 */
public class SensorPresenter implements SensorContract.Presenter {

    private Context mContext;
    private SensorContract.View mView;
    private SensorI mModelI;

    public SensorPresenter(SensorContract.View view) {
        mView = view;
        mContext = view.getContext();
        mModelI = new SensorImpl(mContext);
        start();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void start() {
        mView.showLoading();
        mModelI.handleSensorAndCurrentData(mView.getStaionResponse(), new ModelAfter() {
            @Override
            public void success(Object o) {
                mView.hideLoading();
                mView.updateUI((SensorValueBean[]) o);
            }

            @Override
            public void error() {
                mView.hideLoading();
                mView.showError(mContext.getString(R.string.server_error));
            }
        });
    }

    @Override
    public void unsubscribe() {
        ((BaseModel)mModelI).unsubscribe();
    }
}
