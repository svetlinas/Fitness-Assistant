package bg.su.fmi.fitness.assistant.storage.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class contains information about the table's creation 
 * in the database and some constants.
 *
 */
public class DietsSQLiteHelper {

	public static final String TABLE_NAME = "diets";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_DURATION = "duration";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "discription";

	private static final String TABLE_CREATE = "create table " + TABLE_NAME
			+ "( " + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_TYPE + " text not null, " + COLUMN_DURATION
			+ " integer not null, " + COLUMN_NAME + " text not null, "
			+ COLUMN_DESCRIPTION + " text not null);";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DietsSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
		onCreate(database);
	}

}
