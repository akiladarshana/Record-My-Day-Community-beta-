package com.cstlabs.rmd.UIControl.System;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.Dataacess.Containers.System.SystemData;
import com.cstlabs.rmd.SystemCore.SystemUtilities;

/*
 * This class handles all actions performed in system setup user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class FirstRun extends Activity {

	private TextView password, confirm, question, answer;
	private Button save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_run);
		setTextFields();
		setButtons();
	}

	/*
	 * setting up buttons from UI
	 */
	private void setButtons() {

		save = (Button) findViewById(R.id.first_run_ok);

		save.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				setupSystem(v);

			}

		});

	}

	/*
	 * Setting up text views from UI
	 */
	private void setTextFields() {
		password = (TextView) findViewById(R.id.first_run_password);
		confirm = (TextView) findViewById(R.id.first_run_confirm_password);
		question = (TextView) findViewById(R.id.first_run_question);
		answer = (TextView) findViewById(R.id.first_run_answer);
		password.requestFocus();

	}

	/*
	 * generate and save system settings data from user inputs
	 */
	private void setupSystem(View v) {

		String pass, conf, ques, ans;
		pass = password.getText().toString();
		conf = confirm.getText().toString();
		ques = question.getText().toString();
		ans = answer.getText().toString();

		if ("".equalsIgnoreCase(pass) || "".equalsIgnoreCase(conf)
				|| "".equalsIgnoreCase(ques) || "".equalsIgnoreCase(ans)) {
			Toast.makeText(getBaseContext(), "Please fill all fields",
					Toast.LENGTH_SHORT).show();
			password.setText("");
			confirm.setText("");
			return;
		}
		if (!conf.equals(pass)) {
			Toast.makeText(getBaseContext(), "Passwords doesn't match!",
					Toast.LENGTH_SHORT).show();
			password.setText("");
			confirm.setText("");
			return;
		}

		savesettings(pass, ques, ans);
		startSystem();

	}

	/*
	 * on sucessfull setup procedure , starts the system
	 */
	private void startSystem() {

		finish();
	}

	/*
	 * save generated system settings
	 */
	private void savesettings(String pass, String ques, String ans) {
		SystemData settigs = new SystemData();

		settigs.setPassword(pass);
		settigs.setQuestion(ques);
		settigs.setAnswer(ans);

		SystemUtilities.firstrun(settigs, this);
		Toast.makeText(getBaseContext(), "Settings Saved!", Toast.LENGTH_SHORT)
				.show();
	}
}
