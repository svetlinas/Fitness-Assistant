package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import bg.su.fmi.fitness.assistant.entities.Score;
import bg.su.fmi.fitness.assistant.storage.ScoresDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class ListWorkoutsTypeActivity extends ListActivity {

	Intent intent;

	private ArrayAdapter<Date> arrayAdapter;

	private ScoresDataSourse scoresDataSource;

	private List<Date> dates;

	private Date created;

	public ScoresDataSourse getScoresDataSource() {
		if (scoresDataSource == null) {
			scoresDataSource = new ScoresDataSourse(this);
		}
		return scoresDataSource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = getIntent();
		created = (Date) intent
				.getSerializableExtra(Tools.SCORES_CREATED_EXTRA);
		long workoutId = intent.getLongExtra(Tools.WORKOUT_ID_EXTRA, -1);

		dates = getAllWorkoutDates(workoutId);
		arrayAdapter = new ArrayAdapter<Date>(this,
				android.R.layout.simple_list_item_1, dates);
		setListAdapter(arrayAdapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				openCompareWorkoutsActivity(dates.get(position));
			}
		});

	}

	private List<Date> getAllWorkoutDates(long workoutId) {
		dates = new ArrayList<Date>();
		getScoresDataSource().open();
		List<Score> scoresByWorkoutId = getScoresDataSource()
				.getScoresByWorkoutId(workoutId);
		for (Score score : scoresByWorkoutId) {
			if (!dates.contains(score.getCreated())) {
				dates.add(score.getCreated());
			}
		}
		return dates;
	}

	private void openCompareWorkoutsActivity(Date oldDate) {
		Intent intent = new Intent(this, CompareWorkoutsActivity.class);
		intent.putExtra(Tools.SCORES_CREATED_EXTRA, created);
		intent.putExtra(Tools.OLD_SCORES_CREATED_EXTRA, oldDate);
		startActivityForResult(intent, Tools.COMPARE_WORKOUTS_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == Tools.COMPARE_WORKOUTS_REQUEST_CODE) {
			boolean isCompared = data.getBooleanExtra(Tools.IS_COMPARED_EXTRA,
					false);
			if (isCompared) {
				intent.putExtra(Tools.IS_COMPARED_EXTRA, true);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	}

}
