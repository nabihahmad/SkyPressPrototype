package com.ahmad.nabih.skypressprototype;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AboutUsInfoPopulator extends AsyncTask <String, Double, List<HashMap<String, Object>>> {
	private String aboutUsURL = "http://skypressiq.net/infosite.xml";
	private boolean isNoInternetConnection = false;
	private boolean isXMLParserException = false;
	Activity activity;
	private ProgressDialog progressDialog;
	private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

	public AboutUsInfoPopulator(Activity activity){
		this.activity = activity;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(this.activity);
		progressDialog.setMessage(MainActivity.LOADING);
		progressDialog.show();
	}

	@Override
	protected List<HashMap<String, Object>> doInBackground(String... params) {
		Log.d("URL", aboutUsURL);
		return runRSSThread(aboutUsURL);
	}

	protected void onPostExecute(final List<HashMap<String, Object>> result) {
		super.onPostExecute(result);
		TextView aboutUsTextView = (TextView) activity.findViewById(R.id.about_us_description);
		TextView aboutUsListedTextView = (TextView) activity.findViewById(R.id.about_us_listed);
		TextView aboutUsMailTextView = (TextView) activity.findViewById(R.id.about_us_mail);
		TextView aboutUsMobileTextView = (TextView) activity.findViewById(R.id.about_us_mobile);
		TextView aboutUsMobile2TextView = (TextView) activity.findViewById(R.id.about_us_mobile2);
		TextView aboutUsAddressTextView = (TextView) activity.findViewById(R.id.about_us_address);
		TextView aboutUsFacebookTextView = (TextView) activity.findViewById(R.id.about_us_facebook);
		TextView aboutUsTwitterTextView = (TextView) activity.findViewById(R.id.about_us_twitter);
//		TextView aboutUsTubeTextView = (TextView) activity.findViewById(R.id.about_us_tube);

		if(result != null && result.size() > 0){
			HashMap<String, Object> resultMap = result.get(0);

			aboutUsTextView.setText((String) resultMap.get("about"));
			aboutUsListedTextView.setText((String) resultMap.get("listed"));
			aboutUsMailTextView.setText((String) resultMap.get("mail"));
			String mobile = (String) resultMap.get("mobile");
			if (mobile != null && mobile.startsWith("00"))
				mobile = mobile.replaceFirst("00", "+");
			aboutUsMobileTextView.setText(mobile);
			String mobile2 = (String) resultMap.get("mobile2");
			if (mobile2 != null && mobile2.startsWith("00"))
				mobile2 = mobile2.replaceFirst("00", "+");
			aboutUsMobile2TextView.setText(mobile2);
			aboutUsAddressTextView.setText((String) resultMap.get("address"));
			aboutUsFacebookTextView.setText("http://facebook.com/" + (String) resultMap.get("facebook"));
			aboutUsTwitterTextView.setText("http://twitter.com/" + (String) resultMap.get("twitter"));
//			aboutUsTubeTextView.setText("http://youtube.com/" + (String) resultMap.get("tube"));

			progressDialog.dismiss();
		}else{
			LinearLayout exceptionView = (LinearLayout) activity.findViewById(R.id.about_us_exceptions_view);
			exceptionView.setVisibility(View.VISIBLE);
			ScrollView aboutUsView = (ScrollView) activity.findViewById(R.id.aboutUs_scroll_view);
			aboutUsView.setVisibility(View.GONE);
			activity.findViewById(R.id.aboutUs_title).setVisibility(View.GONE);

			if(isNoInternetConnection) {
				TextView noConnectionTextView = (TextView) activity.findViewById(R.id.about_us_no_connection);
				noConnectionTextView.setText(MainActivity.NO_CONNECTIVITY);
				noConnectionTextView.setVisibility(View.VISIBLE);
			}
			if(isNoInternetConnection)
				activity.findViewById(R.id.about_us_no_connectivity_image).setVisibility(View.VISIBLE);
			else
				activity.findViewById(R.id.about_us_exception_image).setVisibility(View.VISIBLE);
			progressDialog.dismiss();
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(MainActivity.GENERAL_EXCEPTION);
			builder.setCancelable(true);
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	private List<HashMap<String, Object>> runRSSThread(String urlString) {
		List<HashMap<String, Object>> response = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();

			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			// Starts the query
			conn.connect();
			InputStream stream = conn.getInputStream();

			XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
			XmlPullParser myParser = xmlFactoryObject.newPullParser();

			myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			myParser.setInput(stream, "utf-8");

			response = parseXMLAndStoreIt(myParser);
			stream.close();
		} catch (Exception e) {
			isNoInternetConnection = true;
			System.out.println(e.getMessage());
		} finally {
			conn.disconnect();
		}
		return response;
	}

	public ArrayList<HashMap<String, Object>> parseXMLAndStoreIt(XmlPullParser myParser) {
		int event;
		ArrayList<HashMap<String, Object>> listOfNews = new ArrayList<HashMap<String, Object>>();
		try {
			event = myParser.getEventType();
			HashMap<String, Object> tmpMap = new HashMap<String, Object>();
			ArrayList<String> listImgURLs = new ArrayList<String>();
			String text = null;
			while (event != XmlPullParser.END_DOCUMENT) {
				String name = myParser.getName();
				switch (event) {
					case XmlPullParser.START_TAG:
						if (name.equals("enclosure") || name.startsWith("enclosure")) {
							for (int i = 0 ; i < myParser.getAttributeCount(); i++) {
								String tmpAttrName = myParser.getAttributeName(i);
								if (tmpAttrName != null && tmpAttrName.equalsIgnoreCase("url")) {
//									tmpMap.put("imgURL", myParser.getAttributeValue(i));
									listImgURLs.add(myParser.getAttributeValue(i));
								}
							}
						}
						break;
					case XmlPullParser.TEXT:
						text = myParser.getText();
						break;
					case XmlPullParser.END_TAG:
						if (name.equalsIgnoreCase("title") || name.equalsIgnoreCase("listed") || name.equalsIgnoreCase("site")
								|| name.equalsIgnoreCase("mail") || name.equalsIgnoreCase("mobile") || name.equalsIgnoreCase("mobile2") || name.equalsIgnoreCase("address") || name.equalsIgnoreCase("facebook") || name.equalsIgnoreCase("twitter") || name.equalsIgnoreCase("tube")) {
							tmpMap.put(name, text);
						} else if (name.equalsIgnoreCase("about")) {
							text = text.replaceAll("&nbsp;", "");
							tmpMap.put(name, text);
						} else if (name.equalsIgnoreCase("item")) {
							listOfNews.add(tmpMap);
							tmpMap = new HashMap<String, Object>();
							text = null;
						} else {
							tmpMap.put(name, text);
						}
						break;
				}
				event = myParser.next();
			}
		} catch (Exception e) {
			isXMLParserException = true;
			e.printStackTrace();
		}
		Log.d(this.getClass().getName(), "parseXMLAndStoreIt() returned: " + listOfNews);
		return listOfNews;
	}
}