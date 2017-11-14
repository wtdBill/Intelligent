/**
 *
 */
package com.muxin.asus.arg.login;


import android.support.annotation.NonNull;

import com.muxin.asus.arg.base.inter.BasePresenter;
import com.muxin.asus.arg.base.inter.BaseView;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.LoginRequest;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月25日
 * @Description:
 */
public class LoginContract {
    interface View extends BaseView {

        LoginRequest getLoginBean();

        void loginSuccess();
    }

    interface Presenter extends BasePresenter {
        void login();

    }

    public interface Model {
        void login(@NonNull LoginRequest request, @NonNull ModelAfter after);
    }
}
