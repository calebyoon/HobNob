package com.example.hobnob;

public class Messages {
	
	private String from;
    private String text;

    private Messages() { }

    public Messages(String from, String text) {
        this.from = from;
    	this.text = text;
    }
    
	public String getFrom() {
		return from;
	}

	public String getText() {
		return text;
	}


}
