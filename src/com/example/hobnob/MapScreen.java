package com.example.hobnob;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.firebase.client.snapshot.Node;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.Menu;
import android.widget.TextView;

public class MapScreen extends Activity
{

  GoogleMap map;
  MapController mapController;
  String address;
  String eventName;
  TextView distanceText;
  Location location;
  LocationManager locationManager;
  Context mContext;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_screen);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    distanceText = (TextView) findViewById(R.id.distance);
    
    Intent i = getIntent();
    address = i.getStringExtra("address");
    eventName = i.getStringExtra("eventName");
    map.setMyLocationEnabled(true);
    LatLng coords = new LatLng(Float.NaN, Float.NaN);
    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    if (location == null)
      location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    try
    {
      coords = new MapMarkerLoader().execute(address).get();
    }
    catch( InterruptedException e )
    {
      e.printStackTrace();
    }
    catch( ExecutionException e )
    {
      e.printStackTrace();
    }
    map.addMarker(new MarkerOptions().position(new LatLng(coords.latitude, coords.longitude)).title(eventName));
    CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(coords, 16);
    map.moveCamera(cu);
    float [] results = new float[3];
    Location.distanceBetween(location.getLatitude(), location.getLongitude(), coords.latitude, coords.longitude, results);
    Float dist = new Float(results[0]);
    dist = (float) (dist * 0.000621371);
    distanceText.setText(dist.toString());
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.map_screen, menu);
    return true;
  }

}
