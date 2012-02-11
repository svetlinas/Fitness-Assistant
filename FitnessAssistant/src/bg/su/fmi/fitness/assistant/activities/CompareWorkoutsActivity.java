package bg.su.fmi.fitness.assistant.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.live.ExerciseResult;

public class CompareWorkoutsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compare_results);

		// TODO: loadTable(...)
	}

	private void loadTable(ExerciseResult first, ExerciseResult second) {
		TableLayout table = (TableLayout) findViewById(R.id.compare_table);

		// Add the names
		TableRow rowNames = new TableRow(this);
		TextView nameFirst = new TextView(this);
		nameFirst.setText(first.getExercise().getName());
		nameFirst.setTextSize(15);
		TextView nameSecond = new TextView(this);
		nameSecond.setText(second.getExercise().getName());
		nameSecond.setTextSize(15);
		nameSecond.setGravity(Gravity.RIGHT);
		rowNames.addView(nameFirst);
		rowNames.addView(nameSecond);

		// Add Weights
		TableRow rowWeights = new TableRow(this);
		TextView weightFirst = new TextView(this);
		weightFirst.setText("Weight: " + first.getMaxWight());
		TextView weightSecond = new TextView(this);
		weightSecond.setText("Weight: " + second.getMaxWight());
		weightSecond.setGravity(Gravity.RIGHT);
		rowWeights.addView(weightFirst);
		rowWeights.addView(weightSecond);

		// Add Time
		TableRow rowTimes = new TableRow(this);
		TextView timeFirst = new TextView(this);
		timeFirst.setText("Time: " + first.getMaxWight());
		TextView timeSecond = new TextView(this);
		timeSecond.setText("Time: " + second.getMaxWight());
		timeSecond.setGravity(Gravity.RIGHT);
		rowTimes.addView(timeFirst);
		rowTimes.addView(timeSecond);

		table.addView(rowNames);
		table.addView(rowWeights);
		table.addView(rowTimes);
	}

	private int compareExerciseMaxWight(ExerciseResult first,
			ExerciseResult second) {
		if (first.getMaxWight() > second.getMaxWight()) {
			return 1;
		}
		if (first.getMaxWight() < second.getMaxWight()) {
			return -1;
		}
		return 0;
	}

	private long compareExerciseTime(ExerciseResult first, ExerciseResult second) {
		if (first.getTime() > second.getTime()) {
			return 1;
		}
		if (first.getTime() < second.getTime()) {
			return -1;
		}
		return 0;
	}

}
