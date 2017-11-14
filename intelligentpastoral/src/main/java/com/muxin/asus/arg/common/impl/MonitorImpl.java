package com.muxin.asus.arg.common.impl;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_DIGITAL_CHANNEL_STATE;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.MonitorResponse;
import com.muxin.asus.arg.bean.Constants;
import com.muxin.asus.arg.common.inter.IMonitorService;
import com.muxin.asus.arg.common.inter.MonitorI;
import com.muxin.asus.arg.common.utils.ConfigTest;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;
import com.muxin.asus.arg.common.utils.RetrofitUtil;

import org.MediaPlayer.PlayM4.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public class MonitorImpl extends BaseModel implements MonitorI {
    private Context mContext;
    private int mCount;
    private List<Map<String, Integer>> mMonitorParams;

    public MonitorImpl(Context context) {
        super();
        mContext = context;
        initeSdk();
        mMonitorParams = new ArrayList<Map<String, Integer>>();
    }

    @Override
    public void loginDevice(@NonNull final ModelAfter after) {
        Subscriber subscriber = new Subscriber<MonitorResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                after.error();
                Log.e(TAG, throwable.getMessage()+"");
            }

            @Override
            public void onNext(final MonitorResponse monitorResponse) {
                Log.i(TAG, monitorResponse.getUsername()+"");
                login(monitorResponse, after);
            }
        };
        if (getMonitorFromLocal() == null) {
            IMonitorService service = RetrofitUtil.getService(IMonitorService.class);
            Subscription subscriptions = service.getMonitorList(Constants.USER_TOKEN, new RequestData()).map(new Func1<ResponseData, MonitorResponse>() {
                @Override
                public MonitorResponse call(ResponseData responseData) {
                    Log.i(TAG, "re" + responseData.getMessage());
                    ((BaseActivity) mContext).setPreference("monitor", new Gson().toJson(new Gson().fromJson(responseData.getMessage(), MonitorResponse[].class)[0]));
                    return new Gson().fromJson(responseData.getMessage(), MonitorResponse[].class)[0];
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            mSubscriptionm.add(subscriptions);
        } else {
            final MonitorResponse monitorResponse = getMonitorFromLocal();
            login(monitorResponse, after);

        }
    }

    @Override
    public void loginDevice(@NonNull MonitorResponse monitorResponse, @NonNull ModelAfter after) {
        login(monitorResponse, after);
    }

    @Override
    public void logoutDevice() {
        logoutMonitor();
    }

    private MonitorResponse getMonitorFromLocal() {
        return new Gson().fromJson(((BaseActivity) mContext).getPreference("monitor"), MonitorResponse.class);
    }


    @Override
    public void priview(@NonNull final ModelAfter after) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                startPreview(after);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onNext(Integer integer) {

            }
        });
        mSubscriptionm.add(subscription);
    }

    @Override
    public void priview(@NonNull final ModelAfter after, @NonNull final int width, @NonNull final int height) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                startPreview(after, width, height);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onNext(Integer integer) {

            }
        });
        mSubscriptionm.add(subscription);
    }

    @Override
    public void stopPriview() {
//        if (m_iPlayID == 0) {
        stopMultiPreview();
//        }

    }

    @Override
    public int getLogId() {
        return m_iLogID;
    }

    @Override
    public int getPlayId() {
        return m_iPlayID;
    }

    @Override
    public void destroy() {
        Cleanup();
    }

    public boolean m_bNeedDecode = true;
    private int m_iChanNum = 0;
    private int m_iLogID = -1;
    public int m_iPlayID = -1;
    private int m_iStartChan = 0;
    public int m_iPort = -1;
    private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30;
    private static PlaySurfaceView[] playView;
    private int mNum;


    /**
     * &
     * 展示或关闭监控
     *
     * @return -1 打开或关闭错误 0 打开或关闭成功 1 未登陆
     */
    public int startPreview(ModelAfter after) {
        try {
            if (m_iLogID < 0) {
                Log.e(TAG, "please login on device first");
                return 1;
            }
            if (m_bNeedDecode) {
                startMultiPreview(after);
            }
            return 0;
        } catch (Exception err) {
            Log.e(TAG, "error: " + err.toString());
            return -1;
        }
    }

    public int startPreview(ModelAfter after,
                            int width, int height) {
        try {
            if (m_iLogID < 0) {
                Log.e(TAG, "please login on device first");
                return 1;
            }
            if (m_bNeedDecode) {
                startMultiPreview(after, width, height);
            }
            return 0;
        } catch (Exception err) {
            Log.e(TAG, "error: " + err.toString());
            return -1;
        }
    }

    private boolean initeSdk() {
        // init net sdk
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK init is failed!");
            return false;
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/",
                true);
        return true;
    }

    /**
     * 登陆
     *
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return 0登陆成功 1处于登陆状态
     */
    private int login(String ip, int port, String username, String password) {
        // login on the device
        m_iLogID = loginDevice(ip, port, username, password);
        if (m_iLogID < 0) {
            Log.e(TAG, "This device logins failed!");
            return -2;
        }
        // get instance of exception callback and set
        ExceptionCallBack oexceptionCbf = getExceptiongCbf();
        if (oexceptionCbf == null) {
            Log.e(TAG, "ExceptionCallBack object is failed!");
            return -5;
        }

        if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(
                oexceptionCbf)) {
            Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
            return -4;
        }

        Log.i(TAG,
                "Login sucess ****************************1***************************");
        return 0;
    }

    private int logout() {
        // whether we have logout
        if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
            Log.e(TAG, " NET_DVR_Logout is failed!");
            return -3;
        }
        m_iLogID = -1;
        return 0;
    }


    private void startMultiPreview(final ModelAfter after) {
        mMonitorParams.clear();
        mNum = 0;
        Map<String, Integer> map;
        for (byte i : Test_DigitalChannelState(m_iLogID)) {
            if (i == 1) {
                map = new HashMap<String, Integer>();
                map.put("logid", m_iLogID);
                map.put("chan", m_iStartChan + mNum);
                mMonitorParams.add(map);
                ++mNum;
            }
        }
        if (mMonitorParams != null && mMonitorParams.size() != 0) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    after.success(mMonitorParams);
                }
            });
        }

    }

    private void startMultiPreview(final ModelAfter after, final int width,
                                   final int height) {
        mNum = 0;
        int j = 0;
        for (byte i : Test_DigitalChannelState(m_iLogID)) {
            if (i == 1) {
                ++j;
            }

        }
        playView = new PlaySurfaceView[j];
        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                for (byte i : Test_DigitalChannelState(m_iLogID)) {
                    if (i == 1) {
                        Log.e(TAG, "i " + i);
                        if (playView[mNum] == null) {
                            playView[mNum] = new PlaySurfaceView(
                                    (Activity) mContext);
                            playView[mNum].setParam(width, height, 1);
                        }
                        playView[mNum].startPreview(m_iLogID, m_iStartChan + mNum);
                        ++mNum;
                    }

                }
                if (playView != null && playView.length != 0) {
                    after.success(playView);
                }

            }
        });
        m_iPlayID = playView[0].m_iPreviewHandle;
    }

    private void stopMultiPreview() {
        int i = 0;
        for (i = 0; i < playView.length; i++) {
            playView[i].stopPreview();
        }
        m_iPlayID = -1;
    }


    private int loginDevice(String ip, int port, String username,
                            String password) {

        // get instance
        m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
        if (null == m_oNetDvrDeviceInfoV30) {
            Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
            return -1;
        }
        // call NET_DVR_Login_v30 to login on, port 8000 as default
        if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(username)
                || TextUtils.isEmpty(password)) {
            return -1;
        }
        int iLogID = HCNetSDK.getInstance().NET_DVR_Login_V30(ip, port,
                username, password, m_oNetDvrDeviceInfoV30);

        if (iLogID < 0) {
            Log.e(TAG, "NET_DVR_Login is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return -1;
        }
        if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
        } else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
            m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
            m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum
                    + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256;
        }
        Log.i(TAG, "NET_DVR_Login is Successful!");
        Log.i(TAG,
                "m_iChanNum----------" + m_iChanNum);
        ConfigTest.Test_DigitalChannelState(iLogID);
        return iLogID;
    }

    private ExceptionCallBack getExceptiongCbf() {
        ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
            public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
                System.out.println("recv exception, type:" + iType);
            }
        };
        return oExceptionCbf;
    }

    public void Cleanup() {
        // release player resource

        Player.getInstance().freePort(m_iPort);
        m_iPort = -1;

        // release net SDK resource
        HCNetSDK.getInstance().NET_DVR_Cleanup();
    }

    private void login(final MonitorResponse monitorResponse, @NonNull final ModelAfter after) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (m_iLogID != 0) {
                    int login = login(monitorResponse.getAppipaddress().split(":")[0], Integer.parseInt(monitorResponse.getAppipaddress().split(":")[1]), monitorResponse.getUsername(), monitorResponse.getPassword());
                    subscriber.onNext(login);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG + "1", "" + throwable.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG + "222", integer + "");
                        if (integer != 0) {
                            after.error();
                        } else {
                            after.success(monitorResponse);
                        }
                    }
                });
        mSubscriptionm.add(subscription);
    }

    private void logoutMonitor() {
//        if (m_iLogID == 0) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (m_iLogID != -1) {
                    subscriber.onNext(logout());
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer != 0) {
                            logoutMonitor();
                        }

                    }
                });
        mSubscriptionm.add(subscription);
//        }

    }

    public byte[] Test_DigitalChannelState(int iUserID) {
        NET_DVR_DIGITAL_CHANNEL_STATE struChanState = new NET_DVR_DIGITAL_CHANNEL_STATE();
        if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.getInstance().NET_DVR_GET_DIGITAL_CHANNEL_STATE, 0, struChanState)) {
            System.out.println("NET_DVR_GET_DIGITAL_CHANNEL_STATE faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());

            return null;
        } else {

            System.out.println("NET_DVR_GET_DIGITAL_CHANNEL_STATE succ!");
            return struChanState.byDigitalChanState;
        }

    }

}