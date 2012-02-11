package bg.su.fmi.fitness.assistant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Diet;
import bg.su.fmi.fitness.assistant.storage.DietsDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class NewDietActivity extends Activity {

	private DietsDataSourse dataSource;

	private Intent intent;

	private Diet editDiet;

	private boolean isEditMode = false;

	public DietsDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new DietsDataSourse(this);
		}
		return dataSource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.new_diet);

		intent = getIntent();
		editDiet = (Diet) intent
				.getSerializableExtra(Tools.EDIT_DIET_EXTRA);
		if (editDiet != null) {
			isEditMode = true;
			this.setTitle("Edit Diet");
			final EditText typeField = (EditText) findViewById(R.id.new_diet_type_field);
			final EditText durationField = (EditText) findViewById(R.id.new_diet_duration_field);
			final EditText nameField = (EditText) findViewById(R.id.new_diet_name_field);
			final EditText descriptionField = (EditText) findViewById(R.id.new_diet_description_field);
			typeField.setText(editDiet.getType());
			durationField.setText(Tools.stringIntegerValue(editDiet.getDuration()));
			nameField.setText(editDiet.getName());
			descriptionField.setText(editDiet.getDescription());
			final Button AddButtonToEdit = (Button) findViewById(R.id.new_diet_add_button);
			AddButtonToEdit.setText("Edit Diet");
		}
		intent.removeExtra(Tools.OLD_DIET_EXTRA);
		intent.removeExtra(Tools.EDITED_DIET_EXTRA);
		intent.removeExtra(Tools.NEW_DIET_EXTRA);

	}

	public void addButtonClicked(View view) {
		final EditText typeField = (EditText) findViewById(R.id.new_diet_type_field);
		final EditText durationField = (EditText) findViewById(R.id.new_diet_duration_field);
		final EditText nameField = (EditText) findViewById(R.id.new_diet_name_field);
		final EditText descriptionField = (EditText) findViewById(R.id.new_diet_description_field);

		if (Tools.isNullOrEmpty(nameField.getText())) {
			Toast.makeText(getApplicationContext(),
					"The diet name cannot be empty.", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		getDataSource().open();
		String editedOrAdded = " added.";
		long result = -1;
		// If it is new Exercise there is no matter what the constructed id is.
		// The real one will be returned by the db.
		long constructedDId = 0; // DIRTY HACK
		if (isEditMode) {
			// The constructed Id is actually the old one.
			constructedDId = editDiet.getId();
		}
		Diet constructedDiet = new Diet(constructedDId, typeField
				.getText().toString(), Tools.getInteger(durationField.getText()),
				nameField.getText().toString(), descriptionField.getText().toString());
		if (isEditMode) {
			editedOrAdded = " edited.";
			result = getDataSource().updateDiet(editDiet.getId(),
					constructedDiet);
			intent.putExtra(Tools.EDITED_DIET_EXTRA, constructedDiet);
			intent.putExtra(Tools.OLD_DIET_EXTRA, editDiet);
		} else {
			result = getDataSource().addDiet(constructedDiet);
			// DIRTY HACK for avoiding db requests.
			Diet newDiet = new Diet(result,
					constructedDiet.getType(),
					constructedDiet.getDuration(),
					constructedDiet.getName(),
					constructedDiet.getDescription());
			intent.putExtra(Tools.NEW_DIET_EXTRA, newDiet);
		}
		if (result > -1) {
			Toast.makeText(getApplicationContext(), "Diet" + editedOrAdded,
					Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK, intent);
			finish();
		} else {
			Toast.makeText(getApplicationContext(),
					"The diet cannot be" + editedOrAdded,
					Toast.LENGTH_SHORT).show();
		}
	}
}