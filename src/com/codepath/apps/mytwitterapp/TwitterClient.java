package com.codepath.apps.mytwitterapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "fIrVggCE0OcXFlzZBPlDyg";       // Change this
    public static final String REST_CONSUMER_SECRET = "YJ4KEBZ1L7c0Bdkza6Rfyr7E0GX6u5fNMcZE10xm0Yk"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getUserTimeline (long maxID,JsonHttpResponseHandler handler) {
    	String url = getApiUrl ("statuses/user_timeline.json");
    	RequestParams params = new RequestParams();
    	if (maxID != 0 ){
    		params.put("max_id", String.valueOf(maxID));
    	}
    	client.get(url,  null, handler);
    }
    
    // statuses/mentions_timeline.json
    public void getMentions(int maxID, AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	RequestParams params = new RequestParams();
    	if (maxID != 0 ){
    		params.put("max_id", String.valueOf(maxID));
    	}
    	params.put("count", String.valueOf(25));
    	client.get(url, params, handler);
    }
    
    //
    public void getMyInfo(AsyncHttpResponseHandler handler){
    	String url = getApiUrl("account/verify_credentials.json");
    	client.get(url,  null, handler);
    }
    
    
    public void getHomeTimeline(int maxID, AsyncHttpResponseHandler handler){
    	String url = getApiUrl("statuses/home_timeline.json");
    	RequestParams params = new RequestParams();
    	if (maxID != 0 ){
    		params.put("max_id", String.valueOf(maxID));
    	}
    	params.put("count", String.valueOf(25));
    	client.get(url, params, handler);
    }
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    
    public void postTweet( AsyncHttpResponseHandler handler, String tweet){
    	String apiUrl = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", tweet);
    	client.post(apiUrl,params, handler);
    }

	public void getUserInfo(String screenName,
			JsonHttpResponseHandler jsonHttpResponseHandler) {
		String url = getApiUrl("users/show.json");
		Log.d("DEBUG", "TwitterClient " +screenName);
		Log.d("DEBUG", url);
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		client.get(url, params, jsonHttpResponseHandler);
		
	}

	public void getUserTimeline(long maxID, String screenName, JsonHttpResponseHandler handler) {
		String url = getApiUrl ("statuses/user_timeline.json");
    	RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		if (maxID != 0 ){
    		params.put("max_id", String.valueOf(maxID));
    	}
		client.get(url,  params, handler);
		
	}
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}