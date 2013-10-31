package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	String screenName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}	
	
	
	
	public void initializeUserTimeline(){
		Log.d("DEBUG", "In User Timeline");
		MyTwitterApp.getRestClient().getUserTimeline( maxID, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				Log.d("DEBUG", jsonTweets.toString());
				tweets = Tweet.fromJson(jsonTweets);
				adapter.clear();
                adapter.addAll(tweets);
                maxID = tweets.get(tweets.size()-1).getId()-1;
				}
			});
		}
	
	public void initializeUserTimeline(String screenName){
		this.screenName = screenName; 
		Log.d("DEBUG", "In User Timeline");
		MyTwitterApp.getRestClient().getUserTimeline((int)maxID, screenName, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				Log.d("DEBUG", jsonTweets.toString());
				tweets = Tweet.fromJson(jsonTweets);
				adapter.clear();
                adapter.addAll(tweets);
                maxID = tweets.get(tweets.size()-1).getId()-1;
				}
			});
		}
	
	public void refreshUserTimeline(){
		if (screenName == null){
			MyTwitterApp.getRestClient().getUserTimeline( maxID, new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONArray jsonTweets){
					Log.d("DEBUG", jsonTweets.toString());
					tweets = Tweet.fromJson(jsonTweets);
	                adapter.addAll(tweets);
	                maxID = tweets.get(tweets.size()-1).getId()-1;
					}
				});
		} else{
		MyTwitterApp.getRestClient().getUserTimeline( maxID, screenName, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				Log.d("DEBUG", jsonTweets.toString());
				tweets = Tweet.fromJson(jsonTweets);
                adapter.addAll(tweets);
                maxID = tweets.get(tweets.size()-1).getId()-1;
				}
			});
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		lvTweets.setOnScrollListener( new EndlessScrollListener(){
            @Override
            public void onLoadMore(int page, int totalItemsCount){
                    refreshUserTimeline();
            }
    });
	}
}