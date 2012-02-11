package bg.su.fmi.fitness.assistant.storage.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * This class creates the table in the database which is 
 * responsible for the storage of the link between 
 * workouts and exercises.
 *
 */
public class WorkoutsExersizesSQLiteHelper extends BaseSQLiteHelper {

	public static final String TABLE_NAME = "workouts-exersizes";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_WORKOUT_ID = "workout_id";
	public static final String COLUMN_EXERSIZE_ID = "exersize_id";
	public static final String COLUMN_DAY = "day";
	public static final String COLUMN_DAY_CREATED = "created";

	private static final String TABLE_CREATE = "create table " + TABLE_NAME
			+ "( " + COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_WORKOUT_ID + " integer not null, " + COLUMN_EXERSIZE_ID
			+ " integer not null, " + COLUMN_DAY + " integer not null, "
			+ COLUMN_DAY_CREATED + " date not null, foreign key ("
			+ COLUMN_WORKOUT_ID + ") REFERENCES "
			+ WorkoutsExersizesSQLiteHelper.TABLE_NAME + " ("
			+ WorkoutsExersizesSQLiteHelper.COLUMN_ID + "), foreign key ("
			+ COLUMN_EXERSIZE_ID + ") REFERENCES "
			+ ExersizesSQLiteHelper.TABLE_NAME + " ("
			+ ExersizesSQLiteHelper.COLUMN_ID + "));";

	public WorkoutsExersizesSQLiteHelper(Context context) {
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
		// TODO: use the addInformation method to populate this table
		addInformation(database, 1, 1, 5, Calendar.getInstance().getTime());
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(WorkoutsExersizesSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(database);
	}

	private void addInformation(SQLiteDatabase database, long workoutId, long exersizeId, int day, Date created){
		final ContentValues values = new ContentValues();
		values.put(COLUMN_WORKOUT_ID, workoutId);
		values.put(COLUMN_EXERSIZE_ID, exersizeId);
		values.put(COLUMN_DAY, day);
		values.put(COLUMN_DAY_CREATED, new SimpleDateFormat().format(created).toString());
		database.insert(TABLE_NAME, null, values);
	}
}
