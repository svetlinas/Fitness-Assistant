package bg.su.fmi.fitness.assistant;

import bg.su.fmi.fitness.assistant.PlaylistActivity;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;

public class AudioPlayerController extends Activity {
	
	private static final String PATH = "path";
	
	private MediaPlayer mediaPlayer;
	private String currentTrack;
	private static Boolean isPaused;
	
	static {
		isPaused = false;
	}
	
	public AudioPlayerController() {
		super();
		mediaPlayer = new MediaPlayer();
	}
		
	public void playHandler(View view) {
    	if (!mediaPlayer.isPlaying()) {
    		try {
    			if (!isPaused) {
    				mediaPlayer.reset();
        			mediaPlayer.setDataSource(currentTrack);
        			mediaPlayer.prepare();
        			isPaused = false;
    			}
    			mediaPlayer.start();    			
			} catch (Exception e) {
				//ignore
			}    		
    	}
    }
    
    public void stopHandler(View view) {
    	if (mediaPlayer.isPlaying() || isPaused) {
    		try {
        		mediaPlayer.stop();
        		isPaused = false;
            } catch (Exception e) {
				//ignore
			}    		
    	}    	
    }
    
    public void pauseHandler(View view) {
    	if (mediaPlayer.isPlaying()) {
    		try {
        		mediaPlayer.pause();
        		isPaused = true;
        	} catch (Exception e) {
				//ignore
			}    		
    	}    	
    }
    
    public void playlistHandler(View view) {
    	startActivityForResult(new Intent(this, PlaylistActivity.class), 1);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			return;
		}
		try {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();				
			}
			currentTrack = data.getStringExtra(PATH);
			mediaPlayer.reset();
			mediaPlayer.setDataSource(currentTrack);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (Exception e) {
			//ignore
		}	
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mediaPlayer.start();
		isPaused = false;				
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
	}
	
}
