package com.muxin.asus.arg.common;


import android.app.ProgressDialog;
import android.content.Context;

import com.muxin.asus.arg.R;

public class LoadingInstance {
	private static ProgressDialog mLoading;

	 public static ProgressDialog getInstance(Context context) {
	 if (mLoading==null) {
	 mLoading = new ProgressDialog(context);
	 mLoading.setMessage(context.getText(R.string.login));
	 mLoading.setCanceledOnTouchOutside(false);
	 mLoading.setCancelable(true);
	 }
	 return mLoading;
	 }
}
