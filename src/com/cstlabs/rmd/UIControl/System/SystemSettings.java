package com.cstlabs.rmd.UIControl.System;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.Security.SecurityHandler;
import com.cstlabs.rmd.SystemCore.SystemUtilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/*
 * This class handles all actions performed in systen settings  user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class SystemSettings extends Activity {

	private ImageButton password, question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		setupButtons();
	}

	/*
	 * setting up buttons in UI
	 */
	private void setupButtons() {

		question = (ImageButton) findViewById(R.id.settings_button_change_question);
		password = (ImageButton) findViewById(R.id.settings_button_change_pass);

		question.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				changeQuestion(v);

			}
		});

		password.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				changepassword(v);

			}
		});
	}

	/*
	 * change password procedure
	 */
	protected void changepassword(final View v) {

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		new AlertDialog.Builder(this)
				.setTitle("Enter Current Password")
				.setMessage(" ")
				.setView(input)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						if (SecurityHandler.getPassword().equalsIgnoreCase(
								input.getText().toString())) {

							getNewPassword(v);
						} else {
							Toast.makeText(getBaseContext(),
									"Your password is  incorrect",
									Toast.LENGTH_SHORT).show();
						}
					}

				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Do nothing.
							}
						}).show();

	}

	/*
	 * getting new password via dialog
	 */
	private void getNewPassword(final View view) {

		final Dialog getpassword = new Dialog(this);

		getpassword.setTitle("Select New Password");
		getpassword.setContentView(R.layout.chage_password);

		final EditText password = (EditText) getpassword
				.findViewById(R.id.change_password);
		final EditText confpassword = (EditText) getpassword
				.findViewById(R.id.change_confirm_password);
		final Button ok = (Button) getpassword
				.findViewById(R.id.change_password_ok);

		ok.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				if (password.getText().toString()
						.equals(confpassword.getText().toString())
						&& !("".equals(password.getText().toString()))) {

					SystemUtilities.ModifyPassword(password.getText()
							.toString(), view.getContext());
					getpassword.cancel();
					Toast.makeText(getBaseContext(), "Password changed!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(),
							"Passwords doesn't match!", Toast.LENGTH_SHORT)
							.show();
					password.setText("");
					confpassword.setText("");
				}

			}
		});

		getpassword.show();

	}

	/*
	 * change security question via dialogs
	 */
	protected void changeQuestion(final View v) {

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		new AlertDialog.Builder(this)
				.setTitle("Enter Current Password")
				.setMessage(" ")
				.setView(input)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						if (SecurityHandler.getPassword().equalsIgnoreCase(
								input.getText().toString())) {

							getNewQuestion(v);
						} else {
							Toast.makeText(getBaseContext(),
									"Your password is  incorrect",
									Toast.LENGTH_SHORT).show();
						}
					}

				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Do nothing.
							}
						}).show();

	}

	/*
	 * gets user input for new security quesrtion
	 */
	protected void getNewQuestion(View v) {
		final Dialog getpassword = new Dialog(this);

		getpassword.setTitle("Select New Security Question");

		getpassword.setContentView(R.layout.change_question);

		final EditText question = (EditText) getpassword
				.findViewById(R.id.change_question);
		final EditText answer = (EditText) getpassword
				.findViewById(R.id.change_answer);
		final Button ok = (Button) getpassword
				.findViewById(R.id.change_question_ok);

		ok.setOnClickListener(new OnClickListener() {

			public void onClick(final View view) {

				if (!("".equals(question.getText().toString()))
						&& !("".equals(answer.getText().toString()))) {

					SystemUtilities.ModifyQuestion(question.getText()
							.toString(), answer.getText().toString(), view
							.getContext());
					getpassword.cancel();
					Toast.makeText(getBaseContext(), "Question changed!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "Fields can not be empty",
							Toast.LENGTH_SHORT).show();

				}

			}
		});

		getpassword.show();

	}
}
