package bg.su.fmi.fitness.assistant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class NewExerciseActivity extends Activity {

	private ExersizesDataSourse dataSource;

	private Intent intent;

	private Exersize editExercise;

	private boolean isEditMode = false;

	public ExersizesDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new ExersizesDataSourse(this);
		}
		return dataSource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.new_exercise);

		intent = getIntent();
		editExercise = (Exersize) intent
				.getSerializableExtra(Tools.EDIT_EXERCISE_EXTRA);
		if (editExercise != null) {
			isEditMode = true;
			this.setTitle("Edit Exercise");
			final EditText nameField = (EditText) findViewById(R.id.new_exercise_name_field);
			final EditText videoField = (EditText) findViewById(R.id.new_exercise_video_link_field);
			final EditText repetitionsField = (EditText) findViewById(R.id.new_exercise_repetitions_field);
			final EditText setsField = (EditText) findViewById(R.id.new_exercise_sets_field);
			final EditText descriptionField = (EditText) findViewById(R.id.new_exercise_description_field);
			nameField.setText(editExercise.getName());
			videoField.setText(editExercise.getVideo());
			repetitionsField.setText(Tools.stringIntegerValue(editExercise.getRepetitions()));
			setsField.setText(Tools.stringIntegerValue(editExercise.getSets()));
			descriptionField.setText(editExercise.getDescription());
			final Button AddButtonToEdit = (Button) findViewById(R.id.new_exercise_add_button);
			AddButtonToEdit.setText("Edit Exercise");
		}
		intent.removeExtra(Tools.OLD_EXERCISE_EXTRA);
		intent.removeExtra(Tools.EDITED_EXERCISE_EXTRA);
		intent.removeExtra(Tools.NEW_EXERCISE_EXTRA);

	}

	public void addButtonClicked(View view) {
		final EditText nameField = (EditText) findViewById(R.id.new_exercise_name_field);
		final EditText videoField = (EditText) findViewById(R.id.new_exercise_video_link_field);
		final EditText repetitionsField = (EditText) findViewById(R.id.new_exercise_repetitions_field);
		final EditText setsField = (EditText) findViewById(R.id.new_exercise_sets_field);
		final EditText descriptionField = (EditText) findViewById(R.id.new_exercise_description_field);

		if (Tools.isNullOrEmpty(nameField.getText())) {
			Toast.makeText(getApplicationContext(),
					"The exercise name cannot be empty.", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		getDataSource().open();
		String editedOrAdded = " added.";
		long result = -1;
		// If it is new Exercise there is no matter what the constructed id is.
		// The real one will be returned by the db.
		long constructedExId = 0; // DIRTY HACK
		if (isEditMode) {
			// The constructed Id is actually the old one.
			constructedExId = editExercise.getId();
		}
		Exersize constructedExercise = new Exersize(constructedExId, nameField
				.getText().toString(), Tools.getInteger(setsField.getText()),
				Tools.getInteger(repetitionsField.getText()), descriptionField
						.getText().toString(), videoField.getText().toString());
		if (isEditMode) {
			editedOrAdded = " edited.";
			result = getDataSource().updateExercise(editExercise.getId(),
					constructedExercise);
			intent.putExtra(Tools.EDITED_EXERCISE_EXTRA, constructedExercise);
			intent.putExtra(Tools.OLD_EXERCISE_EXTRA, editExercise);
		} else {
			result = getDataSource().addExersize(constructedExercise);
			// DIRTY HACK for avoiding db requests.
			Exersize newExercise = new Exersize(result,
					constructedExercise.getName(),
					constructedExercise.getSets(),
					constructedExercise.getRepetitions(),
					constructedExercise.getDescription(),
					constructedExercise.getVideo());
			intent.putExtra(Tools.NEW_EXERCISE_EXTRA, newExercise);
		}
		if (result > -1) {
			Toast.makeText(getApplicationContext(), "Exercise" + editedOrAdded,
					Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK, intent);
			finish();
		} else {
			Toast.makeText(getApplicationContext(),
					"The exercise cannot be" + editedOrAdded,
					Toast.LENGTH_SHORT).show();
		}
	}
}
