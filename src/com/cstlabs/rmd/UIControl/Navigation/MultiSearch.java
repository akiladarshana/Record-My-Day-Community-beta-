package com.cstlabs.rmd.UIControl.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.UIControl.Notes.NoteSearchResults;
import com.cstlabs.rmd.UIControl.Photos.PhotoSearchResults;
import com.cstlabs.rmd.resources.Values;

/*
 * This class handles all actions performed in search user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */

public class MultiSearch extends Activity {

	private Button search, exit;
	private TextView title, location;

	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		setup();
	}

	/*
	 * Setting up UI components
	 */
	private void setup() {
		setSpinner();

		setButtons();
		setTextFields();
	}

	/*
	 * Referring text fields from UI
	 */
	private void setTextFields() {

		title = (TextView) findViewById(R.id.search_title);
		location = (TextView) findViewById(R.id.search_location);

	}

	/*
	 * Referring buttons from UI
	 */
	private void setButtons() {

		search = (Button) findViewById(R.id.search_search);
		exit = (Button) findViewById(R.id.search_cancel);

		setListners();

	}

	/*
	 * setting up on click listners for buttons
	 */
	private void setListners() {

		search.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				prepareCondition(v);

			}

		});

		exit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});

	}

	/*
	 * Prepare serch condition from user inputs
	 */
	private void prepareCondition(View v) {

		String selection = (String) spinner.getSelectedItem();
		boolean isfirst = true;
		;
		String condition = "";

		if (!("".equalsIgnoreCase(title.getText().toString()))) {

			if (isfirst) {
				isfirst = false;

			} else {
				condition = condition + " and ";
			}
			condition = condition + "title like '%"
					+ title.getText().toString() + "%'";
		}
		if (!("".equalsIgnoreCase(location.getText().toString()))) {

			if (isfirst) {
				isfirst = false;

			} else {
				condition = condition + " and ";
			}
			condition = condition + "location like '%"
					+ location.getText().toString() + "%'";
		}

		if (selection.equalsIgnoreCase(Values.Notes)) {
			Intent Lounch = new Intent(v.getContext(), NoteSearchResults.class);
			Lounch.putExtra(Values.Condition, condition);
			startActivity(Lounch);
		} else {
			Intent Lounch = new Intent(v.getContext(), PhotoSearchResults.class);
			Lounch.putExtra(Values.Condition, condition);
			startActivity(Lounch);
		}

	}

	/*
	 * setting up spinner adoptor
	 */
	private void setSpinner() {

		String[] items = new String[] { Values.Notes, Values.Photos };
		spinner = (Spinner) findViewById(R.id.search_type);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

	}

}
