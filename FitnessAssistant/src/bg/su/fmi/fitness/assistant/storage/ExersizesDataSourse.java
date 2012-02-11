package bg.su.fmi.fitness.assistant.storage;

import static bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper.COLUMN_DISCRIPTION;
import static bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper.COLUMN_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper.COLUMN_NAME;
import static bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper.COLUMN_REPETITIONS;
import static bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper.COLUMN_SETS;
import static bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper.COLUMN_VIDEO;
import static bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.entities.SearchedObject;
import bg.su.fmi.fitness.assistant.storage.helper.BaseSQLiteHelper;

public class ExersizesDataSourse {

	private SQLiteDatabase database;
	private BaseSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_NAME, COLUMN_SETS,
			COLUMN_REPETITIONS, COLUMN_DISCRIPTION, COLUMN_VIDEO };

	public ExersizesDataSourse(Context context) {
		dbHelper = new BaseSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public long addExersize(String name, int sets, int repetitions,
			String description, String video) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_SETS, sets);
		values.put(COLUMN_REPETITIONS, repetitions);
		values.put(COLUMN_DISCRIPTION, description);
		values.put(COLUMN_VIDEO, video);
		return database.insert(TABLE_NAME, null, values);
	}

	public long addExersize(Exersize e) {
		return addExersize(e.getName(), e.getSets(), e.getRepetitions(),
				e.getDescription(), e.getVideo());
	}

	public List<Exersize> getAllExercises() {
		final List<Exersize> exersizes = new ArrayList<Exersize>();
		final Cursor cursor = database.query(TABLE_NAME, allColumns, null,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Exersize exersize = getExersize(cursor);
			exersizes.add(exersize);
			cursor.moveToNext();
		}
		cursor.close();

		return exersizes;
	}

	/**
	 * Returns an Exersize object with the given id.
	 * @param id The id of the needed exercise
	 * @return the Exersize with the given id
	 */
	public Exersize getExercise(long id) {
		final Cursor cursor = database.query(TABLE_NAME, allColumns, COLUMN_ID+"=?",
				new String[] {String.valueOf(id)}, null, null, null);
		cursor.moveToFirst();
		final Exersize exersize = getExersize(cursor);
		cursor.close();

		return exersize;
	}
	
	/**
	 * Returns a list of SerachedObject containing all ids of the exercises with
	 * the given exersizeName.
	 * 
	 * @param exersizeName
	 *            The name of the exercises we are looking for
	 * @return List of SearchedObject
	 */
	public List<SearchedObject> getSearchedExersizes(String exersizeName) {
		List<SearchedObject> result = new ArrayList<SearchedObject>();

		Cursor cursor = database.query(TABLE_NAME, allColumns, COLUMN_NAME
				+ "=?", new String[] { exersizeName }, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			final SearchedObject searchedObject = getSearchedObject(cursor);
			result.add(searchedObject);
			cursor.moveToNext();
		}
		cursor.close();

		return result;
	}

	public int deleteExercise(long id) {
		return database.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] { id
				+ "" });
	}

	public int updateExercise(long id, String name, int sets, int repetitions,
			String description, String video) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_SETS, sets);
		values.put(COLUMN_REPETITIONS, repetitions);
		values.put(COLUMN_DISCRIPTION, description);
		values.put(COLUMN_VIDEO, video);
		return database.update(TABLE_NAME, values, COLUMN_ID + "=?",
				new String[] { id + "" });
	}

	public int updateExercise(long id, Exersize e) {
		return updateExercise(id, e.getName(), e.getSets(), e.getRepetitions(),
				e.getDescription(), e.getVideo());
	}

	private Exersize getExersize(Cursor cursor) {
		return new Exersize(cursor.getLong(0), cursor.getString(1),
				cursor.getInt(2), cursor.getInt(3), cursor.getString(4),
				cursor.getString(5));
	}

	private SearchedObject getSearchedObject(Cursor cursor) {
		return new SearchedObject(cursor.getString(0), cursor.getLong(1));
	}

}
