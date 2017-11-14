package com.muxin.asus.arg.common.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.SensorBean;
import com.muxin.asus.arg.bean.SensorValueBean;
import com.muxin.asus.arg.bean.Station;
import com.muxin.asus.arg.bean.StationResponse;
import com.muxin.asus.arg.bean.Constants;
import com.muxin.asus.arg.common.inter.IGetSensorFromNet;
import com.muxin.asus.arg.common.inter.SensorI;
import com.muxin.asus.arg.common.utils.RetrofitUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/20
 * Description:
 */
public class SensorImpl extends BaseModel implements SensorI {
    private Context mContext;
    private IGetSensorFromNet mService;
    private int loopTime = 3000;//轮询服务器时间
    private SensorValueBean[] mValueBeans;

    public SensorImpl(Context context) {
        super();
        mContext = context;
        mService = RetrofitUtil.getService(IGetSensorFromNet.class);
    }

    @Override
    public void showSensor(@NonNull final ModelAfter after) {
        if (mService == null) {
            mService = RetrofitUtil.getService(IGetSensorFromNet.class);
        }
        if (null == getStationIdFromLocal() || getStationIdFromLocal().equals("")) {
            getStationIdFromNet(after);

        } else {

            intervalData(after);

        }

    }

    private void getStationIdFromNet(@NonNull final ModelAfter after) {
        Subscription subscriptions = mService.getStaionId(Constants.USER_TOKEN, new RequestData())
                .map(new Func1<ResponseData, StationResponse>() {
                    @Override
                    public StationResponse call(ResponseData responseData) {
                        Log.i(TAG, responseData.getMessage());
                        return new Gson().fromJson(responseData.getMessage(), StationResponse[].class)[0];
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, ""+throwable.getMessage());
                        after.error();
                    }

                    @Override
                    public void onNext(StationResponse s) {
                        Log.i(TAG, ""+s.getGatewayname());
                        handleSensorAndCurrentData(s, after);
                        ((BaseActivity) mContext).setPreference("station", new Gson().toJson(s));
                    }
                });
        mSubscriptionm.add(subscriptions);
    }

    private void intervalData(@NonNull final ModelAfter after) {
        mValueBeans = new Gson().fromJson(((BaseActivity) mContext).getPreference("sensor"), SensorValueBean[].class);
        Subscription subscribe = Observable.interval(loopTime, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.i(TAG, aLong + "");
                getData(after);
            }
        });
        mSubscriptionm.add(subscribe);
    }

    private void getData(@NonNull final ModelAfter after) {
        Subscription subscriptions = null;
        if (mValueBeans.length == 0 || mValueBeans == null) {
            return;
        } else {
            subscriptions = mService.getData(Constants.USER_TOKEN, new Station(mValueBeans[0].getGatewayid())).map(new Func1<ResponseData, SensorValueBean[]>() {
                @Override
                public SensorValueBean[] call(ResponseData responseData) {
                    try {
                        JSONArray jsonArray = new JSONArray(responseData.getMessage());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            for (int j = 0; j < mValueBeans.length; j++) {
                                mValueBeans[j].setValue(jsonObject.getDouble("channel" + mValueBeans[j].getChannelno()));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return mValueBeans;
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<SensorValueBean[]>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e(TAG, ""+throwable.getMessage());
                    after.error();
                }

                @Override
                public void onNext(SensorValueBean[] sensorValueBeans) {
                    Log.i(TAG, ""+sensorValueBeans[0].getValue() );
                    after.success(sensorValueBeans);
                }
            });
            mSubscriptionm.add(subscriptions);
        }
    }

    private StationResponse getStationResponse() {
        return new Gson().fromJson(((BaseActivity) mContext).getPreference("station"), StationResponse.class);
    }


    @Override
    public void handleSensorAndCurrentData(final StationResponse stationResponse, @NonNull final ModelAfter after) {
        if (mService == null) {
            mService = RetrofitUtil.getService(IGetSensorFromNet.class);
        }
        RequestData data = new RequestData();
        data.setGatewayid(stationResponse.getGatewayid());
        Subscription subscriptions = Observable.zip(mService.getSensor(Constants.USER_TOKEN, data), mService.getData(Constants.USER_TOKEN, new Station(stationResponse.getGatewayid())), new Func2<ResponseData, ResponseData, SensorValueBean[]>() {

            @Override
            public SensorValueBean[] call(ResponseData responseData, final ResponseData responseData2) {

                SensorBean[] sensorBean1 = new Gson().fromJson(responseData.getMessage(), SensorBean[].class);
                SensorValueBean[] valueBean = new SensorValueBean[sensorBean1.length];
                try {
                    JSONArray jsonArray = new JSONArray(responseData2.getMessage());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        for (int j = 0; j < sensorBean1.length; j++) {
                            SensorValueBean bean = new SensorValueBean();
                            bean.setGatewayid(sensorBean1[j].getGatewayid());
                            bean.setChannelno(sensorBean1[j].getChannelno());
                            bean.setGatewayname(sensorBean1[j].getGatewayname());
                            bean.setTransducername(sensorBean1[j].getTransducername());
                            bean.setLocale(stationResponse.getLocale());
                            bean.setValue(jsonObject.getDouble("channel" + sensorBean1[j].getChannelno()));
                            valueBean[j] = bean;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ((BaseActivity) mContext).setPreference("sensor", new Gson().toJson(valueBean).toString());
                mValueBeans = valueBean;
                return valueBean;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<SensorValueBean[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, ""+throwable.getMessage());
                after.error();
            }

            @Override
            public void onNext(SensorValueBean[] sensorValueBeans) {
                Log.i(TAG, ""+sensorValueBeans[0].getValue() + "");
                intervalData(after);
            }
        });
        mSubscriptionm.add(subscriptions);


    }

    private String getStationIdFromLocal() {
        if (((BaseActivity) mContext).getPreference("station").equals("")) {
            Log.i(TAG, "getStationIdFromLocal: ");
            return null;
        } else {
            Log.i(TAG, "getStationIdFromLocal: 1" + getStationResponse().getGatewayid());
            return getStationResponse().getGatewayid();
        }
    }


}
