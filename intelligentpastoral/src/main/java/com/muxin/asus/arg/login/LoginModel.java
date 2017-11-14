package com.muxin.asus.arg.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.Constants;
import com.muxin.asus.arg.common.utils.RetrofitUtil;
import com.muxin.asus.arg.bean.LoginRequest;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/20
 * Description:
 */
public class LoginModel extends BaseModel implements LoginContract.Model {
    private Context mContext;

    public LoginModel(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void login(@NonNull final LoginRequest body, @NonNull final ModelAfter after) {
        ILoginService service = RetrofitUtil.getService(ILoginService.class);
        Subscription subscriptions = service.login(Constants.USER_TOKEN, body).map(new Func1<ResponseData, String>() {
            @Override
            public String call(ResponseData responseData) {
                return responseData.getMessage();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());
                        after.error();
                        return;
                    }

                    @Override
                    public void onNext(String s) {
                        after.success(s);
                        ((BaseActivity)mContext).setPreference("username", body.getLoginname());
                        ((BaseActivity)mContext).setPreference("password",body.getPassword());
                    }
                });
        mSubscriptionm.add(subscriptions);
    }
}