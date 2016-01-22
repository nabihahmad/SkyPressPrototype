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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
	Context context;
//	ArrayList<Bitmap> imageId = new ArrayList();
	private static LayoutInflater inflater = null;

//	public CustomAdapter(Activity mainActivity, ArrayList<String> prgmNameList, ArrayList<Bitmap> prgmImages) {
	public CustomAdapter(Activity mainActivity, List<HashMap<String, String>> prgmNameList) {
		result = prgmNameList;
		context = mainActivity;
//		imageId = prgmImages;
		inflater = (LayoutInflater) context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		TextView tv;
//		ImageView img;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = new Holder();
		View rowView;
		rowView = inflater.inflate(R.layout.custom_news_list_view, null);
		holder.tv = (TextView) rowView.findViewById(R.id.custom_view_text);
//		holder.img = (ImageView) rowView.findViewById(R.id.Image);
		HashMap<String, String> tmpMap = result.get(position);
		holder.tv.setText(tmpMap.get("title"));

		String strPubDate = tmpMap.get("pubDate");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE', 'dd MMM yyyy HH:mm:ss '+0000'");
		String strMainNewsDate = "";
		String strMainNewsTime = "";
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
			holder.tv = (TextView) rowView.findViewById(R.id.custom_view_date);
			holder.tv.setText(strMainNewsDate);
		}

		holder.tv = (TextView) rowView.findViewById(R.id.custom_view_time);
		holder.tv.setText(strMainNewsTime);

//		if (imageId.get(position) != null)
//			holder.img.setImageBitmap(imageId.get(position));
		// holder.img.setImageBitmap(imageId.get(position));
		 /*
         rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
            }
        });*/
		return rowView;
	}
}