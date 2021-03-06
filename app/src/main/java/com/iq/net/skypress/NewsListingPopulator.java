package com.iq.net.skypress;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class NewsListingPopulator extends AsyncTask <String, Double, JSONObject> {
	private String strURL = "http://skypressiq.net/localnews.xml";
	private String strLabel = null;
	private String id = null;
	private boolean isNoInternetConnection = false;
	private boolean isXMLParserException = false;
	Activity activity;
	private ProgressDialog progressDialog;
	private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

	public NewsListingPopulator (Activity activity, String strURL, String strLabel, String id){
		this.activity = activity;
		this.strURL = strURL;
		this.strLabel = strLabel;
		this.id = id;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(this.activity);
		progressDialog.setMessage(MainActivity.LOADING);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		JSONObject result = new JSONObject();
		List<HashMap<String, Object>> allNewsListResult = runRSSThread(strURL);
		try {
			result.put("allNewsList", allNewsListResult);
		} catch (JSONException e) {
		}
		if(this.id != null){
			List<HashMap<String, Object>> mainNewsListResult = runRSSThread("http://skypressiq.net/" + this.id + "-rss.xml");
			try {
				result.put("mainNewsList", mainNewsListResult);
			} catch (JSONException e) {
			}
		}
		return result;
	}

	protected void onPostExecute(final JSONObject resultLists) {
		super.onPostExecute(resultLists);
		TextView newsTitle = (TextView) activity.findViewById(R.id.news_title);
		newsTitle.setText(strLabel);
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> mainNewsResult = new ArrayList<HashMap<String, Object>>();

		if(resultLists.has("allNewsList"))
			try {
				result = (List<HashMap<String, Object>>)resultLists.get("allNewsList");
			} catch (JSONException e) {
			}
		if(resultLists.has("mainNewsList"))
			try {
				mainNewsResult = (List<HashMap<String, Object>>)resultLists.get("mainNewsList");
			} catch (JSONException e) {
			}
		if (result != null && result.size() > 0) {
			ListView list = (ListView) activity.findViewById(R.id.news_listing);
//			String strNewsTicker = " [img src=logo_news_ticker/]  ";
//			for (int i = 0; result != null && i < result.size(); i++) {
//				HashMap<String, Object> tmpMap = result.get(i);
//				if (tmpMap.containsKey("title") && tmpMap.get("title") != null) {
//					strNewsTicker += tmpMap.get("title") + "  [img src=logo_news_ticker/]  ";
//				}
//			}
//			@SuppressLint("WrongViewCast")
//			TextViewWithImages newsTickerTextView = (TextViewWithImages) activity.findViewById(R.id.news_ticker);
//			newsTickerTextView.setSelected(true);
//			newsTickerTextView.setText(strNewsTicker, TextView.BufferType.SPANNABLE);

			final List<HashMap<String, Object>> finalResult = result;
			@SuppressLint("WrongViewCast")
			TextViewWithCustomFont latestNewsTextView = (TextViewWithCustomFont) activity.findViewById(R.id.latest_news_TextView_0);
//			latestNewsTextView.setText("[img src=logo_news_ticker/] " + result.get(0).get("title"));
			latestNewsTextView.setText((String) result.get(0).get("title"));
			latestNewsTextView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, Object> tmpMap = finalResult.get(0);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			@SuppressLint("WrongViewCast")
			TextViewWithCustomFont latestNewsTextView1 = (TextViewWithCustomFont) activity.findViewById(R.id.latest_news_TextView_1);
//			latestNewsTextView1.setText("[img src=logo_news_ticker/] " + result.get(1).get("title"));
			latestNewsTextView1.setText((String) result.get(1).get("title"));
			latestNewsTextView1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, Object> tmpMap = finalResult.get(1);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			@SuppressLint("WrongViewCast")
			TextViewWithCustomFont latestNewsTextView2 = (TextViewWithCustomFont) activity.findViewById(R.id.latest_news_TextView_2);
//			latestNewsTextView2.setText("[img src=logo_news_ticker/] " + result.get(2).get("title"));
			latestNewsTextView2.setText((String) result.get(2).get("title"));
			latestNewsTextView2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, Object> tmpMap = finalResult.get(2);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			@SuppressLint("WrongViewCast")
			TextViewWithCustomFont latestNewsTextView3 = (TextViewWithCustomFont) activity.findViewById(R.id.latest_news_TextView_3);
//			latestNewsTextView3.setText("[img src=logo_news_ticker/] " + result.get(3).get("title"));
			latestNewsTextView3.setText((String) result.get(3).get("title"));
			latestNewsTextView3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, Object> tmpMap = finalResult.get(3);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);
				}
			});

			ImageView imageView = (ImageView) activity.findViewById(R.id.latest_news_ImageView_0);
			NewsImagesFetcher newsImagesFetcher = new NewsImagesFetcher(imageView, false, 0, 0, false);
			newsImagesFetcher.execute(((ArrayList<String>) result.get(0).get("listImgURLs")).get(0));

			ImageView imageView1 = (ImageView) activity.findViewById(R.id.latest_news_ImageView_1);
			NewsImagesFetcher newsImagesFetcher1 = new NewsImagesFetcher(imageView1, false, 0, 0, false);
			newsImagesFetcher1.execute(((ArrayList<String>) result.get(1).get("listImgURLs")).get(0));

			ImageView imageView2 = (ImageView) activity.findViewById(R.id.latest_news_ImageView_2);
			NewsImagesFetcher newsImagesFetcher2 = new NewsImagesFetcher(imageView2, false, 0, 0, false);
			newsImagesFetcher2.execute(((ArrayList<String>) result.get(2).get("listImgURLs")).get(0));

			ImageView imageView3 = (ImageView) activity.findViewById(R.id.latest_news_ImageView_3);
			NewsImagesFetcher newsImagesFetcher3 = new NewsImagesFetcher(imageView3, false, 0, 0, false);
			newsImagesFetcher3.execute(((ArrayList<String>) result.get(3).get("listImgURLs")).get(0));

			CustomAdapter customAdapter = new CustomAdapter(activity, result);
			list.setAdapter(customAdapter);

			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View titleClicked, int position, long id) {
					Intent intent = new Intent(activity, MainNewsActivity.class);
					HashMap<String, Object> tmpMap = finalResult.get(position);
					intent = setIntentStringsFromMap(intent, tmpMap);
					activity.startActivity(intent);

				}
			});

