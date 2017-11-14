package com.muxin.asus.arg.common;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.bean.Constants;
import com.muxin.asus.arg.bean.SensorValueBean;

public class SensorRecyAdapter extends BaseRecyAdapter<SensorValueBean> {
	private int[] colors;
	private int[] icons;
	private int[] units;
	private TextView mNameView;
	private TextView mValueView;
	private TextView mUnitView;
	private ImageView mIconView;
	private RelativeLayout mLayout;

	public SensorRecyAdapter(Context context, SensorValueBean[] datas) {
		super(context, datas);
		colors = new int[] { R.color.air_t, R.color.air_h, R.color.soir_t,
				R.color.light };
		units = new int[] { R.string.unit_temp, R.string.unit_humi, R.string.unit_humi,
				R.string.unit_light};
		icons = new int[] { R.drawable.icon_temp, R.drawable.icon_humi,
				R.drawable.icon_humi, R.drawable.icon_light };
	}

	@Override
	public int getLayoutId() {
		return R.layout.sensor_item;
	}

	@Override
	public void convert(BaseViewHolder holder, SensorValueBean t) {
		String name = t.getTransducername();
		mNameView = (TextView) holder.getView(R.id.sensor_tv_name);
		mValueView = (TextView) holder.getView(R.id.sensor_tv_value);
		mUnitView = (TextView) holder.getView(R.id.sensor_tv_unit);
		mIconView = (ImageView) holder.getView(R.id.sensor_iv_icon);
		mLayout = (RelativeLayout) holder.getView(R.id.sensor_layout);
		switch (name) {
		case Constants.AIR_TEMP:
			setParams(0);
			break;
		case Constants.AIR_HUMI:
			setParams(1);
			break;

		case Constants.SOIL_TEMP:
			setParams(2);
			break;
		case Constants.LIGHT:
			setParams(3);
			break;
		}
		mNameView.setText(name);
		mValueView.setText(t.getValue() + "");
	}

	private void setParams(int index) {
		mIconView.setImageResource(icons[index]);
		mLayout.setBackgroundColor(mContext.getResources().getColor(
				colors[index]));
		mNameView.setTextColor(mContext.getResources().getColor(colors[index]));
		mUnitView.setText(mContext.getResources().getString(units[index]));
	}

}
