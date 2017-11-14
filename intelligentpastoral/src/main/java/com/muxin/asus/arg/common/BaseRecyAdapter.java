package com.muxin.asus.arg.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseRecyAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
	protected Context mContext;
	private Object[] datas;
	private LayoutInflater mInflater;

	public interface IOnRecyListener {
		void onClickListener(View v, int position);
		void onLongClickListener(View v, int position);
	}

	private IOnRecyListener mListener;

	public void setClickListener(IOnRecyListener listener) {
		mListener = listener;
	}
	public void setLongClickListener(IOnRecyListener listener) {
		mListener = listener;
	}

	public BaseRecyAdapter(Context context, Object[] datas) {
		mContext = context;
		this.datas = datas;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getItemCount() {
		return datas == null ? 0 : datas.length;
	}

	@Override
	public void onBindViewHolder(BaseViewHolder holder, int position) {
		convert(holder, (T) datas[position]);
	}

	@Override
	public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (getLayoutId() != 0) {
			View view = mInflater.inflate(getLayoutId(), parent, false);
			BaseViewHolder holder = new BaseViewHolder(view, mListener);
			return holder;
		} else
			return null;
	}

	public abstract int getLayoutId();

	public abstract void convert(BaseViewHolder holder, T t);

}
