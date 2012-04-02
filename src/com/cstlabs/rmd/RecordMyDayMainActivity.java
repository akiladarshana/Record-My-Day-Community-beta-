package com.cstlabs.rmd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cstlabs.rmd.Dataacess.Containers.System.SystemData;
import com.cstlabs.rmd.Dataacess.Controlllers.System_handler;
import com.cstlabs.rmd.SystemCore.SystemUtilities;
import com.cstlabs.rmd.UIControl.System.FirstRun;
import com.cstlabs.rmd.UIControl.System.Login;

/*
 * this class is the main activity of the program. This class initiates the execution of the program
 * 
 * @author  - Akila Darshana Panditha 
 * @version 1.0
 */

public class RecordMyDayMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// setting up activity and interface
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Starting a new thread to wait 3 seconds before initializing system
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(3000);
						setupSystem();
					}
				} catch (InterruptedException ex) {

				}

				// TODO
			}
		};

		thread.start();

	}

	/*
	 * 
	 * @see android.app.Activity#onRestart()
	 */

	@Override
	protected void onRestart() {
		// calling back setup procedure on restart
		super.onRestart();
		setupSystem();
	}

	/*
	 * This method intializes the system storage.Tries to retrieve saved system
	 * settings from database. Initializes system setup on failure
	 */
	private void setupSystem() {

		System_handler handler = new System_handler(this);
		SystemData data = handler.getSystemData();

		if ("".equals(data.getPassword())) {
			// start new system setup procedure
			Intent Lounch = new Intent(this, FirstRun.class);
			startActivity(Lounch);

		} else {

			// setup core system data
			SystemUtilities.setSettings(data);
			Intent Lounch = new Intent(this, Login.class);
			startActivity(Lounch);

		}
	}
}