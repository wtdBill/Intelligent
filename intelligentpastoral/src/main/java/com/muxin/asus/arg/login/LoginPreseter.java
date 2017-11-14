package com.muxin.asus.arg.login;

import android.content.Context;
import android.text.TextUtils;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.Constants;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/20
 * Description:
 */
public class LoginPreseter implements LoginContract.Presenter {
    private Context mContext;
    private LoginContract.View mView;
    private LoginContract.Model mModel;

    public LoginPreseter( LoginContract.View view) {
        mView = view;
        mContext = mView.getContext();
        mModel = new LoginModel(mContext);

    }

    @Override
    public void login() {

        if (!TextUtils.isEmpty(mView.getLoginBean().getLoginname())&&!TextUtils.isEmpty(mView.getLoginBean().getPassword())) {
            mView.showLoading();
            mModel.login(mView.getLoginBean(), new ModelAfter() {
                @Override
                public void success(Object o) {
                    mView.hideLoading();
                    Constants.USER_TOKEN = o.toString();
                    mView.loginSuccess();
                }

                @Override
                public void error() {
                    mView.hideLoading();
                    mView.showError(mContext.getString(R.string.server_error));

                }
            });
        }else {
            mView.showError(mContext.getString(R.string.usernam_or_password_empty));
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void unsubscribe() {
        ((BaseModel)mModel).unsubscribe();
    }
}
