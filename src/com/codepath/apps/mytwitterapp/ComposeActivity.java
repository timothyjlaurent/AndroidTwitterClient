package com.codepath.apps.mytwitterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class ComposeActivity extends Activity {

	EditText etTweet;
	TextView tvCharRemain;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etTweet = (EditText) findViewById(R.id.etTweet);
		tvCharRemain = (TextView) findViewById(R.id.tvCharsRemain);
        tvCharRemain.setText("140 Characters Left");
		TextWatcher tw = new TextWatcher() {
            public void afterTextChanged(Editable s){
            	tvCharRemain.setText((140 - etTweet.getText().toString().length()) + " Characters Left");
            }
            public void  beforeTextChanged(CharSequence s, int start, int count, int after){
              // you can check for enter key here
            }
            public void  onTextChanged (CharSequence s, int start, int before,int count) { 	
            } 
        };

        etTweet.addTextChangedListener(tw);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void onSendTweet(View v){
		String tweet = etTweet.getText().toString();
		
		MyTwitterApp.getRestClient().postTweet(new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(String resp){
				Toast toast = Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT);
				toast.show();
				Intent data = new Intent();
				data.putExtra("value","success");
				setResult(RESULT_OK, data);
				finish();
			}
			@Override
			public void onFailure(Throwable e, String resp){
				Toast toast = Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG);
				toast.show();
				Log.d("ERROR", resp);	
				Intent data = new Intent();
				data.putExtra("value","failed");
				setResult(RESULT_OK, data);
				finish();
			}
		}, tweet);
	}
	
	public void onGoBack(MenuItem item){
		finish();
	}
}
