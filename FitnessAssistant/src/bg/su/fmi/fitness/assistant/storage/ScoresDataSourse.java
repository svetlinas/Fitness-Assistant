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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.Score;
import bg.su.fmi.fitness.assistant.storage.helper.BaseSQLiteHelper;

public class ScoresDataSourse {
	private SQLiteDatabase database;
	private BaseSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_WORKOUT_ID,
			COLUMN_EXERSIZE_ID, COLUMN_SET_NUMBER, COLUMN_WEIGHT, COLUMN_TIME,
			COLUMN_CREATED };

	public ScoresDataSourse(Context context) {
		dbHelper = new BaseSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
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

	public void addScore(long workoutId, long exersizeId, int setNumber,
			double weight, Date time, Date created) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_WORKOUT_ID, workoutId);
		values.put(COLUMN_EXERSIZE_ID, exersizeId);
		values.put(COLUMN_SET_NUMBER, setNumber);
		values.put(COLUMN_WEIGHT, weight);
		values.put(COLUMN_TIME, new SimpleDateFormat().format(time));
		values.put(COLUMN_CREATED, new SimpleDateFormat().format(created));
		database.insert(TABLE_NAME, null, values);
	}

	public void addScore(Score score) {
		addScore(score.getWorkoutId(), score.getExersizeId(),
				score.getSetNumber(), score.getWeight(), score.getTime(),
				score.getCreated());
	}
	
	public List<Score> getScoresByDay(Date date) {
		final List<Score> scores = new ArrayList<Score>();
		final Cursor cursor = database.query(TABLE_NAME, allColumns,
				COLUMN_CREATED + "=?",
				new String[] { date.toString() }, null, null, null); //TODO: Maybe sql data
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Score(cursor.getLong(0), cursor.getLong(1),
				cursor.getLong(2), cursor.getInt(3), cursor.getDouble(4), time,
				created);
	}

}
