package com.example.hobnob;

import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateAccountDetails extends Activity {
	
	private EditText firstName_tx;
	private EditText lastName_tx;
	private Button 	 submit_bt;
	private String	 userID;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account_details);
		
		firstName_tx = (EditText)findViewById(R.id.firstnameText);
		lastName_tx = (EditText)findViewById(R.id.lastnameText);
		submit_bt = (Button)findViewById(R.id.submitButton);
		
		Intent i = getIntent();
        // Receiving the Data
        userID = i.getStringExtra("ID");
		
		
		submit_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Firebase userFire = new Firebase("https://hobnob.firebaseio.com/users");
				userFire.child(userID+"/name/first").setValue(firstName_tx.getText().toString());
				userFire.child(userID+"/name/last").setValue(lastName_tx.getText().toString());
				
				Intent intent = new Intent(getApplicationContext(), UserScreen.class);
			    intent.putExtra("ID", userID);
			    startActivity(intent);
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_account_details, menu);
		return true;
	}

}
