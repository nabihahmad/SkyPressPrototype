package com.ahmad.nabih.skypressprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputFilter;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_news);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

		String mainNewsTitle = getIntent().getStringExtra("mainNewsTitle");
		if (mainNewsTitle != null) {
			TextView mainNewsTitleTextView = (TextView) findViewById(R.id.main_news_title);
			mainNewsTitleTextView.setFilters(new InputFilter[] { new InputFilter.LengthFilter(mainNewsTitle.length()) });
			mainNewsTitleTextView.setText(mainNewsTitle);
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
			finish();
//			refreshNews(MainActivity.URL_LOCAL, getResources().getString(R.string.local_label));
		} else if (id == R.id.nav_international) {
			finish();
//			refreshNews(MainActivity.URL_ARAB_INTERNATIONAL, getResources().getString(R.string.arab_international_label));
		} else if (id == R.id.nav_economic) {
			finish();
//			refreshNews(URL_ECONOMIC, getResources().getString(R.string.economic_label));
		} else if (id == R.id.nav_analytic) {
			finish();
//			refreshNews(URL_ANALYTIC, getResources().getString(R.string.analytic_label));
		} else if (id == R.id.nav_sport) {
			finish();
//			refreshNews(URL_SPORT, getResources().getString(R.string.sport_label));
		} else if (id == R.id.nav_art) {
			finish();
//			refreshNews(URL_ART, getResources().getString(R.string.art_label));
		} else if (id == R.id.nav_varied) {
			finish();
//			refreshNews(URL_VARIED, getResources().getString(R.string.varied_label));
		} else if (id == R.id.nav_videosite) {
			finish();
//			refreshNews(URL_VIDEOSITE, getResources().getString(R.string.videosite_label));
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}