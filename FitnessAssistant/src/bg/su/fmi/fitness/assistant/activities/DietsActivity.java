package bg.su.fmi.fitness.assistant.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Diet;
import bg.su.fmi.fitness.assistant.storage.DietsDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class DietsActivity extends ListActivity {

	private ArrayAdapter<Diet> arrayAdapter;

	private DietsDataSourse dataSource;

	private List<Diet> diets;

	public DietsDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new DietsDataSourse(this);
		}
		return dataSource;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		diets = getAllDiets();
		arrayAdapter = new ArrayAdapter<Diet>(this,
				android.R.layout.simple_list_item_1, diets);
		setListAdapter(arrayAdapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openViewDietActivity(diets.get(position));
			}
		});
	}

	// COMMENTED SINCE IT MAKES HAVY REQUESTS TO THE DB. FOR UPDATING ARE USED
	// INTENT EXTRAS.
	// @Override
	// public void onResume() {
	// super.onResume();
	// arrayAdapter = new ArrayAdapter<Exersize>(this,
	// android.R.layout.simple_list_item_1, getAllExercises());
	// setListAdapter(arrayAdapter);
	// }

	public List<Diet> getAllDiets() {
		getDataSource().open();
		diets = getDataSource().getAllDiets();
		return diets;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.diets_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.new_diet) {
			openNewDietActivity();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void openNewDietActivity() {
		Intent intent = new Intent(this, NewDietActivity.class);
		startActivityForResult(intent, Tools.NEW_DIET_REQUEST_CODE);
	}

	private void openViewDietActivity(Diet diet) {
		Intent intent = new Intent(this, ViewDietActivity.class);
		intent.putExtra(Tools.CURRENT_DIET_EXTRA, diet);
		startActivityForResult(intent, Tools.START_VIEW_DIET_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == Tools.START_VIEW_DIET_REQUEST_CODE) {
			Diet deletedDiet = (Diet) data
					.getSerializableExtra(Tools.DELETED_DIET_EXTRA);
			if (deletedDiet != null) {
				removeDiet(deletedDiet);
			} else {
				Diet editedDiet = (Diet) data
						.getSerializableExtra(Tools.EDITED_DIET_EXTRA);
				if (editedDiet != null) {
					update((Diet) data
							.getSerializableExtra(Tools.OLD_DIET_EXTRA),
							editedDiet);
				}
			}
		}
		if (resultCode == RESULT_OK
				&& requestCode == Tools.NEW_DIET_REQUEST_CODE) {
			Diet newDiet = (Diet) data
					.getSerializableExtra(Tools.NEW_DIET_EXTRA);
			if (newDiet != null) {
				addDiet(newDiet);
			}
		}
	}

	private void removeDiet(Diet d) {
		arrayAdapter.remove(d);
		arrayAdapter.notifyDataSetChanged();

	}

	private void addDiet(Diet d) {
		arrayAdapter.add(d);
		arrayAdapter.notifyDataSetChanged();
	}

	private void update(Diet oldDiet, Diet newDiet) {
		arrayAdapter.remove(oldDiet);
		arrayAdapter.add(newDiet);
		arrayAdapter.notifyDataSetChanged();
	}
}

