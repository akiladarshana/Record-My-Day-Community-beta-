package com.cstlabs.rmd.UIControl.Photos;

import java.io.FileNotFoundException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.Dataacess.Containers.Photos.Photos;
import com.cstlabs.rmd.Dataacess.Controlllers.Photos_Handler;
import com.cstlabs.rmd.resources.Values;

/*
 * This class handles all actions performed in sadd or edit  user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class AddNewPhoto extends Activity {

	private ImageButton select, changeDate, changeTitle, ChangeLocation, save;
	private TextView title, date, location, content;
	private ImageView image;
	private Boolean isEdit = false;
	private static int PhotoID = 0;
	private static Uri imageLocation;
	private boolean isImageChanged = false;
	// for use in date picker dialog
	private static final int DATE_DIALOG_ID = 1;
	private int year, month, day;
	private Calendar currentDate;
	private Photos_Handler dataHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// getSavedState(savedInstanceState);
		setContentView(R.layout.add_edit_photo);
		setup();
		displayDate();

	}

	private void getSavedState(Bundle inState) {

		isEdit = inState.getBoolean("Values.isEdit", false);
		isImageChanged = inState.getBoolean("IsImageChanged", false);
		PhotoID = inState.getInt(Values.PhotoID, 0);
		if (isImageChanged) {
			imageLocation = Uri.parse(inState.getString("URI"));
			setImage(imageLocation);

		} else {
			try {
				image.setImageBitmap(getBitmap());
			} catch (FileNotFoundException e) {

				Log.e("Error While reading Photo", e.getMessage());
				Toast.makeText(getBaseContext(), "Error While Reading Photo !",
						Toast.LENGTH_LONG).show();
				finish();
			}
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		saveState(outState);
		super.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

		super.onRestoreInstanceState(savedInstanceState);
		getSavedState(savedInstanceState);

	}

	private void saveState(Bundle outstate) {

		outstate.putBoolean(Values.isEdit, isEdit);
		outstate.putInt(Values.PhotoID, PhotoID);
		outstate.putBoolean("IsImageChanged", isImageChanged);
		if (isImageChanged) {

			outstate.putString("URI", imageLocation.toString());
		}

	}

	/**
	 * This method is for setting up calender instance for date operations
	 * 
	 */
	private void setupCalender() {

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		currentDate = Calendar.getInstance();

	}

	// this method is called from constructor to assign variables from IDs and

	// this method is called from constructor to assign variables from IDs and
	// setup listners

	private void setup() {
		setStates();
		assignText();
		assignButtons();
		setupButtons();
		setupCalender();
		dataHandler = new Photos_Handler(this);
		if (isEdit) {
			loadContent();

		}

		displayDate();

	}

	private void loadContent() {

		Photos photo = dataHandler.get(PhotoID);
		title.setText(photo.getTitle());
		location.setText(photo.getLocation());
		currentDate = photo.getDate();
		displayDate();
		content.setText(photo.getNoteDescription());
		image.setImageBitmap(PhotoUtilities.getImage(photo.getPhoto()));

	}

	private void setStates() {

		Bundle extras = getIntent().getExtras();

		isEdit = extras.getBoolean(Values.isEdit, false);

		if (isEdit) {
			PhotoID = extras.getInt(Values.PhotoID, 0);
		}

	}

	private void assignText() {
		date = (TextView) findViewById(R.id.edit_photo_date);
		title = (TextView) findViewById(R.id.edit_photo_title);
		location = (TextView) findViewById(R.id.edit_photo_location);
		content = (TextView) findViewById(R.id.edit_photo_description);
		image = (ImageView) findViewById(R.id.edit_photo_content);
	}

	private void assignButtons() {
		save = (ImageButton) findViewById(R.id.add_edit_photo_button_save);
		select = (ImageButton) findViewById(R.id.add_edit_photo_button_change);
		changeDate = (ImageButton) findViewById(R.id.add_edit_photo_button_date);
		changeTitle = (ImageButton) findViewById(R.id.add_edit_photo_button_title);
		ChangeLocation = (ImageButton) findViewById(R.id.add_edit_photo_button_location);
	}

	// setting up click listners for buttons
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

		changeTitle.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				titleClicked(v);

			}
		});

		ChangeLocation.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				locationClicked(v);

			}
		});

		select.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				selectClicked(v);

			}
		});

	}

	protected void locationClicked(View v) {

		getInputToField("Location", "Where is this photo taken ?", location);

	}

	protected void titleClicked(View v) {

		getInputToField("Title", "What is the Title of this Photo ?", title);

	}

	/**
	 * This method is invoked by action listener when select image button is
	 * clicked by user System invokes system gallery activity and accepts
	 * results from that activity
	 */
	protected void selectClicked(View v) {

		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 0);

	}

	// event handling methods for buttons

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

	// select date
	protected void dateChange(View v) {
		showDialog(DATE_DIALOG_ID);

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

					savePhoto();

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Save Photo ?")
				.setPositiveButton("Save", dialogClickListener)
				.setNegativeButton("Cancel", dialogClickListener).show();

	}

	protected void savePhoto() {

		if (!(isEdit || isImageChanged)) {

			Toast.makeText(getBaseContext(),
					"Plese Select photo before save !", Toast.LENGTH_LONG)
					.show();
			return;
		}
		Photos photo = new Photos();
		photo.setTitle(title.getText().toString());
		photo.setLocation(location.getText().toString());
		photo.setNoteDescription(content.getText().toString());
		photo.setDate(currentDate);

		try {
			photo.setPhoto(PhotoUtilities.getBytes(getBitmap()));
		} catch (FileNotFoundException e) {

			Log.e("Error While reading Photo", e.getMessage());
			Toast.makeText(getBaseContext(), "Error While Reading Photo !",
					Toast.LENGTH_LONG).show();
			finish();
		}

		if (isEdit) {
			photo.setId(PhotoID);
			dataHandler.modify(PhotoID, photo);
			Toast.makeText(getBaseContext(), "Photo  Updated !",
					Toast.LENGTH_LONG).show();
			finish();
		} else {
			photo.setId(0);
			long s = dataHandler.save(photo);
			Toast.makeText(getBaseContext(), "Photo Saved !", Toast.LENGTH_LONG)
					.show();
			finish();
		}

	}

	// method to set action listner to date picker
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

	// method to setup date choose dialog
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, year, month,
					day);

		}
		return null;
	}

	/**
	 * This method is invoked by action listener when user selects an image
	 * System sets selected image on user interface for further activities
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri targetUri = data.getData();

			setImage(targetUri);
		}

	}

	/**
	 * @param targetUri
	 */
	private void setImage(Uri targetUri) {

		try {
			Bitmap bitmap;
			image.setImageBitmap(null);
			image.destroyDrawingCache();
			BitmapFactory.Options bmoptions = new Options();
			bmoptions.inSampleSize = 8;
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(targetUri), null, bmoptions);

			image.setImageBitmap(bitmap);
			image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageLocation = targetUri;

			isImageChanged = true;

		} catch (FileNotFoundException e) {

			Toast.makeText(getBaseContext(),
					"Error Occured During Image Selection", Toast.LENGTH_SHORT)
					.show();
		}
	}

	private Bitmap getBitmap() throws FileNotFoundException {

		if (isImageChanged) {
			BitmapFactory.Options bmoptions = new Options();
			bmoptions.inSampleSize = 8;
			return BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(imageLocation), null, bmoptions);

		}

		Photos photo = dataHandler.get(PhotoID);
		return PhotoUtilities.getImage(photo.getPhoto());

	}

	private void displayDate() {
		date.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(currentDate.getTime().getDate()).append("/")
				.append(currentDate.getTime().getMonth() + 1).append("/")
				.append(currentDate.getTime().getYear() + 1900).append(" "));
	}
}
