package bg.su.fmi.fitness.assistant.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import bg.su.fmi.fitness.assistant.R;

public class ViewExerciseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_exercise);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.view_exercise_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.edit_exercise) {
			//TODO: Edit exercise
			return true;
		} 
		if (item.getItemId() == R.id.delete_exercise) {
			//TODO: Delete exercise
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
