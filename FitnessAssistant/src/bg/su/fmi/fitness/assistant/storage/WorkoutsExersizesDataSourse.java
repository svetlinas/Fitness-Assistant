package bg.su.fmi.fitness.assistant.storage;

import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsExersizesSQLiteHelper.COLUMN_DAY;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsExersizesSQLiteHelper.COLUMN_DAY_CREATED;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsExersizesSQLiteHelper.COLUMN_EXERSIZE_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsExersizesSQLiteHelper.COLUMN_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsExersizesSQLiteHelper.COLUMN_WORKOUT_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.WorkoutsExersizesSQLiteHelper.TABLE_NAME;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.WorkoutExersize;
import bg.su.fmi.fitness.assistant.storage.helper.WorkoutsExersizesSQLiteHelper;

public class WorkoutsExersizesDataSourse {

	private SQLiteDatabase database;
	private WorkoutsExersizesSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_WORKOUT_ID,
			COLUMN_EXERSIZE_ID, COLUMN_DAY, COLUMN_DAY_CREATED };

	public WorkoutsExersizesDataSourse(Context context) {
		dbHelper = new WorkoutsExersizesSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<WorkoutExersize> getAllWorkouts() {
		final List<WorkoutExersize> workoutsExersizes = new ArrayList<WorkoutExersize>();
		final Cursor cursor = database.query(TABLE_NAME, allColumns, null,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final WorkoutExersize workoutExersize = getWorkoutExersize(cursor);
			workoutsExersizes.add(workoutExersize);
			cursor.moveToNext();
		}
		cursor.close();

		return workoutsExersizes;
	}

	private WorkoutExersize getWorkoutExersize(Cursor cursor) {
		Date dateCreated = null;
		try {
			dateCreated = new SimpleDateFormat().parse(cursor.getString(4));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new WorkoutExersize(cursor.getLong(0), cursor.getLong(1),
				cursor.getLong(2), cursor.getInt(3), dateCreated);
	}

}
