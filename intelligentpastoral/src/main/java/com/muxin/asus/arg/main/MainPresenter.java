package com.muxin.asus.arg.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.MonitorResponse;
import com.muxin.asus.arg.bean.SensorValueBean;
import com.muxin.asus.arg.bean.StationResponse;
import com.muxin.asus.arg.common.impl.MonitorImpl;
import com.muxin.asus.arg.common.impl.SensorImpl;
import com.muxin.asus.arg.common.inter.MonitorI;
import com.muxin.asus.arg.common.inter.SensorI;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;

import java.util.List;
import java.util.Map;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public class MainPresenter implements MainContract.Presenter {
    private Context mContext;
    private MainContract.View mView;
    private SensorI mSensorModelI;
    private MonitorI mMonitorModelI;
    private PlaySurfaceView[] mPlaySurfaceViews;

    public MainPresenter( MainContract.View view) {
        mView = view;
        mContext = view.getContext();
        mSensorModelI = new SensorImpl(mContext);
        mMonitorModelI = new MonitorImpl(mContext);
        loginDevice();
    }

    @Override
    public void getSensorData() {

        mSensorModelI.showSensor(new ModelAfter() {
            @Override
            public void success(Object o) {
                mView.updateSensorUI((SensorValueBean[]) o);
                mView.setSensorStation(((SensorValueBean[]) o)[0].getGatewayname());
            }

            @Override
            public void error() {
                mView.showError(mContext.getString(R.string.server_error));
            }
        });
    }

    private void showError() {
        if (mView.getDefaultTextView() != null && mView.getMonitorLayout() != null) {
            mView.getMonitorLayout().setVisibility(View.GONE);
            mView.getDefaultTextView().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void preview() {
        if (mMonitorModelI.getLogId() != -1 && mMonitorModelI.getPlayId() == -1) {
            mMonitorModelI.priview(new ModelAfter() {
                @Override
                public void success(Object o) {
//                    mPlaySurfaceViews = (PlaySurfaceView[]) o;
                    mView.updateUI((List<Map<String, Integer>>) o);
                    mView.getMonitorLayout().setVisibility(View.VISIBLE);
                    mView.getDefaultTextView().setVisibility(View.GONE);
//                    Log.e("blw ", "success: "+mPlaySurfaceViews.length);
                }

                @Override
                public void error() {
//                    showError();
                }
            });
        } else {
            showError();
        }
    }

    @Override
    public void logoutDevice() {
//        if (mPlaySurfaceViews == null||mPlaySurfaceViews.length==0) {
//            return;
//        }
//        for (int i = 0; i < mPlaySurfaceViews.length; i++) {
//            if (mPlaySurfaceViews[i].m_iPreviewHandle!=-1){
//                break;
//            }else {
//                return;
//            }
//        }
//            mMonitorModelI.stopPriview();
        mMonitorModelI.logoutDevice();
    }

    @Override
    public void loginDevice() {
        mMonitorModelI.loginDevice(new ModelAfter() {
            @Override
            public void success(Object o) {
                mView.setMonitorStation(((MonitorResponse) o).getMonitorname());
                preview();
            }

            @Override
            public void error() {
                showError();
            }
        });
    }

    @Override
    public void destory() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void handleSensor(@NonNull StationResponse stationResponse) {
        mSensorModelI.handleSensorAndCurrentData(stationResponse, new ModelAfter() {
            @Override
            public void success(Object o) {
                mView.updateSensorUI((SensorValueBean[]) o);
            }

            @Override
            public void error() {
                mView.showError(mContext.getString(R.string.server_error));
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void unsubscribe() {
        ((BaseModel) mMonitorModelI).unsubscribe();
        ((BaseModel) mSensorModelI).unsubscribe();
        mMonitorModelI.destroy();
    }

}
