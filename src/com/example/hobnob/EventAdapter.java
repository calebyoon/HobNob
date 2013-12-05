package com.example.hobnob;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event>
{

  private ArrayList<Event> items;
  
  public EventAdapter(Context context, int textViewResourceId, ArrayList<Event> items) {
    super(context, textViewResourceId, items);
    this.items = items;
  }
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View v = convertView;
    Event e = items.get(position);
    LocationManager locationManager =  (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (v == null) {
      LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      v = vi.inflate(R.layout.event_list_item, null);
  }
    if (e != null) {
      TextView eventText_t = (TextView) v.findViewById(R.id.eventListText);
      TextView eventDistance_t = (TextView) v.findViewById(R.id.eventListDistance);
      ImageView eventImage_i = (ImageView) v.findViewById(R.id.eventListImage);
      if (eventText_t != null) {
        eventText_t.setText(e.getEvent_name() + "\nBy: " + e.getEvent_host());                            
      }
      if(eventDistance_t != null) {
        LatLng coords = e.getEvent_coords();
        float [] results = new float[3];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), coords.latitude, coords.longitude, results);
        Double dist = Double.valueOf(results[0]);
        dist = dist * 0.000621371;
        dist = (double)Math.round(dist * 10) / 10;
        eventDistance_t.setText(dist.toString() + " mi");
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