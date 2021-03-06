package bg.su.fmi.fitness.assistant.storage.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class contains information about the table's creation 
 * in the database and some constants.
 *
 */
public class WorkoutsDietsSQLiteHelper {

	public static final String TABLE_NAME = "workouts_diets";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_WORKOUT_ID = "workout_id";
	public static final String COLUMN_EXERSIZE_ID = "exersize_id";

	private static final String TABLE_CREATE = "create table "
			+ TABLE_NAME + "( " + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_WORKOUT_ID + " integer not null, " 
			+ COLUMN_EXERSIZE_ID + " integer not null, foreign key (" 
			+ COLUMN_WORKOUT_ID + ") REFERENCES " 
			+ WorkoutsExersizesSQLiteHelper.TABLE_NAME + " ("
			+ WorkoutsExersizesSQLiteHelper.COLUMN_ID + "), foreign key ("
			+ COLUMN_EXERSIZE_ID + ") REFERENCES "
			+ ExersizesSQLiteHelper.TABLE_NAME + " ("
			+ ExersizesSQLiteHelper.COLUMN_ID + "));";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(WorkoutsDietsSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
		onCreate(database);
	}

}
