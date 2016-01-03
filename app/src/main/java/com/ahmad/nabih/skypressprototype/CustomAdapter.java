package com.ahmad.nabih.skypressprototype;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	ArrayList<String> result = new ArrayList();
	Context context;
//	ArrayList<Bitmap> imageId = new ArrayList();
	private static LayoutInflater inflater = null;

//	public CustomAdapter(Activity mainActivity, ArrayList<String> prgmNameList, ArrayList<Bitmap> prgmImages) {
	public CustomAdapter(Activity mainActivity, ArrayList<String> prgmNameList) {
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
		holder.tv.setText(result.get(position));

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