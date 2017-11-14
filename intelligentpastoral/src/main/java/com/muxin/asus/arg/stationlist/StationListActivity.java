package com.muxin.asus.arg.stationlist;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.google.gson.Gson;
import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.bean.Constants;
import com.muxin.asus.arg.bean.StationResponse;
import com.muxin.asus.arg.common.BaseRecyAdapter;
import com.muxin.asus.arg.common.utils.MsgDialog;
import com.muxin.asus.arg.sensor.SensorActivity;

import butterknife.InjectView;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月26日
 * @Description:
 */
public class StationListActivity extends BaseActivity implements
        StationListContract.View, BaseRecyAdapter.IOnRecyListener {
    @InjectView(R.id.recy_sensor)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mRefreshLayout;

    private StationListRecyAdapter mRecyAdapter;
    private StationListContract.Presenter mPresenter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private String mTitle;
    private MsgDialog mDialog;
    private StationResponse mCurrentStation;
    private StationResponse[] mStationResponses;
    @Override
    protected int getLayoutId() {
        return R.layout.stationlist_activity;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        mTitle = bundle.getString("title");
    }

    @Override
    protected String getTitleBarTitle() {
        return mTitle;
    }

    @Override
    protected void initView() {
        mDialog = MsgDialog.getInstance(new MsgDialog.OnSureClick() {

            @Override
            public void onSure() {
                setPreference("station", new Gson().toJson(mCurrentStation));
                StationListActivity.this.setResult(Constants.RESULT_CODE_SENSOR);

            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        mRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mRefreshLayout.setEnabled(false);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mRecyAdapter.getItemCount()) {
                    mRefreshLayout.setRefreshing(false);
                    mPresenter.loadMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mPresenter = new StationListPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        mStationResponses = new StationResponse[4];
    }


    @Override
    public void initListView(StationResponse[] stationBeans) {
        mStationResponses = stationBeans;
        mRecyAdapter = new StationListRecyAdapter(this, stationBeans);
        mRecyclerView.setAdapter(mRecyAdapter);
        mRecyAdapter.setLongClickListener(this);

    }

    @Override
    public void toStation(StationResponse stationBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("stationbean", stationBean);
        toActivityWithBundle(SensorActivity.class, bundle);
    }

    @Override
    public void showError(String msg) {
        mRefreshLayout.setRefreshing(false);
        super.showError(msg);

    }

    @Override
    public int getCurPage() {
        return mCurrentPage;
    }

    @Override
    public void onClickListener(View v, int position) {
        toStation(mStationResponses[position]);
    }

    @Override
    public void onLongClickListener(View v, int position) {
        mCurrentStation = mStationResponses[position];
        mDialog.show(getFragmentManager(), "dialog");
    }
    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }


}
