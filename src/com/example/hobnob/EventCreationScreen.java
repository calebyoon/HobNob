package com.example.hobnob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
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
import android.widget.Spinner;
import android.widget.Toast;



public class EventCreationScreen extends Activity
{

  private Button create_b;
  private EditText eventName_t;
  private Spinner eventType_s;
  private EditText eventTime_t;
  private EditText eventDate_t;
  private EditText eventAddress_t;
  private EditText eventCity_t;
  private EditText eventState_t;
  private EditText eventDescription_t;
  private String realEventID;
  private String address;
  private ParseObject event;
  private LatLng coords;
  private boolean isEditing;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_creation_screen);

    create_b = (Button)findViewById(R.id.createEventButton);
    eventName_t = (EditText)findViewById(R.id.eventNameText);
    eventType_s = (Spinner)findViewById(R.id.eventTypeSpinner);
    eventTime_t = (EditText)findViewById(R.id.eventTimeText);
    eventDate_t = (EditText)findViewById(R.id.eventDateText);
    eventAddress_t = (EditText)findViewById(R.id.EventAddressText);
    eventCity_t = (EditText)findViewById(R.id.EventCityText);
    eventState_t = (EditText)findViewById(R.id.eventStateText);
    eventDescription_t = (EditText)findViewById(R.id.EventDescriptionText);
    
    Intent i = getIntent();
    realEventID = i.getStringExtra("eventID");
    isEditing = i.getBooleanExtra("edit", false);
    
    if(isEditing) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.getInBackground(realEventID, new GetCallback<ParseObject>() {
      	  public void done(ParseObject event, ParseException e) {
      	    if (e == null) {
      	    	if(event.getString("type").equals("Academic")) {
        	        eventType_s.setSelection(1);
    	    	} else if (event.getString("type").equals("Social")) {
        	        eventType_s.setSelection(0);
    	    	} else {
        	        eventType_s.setSelection(2);
    	    	}
    	    		
    	    	eventName_t.setText(event.getString("name"));
    	        eventTime_t.setText(event.getString("time"));
    	        eventDate_t.setText(event.getString("date"));
    	        eventAddress_t.setText(event.getString("address"));
    	        eventCity_t.setText(event.getString("city"));
    	        eventState_t.setText(event.getString("state"));
    	        eventDescription_t.setText(event.getString("description"));
      	    } else {
      	      // something went wrong
      	    }
      	  }
      	});
    }
        
    
    create_b.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
    	  
    	if(eventName_t.getText().toString().equals("") || eventTime_t.getText().toString().equals("") || eventDate_t.getText().toString().equals("") || eventAddress_t.getText().toString().equals("") || eventCity_t.getText().toString().equals("") || eventState_t.getText().toString().equals("")) {
        	Toast.makeText(getApplicationContext(), "One of the fields was not filled out", Toast.LENGTH_LONG).show();
    	} 
    	else {
    		if(isEditing) {
    			
    			ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
    	        query.getInBackground(realEventID, new GetCallback<ParseObject>() {
    	      	  public void done(ParseObject event, ParseException e) {
    	      	    if (e == null) {
    	      	    	event.put("name", eventName_t.getText().toString());
   		        		event.put("type", eventType_s.getSelectedItem().toString());
   		        		event.put("address", eventAddress_t.getText().toString());
   		        		event.put("city", eventCity_t.getText().toString());
   		        		event.put("state", eventState_t.getText().toString());
   		        		event.put("host", "TEST MAN");
   		        		event.put("date", eventDate_t.getText().toString());
   		        		event.put("time", eventTime_t.getText().toString());
   			    		event.put("hostName", ParseUser.getCurrentUser().getString("name"));
   		        		event.put("description", eventDescription_t.getText().toString());
   		        		LatLng coords = new LatLng(Float.NaN, Float.NaN);
   		        		try
       		            {
       		              coords = new MapMarkerLoader().execute(eventAddress_t.getText().toString() + " " + eventCity_t.getText().toString() + " " + eventState_t.getText().toString()).get();
       		            }
       		            catch( Exception e1 )
       		            {
       		              e1.printStackTrace();
       		            }
       		        		event.put("lat", coords.latitude);
       		        		event.put("lng", coords.longitude);
       		        		event.saveInBackground();
       		        		finish();
    	      	    } else {
    	      	      // something went wrong
    	      	    }
    	      	  }
    	      	});
    		} else {
	    		event = new ParseObject("Event");
	    		event.put("name", eventName_t.getText().toString());
	    		event.put("type", eventType_s.getSelectedItem().toString());
	    		event.put("address", eventAddress_t.getText().toString());
	        	event.put("city", eventCity_t.getText().toString());
	        	event.put("state", eventState_t.getText().toString());
	    		event.put("host", ParseUser.getCurrentUser().getObjectId());
	    		event.put("date", eventDate_t.getText().toString());
	    		event.put("time", eventTime_t.getText().toString());
	    		event.put("description", eventDescription_t.getText().toString());
	    		event.put("hostName", ParseUser.getCurrentUser().getString("name"));
	    		coords = new LatLng(Float.NaN, Float.NaN);
	    		try
	            {
   		           coords = new MapMarkerLoader().execute(eventAddress_t.getText().toString() + " " + eventCity_t.getText().toString() + " " + eventState_t.getText().toString()).get();
	            }
	            catch( InterruptedException e )
	            {
	              e.printStackTrace();
	            }
	            catch( ExecutionException e )
	            {
	              e.printStackTrace();
	            }
	        		event.put("lat", coords.latitude);
	        		event.put("lng", coords.longitude);
	        		event.saveInBackground();
	        		finish();
	        	
			}
	    			
    	}
      }
    });
    
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.event_creation_screen, menu);
    return true;
  }

}
