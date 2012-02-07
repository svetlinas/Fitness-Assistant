package bg.su.fmi.fitness.assistant.storage;

import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_DESCRIPTION;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_DURATION;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_ID;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_NAME;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.COLUMN_TYPE;
import static bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper.TABLE_NAME;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.Diet;
import bg.su.fmi.fitness.assistant.storage.helper.DietsSQLiteHelper;

public class DietsDataSourse {

	private SQLiteDatabase database;
	private DietsSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_TYPE, COLUMN_DURATION,
			COLUMN_NAME, COLUMN_DESCRIPTION };

	public DietsDataSourse(Context context) {
		dbHelper = new DietsSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Diet> getAllWorkouts() {
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

	private Diet getDiet(Cursor cursor) {
		return new Diet(cursor.getLong(0), cursor.getString(1),
				cursor.getInt(2), cursor.getString(3), cursor.getString(4));
	}
}
