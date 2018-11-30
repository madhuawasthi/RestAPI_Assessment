package com.qa.tests;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.Util.TestUtil;
import com.qa.base.TestBase;
import com.qa.country.CountryResponse;

import junit.framework.Assert;

public class GetAPITest extends TestBase 
{
	TestBase testbase;
	String serviceUrl;
	String apiUrl;
	String Url;
	CountryResponse restCountry;
	CloseableHttpResponse closablehttpresponse;
	SoftAssert softAssert=new SoftAssert();
 
	 
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException 
	{
		// call constructor 
		testbase = new TestBase();
		serviceUrl = prop.getProperty("Url");
		apiUrl = prop.getProperty("ServiceUrl");
	
		//https://istheapplestoredown.com/api/v1/status/worldwide
	 
		Url = serviceUrl + apiUrl;
		System.out.println("URL------"+Url);
		restCountry = new CountryResponse();
		restCountry.get(Url);
	 }
	
	
	@Test
	public void getTest() throws Exception {
		
		restCountry = new CountryResponse();
		closablehttpresponse=restCountry.get(Url);
		int statusCode = closablehttpresponse.getStatusLine().getStatusCode();
		System.out.println("Status_Code----> " + statusCode);
		// Validating status code
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200);
		
		String responseString = EntityUtils.toString(closablehttpresponse.getEntity(),"UTF-8");
		JSONObject responsejson = new JSONObject(responseString);
		System.out.println("Response json from API"  +responsejson);
		// Creating an object of TestUtil class and checking if any of country test has failed.
		TestUtil testUtilObj = new TestUtil();
		if (testUtilObj.getValueByJPath(responseString))
		{
			// One or multiple countries have failed. 
			softAssert.assertEquals(testUtilObj.lbCountryStatusWithY , false);
			System.out.println("Country name with status:'y' is:"+testUtilObj.lsFailedCountry   );
		}
		else
		{			
			System.out.println("Tests have passed ");
			
		}
		softAssert.assertAll();       
	}
	
}
