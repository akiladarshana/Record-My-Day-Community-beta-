package com.cstlabs.rmd.Dataacess.Controlllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cstlabs.rmd.Dataacess.Containers.System.SystemData;
import com.cstlabs.rmd.StorageHandlers.SQLLiteHandler;

/*
 * This class handles all system data related data access operations of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class System_handler {

	private static SQLiteDatabase database;
	private static SQLLiteHandler handler;

	public System_handler(Context context) {
		handler = new SQLLiteHandler(context);

	}

	/*
	 * Saves given record in the database. performs encryption on sensitive
	 * fields
	 * 
	 * @param record to be saved
	 * 
	 * @return created id of the record
	 */
	public long save(SystemData data) {
		database = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("password", data.getPassword());
		values.put("Question", data.getQuestion());
		values.put("answer", data.getAnswer());
		values.put("key", data.getKey());
		long s = database.insert("SystemData", null, values);
		// database.execSQL("insert into Note (id,date,title,location,noteContent) values (null,'"+format.format(note.getDate().getTime())+"' , '"+note.getTitle()+"' , '"+note.getLocation()+"','"+SecurityHandler.encryptString(note.getNoteContent())+"')");
		database.close();
		return s;
	}

	/*
	 * updates record in the database.
	 * 
	 * @param record to be updated
	 * 
	 * @return number of affected rows in the database
	 */
	public long modifySystemData(SystemData data) {
		database = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("password", data.getPassword());
		values.put("Question", data.getQuestion());
		values.put("answer", data.getAnswer());
		values.put("key", data.getKey());

		long rowsAffected = database.update("SystemData", values, null, null);
		database.close();
		return rowsAffected;
	}

	/*
	 * get the system data
	 * 
	 * 
	 * @return record from the database
	 */
	public SystemData getSystemData() {
		database = handler.getReadableDatabase();

		Cursor cursor = database.query("SystemData", new String[] { "password",
				"Question", "answer", "key" }, null, null, null, null, null);

		SystemData data = new SystemData();
		if (cursor.getCount() == 0) {

			cursor.close();
			database.close();
			return data;
		}
		cursor.moveToFirst();

		data.setPassword(cursor.getString(0));
		data.setQuestion(cursor.getString(1));
		data.setAnswer(cursor.getString(2));
		data.setKey(cursor.getString(3));

		cursor.close();
		database.close();
		return data;
	}

}
