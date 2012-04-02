package com.cstlabs.rmd.SystemCore;

import java.util.Calendar;
import java.util.Random;

import android.content.Context;

import com.cstlabs.rmd.Dataacess.Containers.System.SystemData;
import com.cstlabs.rmd.Dataacess.Controlllers.System_handler;
import com.cstlabs.rmd.Security.SecurityHandler;

/*
 * This class handles all system settings related operations in the system  
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */

public class SystemUtilities {

	private static SystemData settings;

	/*
	 * Sets the system accessible system data holder by decrypting data holder
	 * obtained by storage
	 * 
	 * @param encrypted system data holder
	 */
	public static void setSettings(SystemData settings) {
		SystemUtilities.settings = settings;
		SecurityHandler.setKey(SecurityHandler.decryptKey(settings.getKey()));
		SecurityHandler.setAnswer(SecurityHandler.decryptString(settings
				.getAnswer()));
		SecurityHandler.setQuestion(SecurityHandler.decryptString(settings
				.getQuestion()));
		SecurityHandler.setPassword(SecurityHandler.decryptString(settings
				.getPassword()));

	}

	/*
	 * Sets the system settings in the first instance of program use.
	 * 
	 * @param readerble system data holder
	 * 
	 * @param reffering context
	 */
	public static void firstrun(SystemData settings, Context t) {

		String newkey;
		newkey = generateKey();
		saveSettings(settings, t, newkey);
	}

	/*
	 * generates a new encryption key based on system time and pseudo random
	 * generator
	 * 
	 * @return generated key
	 */
	private static String generateKey() {
		String newkey;
		Random seed = new Random(Calendar.getInstance().getTime().getTime());
		newkey = "" + seed.nextLong();

		SecurityHandler.setKey(newkey);
		return newkey;
	}

	/*
	 * saves readerble system settings container to database. encryption of
	 * fields used in first run instance only
	 * 
	 * @param readerble system data holder
	 * 
	 * @param reffering context
	 * 
	 * @param key for the encryption
	 */
	private static void saveSettings(SystemData settings, Context t,
			String newkey) {

		System_handler handler = new System_handler(t);
		settings.setKey(SecurityHandler.encryptKey(newkey));
		settings.setPassword(SecurityHandler.encryptString(settings
				.getPassword()));
		settings.setQuestion(SecurityHandler.encryptString(settings
				.getQuestion()));
		settings.setAnswer(SecurityHandler.encryptString(settings.getAnswer()));
		handler.save(settings);
	}

	/*
	 * Modifies password .performs update operations on both internal state and
	 * database
	 * 
	 * @param new password
	 * 
	 * @param reffering context
	 */
	public static void ModifyPassword(String password, Context t) {

		System_handler handler = new System_handler(t);
		SystemData data = new SystemData();
		data.setKey(settings.getKey());
		data.setPassword(SecurityHandler.encryptString(password));
		data.setQuestion(settings.getQuestion());
		data.setAnswer(settings.getAnswer());
		handler.modifySystemData(data);
		setSettings(data);
	}

	/*
	 * Modifies security question.performs update operations on both internal
	 * state and database
	 * 
	 * 
	 * @param new question
	 * 
	 * @param new answer
	 * 
	 * @param reffering context
	 */
	public static void ModifyQuestion(String Question, String Answer, Context t) {

		System_handler handler = new System_handler(t);
		SystemData data = new SystemData();
		data.setKey(settings.getKey());
		data.setPassword(settings.getPassword());
		data.setQuestion(SecurityHandler.encryptString(Question));
		data.setAnswer(SecurityHandler.encryptString(Answer));
		handler.modifySystemData(data);
		setSettings(data);
	}

}
