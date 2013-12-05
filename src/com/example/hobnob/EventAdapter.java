package com.example.hobnob;

import java.util.ArrayList;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class EventAdapter extends ArrayAdapter<Event>
{
  private TextView eventText_t;
  private ArrayList<Event> items;
  private LocationListener ll = null;
  private String hostname;
  private Event e;
  public EventAdapter(Context context, int textViewResourceId, ArrayList<Event> items) {
    super(context, textViewResourceId, items);
    this.items = items;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View v = convertView;
    e = items.get(position);
    LocationManager locationManager =  (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if(location == null){
    	locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }
    if (v == null) {
      LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      v = vi.inflate(R.layout.event_list_item, null);
    }
    if (e != null) {
      eventText_t = (TextView) v.findViewById(R.id.eventListText);
      TextView eventDistance_t = (TextView) v.findViewById(R.id.eventListDistance);
      ImageView eventImage_i = (ImageView) v.findViewById(R.id.eventListImage);
      
      if (eventText_t != null) {
	      eventText_t.setText(e.getEvent_name() + "\nBy: " + e.getEvent_host_name());                            
	    }
      if(eventDistance_t != null) {
        LatLng coords = e.getEvent_coords();
        float [] results = new float[3];
        if (location != null){
        	Location.distanceBetween(location.getLatitude(), location.getLongitude(), coords.latitude, coords.longitude, results);
	        Double dist = Double.valueOf(results[0]);
	        dist = dist * 0.000621371;
	        dist = (double)Math.round(dist * 10) / 10;
	        eventDistance_t.setText(dist.toString() + " mi");
        }
      }
      if(eventImage_i != null) {
        if( e.getEvent_type().equals("Social")) {
          eventImage_i.setImageResource(R.drawable.social);
        }
        else if(e.getEvent_type().equals("Academic")) {
          eventImage_i.setImageResource(R.drawable.gradhat);
        }
        else if(e.getEvent_type().equals("Athletic")) {
          eventImage_i.setImageResource(R.drawable.athletic);
        }
      }
    }
    return v;
  }
}
