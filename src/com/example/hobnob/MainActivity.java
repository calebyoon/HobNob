package com.example.hobnob;

import com.example.hobnob.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.simplelogin.SimpleLogin;
import com.firebase.simplelogin.SimpleLoginAuthenticatedHandler;
import com.firebase.simplelogin.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	private Button login_b;
	private Button check_b;
	private Button create_b;
	private EditText username_t;
	private EditText password_t;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Firebase ref = new Firebase("https://hobnob.firebaseio.com");

		//check_b = (Button)findViewById(R.id.checkButton);
		login_b = (Button)findViewById(R.id.loginButton);
		create_b = (Button)findViewById(R.id.createButton);
		username_t = (EditText)findViewById(R.id.usernameText);
		password_t = (EditText)findViewById(R.id.passwordText);
		
		/*check_b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//Firebase fire = new Firebase("https://hobnob.firebaseio.com/");
				SimpleLogin authClient = new SimpleLogin(ref);
				
				authClient.checkAuthStatus(new SimpleLoginAuthenticatedHandler() {

					@Override
					public void authenticated(
							com.firebase.simplelogin.enums.Error error, User user) {

						if (error != null) {
						      // Oh no! There was an error performing the check
							System.out.println("error authenticating");
							System.out.println(error.toString());
						
						} else if (user == null) {
						      // No user is logged in
							System.out.println("no user logged in");
						} else {
						      // There is a logged in user
							System.out.println("user logged in");
						}
					}
					});
				
			}
		});*/
		
		login_b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//Firebase fire = new Firebase("https://hobnob.firebaseio.com/");
				SimpleLogin auth = new SimpleLogin(ref);
				
				auth.loginWithEmail(username_t.getText().toString(), password_t.getText().toString(), new SimpleLoginAuthenticatedHandler() {
					
					@Override
					public void authenticated(
							com.firebase.simplelogin.enums.Error error, User user) {
						// TODO Auto-generated method stub
						if(error != null) {
							System.out.println(error.toString());
							System.out.println("login error");
					      // There was an error logging into this account
					    }
					    else {
							System.out.println("user logged int successfully");
							System.out.println(user.getUserId());
							Intent intent = new Intent(getApplicationContext(), EventTabScreen.class);
						    intent.putExtra("ID", user.getUserId());
						    startActivity(intent);
					      // We are now logged in
					    }
					}
					});
				
			}
		});
		
		create_b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				//Firebase fire = new Firebase("https://hobnob.firebaseio.com/");
				SimpleLogin authClient2 = new SimpleLogin(ref);
				System.out.println(username_t.toString());
				authClient2.createUser(username_t.getText().toString(), password_t.getText().toString(), new SimpleLoginAuthenticatedHandler() {

					@Override
					public void authenticated(
							com.firebase.simplelogin.enums.Error error, User user) {
						// TODO Auto-generated method stub
					    if(error != null) {
						      // There was an error creating this account
							System.out.println("user not created");
							System.out.println(error.toString());

						    }
						else {
						      // We are now logged in
							System.out.println("user created");
							Intent createUserIntent = new Intent(getApplicationContext(), CreateAccountDetails.class);
							createUserIntent.putExtra("ID", user.getUserId());
						    startActivity(createUserIntent);
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
