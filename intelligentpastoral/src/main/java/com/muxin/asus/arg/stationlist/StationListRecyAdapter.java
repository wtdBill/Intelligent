package com.muxin.asus.arg.stationlist;

import android.content.Context;
import android.widget.TextView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.bean.StationResponse;
import com.muxin.asus.arg.common.BaseRecyAdapter;
import com.muxin.asus.arg.common.BaseViewHolder;


public class StationListRecyAdapter extends BaseRecyAdapter<StationResponse> {

	public StationListRecyAdapter(Context context, StationResponse[] datas) {
		super(context, datas);
	}

	@Override
	public int getLayoutId() {
		return R.layout.stationlist_item;
	}

	@Override
	public void convert(BaseViewHolder holder, StationResponse t) {
		((TextView) holder.getView(R.id.sensorlist_tv_station)).setText(t
				.getGatewayname());
		((TextView) holder.getView(R.id.sensorlist_tv_address)).setText(mContext.getString(R.string.sensorlist_address, t
				.getSitename()));
		((TextView) holder.getView(R.id.sensorlist_tv_manager)).setText(mContext.getString(R.string.sensorlist_manager, t
				.getPrincipal()));
	}
}
