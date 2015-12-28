package com.ahmad.nabih.skypressprototype;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class NewsListingPopulator extends AsyncTask <String, Double, List<HashMap<String, String>>> {
	private String strURL = "http://skypressiq.net/localnews.xml";
	private String strLabel = null;
	private List<String> latestNewsImgURLs = new ArrayList<String>();
	Activity activity;
	private ProgressDialog progressDialog;

	public NewsListingPopulator (Activity activity, String strURL, String strLabel){
		this.activity = activity;
		this.strURL = strURL;
		this.strLabel = strLabel;
	}

	public List<String> getLatestNewsImgURLs() {
		return latestNewsImgURLs;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(this.activity);
		progressDialog.setMessage(MainActivity.LOADING);
		progressDialog.show();
	}

	@Override
	protected List<HashMap<String, String>> doInBackground(String... params) {
//		try {
//			Thread.sleep((long) 2000.0);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return runRSSThread(strURL);
	}

	protected void onPostExecute(final List<HashMap<String, String>> result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		TextView newsTitle = (TextView) activity.findViewById(R.id.news_title);
		newsTitle.setText(strLabel);

		if (result != null && result.size() > 0) {
			ListView list = (ListView) activity.findViewById(R.id.news_listing);
			List<String> listOfTitles = new ArrayList<String>();
			List<String> listOfImgURLs = new ArrayList<String>();
			for (int i = 0; result != null && i < result.size(); i++) {
				HashMap<String, String> tmpMap = result.get(i);
				if (tmpMap.containsKey("title") && tmpMap.get("title") != null)
					listOfTitles.add(tmpMap.get("title"));
				if (tmpMap.containsKey("imgURL") && tmpMap.get("imgURL") != null)
					listOfImgURLs.add(tmpMap.get("imgURL"));
			}

			TextView latestNewsTextView = (TextView) activity.findViewById(R.id.latest_news_TextView);
			latestNewsTextView.setText(listOfTitles.get(0));

			ImageView imageView = (ImageView) activity.findViewById(R.id.latest_news_ImageView);
			NewsImagesFetcher newsImagesFetcher = new NewsImagesFetcher(imageView);
			newsImagesFetcher.execute("http://skypressiq.net/uploads/posts/2015-12/1451228958_nb-129574-635634868083750280.jpg");

			list.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, listOfTitles));

			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View titleClicked, int position, long id) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, String> tmpMap = result.get(position);
					String mainNewsTitle = tmpMap.get("title");
					if (mainNewsTitle != null)
						intent.putExtra("mainNewsTitle", mainNewsTitle);
					String mainNewsDate = tmpMap.get("pubDate");
					if (mainNewsDate != null)
						intent.putExtra("mainNewsDate", mainNewsDate);
					String mainNewsDescription = tmpMap.get("description");
					if (mainNewsDescription != null)
						intent.putExtra("mainNewsDescription", mainNewsDescription);
					activity.startActivity(intent);
				}
			});
		} else {
			List<String> tmpList = new ArrayList<String>();
			tmpList.add(MainActivity.GENERAL_EXCEPTION);
			ListView list = (ListView) activity.findViewById(R.id.news_listing);
			list.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, tmpList));

			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View titleClicked, int position, long id) {
				}
			});
		}
	}

	private List<HashMap<String, String>> runRSSThread(String urlString) {
		List<HashMap<String, String>> response = null;
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
			System.out.println(e.getMessage());
		} finally {
			conn.disconnect();
		}
		return response;
	}

	public ArrayList<HashMap<String, String>> parseXMLAndStoreIt(XmlPullParser myParser) {
		int event;
		ArrayList<HashMap<String, String>> listOfNews = new ArrayList<HashMap<String, String>>();
		try {
			event = myParser.getEventType();
			HashMap<String, String> tmpMap = new HashMap<String, String>();
			String text = null;
			while (event != XmlPullParser.END_DOCUMENT) {
				String name = myParser.getName();
				switch (event) {
					case XmlPullParser.START_TAG:
						if (name.equals("enclosure")) {
							for (int i = 0 ; i < myParser.getAttributeCount(); i++) {
								String tmpAttrName = myParser.getAttributeName(i);
								if (tmpAttrName != null && tmpAttrName.equalsIgnoreCase("url")) {
									tmpMap.put("imgURL", myParser.getAttributeValue(i));
								}
							}
						}
						break;
					case XmlPullParser.TEXT:
						text = myParser.getText();
						break;
					case XmlPullParser.END_TAG:
						if (name.equals("title")) {
							tmpMap.put(name, text);
						} else if (name.equals("link")) {
							tmpMap.put(name, text);
						} else if (name.equals("description")) {
							tmpMap.put(name, text);
						} else if (name.equals("pubDate")) {
							tmpMap.put(name, text);
						} else if (name.equals("item")) {
							listOfNews.add(tmpMap);
							tmpMap = new HashMap<String, String>();
							text = null;
						} else {
							tmpMap.put(name, text);
						}
						break;
				}
				event = myParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d(this.getClass().getName(), "parseXMLAndStoreIt() returned: " + listOfNews);
		return listOfNews;
	}
}