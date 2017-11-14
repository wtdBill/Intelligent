package com.muxin.asus.arg.stationlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.StationResponse;
import com.muxin.asus.arg.bean.Constants;
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
public class StationListModel extends BaseModel implements StationListContract.Model {
    private Context mContext;
    private RequestData mRequestData;

    public StationListModel(Context context) {
        super();
        mContext = context;
        mRequestData = new RequestData();
    }

    @Override
    public void loadData(@NonNull final ModelAfter after) {
        Subscriber subscriber = new Subscriber<StationResponse[]>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e(TAG, throwable.getMessage());
                after.error();
            }

            @Override
            public void onNext(StationResponse[] stationResponse) {
                after.success(stationResponse);
                Log.i(TAG, stationResponse[0].getGatewayname());
            }
        };
        IStationService service = RetrofitUtil.getService(IStationService.class);
        Subscription subscriptions=  service.getStationList(Constants.USER_TOKEN, mRequestData).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<ResponseData, StationResponse[]>() {
            @Override
            public StationResponse[] call(ResponseData responseData) {
                return new Gson().fromJson(responseData.getMessage(), StationResponse[].class);
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
