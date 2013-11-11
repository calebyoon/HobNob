package com.example.hobnob;

import java.util.Map;

import com.firebase.client.ChildEventListener;
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

public class EventScreen extends Activity
{
  private TextView eventName_t;
  private TextView eventHost_t;
  private TextView eventType_t;
  private TextView eventDate_t;
  private TextView eventTime_t;
  private TextView eventAddress_t;
  private TextView eventCityState_t;
  private Button attendEvent_b;
  private Button unattendEvent_b;
  private String eventID;
  private String userID;
  private String eventListID;
  private String type;

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
    
    eventName_t = (TextView) findViewById(R.id.eventName);
    eventHost_t = (TextView) findViewById(R.id.eventHost);
    eventType_t = (TextView) findViewById(R.id.eventType);
    eventDate_t = (TextView) findViewById(R.id.eventDate);
    eventTime_t = (TextView) findViewById(R.id.eventTime);
    eventAddress_t = (TextView) findViewById(R.id.eventAddress);
    eventCityState_t = (TextView) findViewById(R.id.eventCityState);
    attendEvent_b = (Button) findViewById(R.id.attendButton);
    unattendEvent_b = (Button) findViewById(R.id.unattendButton);
    
    Intent i = getIntent();
    eventID = i.getStringExtra("eventID");
    userID = i.getStringExtra("userID");
    type = i.getStringExtra("type");
    
    final Firebase eventRoot = new Firebase("https://hobnob.firebaseio.com/events/");
    final Firebase userRoot = new Firebase("https://hobnob.firebaseio.com/users/");
    final Firebase userRef = userRoot.child(userID);
    final Firebase eventRef = eventRoot.child(eventID);
    
    final Firebase listRef = new Firebase("https://hobnob.firebaseio.com/users/" + userID + "/event_list");
    
    switchButtons(true);
    
    listRef.addChildEventListener(new ChildEventListener() {
      
      @Override
      public void onChildRemoved(DataSnapshot arg0) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void onChildMoved(DataSnapshot arg0, String arg1) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void onChildChanged(DataSnapshot snapshot, String arg1) {
        // TODO Auto-generated method stub
        
      }
      
      @Override
      public void onChildAdded(final DataSnapshot snapshot, String arg1) {
        // TODO Auto-generated method stub
        final String curEventID = snapshot.getValue(String.class);
        if(curEventID.equals(eventID)) {
          eventListID = snapshot.getName();
          switchButtons(false);
        }
      }
      
      @Override
      public void onCancelled() {
        // TODO Auto-generated method stub
        
      }
    });
    
    eventRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot snapshot) {
          Object value = snapshot.getValue();
          if (value == null) {
          } else {
              eventName_t.setText((String)((Map)value).get("event_name"));
              eventHost_t.setText("Host: " + (String)((Map)value).get("event_host"));
              eventType_t.setText("Type: " + (String)((Map)value).get("event_type"));
              eventDate_t.setText("Date: " + (String)((Map)value).get("event_date"));
              eventTime_t.setText("Time: " + (String)((Map)value).get("event_time"));
              eventAddress_t.setText((String)((Map)value).get("event_address"));
              eventCityState_t.setText( (String)((Map)value).get("event_city") + ", " + (String)((Map)value).get("event_state") );
          }
      }

      @Override
      public void onCancelled()
      {
        // TODO Auto-generated method stub
        
      }});
    
    attendEvent_b.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0)
      {
          eventRef.child("attendees").push().setValue(userID);
          userRef.child("event_list").push().setValue(eventID);
          finish();
      }
      
    });
    
    unattendEvent_b.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0)
      {
        Firebase eventListRef = listRef.child(eventListID);
        eventListRef.removeValue();
        finish();
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
