package com.ahmad.nabih.skypressprototype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
	Context context;
	private static LayoutInflater inflater = null;

	public CustomAdapter(Activity mainActivity, List<HashMap<String, Object>> prgmNameList) {
		result = prgmNameList;
		context = mainActivity;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return result.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class Holder {
		TextViewWithCustomFont newsTextView;
		TextViewWithCustomFont timeTextView;
		TextViewWithCustomFont dateTextView;
		ImageView imageView;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		//View rowView;
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.custom_news_list_view, null);
			holder.newsTextView = (TextViewWithCustomFont) convertView.findViewById(R.id.custom_view_text);
			HashMap<String, Object> tmpMap = result.get(position);
			holder.newsTextView.setText((String) tmpMap.get("title"));

			String strPubDate = (String) tmpMap.get("pubDate");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE', 'dd MMM yyyy HH:mm:ss '+0000'");
			String strMainNewsDate = "";
			String strMainNewsTime = "";
			String imageURL = "";
			String resizeImageURL = "http://skypressiq.net/resiz/img.php?img=";
			try {
				Date dMainNewsDate = simpleDateFormat.parse(strPubDate);
				simpleDateFormat = new SimpleDateFormat("HH:mm");
				strMainNewsTime = simpleDateFormat.format(dMainNewsDate);

				Calendar calendar = Calendar.getInstance();
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
						0, 0, 0);
				if (calendar.getTime().after(dMainNewsDate)) {
					simpleDateFormat = new SimpleDateFormat("dd/MM");
					strMainNewsDate = simpleDateFormat.format(dMainNewsDate);
				}

			} catch (ParseException e) {
			}
			if (!strMainNewsDate.isEmpty()) {
				holder.dateTextView = (TextViewWithCustomFont) convertView.findViewById(R.id.custom_view_date);
				holder.dateTextView.setText(strMainNewsDate);
			} else {
				holder.dateTextView = (TextViewWithCustomFont) convertView.findViewById(R.id.custom_view_date);
				holder.dateTextView.setVisibility(View.GONE);
			}
			holder.timeTextView = (TextViewWithCustomFont) convertView.findViewById(R.id.custom_view_time);
			holder.timeTextView.setText(strMainNewsTime);

			ImageView imageView = (ImageView) convertView.findViewById(R.id.custom_view_logo);
			NewsImagesFetcher newsImagesFetcher1 = new NewsImagesFetcher(imageView, false, 0, 0, true);
			imageURL = ((ArrayList<String>) tmpMap.get("listImgURLs")).get(0);
			imageURL = imageURL.replace("www.", "");
			resizeImageURL += (imageURL + "&w=100&h=100");

			newsImagesFetcher1.execute(resizeImageURL);
			Log.i("thumbnail", "thumbnail loaded");
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
}