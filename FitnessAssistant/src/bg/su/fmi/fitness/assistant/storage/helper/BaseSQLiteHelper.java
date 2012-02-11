package bg.su.fmi.fitness.assistant.storage.helper;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Base implementation of SQLiteOpenHelper. All classes which
 * will be used to manipulate the database should extend this class.
 * 
 */
public abstract class BaseSQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fitness.db";
	private static final int DATABASE_VERSION = 1;
	
	public BaseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

}
