package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeTimelineFragment extends TweetsListFragment {
	long maxID;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initializeHomeTimeline();
	}
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inf.inflate(R.layout.fragment_tweets_list, parent, false);
		
		lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets) ;
//		lvTweets.setAdapter(adapter);
		initializeHomeTimeline();
//		lvTweets.setOnScrollListener( new EndlessScrollListener(){
//			@Override
//			public void onLoadMore(int page, int totalItemsCount){
//				refreshHomeTimeline(max_id);
//			}
//		});
//		
//        lvTweets.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list contents
//                // Make sure you call listView.onRefreshComplete()
//                // once the loading is done. This can be done from here or any
//                // place such as when the network request has completed successfully.
//                initializeHomeTimeline();
//                lvTweets.onRefreshComplete();
//            }
//        });

		return v;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		initializeHomeTimeline();
		lvTweets.setOnScrollListener( new EndlessScrollListener(){
            @Override
            public void onLoadMore(int page, int totalItemsCount){
                    refreshHomeTimeline();
            }
    });
	}
	
	public void initializeHomeTimeline(){
		MyTwitterApp.getRestClient().getHomeTimeline(0, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsonTweets){
				Log.d("DEBUG", jsonTweets.toString());
				tweets = Tweet.fromJson(jsonTweets);
				adapter.clear();
                adapter.addAll(tweets);
//                lvTweets.setAdapter(adapter);
                maxID = tweets.get(tweets.size()-1).getId()-1;
			}
		});	
	}
	
	public void refreshHomeTimeline(){
		MyTwitterApp.getRestClient().getHomeTimeline((int)maxID, new JsonHttpResponseHandler(){
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
