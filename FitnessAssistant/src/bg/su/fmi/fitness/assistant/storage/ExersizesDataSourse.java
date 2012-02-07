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

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bg.su.fmi.fitness.assistant.entities.Exersize;
import bg.su.fmi.fitness.assistant.storage.helper.ExersizesSQLiteHelper;

public class ExersizesDataSourse {

	private SQLiteDatabase database;
	private ExersizesSQLiteHelper dbHelper;
	private String[] allColumns = { COLUMN_ID, COLUMN_NAME, COLUMN_SETS,
			COLUMN_REPETITIONS, COLUMN_DISCRIPTION, COLUMN_VIDEO };

	public ExersizesDataSourse(Context context) {
		dbHelper = new ExersizesSQLiteHelper(context);
	}

	public void open() {
		database = dbHelper.getReadableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public List<Exersize> getAllWorkouts() {
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

	private Exersize getExersize(Cursor cursor) {
		return new Exersize(cursor.getLong(0), cursor.getString(1),
				cursor.getInt(2), cursor.getInt(3), cursor.getString(4),
				cursor.getString(5));
	}

}
