package com.cstlabs.rmd.UIControl.System;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.Security.SecurityHandler;
import com.cstlabs.rmd.SystemCore.SystemUtilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This class handles all actions performed in login user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class Login extends Activity {

	private Button loginButton;
	private EditText password_field;
	private TextView forget_password;

	@Override
	public void onBackPressed() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setting content view
		setContentView(R.layout.login);

		// locating resources in view
		setupButtons();

		// adding action listner for login button
		setListners();

	}

	private void setListners() {
		loginButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Boolean login_Status = SecurityHandler
						.loginAttempt(password_field.getText().toString());

				if (login_Status) {
					loadSystem(v);
				} else {
					Toast.makeText(getBaseContext(),
							"Entered password is incorrect", Toast.LENGTH_SHORT)
							.show();
					password_field.setText("");
				}
			}
		});

		// adding action listner for forget password text
		forget_password.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				recoverPass(v);

			}

		});
	}

	private void recoverPass(View v) {

		AskQuestion(v);

	}

	private void setupButtons() {
		loginButton = (Button) findViewById(R.id.login_button);
		password_field = (EditText) findViewById(R.id.password_Input);
		forget_password = (TextView) findViewById(R.id.forget_password);
	}

	/*
	 * asks security question and reset password on successful answer
	 */
	private void AskQuestion(final View v) {

		final EditText input = new EditText(this);

		new AlertDialog.Builder(this)
				.setTitle("Password Recovery")
				.setMessage(SecurityHandler.getQuestion() + " ?")
				.setView(input)
				.setPositiveButton("Recover",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

								if (SecurityHandler.getAnswer()
										.equalsIgnoreCase(
												input.getText().toString())) {
									SystemUtilities.ModifyPassword("123",
											v.getContext());
									resetPassword(v);
								} else {
									Toast.makeText(getBaseContext(),
											"Your Answer is incorrect",
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
	 * resets the password to system default string 123
	 */
	protected void resetPassword(final View v) {

		new AlertDialog.Builder(this)
				.setTitle(
						"Password Reset to 123. plase change password in settings page")
				.setNegativeButton("OK", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						loadSystem(v);
						Toast.makeText(getBaseContext(),
								"Your new password is 123", Toast.LENGTH_LONG)
								.show();

					}
				}).setCancelable(true).show();

	}

	/*
	 * loads system on successfull user authentication
	 */
	private void loadSystem(View v) {
		Intent homeLounch = new Intent(v.getContext(),
				com.cstlabs.rmd.UIControl.Navigation.Home.class);
		startActivity(homeLounch);
	}
}
