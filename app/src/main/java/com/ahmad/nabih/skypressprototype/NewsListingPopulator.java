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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsListingPopulator extends AsyncTask <String, Double, List<HashMap<String, String>>> {
	private String strURL = "http://skypressiq.net/localnews.xml";
	private String strLabel = null;
	Activity activity;
	private ProgressDialog progressDialog;
	private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

	public NewsListingPopulator (Activity activity, String strURL, String strLabel){
		this.activity = activity;
		this.strURL = strURL;
		this.strLabel = strLabel;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(this.activity);
		progressDialog.setMessage(MainActivity.LOADING);
		progressDialog.show();
	}

	@Override
	protected List<HashMap<String, String>> doInBackground(String... params) {
		Log.d("URL", strURL);
		return runRSSThread(strURL);
	}

	protected void onPostExecute(final List<HashMap<String, String>> result) {
		super.onPostExecute(result);
		TextView newsTitle = (TextView) activity.findViewById(R.id.news_title);
		newsTitle.setText(strLabel);

		if (result != null && result.size() > 0) {
			ListView list = (ListView) activity.findViewById(R.id.news_listing);
//			ArrayList<String> listOfTitles = new ArrayList<String>();
//			List<String> listOfImgURLs = new ArrayList<String>();
			String strNewsTicker = " [img src=logo_news_ticker/]  ";
			for (int i = 0; result != null && i < result.size(); i++) {
				HashMap<String, String> tmpMap = result.get(i);
				if (tmpMap.containsKey("title") && tmpMap.get("title") != null) {
//					listOfTitles.add(tmpMap.get("title"));
					strNewsTicker += tmpMap.get("title") + "  [img src=logo_news_ticker/]  ";
				}
//				if (tmpMap.containsKey("imgURL") && tmpMap.get("imgURL") != null)
//					listOfImgURLs.add(tmpMap.get("imgURL"));
			}
			@SuppressLint("WrongViewCast")
			TextViewWithImages newsTickerTextView = (TextViewWithImages) activity.findViewById(R.id.news_ticker);
			newsTickerTextView.setSelected(true);
			newsTickerTextView.setText(strNewsTicker, TextView.BufferType.SPANNABLE);

			@SuppressLint("WrongViewCast")
			TextViewWithImages latestNewsTextView = (TextViewWithImages) activity.findViewById(R.id.latest_news_TextView_0);
			latestNewsTextView.setText("[img src=logo_news_ticker/] " + result.get(0).get("title"));
			latestNewsTextView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, String> tmpMap = result.get(0);
//					String mainNewsTitle = tmpMap.get("title");
//					if (mainNewsTitle != null)
//						intent.putExtra("mainNewsTitle", mainNewsTitle);
//					String mainNewsDate = tmpMap.get("pubDate");
//					if (mainNewsDate != null)
//						intent.putExtra("mainNewsDate", mainNewsDate);
//					String mainNewsFull = tmpMap.get("full");
//					if (mainNewsFull != null)
//						intent.putExtra("mainNewsFull", mainNewsFull);
//					String mainNewsVideo = tmpMap.get("video");
//					if (mainNewsVideo != null)
//						intent.putExtra("mainNewsVideo", mainNewsVideo);
//					String mainNewsCategory = tmpMap.get("category");
//					if (mainNewsCategory != null)
//						intent.putExtra("mainNewsCategory", mainNewsCategory);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			@SuppressLint("WrongViewCast")
			TextViewWithImages latestNewsTextView1 = (TextViewWithImages) activity.findViewById(R.id.latest_news_TextView_1);
			latestNewsTextView1.setText("[img src=logo_news_ticker/] " + result.get(1).get("title"));
			latestNewsTextView1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, String> tmpMap = result.get(1);
//					String mainNewsTitle = tmpMap.get("title");
//					if (mainNewsTitle != null)
//						intent.putExtra("mainNewsTitle", mainNewsTitle);
//					String mainNewsDate = tmpMap.get("pubDate");
//					if (mainNewsDate != null)
//						intent.putExtra("mainNewsDate", mainNewsDate);
//					String mainNewsFull = tmpMap.get("full");
//					if (mainNewsFull != null)
//						intent.putExtra("mainNewsFull", mainNewsFull);
//					String mainNewsVideo = tmpMap.get("video");
//					if (mainNewsVideo != null)
//						intent.putExtra("mainNewsVideo", mainNewsVideo);
//					String mainNewsCategory = tmpMap.get("category");
//					if (mainNewsCategory != null)
//						intent.putExtra("mainNewsCategory", mainNewsCategory);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			@SuppressLint("WrongViewCast")
			TextViewWithImages latestNewsTextView2 = (TextViewWithImages) activity.findViewById(R.id.latest_news_TextView_2);
			latestNewsTextView2.setText("[img src=logo_news_ticker/] " + result.get(2).get("title"));
			latestNewsTextView2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, String> tmpMap = result.get(2);
//					String mainNewsTitle = tmpMap.get("title");
//					if (mainNewsTitle != null)
//						intent.putExtra("mainNewsTitle", mainNewsTitle);
//					String mainNewsDate = tmpMap.get("pubDate");
//					if (mainNewsDate != null)
//						intent.putExtra("mainNewsDate", mainNewsDate);
//					String mainNewsFull = tmpMap.get("full");
//					if (mainNewsFull != null)
//						intent.putExtra("mainNewsFull", mainNewsFull);
//					String mainNewsVideo = tmpMap.get("video");
//					if (mainNewsVideo != null)
//						intent.putExtra("mainNewsVideo", mainNewsVideo);
//					String mainNewsCategory = tmpMap.get("category");
//					if (mainNewsCategory != null)
//						intent.putExtra("mainNewsCategory", mainNewsCategory);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			@SuppressLint("WrongViewCast")
			TextViewWithImages latestNewsTextView3 = (TextViewWithImages) activity.findViewById(R.id.latest_news_TextView_3);
			latestNewsTextView3.setText("[img src=logo_news_ticker/] " + result.get(3).get("title"));
			latestNewsTextView3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, String> tmpMap = result.get(3);
//					String mainNewsTitle = tmpMap.get("title");
//					if (mainNewsTitle != null)
//						intent.putExtra("mainNewsTitle", mainNewsTitle);
//					String mainNewsDate = tmpMap.get("pubDate");
//					if (mainNewsDate != null)
//						intent.putExtra("mainNewsDate", mainNewsDate);
//					String mainNewsFull = tmpMap.get("full");
//					if (mainNewsFull != null)
//						intent.putExtra("mainNewsFull", mainNewsFull);
//					String mainNewsVideo = tmpMap.get("video");
//					if (mainNewsVideo != null)
//						intent.putExtra("mainNewsVideo", mainNewsVideo);
//					String mainNewsCategory = tmpMap.get("category");
//					if (mainNewsCategory != null)
//						intent.putExtra("mainNewsCategory", mainNewsCategory);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			ImageView imageView = (ImageView) activity.findViewById(R.id.latest_news_ImageView_0);
			NewsImagesFetcher newsImagesFetcher = new NewsImagesFetcher(imageView, false);
			// TODO: check if the replace is required after the URLs are fixed by Sky Press
			newsImagesFetcher.execute(result.get(0).get("imgURL").replace("/posts/", "/uploads/posts/"));

			ImageView imageView1 = (ImageView) activity.findViewById(R.id.latest_news_ImageView_1);
			NewsImagesFetcher newsImagesFetcher1 = new NewsImagesFetcher(imageView1, false);
			// TODO: check if the replace is required after the URLs are fixed by Sky Press
			newsImagesFetcher1.execute(result.get(1).get("imgURL").replace("/posts/","/uploads/posts/"));

			ImageView imageView2 = (ImageView) activity.findViewById(R.id.latest_news_ImageView_2);
			NewsImagesFetcher newsImagesFetcher2 = new NewsImagesFetcher(imageView2, false);
			// TODO: check if the replace is required after the URLs are fixed by Sky Press
			newsImagesFetcher2.execute(result.get(2).get("imgURL").replace("/posts/","/uploads/posts/"));

			ImageView imageView3 = (ImageView) activity.findViewById(R.id.latest_news_ImageView_3);
			NewsImagesFetcher newsImagesFetcher3 = new NewsImagesFetcher(imageView3, false);
			// TODO: check if the replace is required after the URLs are fixed by Sky Press
			newsImagesFetcher3.execute(result.get(3).get("imgURL").replace("/posts/","/uploads/posts/"));

			CustomAdapter customAdapter = new CustomAdapter(activity, result);
			list.setAdapter(customAdapter);

			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View titleClicked, int position, long id) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, String> tmpMap = result.get(position);
//					String mainNewsTitle = tmpMap.get("title");
//					if (mainNewsTitle != null)
//						intent.putExtra("mainNewsTitle", mainNewsTitle);
//					String mainNewsDate = tmpMap.get("pubDate");
//					if (mainNewsDate != null)
//						intent.putExtra("mainNewsDate", mainNewsDate);
//					String mainNewsFull = tmpMap.get("full");
//					if (mainNewsFull != null)
//						intent.putExtra("mainNewsFull", mainNewsFull);
//					String mainNewsVideo = tmpMap.get("video");
//					if (mainNewsVideo != null)
//						intent.putExtra("mainNewsVideo", mainNewsVideo);
//					String mainNewsCategory = tmpMap.get("category");
//					if (mainNewsCategory != null)
//						intent.putExtra("mainNewsCategory", mainNewsCategory);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});


			activity.findViewById(R.id.news_ticker_layout).setVisibility(View.VISIBLE);
			newsTitle.setVisibility(View.VISIBLE);
			ViewFlipper viewFlipper = (ViewFlipper) activity.findViewById(R.id.latest_news_view_flipper);
			viewFlipper.setVisibility(View.VISIBLE);
			progressDialog.dismiss();
		} else {
			List<String> tmpList = new ArrayList<String>();
			tmpList.add(MainActivity.NO_CONNECTIVITY);
			ListView list = (ListView) activity.findViewById(R.id.news_listing);
			list.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, tmpList));

			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View titleClicked, int position, long id) {
				}
			});


			progressDialog.dismiss();
			activity.findViewById(R.id.no_connectivity_image).setVisibility(View.VISIBLE);
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(MainActivity.GENERAL_EXCEPTION);
			builder.setCancelable(true);
			AlertDialog alert = builder.create();
			alert.show();
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
						if (name.equalsIgnoreCase("enclosure")) {
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
						if (name.equalsIgnoreCase("title") || name.equalsIgnoreCase("link") || name.equalsIgnoreCase("category")
								|| name.equalsIgnoreCase("pubDate") || name.equalsIgnoreCase("video")) {
							tmpMap.put(name, text);
						} else if (name.equalsIgnoreCase("full") || name.equalsIgnoreCase("description")) {
							text = text.replaceAll("&nbsp;", "");
							tmpMap.put(name, text);
						} else if (name.equalsIgnoreCase("item")) {
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

	private Intent setIntentStringsFromMap (Intent intent, HashMap<String, String> map) {
		String mainNewsTitle = map.get("title");
		if (mainNewsTitle != null)
			intent.putExtra("mainNewsTitle", mainNewsTitle);
		String mainNewsDate = map.get("pubDate");
		if (mainNewsDate != null)
			intent.putExtra("mainNewsDate", mainNewsDate);
		String mainNewsFull = map.get("full");
		if (mainNewsFull != null)
			intent.putExtra("mainNewsFull", mainNewsFull);
		String mainNewsVideo = map.get("video");
		if (mainNewsVideo != null)
			intent.putExtra("mainNewsVideo", mainNewsVideo);
		String mainNewsCategory = map.get("category");
		if (mainNewsCategory != null)
			intent.putExtra("mainNewsCategory", mainNewsCategory);
		String mainNewsImageURL = map.get("imgURL");
		if (mainNewsImageURL != null)
			intent.putExtra("mainNewsImageURL", mainNewsImageURL);
		String mainNewsLink = map.get("link");
		if (mainNewsLink != null)
			intent.putExtra("mainNewsLink", mainNewsLink);
		return intent;
	}
}