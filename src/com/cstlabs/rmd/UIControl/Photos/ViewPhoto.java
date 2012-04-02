package com.cstlabs.rmd.UIControl.Photos;

import java.io.FileNotFoundException;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Photo;
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
import com.cstlabs.rmd.UIControl.System.Login;
import com.cstlabs.rmd.resources.Values;
/*
 * This class handles all actions performed in view photo  user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class ViewPhoto extends Activity {

	
	
	private ImageButton edit,delete;  
	private TextView title, date, location, content;
	private ImageView image;

	private static int PhotoID = 0;

	// for use in date picker dialog


	private Calendar currentDate;
    private Photos_Handler dataHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_viewer);
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
	private void getSavedState(Bundle inState) {


			PhotoID = inState.getInt(Values.PhotoID, 0);
			loadContent();
		
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
		

		outstate.putInt(Values.PhotoID, PhotoID);



	}


	/**
	 * This method is for setting up calender instance for date operations 
	 * 
	 */
	private void setupCalender() {


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
			loadContent();	
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

		PhotoID = extras.getInt(Values.PhotoID,0);
	
}

private void assignText() {
		date = (TextView) findViewById(R.id.photo_date);
		title = (TextView) findViewById(R.id.photo_title);
		location = (TextView) findViewById(R.id.photo_location);
		content = (TextView) findViewById(R.id.photo_description);
		image = (ImageView) findViewById(R.id.photo_content);
	}

private void assignButtons() {
	
	edit = (ImageButton) findViewById(R.id.view_photo_edit);
	delete = (ImageButton) findViewById(R.id.view_photo_delete);

}

	// setting up click listners for buttons
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




	protected void editClicked(View v) {
		
		Intent photoLounch = new Intent(v.getContext(), AddNewPhoto.class);
		photoLounch.putExtra("isEdit", true);
		photoLounch.putExtra(Values.PhotoID, PhotoID);
		startActivity(photoLounch);
		
	}
	// event handling methods for buttons

	protected void deleteClicked(View v) {
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:

					deletePhoto();
					finish();

					break;

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you want to delete this Photo ?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();
	}

	protected void deletePhoto() {
		
		dataHandler.delete(PhotoID);
		Toast.makeText(getBaseContext(), "Photo Deleted !", Toast.LENGTH_LONG)
		.show();

		
	}
	// select date


	/**
	 * This method is invoked by action listener when save button is clicked by user
	 * System invokes logic class after user confirm action via dialog 
	 */

	private void displayDate() {
		date.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(currentDate.getTime().getDate()).append("/").append(currentDate.getTime().getMonth() + 1).append("/")
				.append(currentDate.getTime().getYear()+1900).append(" "));
	}
}
