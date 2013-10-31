package com.codepath.apps.mytwitterapp;

import com.codepath.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytwitterapp.fragments.MentionsFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

public class TimelineActivity extends FragmentActivity implements TabListener {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home)
				.setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag("MentionsFragment").setIcon(R.drawable.ic_mentions)
				.setTabListener(this);
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
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
	

	public void onActivityResult(int requestCode, int resultCode, Intent data){
		  if (resultCode == RESULT_OK && data.getExtras().getString("value").equals("success")) {
//			     Toast.makeText(this, data.getExtras().getString("name"),
//			     Toast.LENGTH_SHORT).show();
//			     adapter.clear();
//			     refreshHomeTimeline(0);
//				FragmentManager manager= getSupportFragmentManager();
			
				//				manager.executePendingTransactions();
//				android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
//				fts.replace(R.id.frame_container, new HomeTimelineFragment());
//				fts.commit();  
		  }
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager= getSupportFragmentManager();
		
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if( tab.getTag() == "HomeTimelineFragment")	{
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		} else {
			fts.replace(R.id.frame_container, new MentionsFragment());
		}
		fts.commit();
		
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onProfileView( MenuItem mi ){
		Intent i = new Intent(this, ProfileActivity.class);
		startActivityForResult(i,1);
	}
	
	public void onProfileView ( long id){
		Intent i = new Intent(this, ProfileActivity.class);
		startActivityForResult(i,1);
	}
	
}
