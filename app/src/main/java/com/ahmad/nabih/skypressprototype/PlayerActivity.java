package com.ahmad.nabih.skypressprototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
	private static final int RECOVERY_REQUEST = 1;
	private YouTubePlayerView youTubeView;
	private static final String YOU_TUBE_API_KEY = "AIzaSyCy3lMKDbEejW4EuCSuCYWfp4OMd3xH7Lo";
	public String strYouTubeID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		youTubeView.initialize(YOU_TUBE_API_KEY, this);

		strYouTubeID = getIntent().getStringExtra("youTubeID");
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo(strYouTubeID); // Plays https://www.youtube.com/watch?v={strYouTubeID}
		}
	}

	@Override
	public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
		} else {
			String error = String.format(getString(R.string.general_exception), errorReason.toString());
			Toast.makeText(this, error, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECOVERY_REQUEST) {
			// Retry initialization if user performed a recovery action
			getYouTubePlayerProvider().initialize(YOU_TUBE_API_KEY, this);
		}
	}

	protected Provider getYouTubePlayerProvider() {
		return youTubeView;
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

}
