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
import bg.su.fmi.fitness.assistant.entities.Diet;
import bg.su.fmi.fitness.assistant.storage.DietsDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class ViewDietActivity extends Activity {
	
	private Diet currentDiet;

	private DietsDataSourse dataSource;

	private Intent intent;

	public DietsDataSourse getDataSource() {
		if (dataSource == null) {
			dataSource = new DietsDataSourse(this);
		}
		return dataSource;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_diet);

		intent = getIntent();
		currentDiet = (Diet) intent
				.getSerializableExtra(Tools.CURRENT_DIET_EXTRA);
		intent.removeExtra(Tools.DELETED_DIET_EXTRA);

		loadDataFields(currentDiet);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.view_diet_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.edit_diet) {
			editDiet();
			return true;
		}
		if (item.getItemId() == R.id.delete_diet) {
			int rowsDeleted = deleteDiet();
			if (rowsDeleted > 0) {
				Toast.makeText(getApplicationContext(), "Diet deleted.",
						Toast.LENGTH_SHORT).show();
				intent.putExtra(Tools.DELETED_DIET_EXTRA, currentDiet);
				setResult(RESULT_OK, intent);
				finish();
				return true;
			}
			Toast.makeText(getApplicationContext(),
					"Diet cannot be deleted.", Toast.LENGTH_SHORT).show();
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadDataFields(Diet diet) {
		final TextView type = (TextView) findViewById(R.id.view_diet_type_content);
		final TextView duration = (TextView) findViewById(R.id.view_diet_duration_content);
		final TextView name = (TextView) findViewById(R.id.view_diet_name_content);
		final TextView description = (TextView) findViewById(R.id.view_diet_description_content);
		type.setText(diet.getType());
		duration.setText(Tools.stringIntegerValue(diet.getDuration()));
		name.setText(diet.getName());
		description.setText(diet.getDescription());
	}

	private int deleteDiet() {
		getDataSource().open();
		return getDataSource().deleteDiet(currentDiet.getId());
	}

	private void editDiet() {
		Intent intent = new Intent(this, NewDietActivity.class);
		intent.putExtra(Tools.EDIT_DIET_EXTRA, currentDiet);
		startActivityForResult(intent, Tools.EDIT_DIET_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == Tools.EDIT_DIET_REQUEST_CODE) {
			Diet editedDiet = (Diet) data
					.getSerializableExtra(Tools.EDITED_DIET_EXTRA);
			if (editedDiet != null) {
				loadDataFields(editedDiet);
				intent.putExtra(Tools.EDITED_DIET_EXTRA, editedDiet);
				intent.putExtra(Tools.OLD_DIET_EXTRA, (Diet) data
						.getSerializableExtra(Tools.OLD_DIET_EXTRA));
				currentDiet = editedDiet;
				setResult(RESULT_OK, intent);
			}
		}
	}
}