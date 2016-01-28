package com.ahmad.nabih.skypressprototype;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;

public class NewsImagesFetcher extends AsyncTask<String, Double, Bitmap> {
	ImageView imageView;
	boolean isMainNewsActivity;
	int imageViewID;
	int imageID;
	private ProgressDialog progressDialog;

	public NewsImagesFetcher (ImageView imageView, boolean isMainNewsActivity, int imageID, int imageViewID){
		this.imageView = imageView;
		this.isMainNewsActivity = isMainNewsActivity;
		this.imageViewID = imageViewID;
		this.imageID = imageID;
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
		if(result != null) {
			imageView.setImageBitmap(result);
			if (FrameLayout.LayoutParams.class.isInstance(imageView.getLayoutParams())) {
				imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT));
			} else if (RelativeLayout.LayoutParams.class.isInstance(imageView.getLayoutParams())) {
				imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.MATCH_PARENT));
			}
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			if (isMainNewsActivity) {
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
				layoutParams.addRule(RelativeLayout.BELOW, this.imageViewID);
				layoutParams.setMargins(0, 20, 0, 0);
				imageView.setLayoutParams(layoutParams);
			}
		}
	}
}