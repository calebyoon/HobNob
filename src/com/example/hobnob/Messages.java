package com.example.hobnob;

public class Messages {
	
	private String from;
	private String to;
    private String text;

    private Messages() { }

    public Messages(String from, String to, String text) {
        this.from = from;
        this.to = to;
    	this.text = text;
    }
    
	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getText() {
		return text;
	}


}
