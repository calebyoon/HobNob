package com.example.hobnob;

import java.util.List;
import java.util.Map;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
import android.widget.Toast;

public class EventScreen extends Activity
{
  private TextView eventName_t;
  private TextView eventHost_t;
  private TextView eventType_t;
  private TextView eventDate_t;
  private TextView eventTime_t;
  private TextView eventAddress_t;
  private TextView eventCityState_t;
  private TextView eventDescription_t;
  private Button attendEvent_b;
  private Button unattendEvent_b;

  private Button mapViewButton_b;

  private Button guestsAttending_b;

  private String userID;
  private String eventListID;
  private String type;
  private String arg1;
  private String arg2;
  private String my_address;
  
  private String name;
  private String host;
  private String event_type;
  private String date;
  private String time;
  private String event_ID;
  private boolean attend;
  private String hostname;


  private void switchButtons(boolean showAttend) {
    if(!showAttend) {
      attendEvent_b.setVisibility(View.GONE);
      unattendEvent_b.setVisibility(View.VISIBLE);
    }
    else {
      attendEvent_b.setVisibility(View.VISIBLE);
      unattendEvent_b.setVisibility(View.GONE);
    }
    if(type.equals("host")) {
      attendEvent_b.setVisibility(View.GONE);
      unattendEvent_b.setVisibility(View.GONE);
    }
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_screen);
    Parse.initialize(getApplicationContext(), "mI01KcelEuBnGZo5QdZeuCyEwODIVMjJkREDvraJ", "I91qxlmi6W7scygd1IQudVimpLdMBszZZkvpnzFW"); 

    
    eventName_t = (TextView) findViewById(R.id.eventName);
    eventHost_t = (TextView) findViewById(R.id.eventHost);
    eventType_t = (TextView) findViewById(R.id.eventType);
    eventDate_t = (TextView) findViewById(R.id.eventDate);
    eventTime_t = (TextView) findViewById(R.id.eventTime);
    eventAddress_t = (TextView) findViewById(R.id.eventAddress);
    eventCityState_t = (TextView) findViewById(R.id.eventCityState);
    eventDescription_t = (TextView) findViewById(R.id.eventDesView);
    attendEvent_b = (Button) findViewById(R.id.attendButton);
    unattendEvent_b = (Button) findViewById(R.id.unattendButton);

    mapViewButton_b = (Button) findViewById(R.id.MapViewButton);

    guestsAttending_b = (Button) findViewById(R.id.guestsAttending_b);
    
    attendEvent_b.setVisibility(View.GONE);
    unattendEvent_b.setVisibility(View.GONE);
    guestsAttending_b.setVisibility(View.GONE);

    
    Intent i = getIntent();
    //OLD VERSION
    /*eventID = i.getStringExtra("eventID");
    userID = i.getStringExtra("userID");
    type = i.getStringExtra("type");*/
    ///new intent extra
    type = "local";
    arg1 = i.getStringExtra("arg1");
    arg2 = i.getStringExtra("arg2");
    System.out.println("!" + arg1 + "!");
    System.out.println("!" + arg2 + "!");
    attend = true;
    
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
    query.whereEqualTo("name", arg1);
    query.whereEqualTo("host", arg2);
    
    
    query.findInBackground(new FindCallback<ParseObject>() {
    	  public void done(List<ParseObject> eventList, ParseException e) {
    	    // commentList now contains the last ten comments, and the "post"
    	    // field has been populated. For example:
    		  System.out.println("in function");
    	    for (ParseObject events : eventList) {
      		  System.out.println("in event");

    	      // This does not require a network access.
	          Toast.makeText(getApplicationContext(),events.getString("host"), Toast.LENGTH_LONG).show();

	          System.out.println("host " + events.getString("host"));

    	      eventName_t.setText(events.getString("name"));
    		  eventType_t.setText(events.getString("type"));
    		  ParseQuery<ParseUser> query3 = ParseUser.getQuery();;
    	      query3.getInBackground(events.getString("host"), new GetCallback<ParseUser>(){
    			@Override
    			public void done(ParseUser object, ParseException ex) {
    				hostname = object.getString("name");
    				Log.i("hello", "world");
    			    eventHost_t.setText(hostname);  
    			}
    	      });

    	      //String location = events.getString("location");
    		  eventDate_t.setText(events.getString("date"));
    		  eventTime_t.setText(events.getString("time"));
    		  my_address = events.getString("location");
    		  eventAddress_t.setText(my_address);
    		  event_ID = events.getObjectId();
    		  
    		  ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Attendees");
    		  query2.whereEqualTo("eventID", event_ID);
    		  query2.whereEqualTo("AttendeeID", "14"); //use user id here later
    		  query2.findInBackground(new FindCallback<ParseObject>() {
    		 	  public void done(List<ParseObject> attendeeList, ParseException e) {
    		 	    for (ParseObject attendee : attendeeList) {
    			        Toast.makeText(getApplicationContext(), "inside", Toast.LENGTH_LONG).show();
    		 	    	attend = false;
    		 	    }
    		 	    switchButtons(attend);
    		 	   guestsAttending_b.setVisibility(View.VISIBLE);

    		 	  }
    		 	});
    	      //listAdapter.add(name + "\n" + type + "\n" + host + "\n" + location + "\n" + date + "\n" + time);
    	    }
    	  }
    	});
    System.out.println("host is " + host);
    eventName_t.setText(name);
    eventHost_t.setText(host);
    
   Toast.makeText(getApplicationContext(), event_ID, Toast.LENGTH_LONG).show();
    
    attendEvent_b.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0)
      {
          //eventRef.child("attendees").push().setValue(userID);
          //userRef.child("event_list").push().setValue(eventID);
    	  ParseObject attendee = new ParseObject("Attendees");
    	  attendee.put("eventID", event_ID);
    	  //use user id here
    	  attendee.put("AttendeeID", "14");
    	  attendee.saveInBackground();
    	  
          finish();
      }
      
    });
    
    guestsAttending_b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//intent
			Intent intent = new Intent(getApplicationContext(), GuestsAttending.class);
            intent.putExtra("eventID", event_ID);
            startActivity(intent);
		}
	});
    
    unattendEvent_b.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0)
      {
        //Firebase eventListRef = listRef.child(eventListID);
        //eventListRef.removeValue();
          ParseQuery<ParseObject> query = ParseQuery.getQuery("Attendees");
	      query.whereEqualTo("eventID", event_ID);
	      query.whereEqualTo("AttendeeID", "14");
	      
	      query.findInBackground(new FindCallback<ParseObject>() {
	      	  public void done(List<ParseObject> attendeeList, ParseException e) {
	      	    // commentList now contains the last ten comments, and the "post"
	      	    // field has been populated. For example:
	      	    for (ParseObject attendee : attendeeList) {
	      	      // This does not require a network access.
	      	    	attendee.deleteInBackground();
	      	    }
	      	  }
	      	});



        finish();
      }
      
    });
    
    mapViewButton_b.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0)
      {
        Intent intent = new Intent(getApplicationContext(), MapScreen.class);
        intent.putExtra("ID", userID);
        intent.putExtra("eventID", event_ID);
        startActivity(intent);
        
      }
      
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    //getMenuInflater().inflate(R.menu.event_screen, menu);
    return true;
  }

}
