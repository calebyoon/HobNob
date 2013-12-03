package com.example.hobnob;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


//

public class EventTabFragment extends Fragment {
	
	private static final String KEY_TYPE = "type";
	
	public static final int TYPE_LOCAL = 0;
	public static final int TYPE_HOSTING = 1;
	public static final int TYPE_ATTENDING = 2;
	private static String userID = "14";
	private int numOfEvents;
	private SimpleCursorAdapter adapter;

	public static EventTabFragment create(int type) {
		final EventTabFragment fragment = new EventTabFragment();
		final Bundle args = new Bundle();
		fragment.setArguments(args);
		args.putInt(KEY_TYPE, type);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.my_fragment, container, false);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	    Parse.initialize(getActivity(), "mI01KcelEuBnGZo5QdZeuCyEwODIVMjJkREDvraJ", "I91qxlmi6W7scygd1IQudVimpLdMBszZZkvpnzFW"); 

		// Set up the listview
        ArrayList<String> playerlist = new ArrayList<String>();
        // Create and populate an ArrayList of objects from parse
		final FrameLayout frame = (FrameLayout) view.findViewById(R.layout.my_fragment);

        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        final ListView list = (ListView)view.findViewById(R.id.list_layout);
        list.setAdapter(listAdapter);
		//final TextView text = new TextView(getActivity());
		numOfEvents = 0;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        ParseQuery<ParseObject> attendQuery = ParseQuery.getQuery("Attendees");

        
		switch (getArguments().getInt(KEY_TYPE)) {
		case TYPE_LOCAL:
	        attendQuery.whereEqualTo("AttendeeID", "naugust");
			break;
		case TYPE_HOSTING:
	        query.whereEqualTo("host", "TEST MAN");
	        attendQuery.whereEqualTo("AttendeeID", "naugust");
	        //should give 0 attendees
			break;
		case TYPE_ATTENDING:
			attendQuery.whereEqualTo("AttendeeID", userID);
			query.whereEqualTo("date", "000000");
			//should give 0 events
			break;
		}
		
		attendQuery.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				// TODO Auto-generated method stub
				for (ParseObject event : objects) {
					//eventsAttending.add(event);
					System.out.println("inside add");
					
					ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Event");
					query2.getInBackground(event.getString("eventID"), new GetCallback<ParseObject>() {
					  public void done(ParseObject object, ParseException e) {
					    if (e == null) {
					      // object will be your game score
					    	String name = object.getString("name");
				      	    String host = object.getString("host");
				      	    String text = String.format("%s \nBy: " + host, name);
				      	    listAdapter.add(text);
					    } else {
					      // something went wrong
					    }
					  }
					});
				}
			}
		});
		
		query.findInBackground(new FindCallback<ParseObject>() {
	      	  public void done(List<ParseObject> eventList, ParseException e) {
	      	    // commentList now contains the last ten comments, and the "post"
	      	    // field has been populated. For example:
	      	    for (ParseObject events : eventList) {
	      	      // This does not require a network access.
	      	    	
	      	      String name = events.getString("name");
	      	      String type = events.getString("type");
	      	      String host = events.getString("host");
	      	      String location = events.getString("location");
	      	      String date = events.getString("date");
	      	      String time = events.getString("time");
	      	      
	      	      
	      	      String text = String.format("%s \nBy: " + host, name);
	      	      
	      	      //listAdapter.add(name + "\n" + type + "\n" + host + "\n" + location + "\n" + date + "\n" + time);
	      	      listAdapter.add(text);
	      	      
	      	    }
	      	  }
	      	});
		
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					//TextView txt = (TextView) parent.getChildAt(position - list.getFirstVisiblePosition()).findViewById(android.R.layout.simple_list_item_1);
		            //String keyword = txt.getText().toString();
					// TODO Auto-generated method stub
		            String value = (String)listAdapter.getItem(position); 
		            String lines[] = value.split("[\n]");
		            String line1 = lines[0];
		            String line2 = lines[1];
		            String lines2[] = lines[1].split(": ");
		            String str = lines2[1];

		            //check if location, host, party name, or date is selected to sort by. Change query based
		            //on what is selected. only using host now. CHECK based on sort given through intent below
		            //which goes to event screen
		            
			        Intent intent = new Intent(getActivity(), EventScreen.class);
			        line1 = line1.trim();
			        str = str.trim();
			        
	                intent.putExtra("arg1", line1);
	                intent.putExtra("arg2", str);
	                intent.putExtra("sort", "name");
	                startActivity(intent);
			        
		        	Toast.makeText(getActivity(), "1: " + line1 + " 2: " + str, Toast.LENGTH_LONG).show();
		        	
				}
			});
			
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
