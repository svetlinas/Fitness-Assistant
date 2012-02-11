package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Diet;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.entities.SearchedObject;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.storage.DietsDataSourse;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class SearchActivity extends ListActivity{
	
	private EditText searchTextField;
	private String searchText;
//	private ListView resultList;
	
	private ArrayAdapter<String> arrayAdapter;

	private ExersizesDataSourse exersizeDataSource;
	private WorkoutsDataSourse workoutDataSource;
	private DietsDataSourse dietDataSource;
	
	private List<SearchedObject> searchedObjects;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_box);
		
		searchTextField = (EditText) findViewById(R.id.searchText);
//		resultList = (ListView) findViewById(R.id.resultList);
		
		searchedObjects = new ArrayList<SearchedObject>();
	}

	public void searchButtonClicked(View view)
	{
		searchedObjects.clear();
		
		searchText = searchTextField.getText().toString();
		
		searchedObjects.addAll(getSearchedExersizes(searchText));
		searchedObjects.addAll(getSearchedWorkouts(searchText));
		searchedObjects.addAll(getSearchedDiets(searchText));
		
		List<String> displayList = new ArrayList<String>();
		for(SearchedObject so : searchedObjects) {
			displayList.add(so.getName() + " (" + so.getType() + ")");
			System.out.println(so.getName());
		}

		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, displayList);
		setListAdapter(arrayAdapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(searchedObjects.get(position).getType() == "Diet") {
					Intent intent = new Intent(parent.getContext(), ViewDietActivity.class);
					intent.putExtra(Tools.CURRENT_DIET_EXTRA, getDiet(searchedObjects.get(position).getId()));
					startActivity(intent);
				}
				else if(searchedObjects.get(position).getType() == "Workout") {
					Intent intent = new Intent(parent.getContext(), ViewWorkoutActivity.class);
					intent.putExtra(Tools.CURRENT_DIET_EXTRA, getWorkout(searchedObjects.get(position).getId()));
					startActivity(intent);
				}
				else if(searchedObjects.get(position).getType() == "Exersize") {
					Intent intent = new Intent(parent.getContext(), ViewExerciseActivity.class);
					intent.putExtra(Tools.CURRENT_DIET_EXTRA, getExersize(searchedObjects.get(position).getId()));
					startActivity(intent);
				}
			}
		});
	}
	
	public List<SearchedObject> getSearchedExersizes(String name) {
		getExersizeDataSource().open();
		return getExersizeDataSource().getSearchedExersizes(name);

	}
	
	public Exersize getExersize(long id) {
		getExersizeDataSource().open();
		return getExersizeDataSource().getExercise(id);
	}
	
	public ExersizesDataSourse getExersizeDataSource() {
		if (exersizeDataSource == null) {
			exersizeDataSource = new ExersizesDataSourse(this);
		}
		return exersizeDataSource;
	}
	
	public List<SearchedObject> getSearchedWorkouts(String name) {
		getWorkoutDataSource().open();
		return getWorkoutDataSource().getSearchedWorkouts(name);

	}
	
	public Workout getWorkout(long id) {
		getWorkoutDataSource().open();
		return getWorkoutDataSource().getWorkout(id);
	}
	
	public WorkoutsDataSourse getWorkoutDataSource() {
		if (workoutDataSource == null) {
			workoutDataSource = new WorkoutsDataSourse(this);
		}
		return workoutDataSource;
	}
	
	public List<SearchedObject> getSearchedDiets(String name) {
		getDietDataSource().open();
		return getDietDataSource().getSearchedDiets(name);

	}
	
	public Diet getDiet(long id) {
		getDietDataSource().open();
		return getDietDataSource().getDiet(id);
	}
	
	public DietsDataSourse getDietDataSource() {
		if (dietDataSource == null) {
			dietDataSource = new DietsDataSourse(this);
		}
		return dietDataSource;
	}
	
}
