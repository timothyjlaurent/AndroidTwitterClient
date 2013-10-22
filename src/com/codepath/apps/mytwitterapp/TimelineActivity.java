package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	PullToRefreshListView lvTweets;
	int max_id;
	ArrayList<Tweet> tweets;
	TweetsAdapter adapter;
	long maxID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets) ;
		
		
		initializeHomeTimeline();
		
		lvTweets.setOnScrollListener( new EndlessScrollListener(){
			@Override
			public void onLoadMore(int page, int totalItemsCount){
				refreshHomeTimeline(max_id);
			}
		});
		
        lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list contents
                // Make sure you call listView.onRefreshComplete()
                // once the loading is done. This can be done from here or any
                // place such as when the network request has completed successfully.
                initializeHomeTimeline();
                lvTweets.onRefreshComplete();
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	public void onComposeTweet(MenuItem item){
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		startActivityForResult(i, 1);
	}
	
	public void initializeHomeTimeline(){
		MyTwitterApp.getRestClient().getHomeTimeline(0, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				Log.d("DEBUG", jsonTweets.toString());
				tweets = Tweet.fromJson(jsonTweets);
				adapter = new TweetsAdapter(getBaseContext(),tweets);
				lvTweets.setAdapter(adapter);
				maxID = tweets.get(tweets.size()-1).getId()-1;
			}
		});
	}
	
	public void refreshHomeTimeline(int max_id){
		MyTwitterApp.getRestClient().getHomeTimeline(max_id, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				Log.d("DEBUG", jsonTweets.toString());
				tweets = Tweet.fromJson(jsonTweets);
				adapter.addAll(tweets);	
				maxID = tweets.get(tweets.size()-1).getId()-1;
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		  if (resultCode == RESULT_OK && data.getExtras().getString("value").equals("success")) {
//			     Toast.makeText(this, data.getExtras().getString("name"),
//			     Toast.LENGTH_SHORT).show();
			     adapter.clear();
			     refreshHomeTimeline(0);
		  }
	}
	
}