//			activity.findViewById(R.id.news_ticker).setVisibility(View.VISIBLE);
			newsTitle.setVisibility(View.VISIBLE);
			ViewFlipper viewFlipper = (ViewFlipper) activity.findViewById(R.id.latest_news_view_flipper);
			viewFlipper.setVisibility(View.VISIBLE);
			activity.findViewById(R.id.no_connectivity_image).setVisibility(View.GONE);
			activity.findViewById(R.id.exception_image).setVisibility(View.GONE);
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			public void run() {
				progressDialog.dismiss();
			}
		}, 3000);

			if(this.id != null && mainNewsResult.size() != 0){
				HashMap<String, Object> tmpMap = mainNewsResult.get(0);
				Intent intent = new Intent(activity, MainNewsActivity.class);
				intent = setIntentStringsFromMap(intent, tmpMap);
				activity.startActivity(intent);
			}
			final SharedPreferences getPrefs = PreferenceManager
					.getDefaultSharedPreferences(activity.getBaseContext());
			boolean isFirstStart = getPrefs.getBoolean("isFirstStart", true);
			if(isFirstStart) {
				SharedPreferences.Editor editor = getPrefs.edit();
				editor.putBoolean("isFirstStart", false);
				editor.commit();
				TextView customTitle = new TextView(activity);
				customTitle.setText(activity.getResources().getString(R.string.notifications_dialog_title));
				customTitle.setGravity(Gravity.RIGHT);
				customTitle.setPadding(10, 10, 10, 10);
				customTitle.setTextSize(23);
				new AlertDialog.Builder(activity).setCustomTitle(customTitle)
						.setMessage(activity.getResources().getString(R.string.notifications_dialog_message))
						.setNegativeButton(activity.getResources().getString(R.string.notifications_dialog_cancel), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						}).setPositiveButton(activity.getResources().getString(R.string.notifications_dialog_activate), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						SharedPreferences.Editor editor = getPrefs.edit();
						editor.putBoolean("notifications_new_message", true);
						editor.commit();
						ParseUtils.registerParse(activity.getBaseContext());
					}
				}).setCancelable(false).show();
			}
		} else {
			if(isNoInternetConnection) {
				List<String> tmpList = new ArrayList<String>();
				tmpList.add(MainActivity.NO_CONNECTIVITY);
				ListView list = (ListView) activity.findViewById(R.id.news_listing);
				list.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, tmpList));

				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View titleClicked, int position, long id) {
					}
				});
			}

