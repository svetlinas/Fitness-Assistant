package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.adapters.Day;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.util.Tools;

public class NewWorkoutActivity extends Activity{
	private Workout workout;
	private ArrayList<Day> days;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.new_workout);
		
		days = new ArrayList<Day>();
	}
	
	public void cancel(View view)
	{
		finish();
	}
	public void createWorkout(View view)
	{
		getFieldData();
		
		startNewDayActivity(1);
	}
	
	public void getFieldData()
	{
		EditText nameField = (EditText) findViewById(R.id.workout_name_field);
		EditText genderField = (EditText) findViewById(R.id.workout_gender_field);
		EditText typeField = (EditText) findViewById(R.id.workout_type_field);
		EditText durationField = (EditText) findViewById(R.id.workout_duration_field);
		int duration = Tools.getInteger(durationField.getText());
		workout = new Workout(0, genderField.getText().toString(), typeField.getText().toString(),
								duration, nameField.getText().toString());
	}
	
	public void startNewDayActivity(int dayNumber)
	{
		Intent intent = new Intent(this, NewDayActivity.class);
		intent.putExtra(Tools.NEW_DAY_NUMBER_EXTRA, dayNumber);
		startActivityForResult(intent, Tools.NEW_DAY_REQUEST_CODE);
	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if(requestCode == Tools.NEW_DAY_REQUEST_CODE)
          if (resultCode == RESULT_OK) 
          {
              Day day = (Day)data.getSerializableExtra(Tools.NEW_DAY_EXTRA);
              days.add(day);
              if(day.getNumber() < workout.getDuration())
              {
            	  startNewDayActivity(day.getNumber() + 1);
              }
              else
              {
            	 //ADD WORKOUT TO DB
              }
              
          }
          if(resultCode == RESULT_CANCELED)
          {
        	  finish();
          }
    
    } 
}
