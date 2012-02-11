package bg.su.fmi.fitness.assistant.storage.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Base implementation of SQLiteOpenHelper. Creates all 
 * the tables needed by the application.
 * 
 */
public class BaseSQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fitness.db";
	private static final int DATABASE_VERSION = 1;

	
	public BaseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		DietsSQLiteHelper.onCreate(database);
		ExersizesSQLiteHelper.onCreate(database);
		WorkoutsSQLiteHelper.onCreate(database);
		WorkoutsExersizesSQLiteHelper.onCreate(database);
		WorkoutsDietsSQLiteHelper.onCreate(database);
		ScoresSQLiteHelper.onCreate(database);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		DietsSQLiteHelper.onUpgrade(database, 1, 2);
		ExersizesSQLiteHelper.onUpgrade(database, 1, 2);
		WorkoutsSQLiteHelper.onUpgrade(database, 1, 2);
		WorkoutsExersizesSQLiteHelper.onUpgrade(database, 1, 2);
		WorkoutsDietsSQLiteHelper.onUpgrade(database, 1, 2);
		ScoresSQLiteHelper.onUpgrade(database, 1, 2);
	}
}
