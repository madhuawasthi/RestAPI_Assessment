package com.qa.country;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CountryResponse {
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient =HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse closablehttpresponse = httpClient.execute(httpget);
		return closablehttpresponse;	
		
	}
}
