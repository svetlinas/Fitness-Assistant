package bg.su.fmi.fitness.assistant.activities;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.entities.Score;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.storage.ScoresDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class CompareWorkoutsActivity extends Activity {

	private ScoresDataSourse scoresDataSource;

	private ExersizesDataSourse exercisesDataSource;

	public ExersizesDataSourse getExerciseDataSource() {
		if (exercisesDataSource == null) {
			exercisesDataSource = new ExersizesDataSourse(this);
		}
		return exercisesDataSource;
	}

	public ScoresDataSourse getScoresDataSource() {
		if (scoresDataSource == null) {
			scoresDataSource = new ScoresDataSourse(this);
		}
		return scoresDataSource;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.compare_results);

		Intent intent = getIntent();
		Date created = (Date) intent.getSerializableExtra(Tools.SCORES_CREATED_EXTRA);
		Date oldCreated = (Date) intent
				.getSerializableExtra(Tools.OLD_SCORES_CREATED_EXTRA);

		getScoresDataSource().open();
		getExerciseDataSource().open();
		List<Score> newScores = getScoresDataSource().getScoresByDay(created);
		List<Score> oldScores = getScoresDataSource()
				.getScoresByDay(oldCreated);
		loadTable(newScores, oldScores);
	}

	private void loadTable(List<Score> newScores, List<Score> oldScores) {
		for (Score newScore : newScores) {
			for (Score oldScore : oldScores) {
				if (newScore.getExersizeId() == oldScore.getExersizeId()) {
					loadFields(newScore, oldScore);
				}
			}
		}
	}

	private void loadFields(Score first, Score second) {
		Exersize firstExercise = getExerciseDataSource().getExercise(
				first.getExersizeId());
		Exersize secondExercise = getExerciseDataSource().getExercise(
				second.getExersizeId());
		TableLayout table = (TableLayout) findViewById(R.id.compare_table);

		// Add the names
		TableRow rowNames = new TableRow(this);
		TextView nameFirst = new TextView(this);
		nameFirst.setText(firstExercise.getName());
		nameFirst.setTextSize(15);
		TextView nameSecond = new TextView(this);
		nameSecond.setText(secondExercise.getName());
		nameSecond.setTextSize(15);
		nameSecond.setGravity(Gravity.RIGHT);
		rowNames.addView(nameFirst);
		rowNames.addView(nameSecond);

		// Add Weights
		TableRow rowWeights = new TableRow(this);
		TextView weightFirst = new TextView(this);
		weightFirst.setText("Weight: " + first.getWeight());
		TextView weightSecond = new TextView(this);
		weightSecond.setText("Weight: " + second.getWeight());
		weightSecond.setGravity(Gravity.RIGHT);
		rowWeights.addView(weightFirst);
		rowWeights.addView(weightSecond);

		// Add Time
		TableRow rowTimes = new TableRow(this);
		TextView timeFirst = new TextView(this);
		timeFirst.setText("Time: " + first.getTime());
		TextView timeSecond = new TextView(this);
		timeSecond.setText("Time: " + second.getTime());
		timeSecond.setGravity(Gravity.RIGHT);
		rowTimes.addView(timeFirst);
		rowTimes.addView(timeSecond);

		table.addView(rowNames);
		table.addView(rowWeights);
		table.addView(rowTimes);
	}

	public void okButtonClicked() {
		finish();
	}

}
