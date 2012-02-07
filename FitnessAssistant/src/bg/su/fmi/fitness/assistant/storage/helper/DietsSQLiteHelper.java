package bg.su.fmi.fitness.assistant.storage.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DietsSQLiteHelper extends BaseSQLiteHelper {

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

	public DietsSQLiteHelper(Context context) {
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
		// TODO: use the addDiets method to populate this table
		addDiet(database, "Moon diet", "type1", 5, "Moon diet discription");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DietsSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(database);
	}

	private void addDiet(SQLiteDatabase database, String name, String type,
			int duration, String description) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_TYPE, type);
		values.put(COLUMN_DURATION, duration);
		values.put(COLUMN_DESCRIPTION, description);
		database.insert(TABLE_NAME, null, values);
	}

}
