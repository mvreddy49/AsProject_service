package com.wow.webapp.util;


import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RestClient.class);

	private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	private ArrayList<NameValuePair> headers =  new ArrayList<NameValuePair>();

	public String url;

	private int responseCode;
	private String message;

	private String response;

	public RestClient() {

	}

	public ArrayList<NameValuePair> getParams() {
		return params;
	}

	public void setParams(ArrayList<NameValuePair> params) {
		this.params = params;
	}

	public ArrayList<NameValuePair> getHeaders() {
		return headers;
	}

	public void setHeaders(ArrayList<NameValuePair> headers) {
		this.headers = headers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public String getRestApi() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet getRequest = new HttpGet(this.url);

			getRequest.addHeader("accept", "application/xml");
			
			HttpResponse response = httpClient.execute(getRequest);

			int statusCode = response.getStatusLine().getStatusCode();
			LOGGER.info("HTTP Status Code :::: " + statusCode);
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code -> "
						+ statusCode);
			}

			HttpEntity httpEntity = response.getEntity();
			String apiOutput = EntityUtils.toString(httpEntity);
			return apiOutput;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	public String postRestApi() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			
			HttpPost postRequest = new HttpPost(this.url);
			
			postRequest.addHeader("content-type",
					"application/x-www-form-urlencoded");

			if (getParams().size() > 0) {
				postRequest.setEntity(new UrlEncodedFormEntity(getParams()));
				LOGGER.info("Params are : " + getParams());
			}
			HttpResponse response = httpClient.execute(postRequest);

			int httpStatusCode = response.getStatusLine().getStatusCode();
			LOGGER.info("HTTP Status Code :::: " + httpStatusCode);

			if (httpStatusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code -> "
						+ httpStatusCode);
			}

			String apiOutput = EntityUtils.toString(response.getEntity());

			return apiOutput;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			httpClient.getConnectionManager().shutdown();

		}
		return null;
	}


	public static boolean putRestApi(final String url, final String xmlString) {
		final HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
					10000);

			HttpPut httpPut = new HttpPut(url);
			httpPut.addHeader("Accept", "text/plain");
			httpPut.addHeader("Content-Type", "application/xml;charset=UTF-8");
			httpPut.setHeader("Content-Encoding", "foo-1.0");
			
			StringEntity entity = new StringEntity(xmlString,"UTF-8");
		
			httpPut.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPut);
		
			int statusCode = response.getStatusLine().getStatusCode();
			LOGGER.info("statusCode ::: "+statusCode);
			return statusCode == 200 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return false;
	}
	
	public String deleteRestApi() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			// Define a postRequest request
			HttpDelete deleteRequest = new HttpDelete(this.url);
			
            
			// Set the API media type in http content-type header
			deleteRequest.addHeader("content-type",
					"application/x-www-form-urlencoded");

			
			// Send the request; It will immediately return the response in
			// HttpResponse object if any
			HttpResponse response = httpClient.execute(deleteRequest);

			// verify the valid error code first
			int httpStatusCode = response.getStatusLine().getStatusCode();
			LOGGER.info("HTTP Status Code :::: " + httpStatusCode);

			if (httpStatusCode != 200) {
				//postRequest.reset();
				throw new RuntimeException("Failed with HTTP error code -> "
						+ httpStatusCode);
			}

			String apiOutput = EntityUtils.toString(response.getEntity());
			// LOGGER.info("api response ::: "+apiOutput);

			//postRequest.reset();
			return apiOutput+":"+httpStatusCode;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Important: Close the connect
			httpClient.getConnectionManager().shutdown();

		}
		return null;
	}
	
}
