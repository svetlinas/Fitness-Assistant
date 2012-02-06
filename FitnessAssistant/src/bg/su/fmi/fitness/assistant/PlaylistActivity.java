package bg.su.fmi.fitness.assistant;

import java.util.ArrayList;

import bg.su.fmi.fitness.assistant.PlaylistAdapter;
import bg.su.fmi.fitness.assistant.TrackInfo;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
public class PlaylistActivity extends ListActivity {
	
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
		cursor.moveToFirst();
		while (!cursor.isLast()) {
			path = cursor.getString(0);
			name = cursor.getString(1);
			tracks.add(new TrackInfo(name, path));
			cursor.moveToNext();
		}
		return tracks;
	}
		
}
