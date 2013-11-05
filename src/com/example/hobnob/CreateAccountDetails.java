package com.example.hobnob;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class CreateAccountDetails extends Activity {
	
	private EditText firstName_tx;
	private EditText lastName_tx;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account_details);
		
		firstName_tx = (EditText)findViewById(R.id.firstnameText);
		lastName_tx = (EditText)findViewById(R.id.lastnameText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account_details, menu);
		return true;
	}

}
