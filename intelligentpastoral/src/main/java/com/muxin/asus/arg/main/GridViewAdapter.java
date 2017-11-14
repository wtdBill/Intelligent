package com.muxin.asus.arg.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.base.BaseActivity;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;

import java.util.List;
import java.util.Map;

/**
 * Author:   Lianwei Bu
 * Date:     2016/7/26
 * Description:
 */
public class GridViewAdapter extends BaseAdapter {
    private List<Map<String, Integer>> mDatas;
    private FrameLayout mLayout;
    private Context mContext;
    private GridView mGridView;

    public GridViewAdapter(Context context, List<Map<String, Integer>> datas, GridView gridView) {
        mDatas = datas;
        mContext = context;
        mGridView = gridView;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item, parent, false);
        }
        mLayout = (FrameLayout) convertView.findViewById(R.id.layout);
        PlaySurfaceView playSurfaceView = new PlaySurfaceView((BaseActivity) mContext);
        playSurfaceView.setParam(mGridView.getWidth(), mGridView.getHeight(), mDatas.size());
        playSurfaceView.startPreview(mDatas.get(position).get("logid"), mDatas.get(position).get("chan"));
        mLayout.addView(playSurfaceView);
        return convertView;
    }

}
