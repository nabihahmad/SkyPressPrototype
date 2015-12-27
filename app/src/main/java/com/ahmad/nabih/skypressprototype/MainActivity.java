package com.ahmad.nabih.skypressprototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private static String URL_LOCAL = "http://skypressiq.net/localnews.xml";
	private static String URL_ARAB_INTERNATIONAL = "http://skypressiq.net/arnews.xml";
	private static String URL_ECONOMIC = "http://skypressiq.net/economicnews.xml";
	private static String URL_ANALYTIC = "http://skypressiq.net/analytic.xml";
	private static String URL_SPORT = "http://skypressiq.net/sportnews.xml";
	private static String URL_ART = "http://skypressiq.net/artnews.xml";
	private static String URL_VARIED = "http://skypressiq.net/variednews.xml";
	private static String URL_VIDEOSITE = "http://skypressiq.net/videosite.xml";
	public static String GENERAL_EXCEPTION = null;
	public static String LOADING = null;
	public static String NO_DATA = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LOADING = getResources().getString(R.string.loading);
		GENERAL_EXCEPTION = getResources().getString(R.string.general_exception);
		NO_DATA = getResources().getString(R.string.no_data);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

//		FloatingActionButton newsFab = (FloatingActionButton) findViewById(R.id.contactUs_fab);
//		newsFab.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
//			}
//		});

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		refreshNews(URL_LOCAL, getResources().getString(R.string.local_label));
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
		int id = item.getItemId();
		if (id == R.id.nav_local) {
			refreshNews(URL_LOCAL, getResources().getString(R.string.local_label));
		} else if (id == R.id.nav_international) {
			refreshNews(URL_ARAB_INTERNATIONAL, getResources().getString(R.string.arab_international_label));
		} else if (id == R.id.nav_economic) {
			refreshNews(URL_ECONOMIC, getResources().getString(R.string.economic_label));
		} else if (id == R.id.nav_analytic) {
			refreshNews(URL_ANALYTIC, getResources().getString(R.string.analytic_label));
		} else if (id == R.id.nav_sport) {
			refreshNews(URL_SPORT, getResources().getString(R.string.sport_label));
		} else if (id == R.id.nav_art) {
			refreshNews(URL_ART, getResources().getString(R.string.art_label));
		} else if (id == R.id.nav_varied) {
			refreshNews(URL_VARIED, getResources().getString(R.string.varied_label));
		} else if (id == R.id.nav_videosite) {
			refreshNews(URL_VIDEOSITE, getResources().getString(R.string.videosite_label));
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void refreshNews(String strURL, String strLabel) {
		NewsListingPopulator newsListingPopulator = new NewsListingPopulator(this, strURL, strLabel);
		newsListingPopulator.execute();
	}
}
