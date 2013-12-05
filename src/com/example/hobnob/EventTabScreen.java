package com.example.hobnob;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.*;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class EventTabScreen extends FragmentActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_tab_screen);
	    Parse.initialize(this, "mI01KcelEuBnGZo5QdZeuCyEwODIVMjJkREDvraJ", "I91qxlmi6W7scygd1IQudVimpLdMBszZZkvpnzFW"); 

	    Log.i("hello", ParseUser.getCurrentUser().get("name").toString());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(this);
		
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.addTab(bar.newTab().setText("Local").setTabListener(this), true);
		bar.addTab(bar.newTab().setText("Hosting").setTabListener(this));
		bar.addTab(bar.newTab().setText("Attending").setTabListener(this));
		

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	} 
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.create_event_menu:
	        	/*System.out.println("Touched");
	        	ParseObject newEvent = new ParseObject("Event");
	        	newEvent.put("name", "new event");
	        	newEvent.put("type", "academic");
	    		newEvent.put("location", "new place");
	    		newEvent.put("host", "nick a");
	    		newEvent.put("date", "11/27/13");
	    		newEvent.put("time", "11:00");
	    		newEvent.put("description", "NEW");
	    		newEvent.saveInBackground();*/
	        	Intent intent = new Intent(getApplicationContext(), EventCreationScreen.class);
				intent.putExtra("ID", "14");
			    startActivity(intent);
	            return true;
	        case R.id.action_settings:
	        	Toast.makeText(getApplicationContext(), "Settings pressed", Toast.LENGTH_LONG).show();
	        	return true;
	        case R.id.action_logout:
	        	if (ParseFacebookUtils.getSession() != null)
	        		ParseFacebookUtils.getSession().closeAndClearTokenInformation();
	        	ParseUser.logOut();
	        	finish();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}
	
	@Override
	public void onPageSelected(int position) {
		getActionBar().setSelectedNavigationItem(position);
	}
	
	private class MyPagerAdapter extends FragmentStatePagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public int getCount() {
			return 3;
		}
		
		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return EventTabFragment.create(EventTabFragment.TYPE_LOCAL);
			case 1:
				return EventTabFragment.create(EventTabFragment.TYPE_HOSTING);
			case 2:
				return EventTabFragment.create(EventTabFragment.TYPE_ATTENDING);
			}
			return null;
		}
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
	
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
	}
}
