package bg.su.fmi.fitness.assistant.storage.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class contains information about the table's creation 
 * in the database and some constants.
 *
 */
public class ScoresSQLiteHelper {

	public static final String TABLE_NAME = "scores";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_WORKOUT_ID = "workout_id";
	public static final String COLUMN_EXERSIZE_ID = "exersize_id";
	public static final String COLUMN_SET_NUMBER = "set_number";
	public static final String COLUMN_WEIGHT = "weight";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_CREATED = "created";

	private static final String TABLE_CREATE = "create table " + TABLE_NAME
			+ "( " + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_WORKOUT_ID + " integer not null, " + COLUMN_EXERSIZE_ID
			+ " integer not null, " + COLUMN_SET_NUMBER + " integer not null, "
			+ COLUMN_WEIGHT + " double not null, " + COLUMN_TIME
			+ " date not null, " + COLUMN_CREATED
			+ " date not null, foreign key (" + COLUMN_WORKOUT_ID
			+ ") REFERENCES " + WorkoutsExersizesSQLiteHelper.TABLE_NAME + " ("
			+ WorkoutsExersizesSQLiteHelper.COLUMN_ID + "), foreign key ("
			+ COLUMN_EXERSIZE_ID + ") REFERENCES "
			+ ExersizesSQLiteHelper.TABLE_NAME + " ("
			+ ExersizesSQLiteHelper.COLUMN_ID + "));";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ScoresSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
		onCreate(database);
	}

}
