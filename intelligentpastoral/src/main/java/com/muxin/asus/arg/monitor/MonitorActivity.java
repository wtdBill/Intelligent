package com.muxin.asus.arg.monitor;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.bean.MonitorResponse;
import com.muxin.asus.arg.common.LazyViewPager;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/26
 * Description:
 */
public class MonitorActivity extends BaseActivity implements MonitorContract.View {
    @InjectView(R.id.video_vp_video)
    LazyViewPager mViewPager;
    @InjectView(R.id.indicator_layout)
    RelativeLayout mIndicatorLayout;
    @InjectView(R.id.video_tv_station_name)
    TextView mStationName;
    @InjectView(R.id.default_tv)
    TextView mLayout;
    @InjectView(R.id.guide_container_point)
    LinearLayout mContainerPoint;

    private MonitorAdapter mAdapter;
    private PlaySurfaceView[] mPlaySurfaceViews = new PlaySurfaceView[0];
    private MonitorContract.Presenter mPresenter;
    private MonitorResponse mMonitorResponse;
    private int mPointSpace = 30;
    private View mFocusPoint;

    @Override
    protected int getLayoutId() {
        return R.layout.monitor_activity;
    }

    @Override
    protected String getTitleBarTitle() {
        return mMonitorResponse.getSitename() != null ? mMonitorResponse.getSitename() : "";
    }

    @Override
    protected void getBundle(@NonNull Bundle bundle) {
        mMonitorResponse = (MonitorResponse) bundle.getSerializable("monitorresponse");
    }

    @Override
    protected void initView() {
        mFocusPoint = new View(this);
        mFocusPoint.setBackground(getResources().getDrawable(R.drawable.viewpager_indicator_select_bg));
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(28,28);
        mIndicatorLayout.addView(mFocusPoint,params);
        mPresenter = new MonitorPresenter(this);
        Drawable drawable = getResources().getDrawable(R.drawable.arrow_bottom);
        drawable.setBounds(0, 0, 30, 30);
        mStationName.setCompoundDrawables(drawable, null, null, null);
        mViewPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int leftMargin = (int) (mPointSpace * positionOffset + position * mPointSpace + 0.5f);

                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mFocusPoint.getLayoutParams();
                params.leftMargin = leftMargin;
                mFocusPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                mStationName.setText(mPlaySurfaceViews[position].getChanName());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @OnClick(R.id.video_tv_station_name)
    public void showSpinner(View view) {
        showPop();
    }

    private void showPop() {
        View view = View.inflate(this, R.layout.pop, null);
        final PopupWindow pop = new PopupWindow(view, 200, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ListView listView = (ListView) view.findViewById(R.id.pop_list);
        PopAdapter popAdapter = new PopAdapter(this, mPlaySurfaceViews);
        listView.setAdapter(popAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStationName.setText(mPlaySurfaceViews[position].getChanName());
                mViewPager.setCurrentItem(position);
                pop.dismiss();
            }
        });
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.showAsDropDown(mStationName);

    }

    @Override
    public void updateUI(@NonNull PlaySurfaceView[] views) {
        mPlaySurfaceViews = views;
        mAdapter = new MonitorAdapter(MonitorActivity.this, views);
        mViewPager.setAdapter(mAdapter);
        final int[] i = {views.length};
        final Handler handler = new Handler();
        final PlaySurfaceView[] finalViews = views;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i[0] == 0) {
                    handler.removeCallbacks(this);
                    hideLoading();
                } else {
                    handler.postDelayed(this, 300);
                }
                getViewPager().setCurrentItem(i[0]--);
            }
        }, 0);
        for (int j = 0; j < views.length; j++) {
            // 添加静态的点
            View point = new View(this);
            point.setBackgroundResource(R.drawable.viewpager_indicator_nuselect_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            if (j != 0) {
                params.leftMargin = 50;
            }

            mContainerPoint.addView(point, params);
        }
        mContainerPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                mContainerPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mPointSpace = mContainerPoint.getChildAt(1).getLeft() - mContainerPoint.getChildAt(0).getLeft();
            }
        });
        Log.i(TAG, "updateSensorUI: 1");
    }

    @Override
    public void setMonitorName(@NonNull String name) {
        mStationName.setText(name);
    }

    @Override
    public LazyViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public TextView getDefaultTextView() {
        return mLayout;
    }

    @Override
    public MonitorResponse getMonitorResponse() {
        return mMonitorResponse;
    }

}
