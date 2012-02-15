package bg.su.fmi.fitness.assistant.util;

public final class Tools {

	public static final String DELETED_EXERCISE_EXTRA = "deletedExercise";

	public static final String CURRENT_EXERCISE_EXTRA = "currentExercise";

	public static final String NEW_EXERCISE_EXTRA = "newExercise";

	public static final String EDIT_EXERCISE_EXTRA = "editExercise";

	public static final String OLD_EXERCISE_EXTRA = "oldExercise";

	public static final String EDITED_EXERCISE_EXTRA = "editedExercise";

	public static final int START_VIEW_EXERCISE_REQUEST_CODE = 1;

	public static final int EDIT_EXERCISE_REQUEST_CODE = 2;

	public static final int NEW_EXERCISE_REQUEST_CODE = 3;

	public static final String DELETED_DIET_EXTRA = "deletedDiet";

	public static final String CURRENT_DIET_EXTRA = "currentDiet";

	public static final String NEW_DIET_EXTRA = "newDiet";

	public static final String EDIT_DIET_EXTRA = "editDiet";

	public static final String OLD_DIET_EXTRA = "oldDiet";

	public static final String EDITED_DIET_EXTRA = "editedDiet";

	public static final int START_VIEW_WORKOUT_REQUEST_CODE = 100;

	public static final int EDIT_WORKOUT_REQUEST_CODE = 101;

	public static final int NEW_WORKOUT_REQUEST_CODE = 102;

	public static final int NEW_DAY_REQUEST_CODE = 103;

	public static final String CURRENT_WORKOUT_EXTRA = "currentWorkout";

	public static final String DELETED_WORKOUT_EXTRA = "deletedWorkout";

	public static final String EDITED_WORKOUT_EXTRA = "editedWorkout";

	public static final String OLD_WORKOUT_EXTRA = "oldWorkout";

	public static final String NEW_WORKOUT_EXTRA = "newWorkout";

	public static final String NEW_DAY_NUMBER_EXTRA = "newDayNumber";

	public static final String NEW_DAY_EXTRA = "newDay";

	public static final int START_VIEW_DIET_REQUEST_CODE = 4;

	public static final int EDIT_DIET_REQUEST_CODE = 5;

	public static final int NEW_DIET_REQUEST_CODE = 6;

	public static final int START_TIMER_EXERCISE_REQUEST_CODE = 7;

	public static final int EDIT_DAY_REQUEST_CODE = 104;

	public static final String SCORES_CREATED_EXTRA = "scoresCreated";

	public static final String OLD_SCORES_CREATED_EXTRA = "oldScoresCreated";

	public static final String WORKOUT_ID_EXTRA = "workoutId";

	public static final String TIMER_TIME_EXTRA = "timerTime";

	public static final String TIMER_WEIGHT_EXTRA = "timerWeight";

	public static final String EDIT_WORKOUT_EXTRA = "editWorkout";
	public static final String EDIT_DAY_EXTRA = "editDay";
	public static final String EDIT_WORKOUT_DAYS_EXTRA = "editWorkoutDays";
	public static final String EDITED_WORKOUT_DAYS_EXTRA = "editedWorkoutDaysExtra";
	public static final String EDITED_DAY_EXTRA = "editedDay";

	public static final int AUDIO_TRACKS_RESULT_CODE = 200;

	public static final int NO_AUDIO_TRACKS_RESULT_CODE = 201;

	private Tools() {
		// no instances
	}

	/**
	 * Tries to parse Integer from String. If it cannot be parsed -1 is
	 * returned.
	 * 
	 * @param text
	 * @return
	 */
	public static int getInteger(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Tries to parse Integer from CharSequence. If it cannot be parsed -1 is
	 * returned.
	 * 
	 * @param text
	 * @return
	 */
	public static int getInteger(CharSequence text) {
		return getInteger(text.toString());
	}

	/**
	 * Check if the {@link String} is null or empty.
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(String text) {
		if (null == text || "".equals(text.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the {@link CharSequence} is null or empty.
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isNullOrEmpty(CharSequence text) {
		return isNullOrEmpty(text.toString());
	}

	/**
	 * Converts positive int values to String and -1 to empty String.
	 * 
	 * @param value
	 * @return
	 */
	public static String stringIntegerValue(int value) {
		if (value == -1) {
			return "";
		}
		return value + "";
	}

}
