package com.muxin.asus.arg.monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.common.utils.PlaySurfaceView;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Author:   Lianwei Bu
 * Date:     2016/7/1
 * Description:
 */
public class PopAdapter extends ArrayAdapter<PlaySurfaceView> {
    private Context mContext;
    private LayoutInflater mInflater;
    private PlaySurfaceView[] mViews;

    public PopAdapter(Context context, PlaySurfaceView[] views) {
        super(context, 0);
        mContext = context;
        mViews = views;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mViews == null ? 0 : mViews.length;
    }

    @Override
    public PlaySurfaceView getItem(int position) {
        return mViews[position];
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pop_item, parent, false);
            hodler = new ViewHodler();
            hodler.name = (TextView) convertView.findViewById(R.id.pop_item_tv);
            convertView.setTag(hodler);
            AutoUtils.autoSize(convertView);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        PlaySurfaceView view = mViews[position];
        if (view != null) {
            hodler.name.setText(view.getChanName());
        }

        return convertView;
    }

    class ViewHodler {
        private TextView name;
    }
}
