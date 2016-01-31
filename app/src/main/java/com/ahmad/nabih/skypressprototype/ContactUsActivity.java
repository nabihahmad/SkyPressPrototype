package com.ahmad.nabih.skypressprototype;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.api.client.util.StringUtils;

import java.util.Arrays;

public class ContactUsActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	private static String MANDATORY_VALIDATION_MESSAGE;
	private static String EMAIL_VALIDATION_MESSAGE;
	private static String CONTACT_US_EMAIL_SUBJECT;
	private static String CONTACT_US_SENDER_NAME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi_Regular.ttf");
		MANDATORY_VALIDATION_MESSAGE = getResources().getString(R.string.mandatory_field_validation_message);
		EMAIL_VALIDATION_MESSAGE = getResources().getString(R.string.email_field_validation_message);
		CONTACT_US_EMAIL_SUBJECT = getResources().getString(R.string.contact_us_email_subject);
		CONTACT_US_SENDER_NAME = getResources().getString(R.string.contactUs_label);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_us);
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
	}

	private boolean validateEmailEditTextField(EditText editText) {
		boolean validationsPassed = true;
		String editTextString = editText.getText().toString();
		if (TextUtils.isEmpty(editTextString)) {
			validationsPassed = false;
			editText.setError(MANDATORY_VALIDATION_MESSAGE);
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editTextString).matches()) {
			validationsPassed = false;
			editText.setError(EMAIL_VALIDATION_MESSAGE);
		}
		return validationsPassed;
	}

	private boolean validateEditTextField(EditText editText) {
		boolean validationsPassed = true;
		String editTextString = editText.getText().toString();
		if (editTextString == null || editTextString.isEmpty() || editTextString.replaceAll(" ","").isEmpty()) {
			validationsPassed = false;
			editText.setError(MANDATORY_VALIDATION_MESSAGE);
		}
		return validationsPassed;
	}

	private String getEmailBody(String firstName, String lastName, String fromEmail, String address,
								String subject, String body) {
		String strBody = body;
		return strBody;
	}

	public void submitContactUsForm(View button) {
		boolean validationsPassed = true;
		final EditText contactUsFirstNameEditText = (EditText) findViewById(R.id.contact_us_edit_first_name);
		String contactUsFirstName = contactUsFirstNameEditText.getText().toString();
		validationsPassed = validationsPassed && validateEditTextField(contactUsFirstNameEditText);

		final EditText contactUsLastNameEditText = (EditText) findViewById(R.id.contact_us_edit_last_name);
		String contactUsLastName = contactUsLastNameEditText.getText().toString();
		validationsPassed = validationsPassed && validateEditTextField(contactUsLastNameEditText);

		final EditText contactUsEmailEditText = (EditText) findViewById(R.id.contact_us_edit_email);
		String contactUsEmail = contactUsEmailEditText.getText().toString();
		validationsPassed = validationsPassed && validateEmailEditTextField(contactUsEmailEditText);

		final EditText contactUsAddressEditText = (EditText) findViewById(R.id.contact_us_edit_address);
		String contactUsAddress = contactUsAddressEditText.getText().toString();
		validationsPassed = validationsPassed && validateEditTextField(contactUsAddressEditText);

		final Spinner contactUsSubjectSpinner = (Spinner) findViewById(R.id.contact_us_subject);
		String contactUsSubject = contactUsSubjectSpinner.getSelectedItem().toString();

		final EditText contactUsBodyEditText = (EditText) findViewById(R.id.contact_us_edit_body);
		String contactUsBody = contactUsBodyEditText.getText().toString();
		validationsPassed = validationsPassed && validateEditTextField(contactUsBodyEditText);

		if (validationsPassed) {
			String strBody = getEmailBody(contactUsFirstName, contactUsLastName, contactUsEmail,
					contactUsAddress, contactUsSubject, contactUsBody);
			sendContactUsEmail(strBody);
		}
	}

	protected void sendContactUsEmail(String emailBody) {
		new SendMailTask(ContactUsActivity.this).execute(CONTACT_US_SENDER_NAME,
				Arrays.asList(AppConfig.CONTACT_US_EMAIL_RECIPIENTS), CONTACT_US_EMAIL_SUBJECT, emailBody);
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
		MenuItem item = menu.findItem(R.id.action_refresh);
		item.setVisible(false);
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
		Intent mainActivity = new Intent(ContactUsActivity.this, MainActivity.class);
		Bundle parameters = new Bundle();
		int id = item.getItemId();
		if (id == R.id.nav_local) {
			parameters.putString("navLbl", getResources().getString(R.string.local_label));
			parameters.putString("URL", AppConfig.URL_LOCAL);
		} else if (id == R.id.nav_international) {
			parameters.putString("navLbl", getResources().getString(R.string.arab_international_label));
			parameters.putString("URL", AppConfig.URL_ARAB_INTERNATIONAL);
		} else if (id == R.id.nav_economic) {
			parameters.putString("navLbl", getResources().getString(R.string.economic_label));
			parameters.putString("URL", AppConfig.URL_ECONOMIC);
		} else if (id == R.id.nav_analytic) {
			parameters.putString("navLbl", getResources().getString(R.string.analytic_label));
			parameters.putString("URL", AppConfig.URL_ANALYTIC);
		} else if (id == R.id.nav_sport) {
			parameters.putString("navLbl", getResources().getString(R.string.sport_label));
			parameters.putString("URL", AppConfig.URL_SPORT);
		} else if (id == R.id.nav_art) {
			parameters.putString("navLbl", getResources().getString(R.string.art_label));
			parameters.putString("URL", AppConfig.URL_ART);
		} else if (id == R.id.nav_varied) {
			parameters.putString("navLbl", getResources().getString(R.string.varied_label));
			parameters.putString("URL", AppConfig.URL_VARIED);
		} else if (id == R.id.nav_videosite) {
			parameters.putString("navLbl", getResources().getString(R.string.videosite_label));
			parameters.putString("URL", AppConfig.URL_VIDEOSITE);
		} else if (id == R.id.nav_contactUs) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			return true;
		}
		mainActivity.putExtras(parameters);
		startActivity(mainActivity);
		finish();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
