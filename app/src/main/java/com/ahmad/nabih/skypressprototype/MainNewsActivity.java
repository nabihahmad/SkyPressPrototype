package com.ahmad.nabih.skypressprototype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainNewsActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi_Regular.ttf");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_news);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		SpannableString spannableString = new SpannableString(toolbar.getTitle());
		spannableString.setSpan(new CustomTypefaceSpan("", font), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		toolbar.setTitle(spannableString);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		FloatingActionButton contactUsFAB = (FloatingActionButton) findViewById(R.id.contactUs_fab);
		contactUsFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent contactUsIntent = new Intent(view.getContext(), ContactUsActivity.class);
				startActivity(contactUsIntent);
			}
		});

		FloatingActionButton shareFAB = (FloatingActionButton) findViewById(R.id.share_fab);
		shareFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("text/plain");
				share.putExtra(Intent.EXTRA_SUBJECT, view.getResources().getString(R.string.app_name_abbr) + " " + view.getResources().getString(R.string.share_on_social_media_subject));
				share.putExtra(Intent.EXTRA_TEXT, view.getResources().getString(R.string.app_name_abbr) + " " + view.getResources().getString(R.string.share_on_social_media_body) + " " + getIntent().getStringExtra("mainNewsTitle"));
				startActivity(Intent.createChooser(share, view.getResources().getString(R.string.share_on_social_media_label)));
			}
		});

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		Menu menu = navigationView.getMenu();
		for (int i=0;i<menu.size();i++) {
			MenuItem menuItem = menu.getItem(i);
			//for applying a font to subMenu ...
			SubMenu subMenu = menuItem.getSubMenu();
			if (subMenu!=null && subMenu.size() >0 ) {
				for (int j=0; j <subMenu.size();j++) {
					MenuItem subMenuItem = subMenu.getItem(j);
					SpannableString mNewTitle = new SpannableString(subMenuItem.getTitle());
					mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
					subMenuItem.setTitle(mNewTitle);
				}
			}
			//the method we have create in activity
			SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
			mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			menuItem.setTitle(mNewTitle);
		}

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		String mainNewsTitle = getIntent().getStringExtra("mainNewsTitle");
		if (mainNewsTitle != null) {
			TextView mainNewsTitleTextView = (TextView) findViewById(R.id.main_news_title);
			mainNewsTitleTextView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mainNewsTitle.length())});
			mainNewsTitleTextView.setText(mainNewsTitle);
			mainNewsTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, Float.parseFloat(sharedPref.getString("pref_news_title_text_size", "12")));
		}
		String mainNewsDate = getIntent().getStringExtra("mainNewsDate");
		if (mainNewsDate != null) {
			TextView mainNewsDateTextView = (TextView) findViewById(R.id.main_news_date);
			mainNewsDateTextView.setText(mainNewsDate);
		}
		String mainNewsDescription = getIntent().getStringExtra("mainNewsDescription");
		if (mainNewsDescription != null) {
			TextView mainNewsDescriptionTextView = (TextView) findViewById(R.id.main_news_description);
			mainNewsDescriptionTextView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(mainNewsDescription.length()) });
			mainNewsDescriptionTextView.setText(mainNewsDescription);
			mainNewsDescriptionTextView.setTextSize(TypedValue.COMPLEX_UNIT_PT, Float.parseFloat(sharedPref.getString("pref_news_description_text_size", "10")));
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		Intent mainActivity = new Intent(MainNewsActivity.this, MainActivity.class);
		Bundle parameters = new Bundle();
		int id = item.getItemId();
		if (id == R.id.nav_local) {
			parameters.putString("navLbl", getResources().getString(R.string.local_label));
			parameters.putString("URL", MainActivity.URL_LOCAL);
		} else if (id == R.id.nav_international) {
			parameters.putString("navLbl", getResources().getString(R.string.arab_international_label));
			parameters.putString("URL", MainActivity.URL_ARAB_INTERNATIONAL);
		} else if (id == R.id.nav_economic) {
			parameters.putString("navLbl", getResources().getString(R.string.economic_label));
			parameters.putString("URL", MainActivity.URL_ECONOMIC);
		} else if (id == R.id.nav_analytic) {
			parameters.putString("navLbl", getResources().getString(R.string.analytic_label));
			parameters.putString("URL", MainActivity.URL_ANALYTIC);
		} else if (id == R.id.nav_sport) {
			parameters.putString("navLbl", getResources().getString(R.string.sport_label));
			parameters.putString("URL", MainActivity.URL_SPORT);
		} else if (id == R.id.nav_art) {
			parameters.putString("navLbl", getResources().getString(R.string.art_label));
			parameters.putString("URL", MainActivity.URL_ART);
		} else if (id == R.id.nav_varied) {
			parameters.putString("navLbl", getResources().getString(R.string.varied_label));
			parameters.putString("URL", MainActivity.URL_VARIED);
		} else if (id == R.id.nav_videosite) {
			parameters.putString("navLbl", getResources().getString(R.string.videosite_label));
			parameters.putString("URL", MainActivity.URL_VIDEOSITE);
		} else if (id == R.id.nav_contactUs) {
			Intent contactUsIntent = new Intent(this, ContactUsActivity.class);
			startActivity(contactUsIntent);
			return true;
		}
		mainActivity.putExtras(parameters);
		startActivity(mainActivity);
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}