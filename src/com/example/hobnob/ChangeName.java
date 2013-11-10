package com.example.hobnob;

import java.util.Map;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

public class ChangeName extends Activity {

	private String userID;
	private EditText firstName_t;
	private EditText lastName_t;
	private String firstName;
	private String lastName;
	
	private Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		userID = i.getStringExtra("ID");
		
		Firebase root = new Firebase("https://hobnob.firebaseio.com/users/");
		Firebase userRef = root.child(userID);
		Firebase nameRef = userRef.child("name");
		setContentView(R.layout.activity_change_name);
		
		firstName_t = (EditText)findViewById(R.id.firstName_edit);
		lastName_t = (EditText)findViewById(R.id.lastName_edit);
		submit = (Button)findViewById(R.id.name_change_but);
		
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
					firstName_t.setText(firstName);
					lastName_t.setText(lastName);
				}
			}

			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				Log.i("editUser", "listener was canceled");
			}
		});
		
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
		getMenuInflater().inflate(R.menu.change_name, menu);
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
