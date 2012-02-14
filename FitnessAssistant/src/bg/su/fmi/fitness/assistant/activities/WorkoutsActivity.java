package bg.su.fmi.fitness.assistant.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.storage.WorkoutsDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class WorkoutsActivity extends ListActivity{
	
	private ArrayAdapter<Workout> arrayAdapter;

	private WorkoutsDataSourse dataSource;

	private List<Workout> workouts;

	public WorkoutsDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new WorkoutsDataSourse(this);
		}
		return dataSource;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.workouts);
		setTitle("Workouts");
		workouts = getAllWorkouts();
		arrayAdapter = new ArrayAdapter<Workout>(this,android.R.layout.simple_list_item_1, workouts);
		
		
		setListAdapter(arrayAdapter);
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Workout item = (Workout) getListAdapter().getItem(position);
				openViewWorkoutActivity(item);
			}
		});
	}
	
	
	public List<Workout> getAllWorkouts() {
		getDataSource().close();
		getDataSource().open();
		workouts = getDataSource().getAllWorkouts();
		getDataSource().close();
		return workouts;

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.workouts_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.new_workout) {
			openNewWorkoutActivity();
			return true;
		} else if(item.getItemId() == R.id.view_exercises)
		{
			openExercisesActivity();
			return true;
		}
		else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void openNewWorkoutActivity()
	{
		Intent intent = new Intent(this, NewWorkoutActivity.class);
		startActivityForResult(intent, Tools.NEW_WORKOUT_REQUEST_CODE);
	}
	
	public void openExercisesActivity()
	{
		Intent intent = new Intent(this, ExercisesActivity.class);
		startActivity(intent);
	}
	
	public void openViewWorkoutActivity(Workout item)
	{
		Intent intent = new Intent(this, ViewWorkoutActivity.class);
		intent.putExtra(Tools.CURRENT_WORKOUT_EXTRA, item);
		startActivityForResult(intent, Tools.START_VIEW_WORKOUT_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == Tools.START_VIEW_WORKOUT_REQUEST_CODE)
		{
			Workout deletedWorkout =(Workout) data.getSerializableExtra(Tools.DELETED_WORKOUT_EXTRA);
			if(deletedWorkout != null)
			{
				Log.v("del",deletedWorkout.getType());
				workouts.remove(deletedWorkout);
				arrayAdapter.notifyDataSetChanged();
			}
			else
			{
				Workout editedWorkout = (Workout) data.getSerializableExtra(Tools.EDITED_WORKOUT_EXTRA);
				if(editedWorkout != null)
				{
					Log.v("edit", editedWorkout.getType());
					workouts.remove((Workout) data.getSerializableExtra(Tools.OLD_WORKOUT_EXTRA));
					workouts.add(editedWorkout);
					arrayAdapter.notifyDataSetChanged();
				}
			}
		}
		if(resultCode == RESULT_OK && requestCode == Tools.NEW_WORKOUT_REQUEST_CODE)
		{
			Workout newWorkout = (Workout) data.getSerializableExtra(Tools.NEW_WORKOUT_EXTRA);
			if(newWorkout != null)
			{
				workouts.add(newWorkout);
				arrayAdapter.notifyDataSetChanged();
			}
		}
	}
}
