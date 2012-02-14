package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;
import java.util.List;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.adapters.Day;
import bg.su.fmi.fitness.assistant.adapters.DayAdapter;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.entities.WorkoutExersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ViewWorkoutActivity extends ListActivity{
	
	private Workout workout;
	private ArrayList<Day> days;
	
	private DayAdapter adapter;
	private WorkoutsDataSourse dataSource;
	private WorkoutsExersizesDataSourse weDataSource;
	private ExersizesDataSourse exDataSource;
	
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
	
	public ExersizesDataSourse getExDataSource() {
		if (exDataSource == null) {
			exDataSource = new ExersizesDataSourse(this);
		}
		return exDataSource;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_workout);
		setTitle("View Workout");
		intent = getIntent();
		workout = (Workout) intent.getSerializableExtra(Tools.CURRENT_WORKOUT_EXTRA);
		intent.removeExtra(Tools.DELETED_WORKOUT_EXTRA);
		loadFields();
		
		days = getWorkoutDays(workout.getId());
		adapter = new DayAdapter(this, days);
		setListAdapter(adapter);
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		/*
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Day item = (Day) getListAdapter().getItem(position);
				//openLiveTimeWorkout(item);
			}
		});
		*/
		
	}
	
	public void openLiveTimeWorkout(View view)
	{
		Day day = (Day)view.getTag();
		Log.v("livetime", Integer.toString(day.getNumber()));
		//TODO start LiveTimeWorkout from here
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
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.view_workout_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.edit_workout) {
			Intent intent = new Intent(this, NewWorkoutActivity.class);
			intent.putExtra(Tools.EDIT_WORKOUT_EXTRA, workout);
			intent.putExtra(Tools.EDIT_WORKOUT_DAYS_EXTRA, days);
			startActivityForResult(intent, Tools.EDIT_WORKOUT_REQUEST_CODE);
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
		ArrayList<Day> result= new ArrayList<Day>();
		getExDataSource().open();
		getWeDataSource().open();
		for(int i = 1; i <= workout.getDuration(); i++)
		{
			Day nextDay = new Day(i);
			ArrayList<Exersize> dayExercises= new ArrayList<Exersize>();
			List<WorkoutExersize> we = weDataSource.getWorkoutDay(workoutId, i);
			for(WorkoutExersize item : we)
			{
				dayExercises.add(exDataSource.getExercise(item.getExersizeId()));
			}
			nextDay.setExercises(dayExercises);
			result.add(nextDay);
		}
		getExDataSource().close();
		getWeDataSource().close();
		return result;
	}
	
	private int deleteWorkout() {
		getDataSource().open();
		getWeDataSource().open();
		getWeDataSource().deleteWorkout(workout.getId());
		getWeDataSource().close();
		return getDataSource().deleteWorkout(workout.getId());
	}
	
	
	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == Tools.EDIT_WORKOUT_REQUEST_CODE)
		{
			workout =(Workout) data.getSerializableExtra(Tools.EDITED_WORKOUT_EXTRA);
			days = (ArrayList<Day>) data.getSerializableExtra(Tools.EDITED_WORKOUT_DAYS_EXTRA);
			setListAdapter(new DayAdapter(this,days));
			loadFields();
			intent.putExtra(Tools.OLD_WORKOUT_EXTRA, (Workout) data.getSerializableExtra(Tools.OLD_WORKOUT_EXTRA));
			intent.putExtra(Tools.EDITED_WORKOUT_EXTRA, workout);
			setResult(RESULT_OK, intent);
		}
	}
}
