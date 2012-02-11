package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;
import java.util.List;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.adapters.Day;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewDayActivity extends ListActivity{
	
	private Day day;
	private List<Exersize> exercises;
	
	private boolean editMode;
	private Intent intent;
	
	private ExersizesDataSourse dataSource;
	
	
	
	public ExersizesDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new ExersizesDataSourse(this);
		}
		return dataSource;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        setContentView(R.layout.new_day);
		
        intent = getIntent();
        exercises = getAllExercises();
        
        setListAdapter(new ArrayAdapter<Exersize>(this,
                android.R.layout.simple_list_item_multiple_choice, exercises));

        ListView listView = getListView();

        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        
        day = (Day)intent.getSerializableExtra("EDIT_DAY_EXTRA");
        if(day != null)
        {
        	editMode = true;
        	//TODO show selected exercises
        }
        else
        {
        	day = new Day(getIntent().getIntExtra(Tools.NEW_DAY_NUMBER_EXTRA, 1));
        }
	}
	
	
	public void returnResult(View view)
	{
		ArrayList<Exersize> result = new ArrayList<Exersize>();
		ListView listView = getListView();
		SparseBooleanArray selectedExercises = listView.getCheckedItemPositions();
		for(int i = 0; i < selectedExercises.size(); i++)
		{
			if(selectedExercises.get(i))
			{
				result.add((Exersize)getListAdapter().getItem(selectedExercises.indexOfKey(i)));
			}
		}
		
		Intent resultIntent = new Intent();
		day.setExercises(result);
		resultIntent.putExtra(Tools.NEW_DAY_EXTRA, day);
		setResult(RESULT_OK,resultIntent);        
	    finish();
	}
	public void cancel(View view)
	{
		setResult(RESULT_CANCELED, null);        
	    finish();
	}
	
	public List<Exersize> getAllExercises() {
		getDataSource().open();
		exercises = getDataSource().getAllExercises();
		return exercises;

	}
}
