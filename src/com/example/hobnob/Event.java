package com.example.hobnob;

import java.util.Map;
import java.util.Vector;


class Event {

	    private String event_name;
	    private String event_time;
	    private String event_date;
	    private String event_type;
	    private String event_host;
	    private String event_address;
	    private String event_state;
	    private String event_city;
	    private String event_description;
	    private Map<String, String> attendees;

		private String text;

	    private Event() { }

	    public Event(String event_name, String event_time, String event_date, String event_type, String event_host, String event_address, String event_city, String event_state, String event_description, Map<String, String> attendees) {
	        this.event_name = event_name;
	        this.event_time = event_time;
	        this.event_date = event_date;
	        this.event_type = event_type;
	        this.event_host = event_host;
	        this.event_address = event_address;
	        this.event_city = event_city;
	        this.event_state = event_state;
	        this.event_description = event_description;
	        this.attendees = attendees;
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

    public String getEvent_address()
    {
      return event_address;
    }

    public String getEvent_state()
    {
      return event_state;
    }

    public String getEvent_city()
    {
      return event_city;
    }

    public String getEvent_description()
    {
      return event_description;
    }
    
    public Map<String, String> getAttendees()
    {
      return attendees;
    }
	}
