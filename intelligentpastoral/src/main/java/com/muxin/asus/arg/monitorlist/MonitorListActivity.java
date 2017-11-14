package com.muxin.asus.arg.monitorlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.muxin.asus.arg.bean.MonitorResponse;
import com.muxin.asus.arg.common.BaseRecyAdapter;
import com.muxin.asus.arg.common.utils.MsgDialog;
import com.muxin.asus.arg.monitor.MonitorActivity;

import butterknife.InjectView;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月26日
 * @Description:
 */
public class MonitorListActivity extends BaseActivity implements
        MonitorListContract.View, BaseRecyAdapter.IOnRecyListener {
    @InjectView(R.id.recy_sensor)
    RecyclerView mRecyclerView;
    @InjectView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mRefreshLayout;

    private MonitorListRecyAdapter mRecyAdapter;
    private MonitorListPresenter mPresenter;
    private int mCurrentPage = 1;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private String mTitle;
    private MonitorResponse mCurrentStation;
    private MonitorResponse[] mMonitorResponses;
    private MsgDialog mDialog;


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
                setPreference("monitor", new Gson().toJson(mCurrentStation));
                MonitorListActivity.this.setResult(Constants.RESULT_CODE_MONITOR);
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

        mPresenter = new MonitorListPresenter(this);
        mPresenter.start();
    }

    @Override
    public void initListView(@NonNull MonitorResponse[] monitorBeans) {
        mMonitorResponses = monitorBeans;
        mRecyAdapter = new MonitorListRecyAdapter(this, monitorBeans);
        mRecyclerView.setAdapter(mRecyAdapter);
        mRecyAdapter.setClickListener(this);
    }

    @Override
    public void toStation(MonitorResponse stationBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("monitorresponse", stationBean);
		toActivityWithBundle(MonitorActivity.class, bundle);
    }

    @Override
    public int getCurPage() {
        return mCurrentPage;
    }

    @Override
    public void onClickListener(View v, int position) {
        toStation(mMonitorResponses[position]);
    }

    @Override
    public void onLongClickListener(View v, int position) {
        mCurrentStation = mMonitorResponses[position];
        mDialog.show(getFragmentManager(), "dialog");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
