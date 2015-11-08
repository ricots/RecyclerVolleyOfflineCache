package com.example.big.RecyclerVolleyOfflineCache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.AppBarLayout;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.example.big.RecyclerVolleyOfflineCache.view.adapters.TabsAdapter;
import com.example.big.RecyclerVolleyOfflineCache.view.animations.HeadAnimateListener;

public class MainActivity extends AppCompatActivity {
	RequestQueue queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.meet_tabs);
		ViewPager viewPager = (ViewPager) findViewById(R.id.meet_viewpager);

		AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.meet_appbar);

		RelativeLayout relLogo = (RelativeLayout) findViewById(R.id.meet_relative_logo);
		appbarLayout.addOnOffsetChangedListener(new HeadAnimateListener(appbarLayout, relLogo));

		viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);


	}


}