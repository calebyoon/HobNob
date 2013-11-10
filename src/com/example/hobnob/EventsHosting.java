package com.example.hobnob;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventsHosting extends Activity
{

  private int numOfEvents;
  private LinearLayout myLayout;
  private String userID;
  
  @SuppressLint("NewApi")
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_events_hosting);
    
    Intent i = getIntent();
    userID = i.getStringExtra("ID");
    System.out.println(userID);
    
    numOfEvents = 0;
    myLayout = (LinearLayout) findViewById(R.id.event_hosting_layout);
    
    numOfEvents = 0;
    myLayout = (LinearLayout) findViewById(R.id.event_layout);
    final LayoutParams lp = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        
    Firebase listRef = new Firebase("https://hobnob.firebaseio.com/events");
    
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
        final Event event = snapshot.getValue(Event.class);
        //check if it is your event. if so don't display
        if(event.getEvent_host().equals(userID)) {
          TextView temp = new TextView(getApplicationContext());
          temp.setLayoutParams(lp);
          temp.setId(numOfEvents);
          temp.setTextSize(20);
          System.out.println(event.getEvent_name());
          temp.setText("Event: " + event.getEvent_name() + "\nTime: " + event.getEvent_time() + " on " + event.getEvent_date());
          temp.setPadding(0, 0, 0, 10);
          myLayout.addView(temp);
          numOfEvents++;
          temp.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View arg0)
              {
                Intent intent = new Intent(getApplicationContext(), EventScreen.class);
                intent.putExtra("eventID", snapshot.getName());
                intent.putExtra("userID", userID);
                startActivity(intent);
                System.out.println(snapshot.getName());
              }
          });
         
        }
      }
      
      @Override
      public void onCancelled() {
        // TODO Auto-generated method stub
        
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.events_hosting, menu);
    return true;
  }

}
