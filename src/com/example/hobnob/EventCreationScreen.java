package com.example.hobnob;

import com.firebase.client.Firebase;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
  private String	userID;
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_creation_screen);
    
    Intent i = getIntent();
    userID = i.getStringExtra("ID");
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
        newRef.setValue(new Event(eventName_t.getText().toString(), eventTime_t.getText().toString(), eventDate_t.getText().toString(), eventType_s.getSelectedItem().toString(), userID));
        Intent intent = new Intent(getApplicationContext(), UserScreen.class);
	    intent.putExtra("ID", userID);
	    startActivity(intent);
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