package bg.su.fmi.fitness.assistant.player;

import bg.su.fmi.fitness.assistant.activities.PlaylistActivity;
import bg.su.fmi.fitness.assistant.util.Tools;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;

public class AudioPlayerController {
	
	private static final String PATH = "path";
	
	private MediaPlayer mediaPlayer;
	private String currentTrack;
	private boolean isPaused;
		
	public AudioPlayerController() {
		mediaPlayer = new MediaPlayer();
		isPaused  = false;
	}
		
	public void play() {
    	if (!mediaPlayer.isPlaying() && currentTrack != null) {
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
    
    public void stop() {
    	if (mediaPlayer.isPlaying() || isPaused) {
    		try {
        		mediaPlayer.stop();
        		isPaused = false;
            } catch (Exception e) {
				//ignore
			}    		
    	}    	
    }
    
    public void pause() {
    	if (mediaPlayer.isPlaying()) {
    		try {
        		mediaPlayer.pause();
        		isPaused = true;
        	} catch (Exception e) {
				//ignore
			}    		
    	}    	
    }
	
	public void onDestroy() {
		mediaPlayer.release();
	}

	public void playSelectedTrack(Intent data) {
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
	
}
