/**
 *
 */
package com.muxin.asus.arg.base.inter;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月25日
 * @Description:
 */
public interface BaseView {
    @NonNull
    Context getContext();

    void showNetError();

    void showError(String msg);

    void showLoading();

    void hideLoading();
}
