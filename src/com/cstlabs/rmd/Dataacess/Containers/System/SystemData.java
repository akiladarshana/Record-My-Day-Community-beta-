package com.cstlabs.rmd.Dataacess.Containers.System;

/**
 * This class is the wrapper of system settings in storage
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class SystemData {

	private String password = "";
	private String question = "";
	private String answer = "";
	private String key = "";

	/*
	 * @return encrypted security answer
	 */
	public String getAnswer() {
		return answer;
	}

	/*
	 * @return encrypted key
	 */
	public String getKey() {
		return key;
	}

	/*
	 * @return encrypted password
	 */
	public String getPassword() {
		return password;
	}

	/*
	 * @return encrypted question
	 */
	public String getQuestion() {
		return question;
	}

	/*
	 * set Encrypted answer
	 * 
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/*
	 * set Encrypted key
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/*
	 * set Encrypted password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * set Encrypted question
	 * 
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

}
