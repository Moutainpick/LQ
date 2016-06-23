package com.example.tieda;

import com.example.slidemenu.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.about);
		// 侧滑菜单定位按钮
		findViewById(R.id.locationbutton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(AboutActivity.this, GPSLocation.class);
						AboutActivity.this.startActivity(intent);
					}
				});
		// 侧滑菜单查询按钮
		findViewById(R.id.findroadbutton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(AboutActivity.this, PathActivity.class);
						AboutActivity.this.startActivity(intent);
					}
				});
		findViewById(R.id.fengcaibutton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "努力开发中...",
								Toast.LENGTH_SHORT).show();
					}
				});
		findViewById(R.id.yishibutton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "努力开发中...",
								Toast.LENGTH_SHORT).show();
					}
				});
		findViewById(R.id.showviewbutton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(AboutActivity.this, MainActivity.class);
						AboutActivity.this.startActivity(intent);
					}
				});
	}

}
