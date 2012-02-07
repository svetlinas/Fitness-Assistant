package bg.su.fmi.fitness.assistant.storage.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WorkoutsSQLiteHelper extends BaseSQLiteHelper {
	
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

	public WorkoutsSQLiteHelper(Context context) {
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
		// TODO: use addWorkout method to populate this table
		addWorkout(database, "workout1", "male", "type", 5);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(WorkoutsSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(database);
	}
	
	private void addWorkout(SQLiteDatabase database, String name, String gender, String type, int duration) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_GENDER, gender);
		values.put(COLUMN_TYPE, type);
		values.put(COLUMN_DURATION, duration);
		values.put(COLUMN_NAME, name);
		database.insert(TABLE_NAME, null, values);
	}

}
