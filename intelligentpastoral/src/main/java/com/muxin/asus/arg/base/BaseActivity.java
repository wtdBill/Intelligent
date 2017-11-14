package com.muxin.asus.arg.base;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.inter.BaseView;
import com.muxin.asus.arg.common.AppManager;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月17日
 * @Description:
 */
public abstract class BaseActivity extends AutoLayoutActivity implements BaseView {
    protected TextView mTitleTV;
    protected TextView mRightTV;
    protected LinearLayout mLayout;
    private long exitTime;
    protected String TAG;
    protected ProgressDialog mLoading;
    private String mPageName="123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            initUmeng();
            ButterKnife.inject(this);
            setColor(this, getResources().getColor(R.color.green));
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                getBundle(bundle);
            }
            initTitle(findViewById(R.id.title_bar));
            initLoading();
            initView();
            initData();
        } else {
            new Exception("为Activity 设置布局资源");
        }

        TAG = getClass().getSimpleName();
        Log.i("blw name", "name---" + TAG);
        AppManager.getInstance().addActivity(this);
    }

    private void initUmeng() {
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
        // MobclickAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng", EScenarioType.E_UM_NORMAL));
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void initLoading() {
        mLoading = new ProgressDialog(this);
        mLoading.setMessage(getText(R.string.dialog_loading));
        mLoading.setCanceledOnTouchOutside(false);
        mLoading.setCancelable(true);
    }


    @Override
    public void showLoading() {
        mLoading.show();
    }

    @Override
    public void hideLoading() {
        mLoading.dismiss();
    }

    @Override
    public void showNetError() {
        showToast(getString(R.string.net_error));
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    private void initTitle(@NonNull View view) {
        mTitleTV = (TextView) view.findViewById(R.id.tv_title);
        mRightTV = (TextView) view.findViewById(R.id.tv_right);
        mLayout = (LinearLayout) view.findViewById(R.id.layout_right);
        mRightTV.setText(R.string.back);
        mLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickBack();
            }
        });
        mTitleTV.setText(getTitleBarTitle());
    }

    protected void clickBack() {
        finish();
    }


    protected abstract int getLayoutId();

    protected abstract String getTitleBarTitle();

    protected abstract void getBundle(@NonNull Bundle bundle);

    protected abstract void initView();

    protected  void initData(){};


    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toActivity(Class<?> clas) {
        startActivity(new Intent(this, clas));
    }

    public void toActivityWithBundle(Class<?> clas, Bundle bundle) {
        startActivity(new Intent(this, clas).putExtras(bundle));
    }

    public void toActivityForResultWithBundle(Class<?> clas, int requestCode, Bundle bundle) {
        startActivityForResult(new Intent(this, clas).putExtras(bundle), requestCode);
    }

    public void toActivityFinish(Class<?> clas) {
        startActivity(new Intent(this, clas));
        finish();
    }

    public void setPreference(@NonNull String key, @NonNull String value) {
        SharedPreferences sharedPreferences = getSharedPreference();
        sharedPreferences.edit().putString(key, value).commit();
    }

    private SharedPreferences getSharedPreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("config", 0);
        return sharedPreferences;
    }

    public String getPreference(@NonNull String key) {
        return getSharedPreference().getString(key, "");
    }

    public void toActivityFinishWithBundle(Class<?> clas, Bundle bundle) {
        startActivity(new Intent(this, clas).putExtras(bundle));
        finish();
    }

    private View getTargetView() {
        return findViewById(R.id.content);
    }

    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity
                    .findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow()
                    .getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity
                    .findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(
                resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            // case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_BACK:
                // exit();
                break;
        }

        return true;
    }

    @Override
    public void finish() {
        super.finish();
        // setActivityAnim();
        ButterKnife.reset(this);
        AppManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    protected void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getInstance().clear();
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
    }
}
