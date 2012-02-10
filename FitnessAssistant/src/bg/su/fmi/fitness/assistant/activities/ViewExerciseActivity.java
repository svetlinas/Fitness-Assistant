package bg.su.fmi.fitness.assistant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class ViewExerciseActivity extends Activity {

	private Exersize currentExercise;

	private ExersizesDataSourse dataSource;

	private Intent intent;

	public ExersizesDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new ExersizesDataSourse(this);
		}
		return dataSource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_exercise);

		intent = getIntent();
		currentExercise = (Exersize) intent
				.getSerializableExtra(Tools.CURRENT_EXERCISE_EXTRA);
		intent.removeExtra(Tools.DELETED_EXERCISE_EXTRA);

		loadDataFields(currentExercise);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.view_exercise_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.edit_exercise) {
			editExercise();
			return true;
		}
		if (item.getItemId() == R.id.delete_exercise) {
			int rowsDeleted = deleteExercise();
			if (rowsDeleted > 0) {
				Toast.makeText(getApplicationContext(), "Exercise deleted.",
						Toast.LENGTH_SHORT).show();
				intent.putExtra(Tools.DELETED_EXERCISE_EXTRA, currentExercise);
				setResult(RESULT_OK, intent);
				finish();
				return true;
			}
			Toast.makeText(getApplicationContext(),
					"Exercise cannot be deleted.", Toast.LENGTH_SHORT).show();
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadDataFields(Exersize exercise) {
		final TextView name = (TextView) findViewById(R.id.view_exercise_name_content);
		final TextView video = (TextView) findViewById(R.id.view_exercise_video_link_content);
		final TextView sets = (TextView) findViewById(R.id.view_exercise_sets_content);
		final TextView repetitions = (TextView) findViewById(R.id.view_exercise_repetitions_content);
		final TextView description = (TextView) findViewById(R.id.view_exercise_description_content);
		name.setText(exercise.getName());
		video.setText(exercise.getVideo());
		sets.setText(Tools.stringIntegerValue(exercise.getSets()));
		repetitions.setText(Tools.stringIntegerValue(exercise.getRepetitions()));
		description.setText(exercise.getDescription());
	}

	private int deleteExercise() {
		getDataSource().open();
		return getDataSource().deleteExercise(currentExercise.getId());
	}

	private void editExercise() {
		Intent intent = new Intent(this, NewExerciseActivity.class);
		intent.putExtra(Tools.EDIT_EXERCISE_EXTRA, currentExercise);
		startActivityForResult(intent, Tools.EDIT_EXERCISE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == Tools.EDIT_EXERCISE_REQUEST_CODE) {
			Exersize editedExercise = (Exersize) data
					.getSerializableExtra(Tools.EDITED_EXERCISE_EXTRA);
			if (editedExercise != null) {
				loadDataFields(editedExercise);
				intent.putExtra(Tools.EDITED_EXERCISE_EXTRA, editedExercise);
				intent.putExtra(Tools.OLD_EXERCISE_EXTRA, (Exersize) data
						.getSerializableExtra(Tools.OLD_EXERCISE_EXTRA));
				currentExercise = editedExercise;
				setResult(RESULT_OK, intent);
			}
		}
	}
}
