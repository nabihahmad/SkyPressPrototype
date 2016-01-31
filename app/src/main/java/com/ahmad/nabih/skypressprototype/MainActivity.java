package com.ahmad.nabih.skypressprototype;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	public static String GENERAL_EXCEPTION = null;
	public static String LOADING = null;
	public static String NO_DATA = null;
	public static String NO_CONNECTIVITY = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LOADING = getResources().getString(R.string.loading);
		GENERAL_EXCEPTION = getResources().getString(R.string.general_exception);
		NO_DATA = getResources().getString(R.string.no_data);
		NO_CONNECTIVITY = getResources().getString(R.string.no_connectivity);
		Typeface boldFont = Typeface.createFromAsset(getAssets(), AppConfig.BOLD_FONT);
		Typeface regularFont = Typeface.createFromAsset(getAssets(), AppConfig.REGULAR_FONT);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		SpannableString spannableString = new SpannableString(toolbar.getTitle());
		spannableString.setSpan(new CustomTypefaceSpan("", boldFont), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		toolbar.setTitle(spannableString);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		final ViewFlipper newsViewFlipper = (ViewFlipper) findViewById(R.id.latest_news_view_flipper);
		Animation slideInRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
		Animation slideOutLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
		Animation slideInLeftAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
		Animation slideOutRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
		newsViewFlipper.setOnTouchListener(new OnSwipeTouchListener(this, newsViewFlipper, slideInRightAnimation,
				slideOutLeftAnimation, slideInLeftAnimation, slideOutRightAnimation));
		newsViewFlipper.setInAnimation(slideInRightAnimation);
		newsViewFlipper.setOutAnimation(slideOutLeftAnimation);
		newsViewFlipper.setAutoStart(true);
		newsViewFlipper.setFlipInterval(5000);

		FloatingActionButton contactUsFAB = (FloatingActionButton) findViewById(R.id.contactUs_fab);
		contactUsFAB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent contactUsIntent = new Intent(view.getContext(), ContactUsActivity.class);
				startActivity(contactUsIntent);
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
					mNewTitle.setSpan(new CustomTypefaceSpan("", regularFont), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
					subMenuItem.setTitle(mNewTitle);
				}
			}
			//the method we have create in activity
			SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
			mNewTitle.setSpan(new CustomTypefaceSpan("", regularFont), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			menuItem.setTitle(mNewTitle);
		}
		String parseDataStr = getIntent().getStringExtra("com.parse.Data");
		if(parseDataStr != null && !parseDataStr.equals("")){
			try {
				refreshNews(AppConfig.URL_LOCAL, getResources().getString(R.string.local_label));
				Intent intent = new Intent(this, MainNewsActivity.class);
				// TODO: fix the params to read full, video, images, category, as well as imageURL
				JSONObject parseDataJSON = new JSONObject(parseDataStr);
				String mainNewsTitle = parseDataJSON.has("title") ? parseDataJSON.getString("title") : "";
				if (mainNewsTitle != null)
					intent.putExtra("mainNewsTitle", mainNewsTitle);
				String mainNewsDate = parseDataJSON.has("pubDate") ? parseDataJSON.getString("pubDate") : "";
				if (mainNewsDate != null)
					intent.putExtra("mainNewsDate", mainNewsDate);
				String mainNewsDescription = parseDataJSON.has("description") ? parseDataJSON.getString("description") : "";
				if (mainNewsDescription != null)
					intent.putExtra("mainNewsDescription", mainNewsDescription);
				this.startActivity(intent);
			} catch (JSONException e) {
				Log.e("Parse Data JSON Exc", e.toString());
			}
		}else{
			Bundle b = getIntent().getExtras();
			if(b != null){
				String navLbl = b.getString("navLbl");
				String url = b.getString("URL");
				refreshNews(url, navLbl);
			}else
				refreshNews(AppConfig.URL_LOCAL, getResources().getString(R.string.local_label));
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

		if(id == R.id.action_refresh){
			refreshNews(AppConfig.URL_LOCAL, getResources().getString(R.string.local_label));
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		if (id == R.id.nav_local) {
			refreshNews(AppConfig.URL_LOCAL, getResources().getString(R.string.local_label));
		} else if (id == R.id.nav_international) {
			refreshNews(AppConfig.URL_ARAB_INTERNATIONAL, getResources().getString(R.string.arab_international_label));
		} else if (id == R.id.nav_economic) {
			refreshNews(AppConfig.URL_ECONOMIC, getResources().getString(R.string.economic_label));
		} else if (id == R.id.nav_analytic) {
			refreshNews(AppConfig.URL_ANALYTIC, getResources().getString(R.string.analytic_label));
		} else if (id == R.id.nav_sport) {
			refreshNews(AppConfig.URL_SPORT, getResources().getString(R.string.sport_label));
		} else if (id == R.id.nav_art) {
			refreshNews(AppConfig.URL_ART, getResources().getString(R.string.art_label));
		} else if (id == R.id.nav_varied) {
			refreshNews(AppConfig.URL_VARIED, getResources().getString(R.string.varied_label));
		} else if (id == R.id.nav_videosite) {
			refreshNews(AppConfig.URL_VIDEOSITE, getResources().getString(R.string.videosite_label));
		} else if (id == R.id.nav_contactUs) {
			Intent contactUsIntent = new Intent(this, ContactUsActivity.class);
			startActivity(contactUsIntent);
		}else if(id == R.id.nav_aboutUs){
			Intent aboutUsIntent = new Intent(this, AboutUsActivity.class);
			startActivity(aboutUsIntent);
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void refreshNews(String strURL, String strLabel) {
		resetCarouselImages();
		NewsListingPopulator newsListingPopulator = new NewsListingPopulator(this, strURL, strLabel);
		newsListingPopulator.execute();
		findViewById(R.id.main_scroll_view).scrollTo(0, 0);
	}

	private void resetCarouselImages() {
		ImageView imageView0 = (ImageView) this.findViewById(R.id.latest_news_ImageView_0);
		imageView0.setImageResource(R.drawable.camera_icon);
		imageView0.setScaleType(ImageView.ScaleType.CENTER);

		ImageView imageView1 = (ImageView) this.findViewById(R.id.latest_news_ImageView_1);
		imageView1.setImageResource(R.drawable.camera_icon);
		imageView1.setScaleType(ImageView.ScaleType.CENTER);

		ImageView imageView2 = (ImageView) this.findViewById(R.id.latest_news_ImageView_2);
		imageView2.setImageResource(R.drawable.camera_icon);
		imageView2.setScaleType(ImageView.ScaleType.CENTER);

		ImageView imageView3 = (ImageView) this.findViewById(R.id.latest_news_ImageView_3);
		imageView3.setImageResource(R.drawable.camera_icon);
		imageView3.setScaleType(ImageView.ScaleType.CENTER);
	}
}
