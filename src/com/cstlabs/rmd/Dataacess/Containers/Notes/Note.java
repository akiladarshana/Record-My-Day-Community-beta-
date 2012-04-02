package com.cstlabs.rmd.Dataacess.Containers.Notes;

import java.util.Calendar;

/**
 * This class is the wrapper of notes records in storage.
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class Note {

	private int id;
	private Calendar date;
	private String title;
	private String location;
	private String noteContent;

	/*
	 * @return id of the note
	 */
	public int getId() {
		return id;
	}

	/*
	 * Sets the id of the note
	 * 
	 * @param id of the note
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * @return title of the note
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * Sets the title of the note
	 * 
	 * @param title of the note
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * @return location of the note
	 */
	public String getLocation() {
		return location;
	}

	/*
	 * Sets the location of the note
	 * 
	 * @param location of the note
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/*
	 * @return date of the note
	 */
	public Calendar getDate() {
		return date;
	}

	/*
	 * Sets the date of the note
	 * 
	 * @param date of the note
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/*
	 * @return content of the note
	 */
	public String getNoteContent() {
		return noteContent;
	}

	/*
	 * Sets the content of the note
	 * 
	 * @param content of the note
	 */
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
}
