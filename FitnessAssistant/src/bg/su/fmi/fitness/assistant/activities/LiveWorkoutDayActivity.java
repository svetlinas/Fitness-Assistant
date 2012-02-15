package bg.su.fmi.fitness.assistant.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import bg.su.fmi.fitness.assistant.R;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.entities.Score;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.entities.WorkoutExersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.storage.ScoresDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class LiveWorkoutDayActivity extends Activity {

	private WorkoutsExersizesDataSourse WEDataSource;

	private ExersizesDataSourse exerciseDataSource;

	private ScoresDataSourse scoresDataSource;

	// private WorkoutExersize currentWorkoutExercise;

	private Workout workout;

	private Exersize currentExercise;

	private int currentExerciseNum;

	private List<Exersize> exercisesForDay;

	private List<Score> scores;

	private Date created;

	private int day;

	public WorkoutsExersizesDataSourse getWEDataSource() {
		if (WEDataSource == null) {
			WEDataSource = new WorkoutsExersizesDataSourse(this);
		}
		return WEDataSource;
	}

	public ExersizesDataSourse getExerciseDataSource() {
		if (exerciseDataSource == null) {
			exerciseDataSource = new ExersizesDataSourse(this);
		}
		return exerciseDataSource;
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
		setContentView(R.layout.live_workout_day);

		scores = new ArrayList<Score>();
		created = new Date();

		Intent intent = getIntent();
		workout = (Workout) intent.getSerializableExtra("workout");
		day = intent.getIntExtra("workoutDay", -1);
		exercisesForDay = getAllExercisesForWorkoutInDay(workout.getId(), day);
		currentExerciseNum = 0;
		currentExercise = exercisesForDay.get(currentExerciseNum++);
		loadDataFields(workout.getName(), day, currentExercise.getName(),
				currentExercise.getVideo(), currentExercise.getSets(),
				currentExercise.getRepetitions(),
				currentExercise.getDescription());
	}

	private List<Exersize> getAllExercisesForWorkoutInDay(long workoutId,
			int day) {
		getWEDataSource().open();
		List<WorkoutExersize> workoutExercises = getWEDataSource()
				.getWorkoutDay(workoutId, day);
		List<Exersize> exercises = new ArrayList<Exersize>();
		getExerciseDataSource().open();
		for (WorkoutExersize workoutEx : workoutExercises) {
			exercises.add(getExerciseDataSource().getExercise(
					workoutEx.getExersizeId()));
		}
		return exercises;
	}

	public void startButtonClicked(View view) {
		startTimerWeightActivity();
	}

	private void startTimerWeightActivity() {
		Intent intent = new Intent(this, TimerActivity.class);
		startActivityForResult(intent, Tools.START_TIMER_EXERCISE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == Tools.START_TIMER_EXERCISE_REQUEST_CODE) {
			Long maxWeight = Long.parseLong(data
					.getStringExtra(Tools.TIMER_WEIGHT_EXTRA));
			Long time = data.getLongExtra(Tools.TIMER_TIME_EXTRA, -1);

			scores.add(new Score(0, workout.getId(), currentExercise.getId(),
					0, (double) maxWeight, time, created));

			if (currentExerciseNum < exercisesForDay.size()) {
				// If there are more exercises -> get the next one and reload UI
				// with the new data
				currentExercise = exercisesForDay.get(currentExerciseNum++);
				loadDataFields(workout.getName(), day,
						currentExercise.getName(), currentExercise.getVideo(),
						currentExercise.getSets(),
						currentExercise.getRepetitions(),
						currentExercise.getDescription());
			} else {
				// If no more exercises -> write Scores to DB and go to compare
				// activity
				getScoresDataSource().open();
				for (Score score : scores) {
					getScoresDataSource().addScore(score);
				}
				Intent intent = new Intent(this, ListWorkoutsTypeActivity.class);
				intent.putExtra(Tools.SCORES_CREATED_EXTRA, created);
				intent.putExtra(Tools.WORKOUT_ID_EXTRA, workout.getId());
				startActivityForResult(intent,
						Tools.COMPARE_WORKOUTS_REQUEST_CODE);
			}
		}
		if (resultCode == RESULT_OK
				&& requestCode == Tools.COMPARE_WORKOUTS_REQUEST_CODE) {
			boolean isCompared = data.getBooleanExtra(Tools.IS_COMPARED_EXTRA,
					false);
			if (isCompared) {
				finish();
			}

		}
	}

	private void loadDataFields(String workoutNameValue, int workoutDayValue,
			String exerciseNameValue, String videoValue, int setsValue,
			int repetitionsValue, String descriptionValue) {
		final TextView workoutName = (TextView) findViewById(R.id.live_workout_name_content);
		final TextView workoutDay = (TextView) findViewById(R.id.live_workout_day_content);
		final TextView exerciseName = (TextView) findViewById(R.id.live_exercise_name_content);
		final TextView video = (TextView) findViewById(R.id.live_exercise_video_link_content);
		final TextView sets = (TextView) findViewById(R.id.live_exercise_sets_content);
		final TextView repetitions = (TextView) findViewById(R.id.live_exercise_repetitions_content);
		final TextView description = (TextView) findViewById(R.id.live_exercise_description_content);
		workoutName.setText(workoutNameValue);
		workoutDay.setText("Day " + Tools.stringIntegerValue(workoutDayValue));
		exerciseName.setText(exerciseNameValue);
		video.setText(videoValue);
		sets.setText(Tools.stringIntegerValue(setsValue));
		repetitions.setText(Tools.stringIntegerValue(repetitionsValue));
		description.setText(descriptionValue);
	}

}
