package com.example.weike.ui;

import com.example.weike.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SearchActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		
	}
}
