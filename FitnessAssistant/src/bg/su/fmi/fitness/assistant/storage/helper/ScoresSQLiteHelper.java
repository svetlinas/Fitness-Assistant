package bg.su.fmi.fitness.assistant.storage.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ScoresSQLiteHelper extends BaseSQLiteHelper {

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
			+ ExersizesSQLiteHelper.COLUMN_ID + ");";

	public ScoresSQLiteHelper(Context context) {
		super(context);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(TABLE_CREATE);
		// TODO: populate this table using addInformation method
		addInformation(database, 1, 1, 1, 1.0,
				Calendar.getInstance().getTime(), Calendar.getInstance()
						.getTime());
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(ScoresSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(database);
	}

	private void addInformation(SQLiteDatabase database, long workoutId,
			long exersizeId, int setNumber, double weight, Date time,
			Date created) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_WORKOUT_ID, workoutId);
		values.put(COLUMN_EXERSIZE_ID, exersizeId);
		values.put(COLUMN_SET_NUMBER, setNumber);
		values.put(COLUMN_WEIGHT, weight);
		values.put(COLUMN_TIME, new SimpleDateFormat().format(time));
		values.put(COLUMN_CREATED, new SimpleDateFormat().format(created));
		database.insert(TABLE_NAME, null, values);
	}
}
