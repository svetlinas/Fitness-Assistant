package bg.su.fmi.fitness.assistant.storage.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class contains information about the table's creation 
 * in the database and some constants.
 *
 */
public class ExersizesSQLiteHelper {

	public static final String TABLE_NAME = "exersizes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_SETS = "sets";
	public static final String COLUMN_REPETITIONS = "repetitions";
	public static final String COLUMN_DISCRIPTION = "discription";
	public static final String COLUMN_VIDEO = "video";

	private static final String TABLE_CREATE = "create table " + TABLE_NAME
			+ "( " + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null, " + COLUMN_SETS
			+ " integer not null, " + COLUMN_REPETITIONS
			+ " integer not null, " + COLUMN_DISCRIPTION + " text not null, "
			+ COLUMN_VIDEO + " text not null);";

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ExersizesSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
		onCreate(database);
	}

}
