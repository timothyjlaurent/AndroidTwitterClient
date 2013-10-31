package com.codepath.apps.mytwitterapp;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.fragments.UserTimelineFragment;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	String screenName;
	UserTimelineFragment fr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadProfileInfo();
		fr = (UserTimelineFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
		if (screenName == null){
			fr.initializeUserTimeline();
		} else {
			fr.initializeUserTimeline(screenName);
		}
		
	}


	private void loadProfileInfo() {
		Intent i = getIntent();
		
		if ( i != null && i.getStringExtra("screenName") != null){
			screenName = i.getStringExtra("screenName");
			Log.d("DEBUG", "screenName " + screenName);
			MyTwitterApp.getRestClient().getUserInfo(screenName, 
					new JsonHttpResponseHandler(){
						@Override
						public void onSuccess(JSONObject json){
							User u = User.fromJson(json);
							getActionBar().setTitle("@" + u.getScreenName());
							Log.d("DEBUG", u.getScreenName());
							populateProfileHeader(u);
						}
			});
			
		}else{
		
			MyTwitterApp.getRestClient().getMyInfo(
					new JsonHttpResponseHandler(){
						@Override
						public void onSuccess(JSONObject json){
							User u = User.fromJson(json);
							u.getScreenName();
							getActionBar().setTitle("@" + u.getScreenName());
							populateProfileHeader(u);
						}
			});	
		}
	}

	protected void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline= (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount()+" Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	public void onGoBack(MenuItem item){
		finish();
	}
	public void onProfileView ( long id){
		
	}
	
}
