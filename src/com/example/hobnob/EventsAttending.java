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

public class EventsAttending extends Activity
{
  private int numOfEvents;
  private LinearLayout myLayout;
  private String userID;

  @SuppressLint("NewApi")
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_events_attending);
    
    Intent i = getIntent();
    userID = i.getStringExtra("ID");
  
  numOfEvents = 0;
  myLayout = (LinearLayout) findViewById(R.id.event_attending_layout);
      final LayoutParams lp = new LayoutParams( LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
      
  Firebase listRef = new Firebase("https://hobnob.firebaseio.com/users/" + userID + "/event_list");
  
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
      final String eventID = snapshot.getValue(String.class);
      Firebase eventRef = new Firebase("https://hobnob.firebaseio.com/events");
      eventRef.addChildEventListener(new ChildEventListener() {

        @Override
        public void onCancelled()
        {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void onChildAdded(final DataSnapshot eventSnapshot, String arg1)
        {
          final Event event = eventSnapshot.getValue(Event.class);
          if(eventSnapshot.getName().equals(eventID)) {
            TextView temp = new TextView(getApplicationContext());
            temp.setLayoutParams(lp);
            temp.setId(numOfEvents);
            temp.setTextSize(20);
            System.out.println(event.getEvent_name());
            temp.setText("Event: " + event.getEvent_name() + "\nBy: " + event.getEvent_host() + "\nTime: " + event.getEvent_time() + " on " + event.getEvent_date());
            temp.setPadding(0, 0, 0, 10);
            myLayout.addView(temp);
            numOfEvents++;
            temp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0)
                {
                  Intent intent = new Intent(getApplicationContext(), EventScreen.class);
                  intent.putExtra("eventID", eventSnapshot.getName());
                  intent.putExtra("userID", userID);
                  intent.putExtra("type", "attend");
                  startActivity(intent);
                  System.out.println(snapshot.getName());
                }
            });
          }
        }

        @Override
        public void onChildChanged(DataSnapshot arg0, String arg1)
        {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void onChildMoved(DataSnapshot arg0, String arg1)
        {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void onChildRemoved(DataSnapshot arg0)
        {
          // TODO Auto-generated method stub
          
        }
        
      });
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
    getMenuInflater().inflate(R.menu.events_attending, menu);
    return true;
  }

}
