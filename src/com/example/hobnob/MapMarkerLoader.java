package com.example.hobnob;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

public class MapMarkerLoader extends AsyncTask<String, Void, LatLng >
{

  @Override
  protected LatLng doInBackground(String... arg0) 
  {
    String address = arg0[0];
    System.out.println("ADDRESS: " + address);
    final String requestPrefix = "http://maps.googleapis.com/maps/api/geocode/xml?address=";
    URL requestURL = null;
    try
    {
      requestURL = new URL(requestPrefix + URLEncoder.encode(address, "UTF-8") + "&sensor=false");
    }
    catch( MalformedURLException e )
    {
      e.printStackTrace();
    }
    catch( UnsupportedEncodingException e )
    {
      e.printStackTrace();
    }
    HttpURLConnection conn = null;
    try
    {
      conn = (HttpURLConnection) requestURL.openConnection();
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    float lat = 0, lng = 0;
    Document geocoderResultDocument = null;
    try 
    {
      conn.connect();
      InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
      geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(geocoderResultInputSource);
    }
    catch( IOException e )
    {
      e.printStackTrace();
    }
    catch( SAXException e )
    {
      e.printStackTrace();
    }
    catch( ParserConfigurationException e )
    {
      e.printStackTrace();
    } finally {
      conn.disconnect();
    }

    // prepare XPath
    XPath xpath = XPathFactory.newInstance().newXPath();

    // extract the result
    NodeList resultNodeList = null;

    // a) obtain the formatted_address field for every result
    try
    {
      resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result/formatted_address", geocoderResultDocument, XPathConstants.NODESET);
      for(int i=0; i<resultNodeList.getLength(); ++i) {
        System.out.println(resultNodeList.item(i).getTextContent());
      }

    }
    catch( XPathExpressionException e )
    {
      e.printStackTrace();
    }
    
    // b) extract the locality for the first result
    try
    {
      resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/address_component[type/text()='locality']/long_name", geocoderResultDocument, XPathConstants.NODESET);
    }
    catch( XPathExpressionException e1 )
    {
      e1.printStackTrace();
    }
    for(int i=0; i<resultNodeList.getLength(); ++i) {
      System.out.println(resultNodeList.item(i).getTextContent());
    }

    // c) extract the coordinates of the first result
    try
    {
      resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/geometry/location/*", geocoderResultDocument, XPathConstants.NODESET);
    }
    catch( XPathExpressionException e )
    {
      e.printStackTrace();
    }
    lat = Float.NaN;
    lng = Float.NaN;
    for(int i=0; i<resultNodeList.getLength(); ++i) {
      Node node = (Node) resultNodeList.item(i);
      if("lat".equals(((Node) node).getNodeName())) lat = Float.parseFloat(((Node) node).getTextContent());
      if("lng".equals(((Node) node).getNodeName())) lng = Float.parseFloat(((Node) node).getTextContent());
    }
    System.out.println("lat/lng=" + lat + "," + lng);
    
    return new LatLng(lat, lng);
  }
  
}
