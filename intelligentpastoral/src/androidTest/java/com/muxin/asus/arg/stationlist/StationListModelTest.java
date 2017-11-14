package com.muxin.asus.arg.stationlist;

import android.util.Log;

import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.base.inter.ModelAfter;
import com.muxin.asus.arg.main.MainActivity;

import junit.framework.TestCase;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public class StationListModelTest extends TestCase {

    public void testLoadData() throws Exception {
        new StationListModel(new MainActivity()).loadData(new ModelAfter() {
            @Override
            public void success(Object o) {
                Log.e("blw",o.toString());
            }

            @Override
            public void error() {

            }
        });

    }

    public void testLoadMoreData() throws Exception {
        new StationListModel(new MainActivity()).loadMoreData(new RequestData(), new ModelAfter() {
            @Override
            public void success(Object o) {
                Log.e("blw",o.toString());
            }

            @Override
            public void error() {

            }
        });
    }
}