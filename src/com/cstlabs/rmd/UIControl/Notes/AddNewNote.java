package com.cstlabs.rmd.UIControl.Notes;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.Dataacess.Containers.Notes.Note;
import com.cstlabs.rmd.Dataacess.Controlllers.Note_handler;
import com.cstlabs.rmd.resources.Values;

/*
 * This class handles all actions performed in add or edit note  user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class AddNewNote extends Activity {

	private Boolean isEdit = false;
	private int noteID = 0;

	private ImageButton changeTitle, changeDate, ChangeLocation, save;
	private TextView title, date, location, content;
	private Note_handler controller;

	private int year, month, day;
	private Calendar currentDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_note);

		setup();

	}

	/**
	 * setting up calender instance for use with date operations
	 */
	private void setupCalender() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		currentDate = c.getInstance();

	}

	// this method is called from constructor to assign variables from IDs and
	// setup listners
	private void setup() {

		setStates();
		setupStorage();
		assignTextViews();
		assignButtons();
		setupButtons();
		setupCalender();
		displayDate();
		if (isEdit) {
			loadContent();

		}
	}

	/*
	 * setting up storage controller
	 */
	private void setupStorage() {
		controller = new Note_handler(this);

	}

	/*
	 * loads content from storage
	 */
	private void loadContent() {

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
	 * save state of the instance
	 */
	private void saveState(Bundle outState) {
		outState.putInt(Values.NoteID, noteID);
		outState.putBoolean(Values.isEdit, isEdit);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		restoreState(savedInstanceState);
	}

	/*
	 * retireve state from saves instace state
	 */
	private void restoreState(Bundle savedInstanceState) {

		noteID = savedInstanceState.getInt(Values.NoteID, 0);
		isEdit = savedInstanceState.getBoolean(Values.isEdit, false);

	}

	/*
	 * get information from intent extras
	 */
	private void setStates() {

		Bundle extras = getIntent().getExtras();

		isEdit = extras.getBoolean(Values.isEdit, false);

		if (isEdit) {
			noteID = extras.getInt(Values.NoteID, 0);
		}

	}

	/*
	 * assigning buttons
	 */
	private void assignButtons() {
		changeDate = (ImageButton) findViewById(R.id.add_edit_note_button_editdate);
		changeTitle = (ImageButton) findViewById(R.id.add_edit_note_button_edittitle);
		ChangeLocation = (ImageButton) findViewById(R.id.add_edit_note_button_editlocation);
		save = (ImageButton) findViewById(R.id.add_edit_note_button_save);
	}

	/*
	 * assigning text fields
	 */
	private void assignTextViews() {
		date = (TextView) findViewById(R.id.edit_note_date);
		title = (TextView) findViewById(R.id.edit_note_title);
		location = (TextView) findViewById(R.id.edit_note_location);
		content = (TextView) findViewById(R.id.edit_note_content);

	}

	/*
	 * setting up action listners fro buttons
	 */
	private void setupButtons() {

		save.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				saveClicked(v);

			}
		});

		changeDate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				dateChange(v);

			}
		});

		ChangeLocation.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				locationChange(v);

			}
		});

		changeTitle.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				titleChange(v);

			}
		});

	}

	protected void titleChange(View v) {

		getInputToField("Title", "What is the Title of this note ?", title);

	}

	protected void locationChange(View v) {

		getInputToField("Location", "What is the location of this note ?",
				location);

	}

	/*
	 * gets user input from pop u p dialog
	 * 
	 * @param title of the dialog
	 * 
	 * @param message for dialog
	 * 
	 * @param text firld to be assign data
	 */
	private void getInputToField(String title, String message,
			final TextView field) {

		final EditText input = new EditText(this);
		input.setText(field.getText());
		input.setSelection(field.getText().length());
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(message)
				.setView(input)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						field.setText(input.getText().toString());
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

	/**
	 * This method is invoked by action listener when exit button is clicked by
	 * user System ends activity after user confirm action via dialog
	 */

	protected void cancelClicked(View v) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					// finishing current activity
					finish();

					break;

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you want to cancel changes?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}

	/**
	 * This method is invoked by action listener when change date button is
	 * clicked by user new date chooser dialog is shown for the user to select
	 * date
	 */

	protected void dateChange(View v) {
		showDialog(Values.DATE_DIALOG_ID);

	}

	/**
	 * This method is invoked by action listener when save button is clicked by
	 * user System invokes logic class after user confirm action via dialog
	 */
	protected void saveClicked(View v) {

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					saveNote();
					finish();

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Save Note ?")
				.setPositiveButton("Save", dialogClickListener)
				.setNegativeButton("Cancel", dialogClickListener).show();

	}

	/*
	 * save note to the database
	 */
	protected void saveNote() {

		Note note = new Note();

		note.setLocation(location.getText().toString());
		note.setDate(currentDate);
		note.setTitle(title.getText().toString());
		note.setNoteContent(content.getText().toString());

		if (isEdit) {
			note.setId(noteID);
			controller.modify(noteID, note);
			Toast.makeText(getBaseContext(), "Note Modified !",
					Toast.LENGTH_LONG).show();
			finish();

		} else {
			note.setId(0);
			long s = controller.save(note);
			Toast.makeText(getBaseContext(), "Note  Saved !", Toast.LENGTH_LONG)
					.show();
			finish();

		}

	}

	/**
	 * This method is invoked to generate actionlistner for datechooser dialog
	 * 
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			setDate(year, dayOfMonth, monthOfYear);

		}

		private void setDate(int year, int day, int month) {
			currentDate.set(year, month, day);
			displayDate();
		}

	};

	/*
	 * display date on the UI
	 */
	private void displayDate() {
		date.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(currentDate.getTime().getDate()).append("/")
				.append(currentDate.getTime().getMonth() + 1).append("/")
				.append(currentDate.getTime().getYear() + 1900).append(" "));
	}

	/**
	 * overriding method to generate datechooser with custom configurations
	 * 
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Values.DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, year, month,
					day);

		}
		return null;
	}

}
