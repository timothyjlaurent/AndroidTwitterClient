package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONArray;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.util.Log;

public class MentionsFragment extends TweetsListFragment {
	long maxID;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initializeMentions();
	}	
	
	
	
	public void initializeMentions(){
	
		MyTwitterApp.getRestClient().getMentions(0, new JsonHttpResponseHandler(){
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
}
