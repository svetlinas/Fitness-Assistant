package bg.su.fmi.fitness.assistant.storage;

import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.COLUMN_CREATED;
import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.COLUMN_EXERSIZE_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.COLUMN_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.COLUMN_SET_NUMBER;
import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.COLUMN_TIME;
import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.COLUMN_WEIGHT;
import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.COLUMN_WORKOUT_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper.TABLE_NAME;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.Score;
import bg.su.fmi.fitness.assistant.storage.helper.ScoresSQLiteHelper;

public class ScoresDataSourse {
	private SQLiteDatabase database;
	private ScoresSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_WORKOUT_ID, COLUMN_EXERSIZE_ID,
			COLUMN_SET_NUMBER, COLUMN_WEIGHT, COLUMN_TIME, COLUMN_CREATED};

	public ScoresDataSourse(Context context) {
		dbHelper = new ScoresSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Score> getAllWorkouts() {
		final List<Score> scores = new ArrayList<Score>();
		final Cursor cursor = database.query(TABLE_NAME, allColumns, null,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Score score = getScore(cursor);
			scores.add(score);
			cursor.moveToNext();
		}
		cursor.close();

		return scores;
	}

	private Score getScore(Cursor cursor) {
		Date time = null;
		Date created = null;
		try {
			final SimpleDateFormat formatter = new SimpleDateFormat();
			time = formatter.parse(cursor.getString(5));
			created = formatter.parse(cursor.getString(6));
		} catch (ParseException e){
			e.printStackTrace();
		}
		return new Score(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getInt(3), cursor.getDouble(4), time, created);
	}

}
