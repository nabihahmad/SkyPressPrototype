package com.iq.net.skypress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Thread timerThread = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					String parseDataStr = getIntent().getStringExtra("com.parse.Data");
					Intent intent = new Intent(SplashScreen.this, MainActivity.class);
					if(parseDataStr != null && !parseDataStr.equals(""))
						intent.putExtra("com.parse.Data", parseDataStr);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}
			}
		};
		timerThread.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}