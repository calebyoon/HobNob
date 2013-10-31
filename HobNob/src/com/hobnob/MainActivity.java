package com.hobnob;

import com.hobnob.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	private Button login_b;
	private Button createUser;
	private EditText username_t;
	private EditText password_t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Firebase f = new Firebase("https://hobnob.firebaseio.com/");
		f.addValueEventListener(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub

			}
		});

		login_b = (Button) findViewById(R.id.loginButton);
		username_t = (EditText) findViewById(R.id.usernameText);
		password_t = (EditText) findViewById(R.id.passwordText);
		createUser = (Button) findViewById(R.id.createUser);
		
		createUser.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v){
				Firebase fire = new Firebase("https://hobnob.firebaseio.com");
				SimpleLogin authClient = new SimpleLogin(fire);
				authClient.createUser("email@example.com", "password123", new SimpleLoginAuthenticatedHandler() {
					@Override
					public void authenticated(com.firebase.simplelogin.enums.Error arg0, User arg1) {
						if(arg0 != null) {
						      // There was an error creating this account
							Log.d("create", "user creation error");
						}
					    else {
					    	Log.d("create", "email@example.com and password123 created");
					    }
					}
					});
			}
		});
		
		
		login_b.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Firebase fire = new Firebase("https://hobnob.firebaseio.com");
				SimpleLogin auth = new SimpleLogin(fire);
				Log.d("login", "login button pressed");
				auth.logout();
				auth.checkAuthStatus(new SimpleLoginAuthenticatedHandler() {
					@Override
					public void authenticated(
							com.firebase.simplelogin.enums.Error error, User user) {
						if (error != null) {
							// Oh no! There was an error performing the check
							Log.d("auth status", "error");
							Log.d("debug", error.toString());
						} else if (user == null) {
							Log.d("auth status", "no user logged in");
						} else {
							// There is a logged in user
							Log.d("auth status", "logged in user exists");
						}

					}
					});
				
				auth.loginWithEmail(username_t.toString(),
					password_t.toString(),
					new SimpleLoginAuthenticatedHandler() {
						@Override
						public void authenticated(
								com.firebase.simplelogin.enums.Error arg0,
								User arg1) {
							// TODO Auto-generated method stub
							if (arg0 != null) {
								Log.d("login", "error");
								// There was an error logging into this
								// account
							} else {
								Log.d("login", "logged in");
								// We are now logged in
							}
						}
					});
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
