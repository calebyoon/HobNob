package com.example.hobnob;

import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserScreen extends Activity {
	
	private TextView welcomeUser_tx;
	private String firstName;
	private String lastName;
	private Button createEvent_bt;
	private String userID;
	//private Button addFriend_bt;
	private Button viewEvents_bt;
	//private EditText friend_tx;
	private String friend_id;
	private Button myProfile_bt;
	private Button chat_bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_user_screen);

		
		Intent i = getIntent();
        // Receiving the Data
        userID = i.getStringExtra("ID");
		
		Firebase root = new Firebase("https://hobnob.firebaseio.com/users/");
		final Firebase userRef = root.child(userID);
		Firebase nameRef = userRef.child("name");
		final Firebase friendRef = userRef.child("friends");
		final Firebase newPushRef = friendRef.push();
		
		//addFriend_bt = (Button)findViewById(R.id.addFriend);
		//friend_tx = (EditText)findViewById(R.id.friend);
		chat_bt = (Button)findViewById(R.id.chatButton);
        myProfile_bt = (Button)findViewById(R.id.profileButton);
        viewEvents_bt = (Button)findViewById(R.id.viewEventsButton);
        createEvent_bt = (Button)findViewById(R.id.createEventButton);
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
		
		if(firstName != null) {
	        System.out.println(firstName);
		}
		
		createEvent_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), EventCreationScreen.class);
				intent.putExtra("ID", userID);
			    startActivity(intent);
				
			}
		});
		
		viewEvents_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("viewEvents", "clicked");
				Intent intent = new Intent(getApplicationContext(), EventTabScreen.class);
				startActivity(intent);
			}
		});

		myProfile_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("profile", "clicked");
				Intent intent = new Intent(getApplicationContext(), EditUser.class);
				intent.putExtra("ID", userID);
			    startActivity(intent);
				
			}
		});
		
		/*addFriend_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//friendRef.child(friend_tx.getText().toString() + "/chat/1").setValue("");
				Firebase chatRef = friendRef.push();
				friendRef.child(friend_tx.getText().toString() + "/chat").setValue(new Messages("0", "first message"));
				//newPushRef.setValue(friend_tx.getText().toString());
			}
		});*/
		
		chat_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), FriendList.class);
				intent.putExtra("ID", userID);
				intent.putExtra("firstName", firstName);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_screen, menu);
		return true;
	}

}
