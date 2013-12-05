package com.example.hobnob;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

public class MainApplication extends Application {

	static final String TAG = "MyApp";

	@Override
	public void onCreate() {
		super.onCreate();

		Parse.initialize(this, "mI01KcelEuBnGZo5QdZeuCyEwODIVMjJkREDvraJ",
				"I91qxlmi6W7scygd1IQudVimpLdMBszZZkvpnzFW");

		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize(getString(R.string.app_id));

	}

}
