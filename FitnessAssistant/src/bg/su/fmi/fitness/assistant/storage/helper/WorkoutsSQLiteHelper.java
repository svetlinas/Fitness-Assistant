package bg.su.fmi.fitness.assistant.storage.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class creates the table in the database which is 
 * responsible for the storage of workouts.
 *
 */
public class WorkoutsSQLiteHelper {
	
	public static final String TABLE_NAME = "workouts";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GENDER = "gender";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_DURATION = "duration";
	public static final String COLUMN_NAME = "name";
	
	private static final String TABLE_CREATE = "create table "
				+ TABLE_NAME + "( " + COLUMN_ID
				+ " integer primary key autoincrement, " + 
				COLUMN_GENDER + " text not null, " +
				COLUMN_TYPE + 	" text not null, " +	
				COLUMN_DURATION + " integer not null, " +
				COLUMN_NAME + 	" text not null);";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(WorkoutsSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME +";");
		onCreate(database);
	}
	
}
