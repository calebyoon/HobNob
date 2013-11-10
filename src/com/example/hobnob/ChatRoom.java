package com.example.hobnob;

import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.Query;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatRoom extends Activity {

	private String userID;
	private String friendID;
	private TextView chat;
	private EditText chatBox;
	private Button 	submit_bt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_room);
		
		Intent i = getIntent();
	    userID = i.getStringExtra("ID");
	    friendID = i.getStringExtra("Friend");
	    Firebase correctRef = new Firebase("https://hobnob.firebaseio.com/chat");
	    Firebase checkRef;
	    if(userID.compareTo(friendID) < 0) {
	    	checkRef = correctRef.child(userID + ":" + friendID);
	    } else {
	    	checkRef = correctRef.child(friendID + ":" + userID);
	    } 
	    
	    final Firebase chatRef = checkRef;
		//final Firebase chatRef = new Firebase("https://hobnob.firebaseio.com/users" + userID + "/friends/" + friendID + "/chat");
		Query messageListQuery = chatRef.limit(100);
		
		chatBox = (EditText)findViewById(R.id.chatBox);
		chat = (TextView)findViewById(R.id.chatText);
		chat.setTextSize(20);
		chat.setMovementMethod(new ScrollingMovementMethod());
		submit_bt = (Button)findViewById(R.id.submitButton1);
	    
		messageListQuery.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onChildMoved(DataSnapshot snapshot, String arg1) {
				// TODO Auto-generated method stub
				Messages message = snapshot.getValue(Messages.class);
		        System.out.println("Message " + message.getText() + " from user " + message.getFrom() + " should no longer be displayed");
			}
			
			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildAdded(DataSnapshot snapshot, String arg1) {
				// TODO Auto-generated method stub
		        Messages message = snapshot.getValue(Messages.class);
		        System.out.println("Message " + message.getText() + " from user " + message.getFrom() + " was added");
		        chat.append(message.getFrom() + ": " + message.getText() + "\n");
			}
			
			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				
			}
		});
		
		submit_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(chatBox.getText().toString().equals("")) {
					//do nothing
				} else {
					Firebase newMessageRef = chatRef.push();
					newMessageRef.setValue(new Messages(userID, chatBox.getText().toString()));
				}
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_room, menu);
		return true;
	}

}
