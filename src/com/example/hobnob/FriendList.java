package com.example.hobnob;

import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.Context;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FriendList extends Activity {
	
	private String userID;
	private LinearLayout myLayout;
	private int numOfFriends;
	private String myName;
	private String[] Friends;
	private HelperFunctions helper;
	private Button addFriend_bt;
	private EditText friend_tx;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);
		
		helper = new HelperFunctions();
		Intent i = getIntent();
	    userID = i.getStringExtra("ID");
	    myName = i.getStringExtra("firstName");
	    numOfFriends = 0;
	    Friends = new String[10];
	    
	    addFriend_bt = (Button)findViewById(R.id.addFriend);
	  	friend_tx = (EditText)findViewById(R.id.friend);
		myLayout = (LinearLayout) findViewById(R.id.friend_layout);
		
        //final LayoutParams lp = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        //lp.setMargins(10, 10, 10, 10);
        //myLayout.requestLayout();
        
        
		final Firebase friendRef = new Firebase("https://hobnob.firebaseio.com/users/" + userID + "/friends");
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
				LayoutParams lp = new LayoutParams( LayoutParams.MATCH_PARENT, 200, Gravity.CENTER_HORIZONTAL);
		        //lp.setMargins(30, 30, 30, 30);
		        
		        myLayout.requestLayout();
		        
				android.content.Context context = getApplicationContext();
	            numOfFriends++;
				final Button temp = new Button(getApplicationContext());
				//temp.setLayoutParams(lp);
	            temp.setId(numOfFriends);
	            temp.setTextSize(30);
	            temp.setTextColor(0xff000000);
	            //temp.setPadding(30, 30, 30, 30);
	            temp.setBackgroundResource(R.drawable.custom_button);
	            
	            //temp.setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
	            System.out.println(snapshot.getChildrenCount());
	            System.out.println(snapshot.getName());
	            //System.out.println(HelperFunctions.convertToName(snapshot.getName()));
	            final String friendID = snapshot.getName();
	            //temp.setText(HelperFunctions.convertToName(snapshot.getName(), context));
	            
	            Firebase nameRef = new Firebase("https://hobnob.firebaseio.com/users/" + snapshot.getName() + "/name");
	            nameRef.addValueEventListener(new ValueEventListener() {
	   		     @Override
	   		     public void onDataChange(DataSnapshot snapshot2) {
	   		         Object value = snapshot2.getValue();
	   		         if (value == null) {
	   		             System.out.println("User doesn't exist");
	   		         } else {
	   		        	 System.out.println((String)((Map)value).get("first"));
	   		        	 System.out.println((String)((Map)value).get("last"));

	   		             temp.setText((String)((Map)value).get("first") + " " + (String)((Map)value).get("last"));

	   		         }
	   		     }

	   		     @Override
	   		     public void onCancelled() {
	   		         System.err.println("Listener was cancelled");
	   		     }
	   		 });
	            
	            
	            temp.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View current) {
						Intent intent = new Intent(getApplicationContext(), ChatRoom.class);
						intent.putExtra("ID", userID);
						intent.putExtra("Friend", friendID);
						intent.putExtra("firstName", myName);
						intent.putExtra("friendName", ((TextView)current).getText().toString());
						startActivity(intent);
						//((TextView)current).getText().toString()
					}
				});
	            
	            myLayout.addView(temp, lp);
			}
			
			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				
			}
		});
		
		addFriend_bt.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//friendRef.child(friend_tx.getText().toString() + "/chat/1").setValue("");
			Firebase chatRef = friendRef.push();
			friendRef.child(friend_tx.getText().toString() + "/chat").setValue(new Messages("0", "first message"));
			//newPushRef.setValue(friend_tx.getText().toString());
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
