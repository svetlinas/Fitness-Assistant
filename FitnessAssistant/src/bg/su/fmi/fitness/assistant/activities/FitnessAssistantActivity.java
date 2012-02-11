package bg.su.fmi.fitness.assistant.activities;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.R.id;
import bg.su.fmi.fitness.assistant.R.layout;
import bg.su.fmi.fitness.assistant.R.menu;
import bg.su.fmi.fitness.assistant.player.AudioPlayerController;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class FitnessAssistantActivity extends AudioPlayerController {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
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
    
    private void openSearchActivity()
    {
    	Intent intent = new Intent(this, SearchActivity.class);
    	startActivity(intent);
    }
    
    public void openWorkoutActivity(View view)
    {
//    	TODO
    	Intent intent = new Intent(this, WorkoutsActivity.class);
    	startActivity(intent);
    	System.out.println("workout");
    }
    
    public void openDietActivity(View view)
    {
//    	TODO
//    	Intent intent = new Intent(this, DietActivity.class);
//    	startActivity(intent);
    	System.out.println("diet");
    }
}