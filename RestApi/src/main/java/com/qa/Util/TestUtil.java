package com.qa.Util;
import java.util.Iterator; 
import java.util.Hashtable;

import org.json.JSONObject; 


public class TestUtil 
{
	public Hashtable <String, Hashtable> countryHash =  new Hashtable<String, Hashtable>();
	public String lsFailedCountry = "";
	public boolean lbCountryStatusWithY = false;
	/*
	Reading the key nodes of JSON response and returning if test has failed.
	*/
	public boolean getValueByJPath(String responsejson) throws Exception
	{

		JSONObject jsonObj = new JSONObject(responsejson);
		System.out.println("responsejson->"+responsejson);	
		
		Iterator<String> keys = jsonObj.keys();

		while(keys.hasNext()) {
		    String key = keys.next();
		    if (jsonObj.get(key) instanceof JSONObject) 
		    {
		    	JSONObject countryObje = jsonObj.getJSONObject(key);
		    	iterateCountires(key.toString(),countryObje);
		    }
		}		
		return  lbCountryStatusWithY;
	}
	/*	
 	Checking each node and putting the failed countries in hashtable with all details.
 	Populated hashtable can be used for further processing
	taking one key at a time.
	*/
	
	public void iterateCountires(String Key, JSONObject valJsonObj)
	{

		Iterator<String> keys = valJsonObj.keys();
		Hashtable <String, String> countryCollection =  new Hashtable<String, String>();
		
		String lsLastValue = "";
		while(keys.hasNext()) 
		{
		    String lsKey = keys.next();
		    String lsValue = valJsonObj.get(lsKey).toString();
		    // if any country has failed then the overall test has failed.
		    if (lsValue.equals("y"))
    		{		    	
		    	lsFailedCountry = lsLastValue + " , "+  lsFailedCountry;
		    	lbCountryStatusWithY = true;
    		}
		    countryCollection.put(lsKey,lsValue);
		    
		    lsLastValue = lsValue;
		}		
		countryHash.put(Key, countryCollection);
	}	
}
