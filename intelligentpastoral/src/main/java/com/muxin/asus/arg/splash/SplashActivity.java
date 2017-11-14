package com.muxin.asus.arg.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.muxin.asus.arg.R;
import com.muxin.asus.arg.login.LoginActivity;


public class SplashActivity extends Activity {

	private long sleepTime = 3 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this,
						LoginActivity.class));
				finish();
			}
		}, sleepTime);
		
	}

}
