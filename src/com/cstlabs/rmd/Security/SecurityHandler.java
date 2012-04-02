/**
 * 
 */
package com.cstlabs.rmd.Security;

import android.util.Log;

/*
 * This class handles all security operations in the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */

public class SecurityHandler {

	// system default key to be use in key encryption
	private static final String defaultkey = "cncpunchimec";

	private static String key = "cncpunchimec";
	private static String password = "abc";
	private static String question;
	private static String answer;

	/*
	 * @return security key
	 */
	public static String getKey() {
		return key;
	}

	/*
	 * @param security answer
	 */
	public static void setAnswer(String answer) {
		SecurityHandler.answer = answer;
	}

	/*
	 * @param security question
	 */
	public static void setQuestion(String question) {
		SecurityHandler.question = question;
	}

	/*
	 * @return default system key
	 */
	public static String getDefaultkey() {
		return defaultkey;
	}

	/*
	 * @return security answer
	 */
	public static String getAnswer() {
		return answer;
	}

	/*
	 * @return user password
	 */
	public static String getPassword() {
		return password;
	}

	/*
	 * @param key
	 */
	public static void setKey(String key) {
		SecurityHandler.key = key;
	}

	/*
	 * @return security question
	 */
	public static String getQuestion() {
		return question;
	}

	/*
	 * @param password
	 */
	public static void setPassword(String password) {
		SecurityHandler.password = password;
	}

	/*
	 * Authenticate user login attempt by checking passwords
	 * 
	 * @param password provided by user
	 * 
	 * @return whether attempt is validated or not
	 */
	public static boolean loginAttempt(String password) {

		if (password.equals(SecurityHandler.password)) {

			return true;
		} else {

			return false;
		}
	}

	/*
	 * Encrypts a key using default system key
	 * 
	 * 
	 * @param string to be encrypted
	 * 
	 * @return encrypted string
	 */
	public static String encryptKey(String original) {
		try {
			return Crypto.encrypt(defaultkey, original);
		} catch (Exception e) {
			Log.e("Error in Enctryption", e.getMessage());

			return null;
		}

	}

	/*
	 * Encrypts a String using defined system key
	 * 
	 * 
	 * @param string to be encrypted
	 * 
	 * @return encrypted string
	 */
	public static String encryptString(String original) {
		try {
			return Crypto.encrypt(key, original);
		} catch (Exception e) {
			Log.e("Error in Enctryption", e.getMessage());

			return null;
		}

	}

	/*
	 * Encrypts a byte Stream using defined system key
	 * 
	 * 
	 * @param byte Stream to be encrypted
	 * 
	 * @return byte Stream
	 */
	public static byte[] encryptStream(byte[] original) {
		try {
			return Crypto.encrypt(key, original);
		} catch (Exception e) {
			Log.e("Error in Enctryption", e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Derypts a String using defined system key
	 * 
	 * 
	 * @param string to be decrypted
	 * 
	 * @return decrypted string
	 */
	public static String decryptString(String cipher) {

		try {

			return Crypto.decrypt(key, cipher);

		} catch (Exception e) {
			Log.e("Error in Dectryption", e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Encrypts a key using default system key
	 * 
	 * 
	 * @param string to be encrypted
	 * 
	 * @return encrypted string
	 */
	public static String decryptKey(String cipher) {

		try {

			return Crypto.decrypt(defaultkey, cipher);

		} catch (Exception e) {
			Log.e("Error in Dectryption", e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Decrypts a byte Stream using defined system key
	 * 
	 * 
	 * @param byte Stream to be decrypted
	 * 
	 * @return byte Stream
	 */
	public static byte[] decryptStream(byte[] cipher) {

		try {

			return Crypto.decrypt(key, cipher);

		} catch (Exception e) {
			Log.e("Error in Dectryption", e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

}
