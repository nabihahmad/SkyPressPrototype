package com.iq.net.skypress;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewWithCustomFont extends AppCompatTextView {
	public TextViewWithCustomFont(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConfig.REGULAR_FONT));
	}

	public TextViewWithCustomFont(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConfig.REGULAR_FONT));
	}

	public TextViewWithCustomFont(Context context) {
		super(context);
		this.setTypeface(Typeface.createFromAsset(context.getAssets(), AppConfig.REGULAR_FONT));
	}
}
