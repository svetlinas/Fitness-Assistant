package bg.su.fmi.fitness.assistant.storage;

import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsSQLiteHelper.COLUMN_DURATION;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsSQLiteHelper.COLUMN_GENDER;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsSQLiteHelper.COLUMN_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsSQLiteHelper.COLUMN_NAME;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsSQLiteHelper.COLUMN_TYPE;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsSQLiteHelper.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.SearchedObject;
import bg.su.fmi.fitness.assistant.entities.Workout;
import bg.su.fmi.fitness.assistant.storage.helper.BaseSQLiteHelper;

public class WorkoutsDataSourse {

	private SQLiteDatabase database;
	private BaseSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_GENDER, COLUMN_TYPE,
			COLUMN_DURATION, COLUMN_NAME };

	public WorkoutsDataSourse(Context context) {
		dbHelper = new BaseSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Workout> getAllWorkouts() {
		final List<Workout> workouts = new ArrayList<Workout>();
		final Cursor cursor = database.query(TABLE_NAME, allColumns, null,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Workout workout = getWorkout(cursor);
			workouts.add(workout);
			cursor.moveToNext();
		}
		cursor.close();

		return workouts;
	}

	/**
	 * Returns an Workout object with the given id.
	 * @param id The id of the needed workout
	 * @return the Workout with the given id
	 */
	public Workout getWorkout(long id) {
		final Cursor cursor = database.query(TABLE_NAME, allColumns, COLUMN_ID+"=?",
				new String[] {String.valueOf(id)}, null, null, null);
		cursor.moveToFirst();
		final Workout workout = getWorkout(cursor);
		cursor.close();

		return workout;
	}
	
	/**
	 * Returns a list of SerachedObject containing all ids of the workouts with
	 * the given workoutName.
	 * 
	 * @param workoutName
	 *            The name of the workout we are looking for
	 * @return List of SearchedObject
	 */
	public List<SearchedObject> getSearchedWorkouts(String workoutName) {
		List<SearchedObject> result = new ArrayList<SearchedObject>();

		Cursor cursor = database.query(TABLE_NAME, allColumns, COLUMN_NAME
				+ "=?", new String[] { workoutName }, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			final SearchedObject searchedObject = getSearchedObject(cursor);
			result.add(searchedObject);
			cursor.moveToNext();
		}
		cursor.close();

		return result;
	}
	
	private Workout getWorkout(Cursor cursor) {
		return new Workout(cursor.getLong(0), cursor.getString(1),
				cursor.getString(2), cursor.getInt(3), cursor.getString(4));
	}
	
	public long addWorkout(String name, String gender, String type, int duration) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_GENDER, gender);
		values.put(COLUMN_TYPE, type);
		values.put(COLUMN_DURATION, duration);
		values.put(COLUMN_NAME, name);
		return database.insert(TABLE_NAME, null, values);
	}
	
	public int deleteWorkout(long id) {
		return database.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] { id
				+ "" });
	}
	
	private SearchedObject getSearchedObject(Cursor cursor) {
		return new SearchedObject(cursor.getString(4), cursor.getLong(1), "Workout");
	}
	
	
	public long addWorkout(Workout w)
	{
		return addWorkout(w.getName(),w.getGender(),w.getType(),w.getDuration());
	}
	
	public int updateWorkout(Workout w) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_GENDER, w.getGender());
		values.put(COLUMN_TYPE, w.getType());
		values.put(COLUMN_DURATION, w.getDuration());
		values.put(COLUMN_NAME, w.getName());
		return database.update(TABLE_NAME, values, COLUMN_ID + "=?",
				new String[] { w.getId() + "" });
	}
}
