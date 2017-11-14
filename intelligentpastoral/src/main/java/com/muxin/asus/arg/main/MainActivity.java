package com.muxin.asus.arg.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.bean.Constants;
import com.muxin.asus.arg.bean.SensorValueBean;
import com.muxin.asus.arg.bean.StationResponse;
import com.muxin.asus.arg.common.SensorRecyAdapter;
import com.muxin.asus.arg.monitorlist.MonitorListActivity;
import com.muxin.asus.arg.stationlist.StationListActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月30日
 * @Description:
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    @InjectView(R.id.main_tv_sensor_station)
    TextView mSensorStationTV;
    @InjectView(R.id.main_tv_video_station)
    TextView mVideoStationTV;
    @InjectView(R.id.recy_sensor)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mRefreshLayout;
    @InjectView(R.id.default_tv)
    TextView mDefaultTextView;
    @InjectView(R.id.monitor_layout)
    FrameLayout mMonitorLayout;
    @InjectView(R.id.gridview)
    GridView mGridView;

    private SensorRecyAdapter mRecyAdapter;
    private MainContract.Presenter mPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.intelligent_pastoral);
    }

    @Override
    protected void getBundle(@NonNull Bundle bundle) {

    }

    @Override
    protected void initView() {

        mLayout.setVisibility(View.GONE);
        mRefreshLayout.setEnabled(false);
        mPresenter = new MainPresenter( this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mPresenter.getSensorData();
        mLoading.show();

    }

    @Override
    public View getMonitorLayout() {
        return mGridView;
    }


    @Override
    public void updateSensorUI(SensorValueBean[] sensorBeans) {
        mLoading.dismiss();
        if (mRecyclerView != null) {
            mRecyAdapter = new SensorRecyAdapter(MainActivity.this, sensorBeans);
            mRecyclerView.setAdapter(mRecyAdapter);
        }
    }

    @Override
    public void updateUI(@NonNull List<Map<String, Integer>> lists) {
        if (mGridView != null) {
            handleSize(lists);
        }
    }

    private void handleSize(@NonNull List<Map<String, Integer>> lists) {
        for (int j = 1; ; j++) {
            if (d(j, lists.size())) {
                mGridView.setNumColumns(j + 1);
                mGridView.setAdapter(new GridViewAdapter(MainActivity.this, lists, mGridView));
                Log.i("blw han", "handleSize");
                break;
            }
        }
    }

    private boolean d(int num, int count) {
        return (num - 1) * (num - 1) < count && (num + 1) * (num + 1) >= count ? true
                : false;
    }


    @OnClick({R.id.main_layout_sensor_all, R.id.main_layout_video_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_layout_sensor_all:
                toSensorAll();
                break;
            case R.id.main_layout_video_all:
                toVideoAll();
                break;

        }
    }

    private void toVideoAll() {
        Bundle bundle = new Bundle();
        bundle.putString("title", getString(R.string.video_list_title));
//        toActivityWithBundle(MonitorListActivity.class, bundle);
        toActivityForResultWithBundle(MonitorListActivity.class, Constants.REQUEST_CODE_MONITOR, bundle);
    }

    private void toSensorAll() {
        Bundle bundle = new Bundle();
        bundle.putString("title", getString(R.string.sensor_list_title));
//        toActivityWithBundle(StationListActivity.class, bundle);
        toActivityForResultWithBundle(StationListActivity.class, Constants.REQUEST_CODE_SENSOR, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: " + requestCode + "," + resultCode);
        switch (requestCode) {
            case Constants.REQUEST_CODE_SENSOR:
                if (resultCode == Constants.RESULT_CODE_SENSOR) {
                    mPresenter.destory();
                    mPresenter.handleSensor(new Gson().fromJson(getPreference("station"), StationResponse.class));
                }
                break;
            case Constants.REQUEST_CODE_MONITOR:
                if (resultCode == Constants.RESULT_CODE_MONITOR) {
                    mPresenter.logoutDevice();
                    mPresenter.loginDevice();

                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            // case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_BACK:
                exit();
                break;
        }

        return true;
    }


    @NonNull
    @Override
    public TextView getDefaultTextView() {
        return mDefaultTextView;
    }

    @Override
    public void setMonitorStation(@NonNull String monitorStation) {
        mVideoStationTV.setText(monitorStation);
        Log.e(TAG, monitorStation + "");
    }

    @Override
    public void setSensorStation(@NonNull String sensorStation) {
        mSensorStationTV.setText(sensorStation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
