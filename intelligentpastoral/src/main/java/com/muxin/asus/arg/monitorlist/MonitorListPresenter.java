package com.muxin.asus.arg.monitorlist;

import android.content.Context;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseModel;
import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.bean.MonitorResponse;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public class MonitorListPresenter implements MonitorListContract.Presenter {
    private Context mContext;
    private MonitorListContract.View mView;
    private MonitorListContract.Model mModel;
    private int mTotal = 0;
    private int mSize = 10;
    private int mCount = 0;
    private int mCurrentPage = 1;
    private RequestData mRequestData;

    public MonitorListPresenter( MonitorListContract.View view) {
        mView = view;
        mContext = view.getContext();
        mModel = new MonitorListModel(mContext);
        mRequestData = new RequestData();
        loadData();
    }

    @Override
    public void loadData() {
        mModel.loadData(new ModelAfter() {
            @Override
            public void success(Object o) {
                mView.initListView((MonitorResponse[]) o);
            }

            @Override
            public void error() {
                mView.showError(mContext.getString(R.string.server_error));
            }
        });
    }

    @Override
    public void loadMoreData() {
        if (mCount > mRequestData.getCurpage()) {
            mRequestData.setCurpage(++mCurrentPage);
            mModel.loadMoreData(mRequestData, new ModelAfter() {
                @Override
                public void success(Object o) {
                    mView.initListView((MonitorResponse[]) o);
                }

                @Override
                public void error() {
                    mView.showError(mContext.getString(R.string.server_error));
                }
            });
            if (mTotal % mSize == 0) {
                mCount = mTotal / mSize;
            } else {
                mCount = mTotal / mSize + 1;
            }

        } else {
            mView.showError("已经获取到全部数据了");
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
