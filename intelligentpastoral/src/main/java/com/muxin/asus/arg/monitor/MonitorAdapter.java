package com.muxin.asus.arg.monitor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.muxin.asus.arg.common.utils.PlaySurfaceView;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/30
 * Description:
 */
public class MonitorAdapter extends PagerAdapter {
    private Context mContext;
    private PlaySurfaceView[] mList;

    public MonitorAdapter(Context context, PlaySurfaceView[] list) {
        mContext = context;
        mList = list;
    }

    @Override

    public int getCount() {
        return mList == null ? 0 : mList.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        PlaySurfaceView surfaceView = mList[position];
        container.addView(surfaceView);
        Log.i("blw", "position--->" + surfaceView.getChanName());
        return surfaceView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((View) object);
    }

}
