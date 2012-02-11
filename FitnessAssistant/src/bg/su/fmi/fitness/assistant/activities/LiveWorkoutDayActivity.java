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
import bg.su.fmi.fitness.assistant.entities.WorkoutExersize;
import bg.su.fmi.fitness.assistant.storage.ExersizesDataSourse;
import bg.su.fmi.fitness.assistant.storage.ScoresDataSourse;
import bg.su.fmi.fitness.assistant.storage.WorkoutsExersizesDataSourse;
import bg.su.fmi.fitness.assistant.util.Tools;

public class LiveWorkoutDayActivity extends Activity {

	private WorkoutsExersizesDataSourse WEDataSource;

	private ExersizesDataSourse exerciseDataSource;
	
	private ScoresDataSourse scoresDataSource;

	private WorkoutExersize currentWorkoutExercise;

	private Exersize currentExercise;

	private int currentExerciseNum;

	private List<Exersize> exercisesForDay;

	private List<Score> scores;
	
	private Date created; 

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
		currentWorkoutExercise = (WorkoutExersize) intent
				.getSerializableExtra("currentWorkoutExercise");
		int days = currentWorkoutExercise.getDays();
		for (int day = 1; day <= days; day++) {
			exercisesForDay = getAllExercisesForWorkoutInDay(
					currentWorkoutExercise.getWorkoutId(), day);
			for (Exersize exercise : exercisesForDay) {
				loadDataFields(currentWorkoutExercise.getWorkoutId() + "NAmee",
						day, exercise.getName(), exercise.getVideo(),
						exercise.getSets(), exercise.getRepetitions(),
						exercise.getDescription());
			}
		}
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
		currentExerciseNum = 1;
		startTimerWeightActivity();
	}

	private void startTimerWeightActivity() {
		Intent intent = new Intent(this, TimerWeightActivity.class);
		startActivityForResult(intent, Tools.START_TIMER_EXERCISE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK
				&& requestCode == Tools.START_TIMER_EXERCISE_REQUEST_CODE) {
			Double maxWeight = data.getDoubleExtra("maxWeight", -1); // TODO:
																		// Change
			Long time = data.getLongExtra("timeExercise", -1); // TODO: Change

			// TODO: Check 0 params and new Date(time)
			scores.add(new Score(0, currentWorkoutExercise.getWorkoutId(),
					currentExercise.getId(), 0, maxWeight, new Date(time),
					created));

			if (currentExerciseNum < exercisesForDay.size()) {
				// If there are more exercises -> get the next one and reload UI
				// with the new data
				currentExercise = exercisesForDay.get(currentExerciseNum++);
				loadDataFields(currentWorkoutExercise.getWorkoutId() + "NAmee",
						currentWorkoutExercise.getDays(),
						currentExercise.getName(), currentExercise.getVideo(),
						currentExercise.getSets(),
						currentExercise.getRepetitions(),
						currentExercise.getDescription());
			} else {
				// If no more exercises -> write Scores to DB and go to compare
				// activity
				getScoresDataSource().open();
				for(Score score : scores) {
					getScoresDataSource().addScore(score);
				}
				Intent intent = new Intent(this, CompareWorkoutsActivity.class);
				intent.putExtra("scoresCreated", created);
				startActivity(intent);

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
		workoutDay.setText(workoutDayValue);
		exerciseName.setText(exerciseNameValue);
		video.setText(videoValue);
		sets.setText(setsValue);
		repetitions.setText(repetitionsValue);
		description.setText(descriptionValue);
	}

}
