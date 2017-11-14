package com.muxin.asus.arg.common.utils;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muxin.asus.arg.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MsgDialog extends DialogFragment implements OnClickListener {
	@InjectView(R.id.dialog_msg)
	TextView mMsgTV;
	@InjectView(R.id.dialog_cancle)
	TextView mCancleTV;
	@InjectView(R.id.dialog_sure)
	TextView mSureTV;

	public interface OnSureClick {
		void onSure();
	}

	private static OnSureClick mClick;

	public static MsgDialog getInstance(OnSureClick onSureClick) {
		mClick = onSureClick;
		return new MsgDialog();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		View view = inflater.inflate(R.layout.dialog, container);
		// LinearLayout.LayoutParams layoutParams = new
		// LinearLayout.LayoutParams(
		// 500, 350);
		// view.setLayoutParams(layoutParams);
		ButterKnife.inject(this, view);
		mCancleTV.setOnClickListener(this);
		mSureTV.setOnClickListener(this);
		return view;
	}

	public void onResume() {
		super.onResume();
		getDialog().getWindow().setLayout(500, 320);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ButterKnife.reset(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_cancle:
			MsgDialog.this.dismiss();
			break;
		case R.id.dialog_sure:
			mClick.onSure();
			MsgDialog.this.dismiss();
			break;

		}
	}

}
