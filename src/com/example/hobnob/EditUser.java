package com.example.hobnob;

import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

public class EditUser extends Activity {
	
	private TextView welcomeUser_tx;
	private String firstName;
	private String lastName;
	private String email;
	private Button editName;
	private Button editEmail;
	private Button editPassword;
	private String userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_edit_user);
		Intent i = getIntent();
		userID = i.getStringExtra("ID");
		
		Firebase root = new Firebase("https://hobnob.firebaseio.com/users/");
		Firebase userRef = root.child(userID);
		Firebase nameRef = userRef.child("name");
		
		Log.i("editUser", "setting buttons");
		editName = (Button) findViewById(R.id.editName);
		editEmail = (Button) findViewById(R.id.emailAddress);
		editPassword = (Button) findViewById(R.id.password);
		
		
		nameRef.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot snapshot)
			{
				Object value = snapshot.getValue();
				if (value == null) 
				{
					
				}
				else
				{
					firstName = (String)((Map)value).get("first");
					lastName = (String)((Map)value).get("last");
					email = (String)((Map)value).get("email");
					editName.setText("Name:\n" + firstName + " " + lastName);
					editEmail.setText("Email:\n" + email);
				}
			}

			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				Log.i("editUser", "listener was canceled");
			}
		});

		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
