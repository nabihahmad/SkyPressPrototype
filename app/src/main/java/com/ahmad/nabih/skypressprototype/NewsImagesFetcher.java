package com.ahmad.nabih.skypressprototype;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class NewsImagesFetcher extends AsyncTask<String, Double, Bitmap> {
	ImageView imageView;
	private ProgressDialog progressDialog;

	public NewsImagesFetcher (ImageView imageView){
		this.imageView = imageView;
	}

	protected void onPreExecute() {
		super.onPreExecute();
//		progressDialog = new ProgressDialog(this.activity);
//		progressDialog.setMessage(MainActivity.LOADING);
//		progressDialog.show();
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		String urldisplay = params[0];
		Bitmap bitmap = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			bitmap = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		return bitmap;
	}

	protected void onPostExecute(final Bitmap result) {
		super.onPostExecute(result);
//		progressDialog.dismiss();
		imageView.setImageBitmap(result);
	}
}