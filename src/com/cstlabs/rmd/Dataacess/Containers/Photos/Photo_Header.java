package com.cstlabs.rmd.Dataacess.Containers.Photos;

import java.util.Calendar;

/**
 * This class is the wrapper of photo records in storage this class is
 * specifically designed to work with search queries for fast access
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class Photo_Header {

	private int id;
	private Calendar date;
	private String title;
	private String location;

	/*
	 * @return id of the photo
	 */
	public int getId() {
		return id;
	}

	/*
	 * Sets the id of the photo
	 * 
	 * @param id of the photo
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * @return title of the photo
	 */
	public String getTitle() {
		return title;
	}

	/*
	 * Sets the title of the photo
	 * 
	 * @param title of the photo
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * @return location of the photo
	 */
	public String getLocation() {
		return location;
	}

	/*
	 * Sets the location of the photo
	 * 
	 * @param location of the photo
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/*
	 * @return date of the photo
	 */
	public Calendar getDate() {
		return date;
	}

	/*
	 * Sets the date of the photo
	 * 
	 * @param date of the photo
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

}
