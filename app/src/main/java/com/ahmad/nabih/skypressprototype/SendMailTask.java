package com.ahmad.nabih.skypressprototype;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class SendMailTask extends AsyncTask {

	private ProgressDialog statusDialog;
	private Activity sendMailActivity;
	private String CONTACT_US_POST_URL = "http://192.168.0.104:1234/docapp/test.php";

	public SendMailTask(Activity activity) {
		sendMailActivity = activity;
	}

	protected void onPreExecute() {
		statusDialog = new ProgressDialog(sendMailActivity);
		statusDialog.setMessage("Getting ready...");
		statusDialog.setIndeterminate(false);
		statusDialog.setCancelable(false);
		statusDialog.setCanceledOnTouchOutside(false);
		statusDialog.show();
	}

	@Override
	protected Object doInBackground(Object... args) {
//		try {
//			Log.i("SendMailTask", "About to instantiate GMail...");
//			publishProgress("Processing input....");
//			GMail androidEmail = new GMail(args[0].toString(),
//					(List) args[1], args[2].toString(),
//					args[3].toString());
//			publishProgress("Preparing mail message....");
//			androidEmail.createEmailMessage();
//			publishProgress("Sending email....");
//			androidEmail.sendEmail();
//			publishProgress("Email Sent.");
//			Log.i("SendMailTask", "Mail Sent.");
//		} catch (Exception e) {
//			publishProgress(e.getMessage());
//			Log.e("SendMailTask", e.getMessage(), e);
//		}
//		return null;
		InputStream is = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", args[0]);
		params.put("subject", args[1]);
		params.put("message", args[2]);
		HttpURLConnection conn;
		String json = null;
		JSONObject jObj;
		try{
			URL url = new URL(CONTACT_US_POST_URL);
			conn = (HttpURLConnection)url.openConnection();
			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String,Object> param : params.entrySet()) {
				if (postData.length() != 0) postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			OutputStream dos = conn.getOutputStream();
			dos.write(postDataBytes);
			dos.flush();
			dos.close();
			is = conn.getInputStream();
		}catch(Exception e){
			Log.e("URL Connection", e.toString());
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return null;
	}

	@Override
	public void onProgressUpdate(Object... values) {
		statusDialog.setMessage(values[0].toString());
	}

	@Override
	public void onPostExecute(Object result) {
		statusDialog.dismiss();
	}
}
