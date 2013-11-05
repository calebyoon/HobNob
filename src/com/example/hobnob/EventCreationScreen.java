package com.example.hobnob;

import com.firebase.client.Firebase;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EventCreationScreen extends Activity
{

  private Button create_b;
  private EditText eventName_t;
  private Spinner eventType_s;
  private EditText eventTime_t;
  private EditText eventDate_t;
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_creation_screen);
    
    final Firebase ref = new Firebase("https://hobnob.firebaseio.com");

    create_b = (Button)findViewById(R.id.createEventButton);
    eventName_t = (EditText)findViewById(R.id.eventNameText);
    eventType_s = (Spinner)findViewById(R.id.eventTypeSpinner);
    eventTime_t = (EditText)findViewById(R.id.eventTimeText);
    eventDate_t = (EditText)findViewById(R.id.eventDateText);
    
    create_b.setOnClickListener(new OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Firebase eventRef = new Firebase("https://hobnob.firebaseio.com/events");
        Firebase newRef = eventRef.push();
        newRef.child("name/" + eventName_t.toString());
        newRef.child("type/" + eventType_s.toString());
        newRef.child("date/" + eventDate_t.toString());
        newRef.child("time/" + eventTime_t.toString());
      }
    });
    
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.event_creation_screen, menu);
    return true;
  }

}
