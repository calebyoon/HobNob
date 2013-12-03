package com.example.hobnob;

import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GuestsAttending extends Activity {
	
	private String eventID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guests_attending);
	    Parse.initialize(getApplicationContext(), "mI01KcelEuBnGZo5QdZeuCyEwODIVMjJkREDvraJ", "I91qxlmi6W7scygd1IQudVimpLdMBszZZkvpnzFW"); 

	    Intent i = getIntent();
	    eventID = i.getStringExtra("eventID");
	    
	    final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_list_item_1);
        final ListView list = (ListView) findViewById(R.id.guest_attending_list);
        list.setAdapter(listAdapter);
        
        
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Attendees");
		  query.whereEqualTo("eventID", eventID);
		  query.findInBackground(new FindCallback<ParseObject>() {
		 	  public void done(List<ParseObject> attendeeList, ParseException e) {
		 	    for (ParseObject attendee : attendeeList) {
		 	    	String attendeeID = attendee.getString("AttendeeID");
		 	    	
		 	    	//query to get the user first and last name for the chat 
		 	    	 ParseQuery<ParseObject> query = ParseQuery.getQuery("myUser");
		 			  query.whereEqualTo("userID", attendeeID);
		 			  query.findInBackground(new FindCallback<ParseObject>() {
		 			 	  public void done(List<ParseObject> userList, ParseException e) {
		 			 	    for (ParseObject user : userList) {
		 			 	    	
		 			 	    	listAdapter.add(user.getString("first_name") + " " + user.getString("last_name"));
		 			 	    }
		 			 	  }
		 			 	});
		 	    }
		 	  }
		 	});
		  
		  list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
		            String value = (String)listAdapter.getItem(position); 

		            //check if location, host, party name, or date is selected to sort by. Change query based
		            //on what is selected. only using host now. CHECK based on sort given through intent below
		            //which goes to event screen
		            
		        	Toast.makeText(getApplicationContext(), value + " selected", Toast.LENGTH_LONG).show();
		        	
				}
			});
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guests_attending, menu);
		return true;
	}
	

}
