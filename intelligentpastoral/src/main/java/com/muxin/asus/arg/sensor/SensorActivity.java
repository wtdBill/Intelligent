package com.muxin.asus.arg.sensor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.bean.SensorValueBean;
import com.muxin.asus.arg.bean.StationResponse;
import com.muxin.asus.arg.common.SensorRecyAdapter;

import butterknife.InjectView;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月30日
 * @Description:
 */
public class SensorActivity extends BaseActivity implements SensorContract.View {
    @InjectView(R.id.recy_sensor)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mRefreshLayout;
    @InjectView(R.id.sensor_tv_station_name)
    TextView mStationName;

    private SensorRecyAdapter mRecyAdapter;
    private StationResponse mSationBean;
    private SensorContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.sensor_activity;
    }

    @Override
    protected String getTitleBarTitle() {
        return mSationBean == null ? "" : mSationBean.getLocale();
    }

    @Override
    protected void getBundle(Bundle bundle) {
        mSationBean = (StationResponse) bundle.getSerializable("stationbean");
    }

    @Override
    protected void initView() {
        mRefreshLayout.setEnabled(false);
        mPresenter = new SensorPresenter(this);
        mStationName.setText(mSationBean.getGatewayname());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRefreshLayout.setRefreshing(true);
    }


    @Override
    public void updateUI(@NonNull SensorValueBean[] sensorBeans) {
        if (mRecyclerView != null) {
            mRefreshLayout.setRefreshing(false);
            mRecyAdapter = new SensorRecyAdapter(this, sensorBeans);
            mRecyclerView.setAdapter(mRecyAdapter);
        }
    }

    @Override
    public String getStationId() {
        return mSationBean == null ? "" : mSationBean.getGatewayid();
    }


    @Override
    public StationResponse getStaionResponse() {
        return mSationBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
