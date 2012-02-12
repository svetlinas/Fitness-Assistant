package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.R.layout;
import bg.su.fmi.fitness.assistant.player.PlaylistAdapter;
import bg.su.fmi.fitness.assistant.player.TrackInfo;
import bg.su.fmi.fitness.assistant.util.Tools;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;

public class PlaylistActivity extends ListActivity implements OnCancelListener {
	
	private static final String NO_SD_CARD_MESSAGE = "No SD card!";
	private static final String NO_AUDIO_TRACKS_MESSAGE = "No audio tracks!";
	
	private ArrayList<TrackInfo> tracks;
	private LayoutInflater inflater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		tracks = getPlaylistItems();
		setContentView(bg.su.fmi.fitness.assistant.R.layout.playlist_main);
	    setListAdapter(new PlaylistAdapter(this, bg.su.fmi.fitness.assistant.R.layout.playlist_item,
	            tracks, inflater, this));	    
	 }
	
	private ArrayList<TrackInfo> getPlaylistItems() {
		ArrayList<TrackInfo> tracks = new ArrayList<TrackInfo>();
		Cursor cursor = null;
		String name;
		String path;
		ContentResolver resolver = getContentResolver();
		String[] projection = {
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.DISPLAY_NAME				
		};
		cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				projection, null, null, null);
		if (cursor == null) {
			showNoAudioTracksDialog(NO_SD_CARD_MESSAGE);
			return tracks;
		}
		if (cursor.moveToFirst()) {
			while (!cursor.isLast()) {
				path = cursor.getString(0);
				name = cursor.getString(1);
				tracks.add(new TrackInfo(name, path));
				cursor.moveToNext();
			}			
		} else {
			showNoAudioTracksDialog(NO_AUDIO_TRACKS_MESSAGE);
		}		
		return tracks;
	}
	
	private void showNoAudioTracksDialog(String message) {
		Dialog dialog = new Dialog(this);
	    dialog.setCancelable(true);
	    dialog.setOnCancelListener(this);
		dialog.setTitle(message);
		dialog.show();
	}

	public void onCancel(DialogInterface dialog) {
		setResult(Tools.NO_AUDIO_TRACKS_RESULT_CODE, null);
		finish();	
	}
		
}
