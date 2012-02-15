package bg.su.fmi.fitness.assistant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.player.AudioPlayerController;
import bg.su.fmi.fitness.assistant.util.Tools;

public class FitnessAssistantActivity extends Activity {
	
	private AudioPlayerController audioPlayer;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		audioPlayer = new AudioPlayerController();
		setContentView(R.layout.home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.search) {
			openSearchActivity();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void openSearchActivity() {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}

	public void openWorkoutActivity(View view) {
		Intent intent = new Intent(this, WorkoutsActivity.class);
		startActivity(intent);
		System.out.println("workout");
	}

	public void openDietActivity(View view) {
		Intent intent = new Intent(this, DietsActivity.class);
		startActivity(intent);
		System.out.println("diet");
	}
	
    public void playlistHandler(View view) {
    	startActivityForResult(new Intent(this, PlaylistActivity.class), Tools.AUDIO_PLAYER_REQUEST_CODE);
	}
    
    public void playHandler(View view) {
    	audioPlayer.play();
	}
    
    public void pauseHandler(View view) {
    	audioPlayer.pause();
	}
    
    public void stopHandler(View view) {
    	audioPlayer.stop();
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Tools.NO_AUDIO_TRACKS_RESULT_CODE) {
			return;
		}
		audioPlayer.playSelectedTrack(data);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		audioPlayer.onDestroy();
	}
	
	
	
}