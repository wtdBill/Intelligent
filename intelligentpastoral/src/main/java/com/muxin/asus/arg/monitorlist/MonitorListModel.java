package com.muxin.asus.arg.monitorlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.MonitorResponse;
import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.bean.Constants;
import com.muxin.asus.arg.common.inter.IMonitorService;
import com.muxin.asus.arg.common.utils.RetrofitUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public class MonitorListModel extends BaseModel implements MonitorListContract.Model {
    private Context mContext;
    private RequestData mRequestData;

    public MonitorListModel(Context context) {
        super();
        mContext = context;
        mRequestData = new RequestData();
    }

    @Override
    public void loadData(@NonNull final ModelAfter after) {
        Subscriber subscriber = new Subscriber<MonitorResponse[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                after.error();
            }

            @Override
            public void onNext(MonitorResponse[] stationResponse) {
                after.success(stationResponse);
                Log.i(TAG, stationResponse[0].getSitename());
            }
        };
        IMonitorService service = RetrofitUtil.getService(IMonitorService.class);
        Subscription subscriptions=  service.getMonitorList(Constants.USER_TOKEN, mRequestData).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<ResponseData, MonitorResponse[]>() {
            @Override
            public MonitorResponse[] call(ResponseData responseData) {
                return new Gson().fromJson(responseData.getMessage(), MonitorResponse[].class);
            }
        }).subscribe(subscriber);
mSubscriptionm.add(subscriptions);
    }

    @Override
    public void loadMoreData(@NonNull RequestData requestData, @NonNull ModelAfter after) {
        mRequestData=requestData;
        loadData(after);
    }
}
