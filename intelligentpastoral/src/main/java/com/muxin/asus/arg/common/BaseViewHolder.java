package com.muxin.asus.arg.common;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

public class BaseViewHolder extends ViewHolder implements OnClickListener,OnLongClickListener {
	private SparseArray<View> mViews;
	private View mIteView;
	private BaseRecyAdapter.IOnRecyListener mListener;

	public BaseViewHolder(View itemView, BaseRecyAdapter.IOnRecyListener listener) {
		super(itemView);
		mIteView = itemView;
		mViews = new SparseArray<View>();
		itemView.setOnClickListener(this);
		itemView.setOnLongClickListener(this);
		mListener = listener;
		AutoUtils.autoSize(itemView);
	}

	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mIteView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public BaseViewHolder setText(int viewId, String text) {
		TextView textView = getView(viewId);
		textView.setText(text);
		return this;
	}

	public BaseViewHolder setText(int viewId, int textId) {
		TextView textView = getView(viewId);
		textView.setText(textId);
		return this;
	}

	@Override
	public void onClick(View v) {
		if (mListener != null) {
			mListener.onClickListener(v, getPosition());
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if (mListener != null) {
			mListener.onLongClickListener(v, getPosition());
		}
		return true;
	}
}
