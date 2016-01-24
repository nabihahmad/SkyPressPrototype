package com.ahmad.nabih.skypressprototype;

import android.content.Context;
import android.graphics.Typeface;
import android.preference.ListPreference;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomListPreference extends ListPreference {

	public CustomListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomListPreference(Context context) {
		super(context);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//		if (LinearLayout.class.isInstance(view)) {
//			for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
//				View childView = ((LinearLayout) view).getChildAt(i);
//				if (RelativeLayout.class.isInstance(childView)) {
//					for (int j = 0; j < ((RelativeLayout) childView).getChildCount(); j++) {
//						View grandChildView = ((RelativeLayout) childView).getChildAt(i);
//						if (AppCompatTextView.class.isInstance(grandChildView)) {
//							((AppCompatTextView) grandChildView).setTypeface(Typeface.createFromAsset(getContext().getAssets(), AppConfig.REGULAR_FONT));
//						}
//					}
//				}
//			}
//		}
	}
}