//			if(activity.findViewById(R.id.news_ticker) != null)
//				activity.findViewById(R.id.news_ticker).setVisibility(View.GONE);
			if(newsTitle != null)
				newsTitle.setVisibility(View.GONE);
			ViewFlipper viewFlipper = (ViewFlipper) activity.findViewById(R.id.latest_news_view_flipper);
			if(viewFlipper != null)
				viewFlipper.setVisibility(View.GONE);

			progressDialog.dismiss();
			if(isNoInternetConnection)
				activity.findViewById(R.id.no_connectivity_image).setVisibility(View.VISIBLE);
			else
				activity.findViewById(R.id.exception_image).setVisibility(View.VISIBLE);

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(MainActivity.GENERAL_EXCEPTION);
			builder.setCancelable(true);
			AlertDialog alert = builder.create();
			alert.show();
		}
		activity.findViewById(R.id.main_scroll_view).scrollTo(0, 0);
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
						if (name.equalsIgnoreCase("title") || name.equalsIgnoreCase("link") || name.equalsIgnoreCase("category")
								|| name.equalsIgnoreCase("pubDate") || name.equalsIgnoreCase("video")) {
							tmpMap.put(name, text);
						} else if (name.equalsIgnoreCase("full") || name.equalsIgnoreCase("description")) {
							text = text.replaceAll("&nbsp;", "").replaceAll("&raquo;","").replaceAll("&laquo;","")
									.replaceAll("&rdquo;","").replaceAll("&ldquo;","");
							tmpMap.put(name, text);
						} else if (name.equalsIgnoreCase("item")) {
							tmpMap.put("listImgURLs", listImgURLs);
							if(tmpMap.containsKey("link")){
								String mainNewsLink = (String) tmpMap.get("link");
								if(mainNewsLink != null && !mainNewsLink.equals("")){
									String mainNewsId = mainNewsLink.split("/")[3];
									mainNewsId = mainNewsId.split("-")[0];
									tmpMap.put("id", mainNewsId);
								}
							}
							listOfNews.add(tmpMap);
							tmpMap = new HashMap<String, Object>();
							listImgURLs = new ArrayList<String>();
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
		return listOfNews;
	}

	private Intent setIntentStringsFromMap (Intent intent, HashMap<String, Object> map) {
		String mainNewsTitle = (String) map.get("title");
		if (mainNewsTitle != null)
			intent.putExtra("mainNewsTitle", mainNewsTitle);
		String mainNewsDate = (String) map.get("pubDate");
		if (mainNewsDate != null)
			intent.putExtra("mainNewsDate", mainNewsDate);
		String mainNewsFull = (String) map.get("full");
		if (mainNewsFull != null)
			intent.putExtra("mainNewsFull", mainNewsFull);
		String mainNewsVideo = (String) map.get("video");
		if (mainNewsVideo != null)
			intent.putExtra("mainNewsVideo", mainNewsVideo);
		String mainNewsCategory = (String) map.get("category");
		if (mainNewsCategory != null)
			intent.putExtra("mainNewsCategory", mainNewsCategory);
		ArrayList<String> listImgURLs = (ArrayList<String>) map.get("listImgURLs");
		Iterator<String> imgURLsIterator = listImgURLs.iterator();
		int i = 1;
		while (imgURLsIterator.hasNext()) {
//		for (int i = 1; imgURLsIterator.hasNext(); i++) {
			intent.putExtra("mainNewsImageURL" + i++, imgURLsIterator.next());
		}
		String mainNewsLink = (String) map.get("link");
		if (mainNewsLink != null)
			intent.putExtra("mainNewsLink", mainNewsLink);

		String mainNewsRead = (String) map.get("news_read");
		if(mainNewsRead != null)
			intent.putExtra("mainNewsRead", mainNewsRead);

        String mainNewsId = (String) map.get("id");
		if(mainNewsId != null)
			intent.putExtra("mainNewsId", mainNewsId);

		return intent;
	}
}