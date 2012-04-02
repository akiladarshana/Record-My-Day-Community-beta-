package com.cstlabs.rmd.UIControl.Notes;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.Dataacess.Containers.Notes.Note;
import com.cstlabs.rmd.Dataacess.Controlllers.Note_handler;
import com.cstlabs.rmd.UIControl.System.Login;
import com.cstlabs.rmd.resources.Values;

/*
 * This class handles all actions performed in view note  user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class ViewNote extends Activity {

	private int noteID = 0;

	private ImageButton edit, delete;
	private TextView title, date, location, content;
	private Note_handler controller;

	Calendar currentDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_viewer);

		setup();

	}

	// security enchancement - loack system on back operation
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent Lounch = new Intent(this, Login.class);
		startActivity(Lounch);
	}

	/**
	 * setting up calender instance for use with date operations
	 */

	// this method is called from constructor to assign variables from IDs and
	// setup listners
	private void setup() {

		setStates();

		assignTextViews();
		assignButtons();
		setupButtons();

		loadContent();

	}

	/*
	 * load content from database
	 */
	private void loadContent() {

		controller = new Note_handler(this);
		Note note = controller.get(noteID);
		title.setText(note.getTitle());
		currentDate = note.getDate();
		displayDate();
		location.setText(note.getLocation());
		content.setText(note.getNoteContent());

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		saveState(outState);
		super.onSaveInstanceState(outState);
	}

	/*
	 * save instance state
	 */
	private void saveState(Bundle outState) {
		outState.putInt(Values.NoteID, noteID);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		restoreState(savedInstanceState);
	}

	/*
	 * restore instance state from saved state
	 */
	private void restoreState(Bundle savedInstanceState) {

		noteID = savedInstanceState.getInt(Values.NoteID, 0);

	}

	/*
	 * set states from intent extras
	 */
	private void setStates() {

		Bundle extras = getIntent().getExtras();

		noteID = extras.getInt(Values.NoteID, 0);

	}

	/*
	 * assigns buttons fron UI
	 */
	private void assignButtons() {

		edit = (ImageButton) findViewById(R.id.view_note_button_edit);
		delete = (ImageButton) findViewById(R.id.view_note_delete);
	}

	/*
	 * assign text views from UI
	 */
	private void assignTextViews() {
		date = (TextView) findViewById(R.id.view_note_date);
		title = (TextView) findViewById(R.id.view_note_title);
		location = (TextView) findViewById(R.id.view_note_location);
		content = (TextView) findViewById(R.id.view_note_content);

	}

	/*
	 * setting up onclick listners for buttons
	 */
	private void setupButtons() {

		edit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				editClicked(v);

			}

		});

		delete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				deleteClicked(v);

			}
		});

	}

	private void editClicked(View v) {

		Intent noteLounch = new Intent(v.getContext(), AddNewNote.class);
		noteLounch.putExtra(Values.isEdit, true);
		noteLounch.putExtra(Values.NoteID, noteID);
		startActivity(noteLounch);

	}

	/**
	 * This method is invoked by action listener when save button is clicked by
	 * user System invokes logic class after user confirm action via dialog
	 */
	protected void deleteClicked(View v) {

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					deleteNote();
					finish();

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Delete Note ?")
				.setPositiveButton("Delete", dialogClickListener)
				.setNegativeButton("Cancel", dialogClickListener).show();

	}

	protected void deleteNote() {

		controller = new Note_handler(this);
		controller.delete(noteID);
		Toast.makeText(getBaseContext(), "Note Deleted !", Toast.LENGTH_LONG)
				.show();

	}

	/**
	 * This method is invoked to generate actionlistner for datechooser dialog
	 * 
	 */

	private void displayDate() {
		date.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(currentDate.getTime().getDate()).append("/")
				.append(currentDate.getTime().getMonth() + 1).append("/")
				.append(currentDate.getTime().getYear() + 1900).append(" "));
	}

}
