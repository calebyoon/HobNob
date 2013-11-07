package com.example.hobnob;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendList extends Activity {
	
	private String userID;
	private LinearLayout myLayout;
	private int numOfFriends;
	private String[] Friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);
		
		Intent i = getIntent();
	    userID = i.getStringExtra("ID");
	    numOfFriends = 0;
	    Friends = new String[10];
		
		myLayout = (LinearLayout) findViewById(R.id.friend_layout);
        final LayoutParams lp = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        
		Firebase friendRef = new Firebase("https://hobnob.firebaseio.com/users/" + userID + "/friends");
		friendRef.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot snapshot) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot snapshot, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot snapshot, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildAdded(DataSnapshot snapshot, String arg1) {
				// TODO Auto-generated method stub
	            numOfFriends++;
				TextView temp = new TextView(getApplicationContext());
				temp.setLayoutParams(lp);
	            temp.setId(numOfFriends);
	            temp.setTextSize(20);
	            System.out.println(snapshot.getChildrenCount());
	            System.out.println(snapshot.getName());
	            temp.setText(snapshot.getName());
	            temp.setPadding(0, 0, 0, 20);
	            
	            temp.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View current) {
						Intent intent = new Intent(getApplicationContext(), ChatRoom.class);
						intent.putExtra("ID", userID);
						intent.putExtra("Friend", ((TextView)current).getText().toString());
						startActivity(intent);
					}
				});
	            
	            myLayout.addView(temp);
			}
			
			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_list, menu);
		return true;
	}

}
