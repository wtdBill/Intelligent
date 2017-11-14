package com.muxin.asus.arg.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/20
 * Description:
 */
public class BaseModel {
    protected CompositeSubscription mSubscriptionm;
    protected String TAG = "";

    public BaseModel() {
        mSubscriptionm = new CompositeSubscription();
        TAG = this.getClass().getSimpleName();
    }

    public void unsubscribe() {
        if (mSubscriptionm != null) {
            mSubscriptionm.unsubscribe();
        }
    }

}
