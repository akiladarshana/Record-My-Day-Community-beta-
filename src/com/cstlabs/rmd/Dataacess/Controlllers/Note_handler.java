package com.cstlabs.rmd.Dataacess.Controlllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cstlabs.rmd.Dataacess.Containers.Notes.Note;
import com.cstlabs.rmd.Dataacess.Containers.Notes.Note_header;
import com.cstlabs.rmd.Security.SecurityHandler;
import com.cstlabs.rmd.StorageHandlers.SQLLiteHandler;

/*
 * This class handles all notes related data access operations of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class Note_handler {

	private static SQLiteDatabase database;
	private static SQLLiteHandler handler;
	private static SimpleDateFormat format;

	public Note_handler(Context context) {
		handler = new SQLLiteHandler(context);
		format = new SimpleDateFormat("yyyy-MM-dd");
	}

	/*
	 * Saves given record in the database. performs encryption on sensitive
	 * fields
	 * 
	 * @param note record to be saved
	 * 
	 * @return created id of the note
	 */
	public long save(Note note) {
		database = handler.getWritableDatabase();
		ContentValues values = new ContentValues();
		// values.put("id", note.getId());
		values.put("date", format.format(note.getDate().getTime()));
		values.put("title", SecurityHandler.encryptString(note.getTitle()));
		values.put("location",
				SecurityHandler.encryptString(note.getLocation()));
		values.put("noteContent",
				SecurityHandler.encryptString(note.getNoteContent()));
		long s = database.insert("note", null, values);
		// database.execSQL("insert into Note (id,date,title,location,noteContent) values (null,'"+format.format(note.getDate().getTime())+"' , '"+note.getTitle()+"' , '"+note.getLocation()+"','"+SecurityHandler.encryptString(note.getNoteContent())+"')");
		database.close();
		return s;
	}

	/*
	 * updates given record in the database. performs encryption on sensitive
	 * fields
	 * 
	 * @param note record to be updated
	 * 
	 * @return number of affected rows in the database
	 */
	public long modify(int id, Note note) {
		database = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("id", note.getId());
		values.put("date", format.format(note.getDate().getTime()));
		values.put("title", SecurityHandler.encryptString(note.getTitle()));
		values.put("location",
				SecurityHandler.encryptString(note.getLocation()));
		values.put("noteContent",
				SecurityHandler.encryptString(note.getNoteContent()));

		long rowsAffected = database.update("Note", values, "id = " + id, null);
		database.close();
		return rowsAffected;
	}

	/*
	 * queries database on given condition and returns headers of records.
	 * performs decryption on sensitive fields
	 * 
	 * @param condition for the query
	 * 
	 * @return collection of headers in the result set
	 */
	public List<Note_header> search(String selection) {
		database = handler.getReadableDatabase();

		List<Note_header> notes = new ArrayList<Note_header>();
		Cursor cursor;

		if ("".equalsIgnoreCase(selection)) {
			cursor = database.query("Note", new String[] { "id", "date",
					"title", "location", "noteContent" }, null, null, null,
					null, null);

		} else {
			cursor = database.query("Note", new String[] { "id", "date",
					"title", "location", "noteContent" }, selection, null,
					null, null, null);

		}

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			try {
				Note_header note = new Note_header();
				note.setId(cursor.getInt(0));

				Calendar c = Calendar.getInstance();
				c.setTime(format.parse(cursor.getString(1)));

				note.setDate(c);
				note.setTitle(SecurityHandler.decryptString(cursor.getString(2)));
				note.setLocation(SecurityHandler.decryptString(cursor
						.getString(3)));
				// note.setNoteContent(SecurityHandler.encryptString(cursor.getString(4)));

				notes.add(note);
				cursor.moveToNext();
			} catch (ParseException ex) {
				Log.e("NoteDbHandler", "Parse error");
			}
		}
		cursor.close();
		database.close();
		return notes;
	}

	/*
	 * get the data for given note id
	 * 
	 * @param id of the note
	 * 
	 * @return record of the note
	 */
	public static Note get(int id) {
		database = handler.getReadableDatabase();

		Cursor cursor = database.query("Note", new String[] { "id", "date",
				"title", "location", "noteContent" }, "id = " + id, null, null,
				null, null);
		cursor.moveToFirst();
		Note note = new Note();
		try {

			note.setId(cursor.getInt(0));

			Calendar c = Calendar.getInstance();
			c.setTime(format.parse(cursor.getString(1)));

			note.setDate(c);
			note.setTitle(SecurityHandler.decryptString(cursor.getString(2)));
			note.setLocation(SecurityHandler.decryptString(cursor.getString(3)));
			note.setNoteContent(SecurityHandler.decryptString(cursor
					.getString(4)));

			cursor.moveToNext();
		} catch (ParseException ex) {
			Log.e("NoteDbHandler", "Parse error");
		}

		cursor.close();
		database.close();
		return note;
	}

	/*
	 * delete data for given note id
	 * 
	 * @param id of the note
	 */
	public void delete(int id) {
		database = handler.getWritableDatabase();
		database.delete("note", "id = " + id, null);
		database.close();
	}
}
