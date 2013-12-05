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
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ListFragment;
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
	private ListView list;
	private int numOfEvents;
	private EventAdapter listAdapter;
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
        // Create and populate an ArrayList of objects from parse
	final FrameLayout frame = (FrameLayout) view.findViewById(R.layout.my_fragment);
    ArrayList<Event> m_event = new ArrayList<Event>();
    listAdapter = new EventAdapter(getActivity(), R.layout.event_list_item, m_event);
    list = (ListView)view.findViewById(R.id.list_layout);
    list.setAdapter(listAdapter);
    numOfEvents = 0;
    
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		listAdapter.clear();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
	    ParseQuery<ParseObject> attendQuery = ParseQuery.getQuery("Attendees");
	        
			switch (getArguments().getInt(KEY_TYPE)) {
			case TYPE_LOCAL:
		        attendQuery.whereEqualTo("AttendeeID", "naugust");
		        attendQuery.whereNotEqualTo("host", ParseUser.getCurrentUser().getObjectId());
				break;
			case TYPE_HOSTING:
		        query.whereEqualTo("host", ParseUser.getCurrentUser().getObjectId());
		        attendQuery.whereEqualTo("AttendeeID", "naugust");
		        //should give 0 attendees
				break;
			case TYPE_ATTENDING:
				attendQuery.whereEqualTo("AttendeeID", ParseUser.getCurrentUser().getObjectId());
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
						    	String name = object.getString("name");
						    	String id = object.getObjectId();
						    	String time = object.getString("time");
						    	String date = object.getString("date");
						    	String type = object.getString("type");
			      	    String host = object.getString("host");
		                String hostName = object.getString("hostName");
			      	    double lat = object.getDouble("lat");
			      	    double lng = object.getDouble("lng");
			      	    listAdapter.add(new Event(id, name, time, date, type, host, hostName, new LatLng(lat, lng)));
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
		      	    for (ParseObject event : eventList) {
		      	      String name = event.getString("name");
	                String id = event.getObjectId();
	                String time = event.getString("time");
	                String date = event.getString("date");
	                String type = event.getString("type");
	                String host = event.getString("host");
	                String hostname = event.getString("hostName");
	                double lat = event.getDouble("lat");
	                double lng = event.getDouble("lng");
	                listAdapter.add(new Event(id, name, time, date, type, host, hostname, new LatLng(lat, lng)));
		      	      
		      	    }
		      	  }
		      	});
			
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						//TextView txt = (TextView) parent.getChildAt(position - list.getFirstVisiblePosition()).findViewById(android.R.layout.simple_list_item_1);
	          //String keyword = txt.getText().toString();
	          Event value = (Event)listAdapter.getItem(position); 
		        Intent intent = new Intent(getActivity(), EventScreen.class);
	      
	          intent.putExtra("arg1", value.getEvent_name());
	          intent.putExtra("arg2", value.getEvent_host());
	          intent.putExtra("sort", "name");
	          startActivity(intent);		        	
					}
				});
	}
}
