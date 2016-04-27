package com.iq.net.skypress;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IncrementNewsReaders extends AsyncTask<String, Object, List<HashMap<String, Object>>> {

    Activity activity = null;

	public IncrementNewsReaders(Activity activity){
        this.activity = activity;
	}

	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected List<HashMap<String, Object>> doInBackground(String... params) {
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return listOfNews;
    }

    protected void onPostExecute(final List<HashMap<String, Object>> result) {
        super.onPostExecute(result);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.activity.getBaseContext());
        try {
            HashMap<String, Object> tmpMap = result.get(0);
            String mainNewsRead = (String) tmpMap.get("news_read");
            if (mainNewsRead != null) {
                TextView mainNewsReadTextView = (TextView) this.activity.findViewById(R.id.main_news_read);
                mainNewsReadTextView.setText(mainNewsRead);
                mainNewsReadTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(sharedPref.getString("pref_news_title_text_size", "8")));
            } else {
                TextView mainNewsReadTextView = (TextView) this.activity.findViewById(R.id.main_news_read);
                mainNewsReadTextView.setText("0");
                mainNewsReadTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(sharedPref.getString("pref_news_title_text_size", "8")));
            }
        }catch(Exception e){
            TextView mainNewsReadTextView = (TextView) this.activity.findViewById(R.id.main_news_read);
            mainNewsReadTextView.setText("0");
            mainNewsReadTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(sharedPref.getString("pref_news_title_text_size", "8")));
        }
	}
}