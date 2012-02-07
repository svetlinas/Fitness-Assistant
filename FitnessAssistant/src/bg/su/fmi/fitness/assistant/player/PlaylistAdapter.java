package bg.su.fmi.fitness.assistant.player;

import java.util.ArrayList;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.R.id;
import bg.su.fmi.fitness.assistant.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaylistAdapter extends ArrayAdapter<TrackInfo> {
	
	private static final String PATH = "path";
	
	private ArrayList<TrackInfo> tracks;
	private LayoutInflater inflater;
	private Activity parentActivity;
	
    public PlaylistAdapter(Context context, int textViewResourceId, ArrayList<TrackInfo> tracks, LayoutInflater inflater, Activity parent) {
            super(context, textViewResourceId, tracks);
            this.tracks = tracks; 
            this.inflater = inflater;
            this.parentActivity = parent;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = inflater.inflate(R.layout.playlist_item, null);
            }
            final TrackInfo o = tracks.get(position);
            if (o != null) {
                    TextView trackPath = (TextView) v.findViewById(R.id.track_path);
                    if (trackPath != null) {
                        trackPath.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Intent i = new Intent();
								i.putExtra(PATH, o.getPath());
								parentActivity.setResult(1, i);
								parentActivity.finish();						
							}
						});
                    	trackPath.setText(o.getTrackName());
                    }
            }
            return v;
    }
}
