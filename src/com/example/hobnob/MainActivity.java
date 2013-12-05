package com.example.hobnob;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class MainActivity extends Activity {
	
	private Dialog progressDialog;
	private Button login_b;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//check_b = (Button)findViewById(R.id.checkButton);
		login_b = (Button)findViewById(R.id.loginButton);
		login_b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				onLoginButtonClicked();
			}
		});
		
		Session session = ParseFacebookUtils.getSession();
	    if (session != null && session.isOpened()) {
	        makeMeRequest();
	    }
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			Log.i("linked", "is linked " + currentUser.getUsername());
		}
	}

	private void makeMeRequest() {
		Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
        new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser user, Response response) {
                // handle response
            	if (user != null) { 
                // Create a JSON object to hold the profile info
                JSONObject userProfile = new JSONObject();
                try {                   
                    // Populate the JSON object 
                    userProfile.put("facebookId", user.getId());
                    userProfile.put("name", user.getName());
                    Log.i("userfromfb", userProfile.get("name").toString());
                    Log.i("parseuserName", "name " + ParseUser.getCurrentUser().getUsername());
                    ParseUser.getCurrentUser().put("name", user.getName());
                    ParseUser.getCurrentUser().saveInBackground();
                    Log.i("thename", ParseUser.getCurrentUser().get("name").toString());
                    
                    if (user.getLocation().getProperty("name") != null) {
                        userProfile.put("location", (String) user
                                .getLocation().getProperty("name")); 
                    }                           
                        
                } catch (JSONException e) {
                    Log.d("JSON",
                            "Error parsing returned user data.");
                }

            } else if (response.getError() != null) {
                // handle error
            	 if ((response.getError().getCategory() == 
                         FacebookRequestError.Category.AUTHENTICATION_RETRY) || 
                         (response.getError().getCategory() == 
                         FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION)) 
                     {
                         Log.d("error",
                                 "The facebook session was invalidated.");
                     } else {
                         Log.d("error",
                                 "Some other error: "
                                         + response.getError()
                                                 .getErrorMessage());
                     }
            	}         
            }
        });
	    request.executeAsync();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void onLoginButtonClicked() {
		MainActivity.this.progressDialog = ProgressDialog.show(
				MainActivity.this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList("basic_info", "user_about_me",
				"user_relationships", "user_birthday", "user_location");
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				MainActivity.this.progressDialog.dismiss();
				if (user == null) {
					Log.d("usernull",
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d("usernew",
							"User signed up and logged in through Facebook!");
					startIntent();
				} else {
					Log.d("loggedin",
							"User logged in through Facebook!");
					startIntent();
				}
			}

			private void startIntent() {
				Intent intent = new Intent(getApplicationContext(), EventTabScreen.class);
		        startActivity(intent);
			}
		});
	}
}
