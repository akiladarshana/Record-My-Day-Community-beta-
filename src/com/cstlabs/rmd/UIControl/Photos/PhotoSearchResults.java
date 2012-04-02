package com.cstlabs.rmd.UIControl.Photos;

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
import com.cstlabs.rmd.Dataacess.Containers.Photos.Photo_Header;
import com.cstlabs.rmd.Dataacess.Controlllers.Photos_Handler;
import com.cstlabs.rmd.resources.Values;

/*
 * This class handles all actions performed in photo search results user interface of the system 
 * 
 * @author Akila Darshana Panditha
 * @version 1.0
 */
public class PhotoSearchResults extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_results);
		List<Photo_Header> photos = loadContent();
		loadList(photos);

	}

	/*
	 * load list from given serch condition and setting up the list view in UI
	 */
	private void loadList(List<Photo_Header> photos) {
		CustomAdaptor adoptor = new CustomAdaptor(this, R.layout.item,
				R.id.title, photos);
		setListAdapter(adoptor);
		this.getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> list, View v, int position,
					long id) {
				Photo_Header selected = (Photo_Header) list
						.getItemAtPosition(position);

				Intent Lounch = new Intent(v.getContext(), ViewPhoto.class);
				Lounch.putExtra(Values.isEdit, false);
				Lounch.putExtra(Values.PhotoID, selected.getId());
				startActivity(Lounch);

			}
		});
	}

	/*
	 * get instance state and load content from database
	 */
	private List<Photo_Header> loadContent() {
		Photos_Handler handler = new Photos_Handler(this);

		Bundle extras = getIntent().getExtras();
		String condition = "";
		condition = extras.getString(Values.Condition);
		List<Photo_Header> photos = handler.search(condition);

		if (photos.isEmpty()) {
			Toast.makeText(getBaseContext(), "No Results Found !",
					Toast.LENGTH_LONG).show();
			finish();

		}
		return photos;
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

		List<Photo_Header> photos;

		public CustomAdaptor(Context context, int resource,

		int textViewResourceId, List<Photo_Header> list) {

			this.photos = list;

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
				title.setText(photos.get(position).getTitle());
			}
			if (location != null) {
				location.setText(photos.get(position).getLocation());
			}
			if (date != null) {
				date.setText(displayDate(photos.get(position).getDate()));
			}
			if (image != null) {
				image.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.imagethumb));

			}

			return view;

		}

		public int getCount() {
			return photos.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return photos.get(position);
		}

		public long getItemId(int position) {

			return position;
		}

	}

}
