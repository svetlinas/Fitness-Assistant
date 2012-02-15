package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.adapters.Day;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class NewWorkoutActivity extends ListActivity{
	private Workout workout;
	private ArrayList<Day> days;
	
	
	private boolean editMode;
	private Intent intent;
	private ArrayAdapter<Day> adapter;
	
	private WorkoutsDataSourse dataSource;
	private WorkoutsExersizesDataSourse weDataSource;
	private ExersizesDataSourse exDataSource;
	
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.new_workout);
		
		intent = getIntent();
		workout = (Workout) intent.getSerializableExtra(Tools.EDIT_WORKOUT_EXTRA);
		
		if(workout != null)
		{
			editMode = true;
			setTitle("Edit Workout");
			setFieldData();
			days = (ArrayList<Day>) intent.getSerializableExtra(Tools.EDIT_WORKOUT_DAYS_EXTRA);
			intent.putExtra(Tools.OLD_WORKOUT_EXTRA, workout);
		}
		else
		{
			setTitle("New Workout");
			workout = new Workout();
			days = new ArrayList<Day>();
		}
		
		adapter = new ArrayAdapter<Day>(this,android.R.layout.simple_list_item_1, days);
		
		setListAdapter(adapter);
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		registerForContextMenu(lv);
		
	}
	
	public void cancel(View view)
	{
		setResult(RESULT_CANCELED, intent);
		finish();
	}
	public void createWorkout(View view)
	{
		getFieldData();
		getDataSource().open();
		getWeDataSource().open();
		
		
		if(editMode)
		{
			getDataSource().updateWorkout(workout);
			intent.putExtra(Tools.EDITED_WORKOUT_EXTRA, workout);
			intent.putExtra(Tools.EDITED_WORKOUT_DAYS_EXTRA, days);
		}
		else
		{
			long newId = getDataSource().addWorkout(workout);
			workout.setId(newId);
			intent.removeExtra(Tools.OLD_WORKOUT_EXTRA);
			intent.putExtra(Tools.NEW_WORKOUT_EXTRA, workout);
		}
		
		getWeDataSource().deleteWorkout(workout.getId());
		for(Day day : days)
		{
			for(Exersize e : day.getExercises())
			{
				getWeDataSource().addWorkoutExercise(workout.getId(), e.getId(), day.getNumber());
			}
		}
		getDataSource().close();
		getWeDataSource().close();
		setResult(RESULT_OK, intent);
		finish();
	}
	
	public void getFieldData()
	{
		EditText nameField = (EditText) findViewById(R.id.workout_name_field);
		EditText genderField = (EditText) findViewById(R.id.workout_gender_field);
		EditText typeField = (EditText) findViewById(R.id.workout_type_field);
		
		workout.setName(nameField.getText().toString());
		workout.setGender(genderField.getText().toString());
		workout.setDuration(days.size());
		workout.setType(typeField.getText().toString());
	}
	
	public void setFieldData()
	{
		EditText nameField = (EditText) findViewById(R.id.workout_name_field);
		EditText genderField = (EditText) findViewById(R.id.workout_gender_field);
		EditText typeField = (EditText) findViewById(R.id.workout_type_field);
		
		nameField.setText(workout.getName());
		genderField.setText(workout.getGender());
		typeField.setText(workout.getType());
		
		
	}
	
	public void startNewDayActivity(int dayNumber)
	{
		Intent intent = new Intent(this, NewDayActivity.class);
		intent.putExtra(Tools.NEW_DAY_NUMBER_EXTRA, dayNumber);
		startActivityForResult(intent, Tools.NEW_DAY_REQUEST_CODE);
	}
	
	public void editDay(Day day)
	{
		Intent intent = new Intent(this, NewDayActivity.class);
		intent.putExtra(Tools.EDIT_DAY_EXTRA, day);
		startActivityForResult(intent, Tools.EDIT_DAY_REQUEST_CODE);
	}
	
	public void removeDay(Day day)
	{
		adapter.remove(day);
		days.remove(day);
		for(Day d : days)
		{
			int number = d.getNumber();
			if(number > day.getNumber())
			{
				d.setNumber(number-1);
			}
		}
	}
	
	public void addDay(View view)
	{
		startNewDayActivity(days.size()+1);
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if(requestCode == Tools.NEW_DAY_REQUEST_CODE)
		{
          if (resultCode == RESULT_OK) 
          {
              Day day = (Day)data.getSerializableExtra(Tools.NEW_DAY_EXTRA);
              adapter.add(day);
              adapter.notifyDataSetChanged();
           
          }
		}
        if(requestCode == Tools.EDIT_DAY_REQUEST_CODE && resultCode == RESULT_OK)
        {
        	Day day = (Day)data.getSerializableExtra(Tools.EDITED_DAY_EXTRA);
        	if(day != null)
        	{
	        	int index = days.indexOf(day);
	        	//Log.v("edit day", Integer.toString(day.getExercises().size()));
	        	days.set(index, day);
	        	adapter.notifyDataSetChanged();
        	}
        }
    } 
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) 
	{
	  if (v.getId() == android.R.id.list) 
	  {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle(days.get(info.position).toString());
	    String[] menuItems = new String[]  {"Edit", "Remove"};
	    for (int i = 0; i<menuItems.length; i++) {
	        menu.add(Menu.NONE, i, i, menuItems[i]);
	      }
	    
	  }
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) 
	{
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int menuItemIndex = item.getItemId();
	  String[] menuItems = new String[]  {"Edit", "Remove"};
	  String menuItemName = menuItems[menuItemIndex];
	  Day selectedDay = days.get(info.position);

	  if(menuItemName == "Edit")
	  {
		  editDay(selectedDay);
	  }
	  else
	  {
		  removeDay(selectedDay);
	  }
	  return true;
	}
}
