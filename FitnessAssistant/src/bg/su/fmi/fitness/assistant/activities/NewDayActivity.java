package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.adapters.Day;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

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
        
        
        day = (Day)intent.getSerializableExtra(Tools.EDIT_DAY_EXTRA);
        if(day != null)
        {
        	editMode = true;
        	for(Exersize e : day.getExercises())
        	{
        		setTitle("Edit Day");
        		int position = exercises.indexOf(e);
        		listView.setItemChecked(position, true);
        	}
        }
        else
        {
        	setTitle("New Day");
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
			System.out.println(selectedExercises.valueAt(i));
			if(selectedExercises.valueAt(i))
			{
				
				result.add((Exersize)exercises.get(selectedExercises.keyAt(i)));
				
			}
		}
		
		
		day.setExercises(result);
		
		if(editMode)
		{
			intent.putExtra(Tools.EDITED_DAY_EXTRA, day);
		}
		else
		{
			intent.putExtra(Tools.NEW_DAY_EXTRA, day);
		}
		
		setResult(RESULT_OK,intent);        
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
		getDataSource().close();
		return exercises;

	}
}
