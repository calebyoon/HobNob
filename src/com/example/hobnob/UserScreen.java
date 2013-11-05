package com.example.hobnob;

import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class UserScreen extends Activity {
	
	private TextView welcomeUser_tx;
	private String firstName;
	private String lastName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_screen);
		
		Intent i = getIntent();
        // Receiving the Data
        String userID = i.getStringExtra("ID");
		
		Firebase root = new Firebase("https://hobnob.firebaseio.com/users/");
		Firebase userRef = root.child(userID);
		Firebase nameRef = userRef.child("name");
		
		
        welcomeUser_tx = (TextView)findViewById(R.id.welcomeUserText);
        
        nameRef.addValueEventListener(new ValueEventListener() {
		     @Override
		     public void onDataChange(DataSnapshot snapshot) {
		         Object value = snapshot.getValue();
		         if (value == null) {
		             System.out.println("User doesn't exist");
		         } else {
		        	 System.out.println((String)((Map)value).get("first"));
		        	 System.out.println((String)((Map)value).get("last"));

		             firstName = (String)((Map)value).get("first");
		             lastName = (String)((Map)value).get("last");
		             welcomeUser_tx.setText("Welcome " + firstName + " " + lastName);

		         }
		     }

		     @Override
		     public void onCancelled() {
		         System.err.println("Listener was cancelled");
		     }
		 });
		
		userRef.addValueEventListener(new ValueEventListener() {
		     @Override
		     public void onDataChange(DataSnapshot snapshot) {
		         Object value = snapshot.getValue();
		         if (value == null) {
		             System.out.println("User doesn't exist");
		         } else {
		        	 System.out.println((String)((Map)value).get("first"));
		        	 System.out.println((String)((Map)value).get("last"));
		         }
		     }

		     @Override
		     public void onCancelled() {
		         System.err.println("Listener was cancelled");
		     }
		 });
		if(firstName != null) {
	        System.out.println(firstName);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_screen, menu);
		return true;
	}

}
