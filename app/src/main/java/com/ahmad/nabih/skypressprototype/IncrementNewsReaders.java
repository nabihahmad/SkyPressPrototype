package com.ahmad.nabih.skypressprototype;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class IncrementNewsReaders extends AsyncTask<String, Object, Object> {

	public IncrementNewsReaders(){
	}

	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(String... params) {
		String newsID = params[0];
		List<HashMap<String, Object>> response = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://skypressiq.net/" + newsID + "-rss.xml");
			conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			InputStream stream = conn.getInputStream();
			stream.close();
		} catch (Exception e) {
		} finally {
			conn.disconnect();
		}
		return null;
	}

	protected void onPostExecute(final String result) {
		super.onPostExecute(result);
	}
}