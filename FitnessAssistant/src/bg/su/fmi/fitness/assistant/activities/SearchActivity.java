package bg.su.fmi.fitness.assistant.activities;

import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.R.id;
import bg.su.fmi.fitness.assistant.R.layout;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class SearchActivity extends Activity{
	
	private EditText searchText;
	private ListView resultList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search);
		
		searchText = (EditText) findViewById(R.id.searchText);
		resultList = (ListView) findViewById(R.id.resultList);
	}

	public void searchButtonClicked(View view)
	{
		//TODO:
		//call database for results
		//search for @searchText, populate @resultList
		System.out.println("search button clicked");
	}
	
}
