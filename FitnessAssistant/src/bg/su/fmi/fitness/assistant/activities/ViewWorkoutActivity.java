package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.adapters.Day;
import bg.su.fmi.fitness.assistant.adapters.DayAdapter;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.storage.WorkoutsDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class ViewWorkoutActivity extends ListActivity{
	
	private Workout workout;
	private ArrayList<Day> days;
	
	private DayAdapter adapter;
	private WorkoutsDataSourse dataSource;
	private WorkoutsExersizesDataSourse weDataSource;
	
	private Intent intent;
	
	public WorkoutsDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new WorkoutsDataSourse(this);
		}
		return dataSource;
	}
	
	public WorkoutsExersizesDataSourse getWeDataSource()
	{
		if(weDataSource == null)
		{
			weDataSource = new WorkoutsExersizesDataSourse(this);
		}
		return weDataSource;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_workout);
		
		intent = getIntent();
		workout = (Workout) intent.getSerializableExtra(Tools.CURRENT_WORKOUT_EXTRA);
		
		loadFields();
		
		days = getWorkoutDays(workout.getId());
		adapter = new DayAdapter(this, days);
		setListAdapter(adapter);
		//TODO start LiveTimeWorkout
	}
	
	
	private void loadFields()
	{
		TextView workoutName = (TextView) findViewById(R.id.workout_name_content);
		workoutName.setText(workout.getName());
		TextView genderTextView = (TextView) findViewById(R.id.gender_value);
		genderTextView.setText(workout.getGender());
		TextView typeTextView = (TextView) findViewById(R.id.type_value);
		typeTextView.setText(workout.getType());
		TextView durationTextView = (TextView) findViewById(R.id.duration_value);
		durationTextView.setText(Tools.stringIntegerValue(workout.getDuration()));
		
		//TODO start ViewDietActivity
				TextView dietTextView = (TextView) findViewById(R.id.diet_value);
				dietTextView.setText("diet");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.workouts_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.edit_workout) {
			//TODO open activity
			return true;
		} else if(item.getItemId() == R.id.delete_workout)
		{
			int rowsDeleted = deleteWorkout();
			if (rowsDeleted > 0) {
				Toast.makeText(getApplicationContext(), "Workout deleted.",
						Toast.LENGTH_SHORT).show();
				intent.putExtra(Tools.DELETED_WORKOUT_EXTRA, workout);
				setResult(RESULT_OK, intent);
				finish();
				
			return true;
			}
			Toast.makeText(getApplicationContext(),
					"Workout cannot be deleted.", Toast.LENGTH_SHORT).show();
			return false;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	private ArrayList<Day> getWorkoutDays(long workoutId)
	{
		//TODO
		return null;
	}
	
	private int deleteWorkout() {
		getDataSource().open();
		//TODO DELETE workoutExercise
		
		return getDataSource().deleteWorkout(workout.getId());
	}
	
	
	
}
