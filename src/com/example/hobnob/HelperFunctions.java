package com.example.hobnob;

import java.util.Map;

import android.content.Context;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

public class HelperFunctions {
	
	private String fullName;

	public String convertToName(String userID, Context context) {
		//Firebase nameRef = new Firebase("https://hobnob.firebaseio.com/users/" + userID + "/name");
		Firebase root = new Firebase("https://hobnob.firebaseio.com/users/");
		final Firebase userRef = root.child(userID);
		Firebase nameRef = userRef.child("name");
		
		System.out.println("IN CONVERT");
		nameRef.addValueEventListener(new ValueEventListener() {
		     @Override
		     public void onDataChange(DataSnapshot snapshot) {
		         Object value = snapshot.getValue();
		         if (value == null) {
		             System.out.println("User doesn't exist");
		             //fullName.concat("");//setFullName("");
		             fullName = "";
		         } else {
		        	 System.out.println("GETTING");
		        	 fullName = (String)((Map)value).get("first") + " " + (String)((Map)value).get("last");
		             //fullName.concat((String)((Map)value).get("first") + (String)((Map)value).get("last"));
		         }
		     }

		     @Override
		     public void onCancelled() {
		         System.err.println("Listener was cancelled");
		     }
		 });
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RETURN");
		return fullName;
	}
}
