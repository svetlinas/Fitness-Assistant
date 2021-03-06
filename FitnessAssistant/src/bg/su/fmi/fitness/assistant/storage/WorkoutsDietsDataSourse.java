package bg.su.fmi.fitness.assistant.storage;

import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsDietsSQLiteHelper.COLUMN_EXERSIZE_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsDietsSQLiteHelper.COLUMN_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsDietsSQLiteHelper.COLUMN_WORKOUT_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsDietsSQLiteHelper.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.WorkoutDiet;
import bg.su.fmi.fitness.assistant.storage.helper.BaseSQLiteHelper;

public class WorkoutsDietsDataSourse {
	private SQLiteDatabase database;
	private BaseSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_WORKOUT_ID, COLUMN_EXERSIZE_ID };

	public WorkoutsDietsDataSourse(Context context) {
		dbHelper = new BaseSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<WorkoutDiet> getAllWorkouts() {
		final List<WorkoutDiet> workoutDiets = new ArrayList<WorkoutDiet>();
		final Cursor cursor = database.query(TABLE_NAME, allColumns, null,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final WorkoutDiet workoutDiet = getWorkoutDiet(cursor);
			workoutDiets.add(workoutDiet);
			cursor.moveToNext();
		}
		cursor.close();

		return workoutDiets;
	}
	
	public void addInformationToDatabase(SQLiteDatabase database, long workoutId, long exersizeId) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_WORKOUT_ID, workoutId);
		values.put(COLUMN_EXERSIZE_ID, exersizeId);
		database.insert(TABLE_NAME, null, values);
	}
	

	private WorkoutDiet getWorkoutDiet(Cursor cursor) {
		return new WorkoutDiet(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2));
	}

}
