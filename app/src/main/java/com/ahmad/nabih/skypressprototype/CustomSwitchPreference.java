package com.ahmad.nabih.skypressprototype;

import android.content.Context;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.View;

public class CustomSwitchPreference extends SwitchPreference {
	public CustomSwitchPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomSwitchPreference(Context context) {
		super(context);
	}

	@Override
	protected void onBindView(View view) {
		super.onBindView(view);
		view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
	}
}
