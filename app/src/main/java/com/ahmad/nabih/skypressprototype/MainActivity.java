package com.ahmad.nabih.skypressprototype;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.DragEvent;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	public static String URL_LOCAL = "http://skypressiq.net/localnews.xml";
	public static String URL_ARAB_INTERNATIONAL = "http://skypressiq.net/arnews.xml";
	public static String URL_ECONOMIC = "http://skypressiq.net/economicnews.xml";
	public static String URL_ANALYTIC = "http://skypressiq.net/analytic.xml";
	public static String URL_SPORT = "http://skypressiq.net/sportnews.xml";
	public static String URL_ART = "http://skypressiq.net/artnews.xml";
	public static String URL_VARIED = "http://skypressiq.net/variednews.xml";
	public static String URL_VIDEOSITE = "http://skypressiq.net/videosite.xml";
	public static String GENERAL_EXCEPTION = null;
	public static String LOADING = null;
	public static String NO_DATA = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LOADING = getResources().getString(R.string.loading);
		GENERAL_EXCEPTION = getResources().getString(R.string.general_exception);
		NO_DATA = getResources().getString(R.string.no_data);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi_Regular.ttf");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

		// TODO: TEST CODE FOR HIDING IMAGE
//		@SuppressLint("WrongViewCast")
//		TextViewWithImages latestNewsTextView = (TextViewWithImages) findViewById(R.id.latest_news_TextView);
//		latestNewsTextView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				FrameLayout frameLayout = (FrameLayout) findViewById(R.id.latest_news_layout);
//				frameLayout.animate()
//						.translationY(0)
//						.alpha(0.0f)
//						.setListener(new AnimatorListenerAdapter() {
//							@Override
//							public void onAnimationEnd(Animator animation) {
//								super.onAnimationEnd(animation);
//							}
//						});
//				frameLayout.setVisibility(View.GONE);
//			}
//		});

//		ViewSwitcher imageViewSwitcher = (ViewSwitcher) findViewById(R.id.latest_news_view_switcher);
//		ViewSwitcher textViewSwitcher = (ViewSwitcher) findViewById(R.id.latest_news_text_view_switcher);

//		imageViewSwitcher.setOnTouchListener(new OnSwipeTouchListener(this, imageViewSwitcher, textViewSwitcher));

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

//		((ImageView) findViewById(R.id.right_arrow_0)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showPrevious();
//			}
//		});
//
//		((ImageView) findViewById(R.id.right_arrow_1)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showPrevious();
//			}
//		});
//
//		((ImageView) findViewById(R.id.right_arrow_2)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showPrevious();
//			}
//		});
//
//		((ImageView) findViewById(R.id.right_arrow_3)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showPrevious();
//			}
//		});
//
//		((ImageView) findViewById(R.id.left_arrow_0)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showNext();
//			}
//		});
//
//		((ImageView) findViewById(R.id.left_arrow_1)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showNext();
//			}
//		});
//
//		((ImageView) findViewById(R.id.left_arrow_2)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showNext();
//			}
//		});
//
//		((ImageView) findViewById(R.id.left_arrow_3)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				newsViewFlipper.showNext();
//			}
//		});

//		setOnClick(imageViewSwitcher, textViewSwitcher);

//		((ViewSwitcher) findViewById(R.id.latest_news_view_switcher)).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				ViewSwitcher switcher = (ViewSwitcher) v;
//				if (switcher.getDisplayedChild() == 0) {
//					switcher.showNext();
//				} else {
//					switcher.showPrevious();
//				}
//			}
//		});

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
					mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
					subMenuItem.setTitle(mNewTitle);
				}
			}
			//the method we have create in activity
			SpannableString mNewTitle = new SpannableString(menuItem.getTitle());
			mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			menuItem.setTitle(mNewTitle);
		}

		Bundle b = getIntent().getExtras();
		if(b != null){
			String navLbl = b.getString("navLbl");
			String url = b.getString("URL");
			refreshNews(url, navLbl);
		}else
			refreshNews(URL_LOCAL, getResources().getString(R.string.local_label));
	}

//	private void setOnClick(final ViewSwitcher imageViewSwitcher, final ViewSwitcher textViewSwitcher){
//		imageViewSwitcher.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				ViewSwitcher switcher = (ViewSwitcher) v;
//				if (switcher.getDisplayedChild() == 0) {
//					switcher.showNext();
//					textViewSwitcher.showNext();
//				} else {
//					switcher.showPrevious();
//					textViewSwitcher.showPrevious();
//				}
//			}
//		});
//	}

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
		} else if (id == R.id.nav_contactUs) {
			Intent contactUsIntent = new Intent(this, ContactUsActivity.class);
			startActivity(contactUsIntent);
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
