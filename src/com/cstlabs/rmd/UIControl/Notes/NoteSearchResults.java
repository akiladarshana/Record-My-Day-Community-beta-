package com.cstlabs.rmd.UIControl.Notes;

import java.util.Calendar;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cstlabs.rmd.R;
import com.cstlabs.rmd.Dataacess.Containers.Notes.Note_header;
import com.cstlabs.rmd.Dataacess.Controlllers.Note_handler;
import com.cstlabs.rmd.resources.Values;

/*
 * This class handles all actions performed in note search results  user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */

public class NoteSearchResults extends ListActivity {

	private List<Note_header> notes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_results);
		loadContent();
		loadList();

	}

	/*
	 * load list from given serch condition and setting up the list view in UI
	 */
	private void loadList() {
		CustomAdaptor adoptor = new CustomAdaptor(this, R.layout.item,
				R.id.title, notes);
		setListAdapter(adoptor);
		this.getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> list, View v, int position,
					long id) {
				Note_header selected = (Note_header) list
						.getItemAtPosition(position);

				Intent Lounch = new Intent(v.getContext(), ViewNote.class);
				Lounch.putExtra(Values.NoteID, false);
				Lounch.putExtra(Values.NoteID, selected.getId());
				startActivity(Lounch);

			}
		});
	}

	/*
	 * get instance state and load content from database
	 */
	private void loadContent() {
		Note_handler handler = new Note_handler(this);

		Bundle extras = getIntent().getExtras();
		String condition = "";
		condition = extras.getString(Values.Condition);
		notes = handler.search(condition);

		if (notes.isEmpty()) {
			Toast.makeText(getBaseContext(), "No Results Found !",
					Toast.LENGTH_LONG).show();
			finish();

		}
	}

	/*
	 * display date in UI
	 */
	private String displayDate(Calendar currentDate) {

		return new StringBuilder()
				// Month is 0 based so add 1
				.append(currentDate.getTime().getDate()).append("/")
				.append(currentDate.getTime().getMonth() + 1).append("/")
				.append(currentDate.getTime().getYear() + 1900).append(" ")
				.toString();
	}

	/*
	 * this class is the adopter with customized view for list activity
	 */
	private class CustomAdaptor extends BaseAdapter {

		List<Note_header> notes;

		public CustomAdaptor(Context context, int resource,

		int textViewResourceId, List<Note_header> list) {

			this.notes = list;

		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			return getView(position, convertView, parent);

		}

		public View getView(int position, View convertView, ViewGroup parent) {

			View view = convertView;

			if (view == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.item, null);

			}
			TextView title = (TextView) view.findViewById(R.id.title);
			TextView location = (TextView) view.findViewById(R.id.location);
			TextView date = (TextView) view.findViewById(R.id.date);
			ImageView image = (ImageView) view.findViewById(R.id.image);

			if (title != null) {
				title.setText(notes.get(position).getTitle());
			}
			if (location != null) {
				location.setText(notes.get(position).getLocation());
			}
			if (date != null) {
				date.setText(displayDate(notes.get(position).getDate()));
			}
			if (image != null) {
				image.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.notethumb));

			}

			return view;

		}

		public int getCount() {
			return notes.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return notes.get(position);
		}

		public long getItemId(int position) {

			return position;
		}

	}

}
