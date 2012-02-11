package bg.su.fmi.fitness.assistant.storage;

import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_DESCRIPTION;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_DURATION;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_NAME;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_TYPE;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.Diet;
import bg.su.fmi.fitness.assistant.entities.SearchedObject;
import bg.su.fmi.fitness.assistant.storage.helper.BaseSQLiteHelper;

public class DietsDataSourse {

	private SQLiteDatabase database;
	private BaseSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_TYPE, COLUMN_DURATION,
			COLUMN_NAME, COLUMN_DESCRIPTION };

	public DietsDataSourse(Context context) {
		dbHelper = new BaseSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public long addDiet(String type, int duration, String name, String description) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_TYPE, type);
		values.put(COLUMN_DURATION, duration);
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_DESCRIPTION, description);
		return database.insert(TABLE_NAME, null, values);
	}

	public long addDiet(Diet d) {
		return addDiet(d.getType(), d.getDuration(), d.getName(), d.getDescription());
	}

	public List<Diet> getAllDiets() {
		final List<Diet> diets = new ArrayList<Diet>();
		final Cursor cursor = database.query(TABLE_NAME, allColumns, null,
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Diet diet = getDiet(cursor);
			diets.add(diet);
			cursor.moveToNext();
		}
		cursor.close();

		return diets;
	}
	
	/**
	 * Returns an Diet object with the given id.
	 * @param id The id of the needed diet
	 * @return the Diet with the given id
	 */
	public Diet getExercise(long id) {
		final Cursor cursor = database.query(TABLE_NAME, allColumns, COLUMN_ID+"=?",
				new String[] {String.valueOf(id)}, null, null, null);
		cursor.moveToFirst();
		final Diet diet = getDiet(cursor);
		cursor.close();

		return diet;
	}
	
	/**
	 * Returns a list of SerachedObject containing all ids of the diets with
	 * the given dietName.
	 * 
	 * @param dietName
	 *            The name of the diet we are looking for
	 * @return List of SearchedObject
	 */
	public List<SearchedObject> getSearchedExersizes(String dietName) {
		List<SearchedObject> result = new ArrayList<SearchedObject>();

		Cursor cursor = database.query(TABLE_NAME, allColumns, COLUMN_NAME
				+ "=?", new String[] { dietName }, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			final SearchedObject searchedObject = getSearchedObject(cursor);
			result.add(searchedObject);
			cursor.moveToNext();
		}
		cursor.close();

		return result;
	}
	
	public int deleteDiet(long id) {
		return database.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] { id
				+ "" });
	}

	public int updateDiet(long id, String type, int duration, String name, String description) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_TYPE, type);
		values.put(COLUMN_DURATION, duration);
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_DESCRIPTION, description);
		return database.update(TABLE_NAME, values, COLUMN_ID + "=?",
				new String[] { id + "" });
	}

	public int updateDiet(long id, Diet d) {
		return updateDiet(id, d.getType(), d.getDuration(), d.getName(), d.getDescription());
	}

	private Diet getDiet(Cursor cursor) {
		return new Diet(cursor.getLong(0), cursor.getString(1),
				cursor.getInt(2), cursor.getString(3), cursor.getString(4));
	}
	
	private SearchedObject getSearchedObject(Cursor cursor) {
		return new SearchedObject(cursor.getString(0), cursor.getLong(1));
	}
}

