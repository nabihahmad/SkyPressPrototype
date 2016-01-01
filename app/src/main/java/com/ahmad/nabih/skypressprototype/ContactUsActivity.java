package com.ahmad.nabih.skypressprototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;

public class ContactUsActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_us);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		WebView webView = (WebView) findViewById(R.id.contactUs_WebView);
//		webView.setInitialScale(1);
//		webView.setWebChromeClient(new WebChromeClient());
//		webView.getSettings().setAllowFileAccess(true);
//		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//		webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
//		webView.setWebViewClient(new WebViewClient());
//		webView.getSettings().setJavaScriptEnabled(true);
//		webView.getSettings().setLoadWithOverviewMode(true);
//		webView.getSettings().setUseWideViewPort(true);
//		DisplayMetrics displaymetrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//		int height = displaymetrics.heightPixels;
//		int width = displaymetrics.widthPixels;
//
//		Log.e(SimpleBillsConstants.SIMPLE_BILLS, width + "-" + height);

		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.setInitialScale(1);
//		webView.loadData("<iframe src=\"http://www.skypressiq.net\"></iframe>", "text/html", "utf-8");
		webView.loadUrl("http://skypressiq.net/sendme.html");

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
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
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		Intent mainActivity = new Intent(ContactUsActivity.this, MainActivity.class);
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
		}
		mainActivity.putExtras(parameters);
		startActivity(mainActivity);
		finish();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
