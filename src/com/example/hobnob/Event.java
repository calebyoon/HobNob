package com.example.hobnob;


class Event {

	    private String event_name;
		private String event_time;
	    private String event_date;
	    private String event_type;
	    private String event_host;

		private String text;

	    private Event() { }

	    public Event(String event_name, String event_time, String event_date, String event_type, String event_host) {
	        this.event_name = event_name;
	        this.event_time = event_time;
	    	this.event_date = event_date;
	        this.event_type = event_type;
	        this.event_host = event_host;
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
	}