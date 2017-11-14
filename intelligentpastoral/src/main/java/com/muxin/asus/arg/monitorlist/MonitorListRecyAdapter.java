package com.muxin.asus.arg.monitorlist;

import android.content.Context;
import android.widget.TextView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.bean.MonitorResponse;
import com.muxin.asus.arg.common.BaseRecyAdapter;
import com.muxin.asus.arg.common.BaseViewHolder;

public class MonitorListRecyAdapter extends BaseRecyAdapter<MonitorResponse> {

	public MonitorListRecyAdapter(Context context, MonitorResponse[] datas) {
		super(context, datas);
	}

	@Override
	public int getLayoutId() {
		return R.layout.stationlist_item;
	}

	@Override
	public void convert(BaseViewHolder holder, MonitorResponse t) {
		((TextView) holder.getView(R.id.sensorlist_tv_station)).setText( t.getMonitorname());
		((TextView) holder.getView(R.id.sensorlist_tv_address)).setText(mContext.getString(R.string.sensorlist_address, t
				.getSitename()));
		((TextView) holder.getView(R.id.sensorlist_tv_manager)).setText("");
	}
}
