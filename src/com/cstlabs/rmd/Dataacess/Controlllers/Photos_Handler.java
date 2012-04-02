package com.cstlabs.rmd.Dataacess.Controlllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cstlabs.rmd.Dataacess.Containers.Photos.*;
import com.cstlabs.rmd.Security.SecurityHandler;
import com.cstlabs.rmd.StorageHandlers.SQLLiteHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * This class handles all photos related data access operations of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class Photos_Handler {

	private SQLiteDatabase database;
	private SQLLiteHandler handler;
	private SimpleDateFormat format;

	public Photos_Handler(Context context) {
		handler = new SQLLiteHandler(context);
		format = new SimpleDateFormat("yyyy-MM-dd");
	}

	/*
	 * Saves given record in the database. performs encryption on sensitive
	 * fields
	 * 
	 * @param photo record to be saved
	 * 
	 * @return created id of the photo
	 */
	public long save(Photos photos) {

		database = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("date", format.format(photos.getDate().getTime()));
		values.put("title", SecurityHandler.encryptString(photos.getTitle()));
		values.put("location",
				SecurityHandler.encryptString(photos.getLocation()));
		values.put("noteDescription",
				SecurityHandler.encryptString(photos.getNoteDescription()));
		values.put("photo", SecurityHandler.encryptStream(photos.getPhoto()));

		long insertId = database.insert("Photos", null, values);
		database.close();
		return insertId;

		
		
	}

	/*
	 * updates given record in the database. performs encryption on sensitive
	 * fields
	 * 
	 * @param photo record to be updated
	 * 
	 * @return number of affected rows in the database
	 */
	public void modify(int id, Photos photos) {

		database = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("id", photos.getId());
		values.put("date", format.format(photos.getDate().getTime()));
		values.put("title", SecurityHandler.encryptString(photos.getTitle()));
		values.put("location",
				SecurityHandler.encryptString(photos.getLocation()));
		values.put("noteDescription",
				SecurityHandler.encryptString(photos.getNoteDescription()));
		values.put("photo", SecurityHandler.encryptStream(photos.getPhoto()));
		database.update("Photos", values, "id = " + id, null);
		database.close();
		Log.v("Desc", photos.getNoteDescription());
	}

	/*
	 * queries database on given condition and returns headers of records.
	 * performs decryption on sensitive fields
	 * 
	 * @param condition for the query
	 * 
	 * @return collection of headers in the result set
	 */
	public List<Photo_Header> search(String selection) {
		database = handler.getReadableDatabase();
		List<Photo_Header> photoss = new ArrayList<Photo_Header>();
		Cursor cursor;

		if ("".equalsIgnoreCase(selection)) {

			cursor = database.query("photos", new String[] { "id", "date",
					"title", "location", "noteDescription", "photo" }, null,
					null, null, null, null);

		} else {

			cursor = database.query("photos", new String[] { "id", "date",
					"title", "location", "noteDescription", "photo" },
					selection, null, null, null, null);
		}

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			try {
				Photo_Header photos = new Photo_Header();
				photos.setId(cursor.getInt(0));

				Calendar c = Calendar.getInstance();
				c.setTime(format.parse(cursor.getString(1)));

				photos.setDate(c);
				photos.setTitle(SecurityHandler.decryptString(cursor
						.getString(2)));
				photos.setLocation(SecurityHandler.decryptString(cursor
						.getString(3)));

				photoss.add(photos);
				cursor.moveToNext();
			} catch (ParseException ex) {
				Log.e("PhotosDbHandler", "Parse error");
			}
		}
		cursor.close();
		database.close();
		return photoss;
	}

	/*
	 * get the data for given photo id
	 * 
	 * @param id of the photo
	 * 
	 * @return record of the photo
	 */
	public Photos get(int id) {
		database = handler.getReadableDatabase();

		Cursor cursor = database.query("photos", new String[] { "id", "date",
				"title", "location", "noteDescription", "photo" },
				"id = " + id, null, null, null, null);
		cursor.moveToFirst();
		Photos photos = new Photos();
		try {

			photos.setId(cursor.getInt(0));

			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(cursor.getString(1)));
			photos.setDate(c);
			photos.setTitle(SecurityHandler.decryptString(cursor.getString(2)));
			photos.setLocation(SecurityHandler.decryptString(cursor
					.getString(3)));
			photos.setNoteDescription(SecurityHandler.decryptString(cursor
					.getString(4)));
			photos.setPhoto(SecurityHandler.decryptStream(cursor.getBlob(5)));

			cursor.moveToNext();
		} catch (ParseException ex) {
			Log.e("PhotosDbHandler", "Parse error");
		}

		cursor.close();
		database.close();
		return photos;
	}

	/*
	 * delete data for given photo id
	 * 
	 * @param id of the photo
	 */
	public void delete(int id) {
		database = handler.getWritableDatabase();
		database.delete("Photos", "id = " + id, null);
		database.close();
	}
}
