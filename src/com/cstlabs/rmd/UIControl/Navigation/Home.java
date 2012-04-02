package com.cstlabs.rmd.UIControl.Navigation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.UIControl.Notes.AddNewNote;
import com.cstlabs.rmd.UIControl.Notes.NoteSearchResults;
import com.cstlabs.rmd.UIControl.Photos.AddNewPhoto;
import com.cstlabs.rmd.UIControl.System.Login;
import com.cstlabs.rmd.UIControl.System.SystemSettings;
import com.cstlabs.rmd.resources.Values;

/*
 * This class handles all actions performed in home user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class Home extends Activity {

	// click listners for buttons
	private OnClickListener timelineListner, notesListner, photoListner,
			settingsLisner;

	// buttons
	private ImageButton timeLine, notes, photos, settings;

	// textviews
	private TextView txt_notes, txt_photos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		// assigning refferences to variables;

		setupActions();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

	}

	/*
	 * this method is called fron onCreate() and assgined Listners to buttons
	 * and clickable text views
	 */
	private void setupActions() {

		// assigning listner to story button
		setupTimeLine();

		// Assigning listner to notes activity
		setupNotes();

		// Assigning listner to photos activity

		setupPhotos();

		// Assigning listner to calender activity

		// Assigning listner to exit activity

		// setupExit();

		// Assigning listner to settings activity

		setupSettings();

	}

	/**
	 * setting up settings actions
	 */
	private void setupSettings() {

		settings = (ImageButton) findViewById(R.id.button_settings);
		settingsLisner = new OnClickListener() {

			public void onClick(View v) {
				settingsClicked(v);

			}
		};
		settings.setOnClickListener(settingsLisner);
	}

	/**
	 * setting up photos actions
	 */
	private void setupPhotos() {

		photos = (ImageButton) findViewById(R.id.button_photos);
		txt_photos = (TextView) findViewById(R.id.text_new_photo);
		photoListner = new OnClickListener() {

			public void onClick(View v) {
				photosClicked(v);

			}
		};
		photos.setOnClickListener(photoListner);
		txt_photos.setOnClickListener(photoListner);
	}

	/**
	 * setting up notes actions
	 */
	private void setupNotes() {
		notes = (ImageButton) findViewById(R.id.button_notes);
		txt_notes = (TextView) findViewById(R.id.text_new_note);

		notesListner = new OnClickListener() {

			public void onClick(View v) {
				notesClicked(v);

			}
		};
		notes.setOnClickListener(notesListner);
		txt_notes.setOnClickListener(notesListner);
	}

	/**
	 * setting up timeline actions
	 */

	private void setupTimeLine() {
		timeLine = (ImageButton) findViewById(R.id.button_story);
		timelineListner = new OnClickListener() {

			public void onClick(View v) {
				storyClicked(v);
			}

		};
		timeLine.setOnClickListener(timelineListner);
	}

	/**
	 * This method is invoked by action listener when settings button is clicked
	 * by user
	 * 
	 */

	protected void settingsClicked(View v) {

		Intent photoLounch = new Intent(v.getContext(), SystemSettings.class);
		startActivity(photoLounch);
	}

	/**
	 * This method is invoked by action listener when calender button is clicked
	 * by user
	 * 
	 */
	protected void calenderClicked(View v) {
		Intent photoLounch = new Intent(v.getContext(), NoteSearchResults.class);
		photoLounch.putExtra(Values.Condition, "");
		startActivity(photoLounch);

	}

	// overing for security implementations
	@Override
	public void onBackPressed() {

		super.onBackPressed();
		Intent Lounch = new Intent(this, Login.class);
		startActivity(Lounch);
	}

	/**
	 * This method is invoked by action listener when exit button is clicked by
	 * user System invokes home activity is OS after user confirm action via
	 * dialog
	 */
	protected void exitClicked() {

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					// calling up home activity in os

					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);

					break;

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you want to exit?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();

	}

	/**
	 * This method is invoked by action listener when story button is clicked by
	 * user
	 * 
	 */
	private void storyClicked(View v) {
		Intent noteLounch = new Intent(v.getContext(), MultiSearch.class);
		startActivity(noteLounch);
	}

	/**
	 * This method is invoked by action listener when notes button is clicked by
	 * user this method starts new AddNewNote activity
	 */
	protected void notesClicked(View v) {
		Intent noteLounch = new Intent(v.getContext(), AddNewNote.class);

		noteLounch.putExtra(Values.isEdit, false);
		startActivity(noteLounch);

	}

	/**
	 * This method is invoked by action listener when photos button is clicked
	 * by user this method starts new AddNewPhoto activity
	 */
	protected void photosClicked(View v) {
		// TODO when photos button clicked

		Intent photoLounch = new Intent(v.getContext(), AddNewPhoto.class);
		photoLounch.putExtra(Values.isEdit, false);
		startActivity(photoLounch);

	}

}
