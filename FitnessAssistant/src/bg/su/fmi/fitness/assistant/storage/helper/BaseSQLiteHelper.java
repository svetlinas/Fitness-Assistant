package bg.su.fmi.fitness.assistant.storage.helper;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseSQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "fitness.db";
	private static final int DATABASE_VERSION = 1;
	
	public BaseSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

}
