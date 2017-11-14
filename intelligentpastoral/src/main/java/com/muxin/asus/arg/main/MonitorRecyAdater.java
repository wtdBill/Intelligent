package com.muxin.asus.arg.main;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.common.BaseRecyAdapter;
import com.muxin.asus.arg.common.BaseViewHolder;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;

/**
 * Author:   Lianwei Bu
 * Date:     2016/7/26
 * Description:
 */
public class MonitorRecyAdater extends BaseRecyAdapter<PlaySurfaceView> {
    private PlaySurfaceView[] mPlaySurfaceViews;
    public MonitorRecyAdater(Context context, Object[] datas) {
        super(context, datas);
        mPlaySurfaceViews= (PlaySurfaceView[]) datas;
    }

    @Override
    public int getLayoutId() {
        return R.layout.gridview_item;
    }

    @Override
    public void convert(BaseViewHolder holder, PlaySurfaceView playSurfaceView) {
        LinearLayout view = holder.getView(R.id.layout);
        view.addView(view);
        Log.e("monitor", "convert: ");
    }

}
