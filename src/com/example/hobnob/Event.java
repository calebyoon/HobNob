package com.example.hobnob;

import java.util.Map;
import java.util.Vector;

import com.google.android.gms.maps.model.LatLng;


class Event {

      private String eventID;
	    private String event_name;
	    private String event_time;
	    private String event_date;
	    private String event_type;
	    private String event_host;
	    private LatLng coords;

	    private Event() { }

	    public Event(String eventID, String event_name, String event_time, String event_date, String event_type, String event_host, LatLng coords) {
	        this.event_name = event_name;
	        this.eventID = eventID;
	        this.event_time = event_time;
	        this.event_date = event_date;
	        this.event_type = event_type;
	        this.event_host = event_host;
	        this.coords = coords;
	    }

	    
	  public String getEventID() {
	    return eventID;
	  }
	    
	  public String getEvent_name() {
			return event_name;
		}

		public String getEvent_time() {
			return event_time;
		}

		public String getEvent_date() {
			return event_date;
		}

		public String getEvent_type() {
			return event_type;
		}
		
	  public String getEvent_host() {
			return event_host;
		}

	  public LatLng getEvent_coords() {
      return coords;
    }
	}
