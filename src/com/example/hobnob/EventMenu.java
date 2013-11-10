package com.example.hobnob;

import java.util.HashMap;

import com.firebase.client.Firebase;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EventMenu extends Activity
{

  private Button localEvents_b;
  private Button eventsHosting_b;
  private Button eventsAttending_b;
  private String userID;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_menu);
    
    Intent i = getIntent();
    userID = i.getStringExtra("ID");
    
    localEvents_b = (Button)findViewById(R.id.ViewLocalEventsButton);
    eventsHosting_b = (Button)findViewById(R.id.EventsHostingButton);
    eventsAttending_b = (Button)findViewById(R.id.EventsAttendingButton);
    localEvents_b.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ListEvents.class);
        intent.putExtra("ID", userID);
        startActivity(intent);
      }
    });
    eventsHosting_b.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), EventsHosting.class);
        intent.putExtra("ID", userID);
        startActivity(intent);
      }
    });
    eventsAttending_b.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), EventsAttending.class);
        intent.putExtra("ID", userID);
        startActivity(intent);
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.event_menu, menu);
    return true;
  }

}
