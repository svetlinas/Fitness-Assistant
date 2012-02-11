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

	private Workout getWorkout(Cursor cursor) {
		return new Workout(cursor.getLong(0), cursor.getString(1),
				cursor.getString(2), cursor.getInt(3), cursor.getString(4));
	}
	
	public void addWorkout(String name, String gender, String type, int duration) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_GENDER, gender);
		values.put(COLUMN_TYPE, type);
		values.put(COLUMN_DURATION, duration);
		values.put(COLUMN_NAME, name);
		database.insert(TABLE_NAME, null, values);
	}
	
	public int deleteWorkout(long id) {
		return database.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] { id
				+ "" });
	}
}
