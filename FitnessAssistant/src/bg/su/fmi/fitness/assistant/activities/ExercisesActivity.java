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
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class ExercisesActivity extends ListActivity {

	private ArrayAdapter<Exersize> arrayAdapter;

	private ExersizesDataSourse dataSource;

	private List<Exersize> exercises;

	public ExersizesDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new ExersizesDataSourse(this);
		}
		return dataSource;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		exercises = getAllExercises();
		arrayAdapter = new ArrayAdapter<Exersize>(this,
				android.R.layout.simple_list_item_1, exercises);
		setListAdapter(arrayAdapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openViewExerciseActivity(exercises.get(position));
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

	public List<Exersize> getAllExercises() {
		getDataSource().open();
		exercises = getDataSource().getAllExercises();
		getDataSource().close();
		return exercises;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.exercises_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.new_exercise) {
			openNewExerciseActivity();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	private void openNewExerciseActivity() {
		Intent intent = new Intent(this, NewExerciseActivity.class);
		startActivityForResult(intent, Tools.NEW_EXERCISE_REQUEST_CODE);
	}

	private void openViewExerciseActivity(Exersize exercise) {
		Intent intent = new Intent(this, ViewExerciseActivity.class);
		intent.putExtra(Tools.CURRENT_EXERCISE_EXTRA, exercise);
		startActivityForResult(intent, Tools.START_VIEW_EXERCISE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == Tools.START_VIEW_EXERCISE_REQUEST_CODE) {
			Exersize deletedExercise = (Exersize) data
					.getSerializableExtra(Tools.DELETED_EXERCISE_EXTRA);
			if (deletedExercise != null) {
				removeExercise(deletedExercise);
			} else {
				Exersize editedExercise = (Exersize) data
						.getSerializableExtra(Tools.EDITED_EXERCISE_EXTRA);
				if (editedExercise != null) {
					update((Exersize) data
							.getSerializableExtra(Tools.OLD_EXERCISE_EXTRA),
							editedExercise);
				}
			}
		}
		if (resultCode == RESULT_OK
				&& requestCode == Tools.NEW_EXERCISE_REQUEST_CODE) {
			Exersize newExercise = (Exersize) data
					.getSerializableExtra(Tools.NEW_EXERCISE_EXTRA);
			if (newExercise != null) {
				addExercise(newExercise);
			}
		}
	}

	private void removeExercise(Exersize ex) {
		arrayAdapter.remove(ex);
		arrayAdapter.notifyDataSetChanged();

	}

	private void addExercise(Exersize ex) {
		arrayAdapter.add(ex);
		arrayAdapter.notifyDataSetChanged();
	}

	private void update(Exersize oldExercise, Exersize newExercise) {
		arrayAdapter.remove(oldExercise);
		arrayAdapter.add(newExercise);
		arrayAdapter.notifyDataSetChanged();
	}
}
