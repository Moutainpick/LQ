package com.example.tieda;

import com.example.guide.GuideActivity;
import com.example.slidemenu.MainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class WelcomeActivity extends Activity {

	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private LinearLayout animLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		
		init();
	}

	@SuppressLint("WorldReadableFiles")
	private void init() {
		animLayout = (LinearLayout) this.findViewById(R.id.animLayout);
		leftLayout = (LinearLayout) this.findViewById(R.id.leftLayout);
		rightLayout = (LinearLayout) this.findViewById(R.id.rightLayout);

		animLayout.setBackgroundResource(R.drawable.main_bg);
		// ���ؿ��Ŷ���
		Animation leftOutAnimation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.translate_left);
		Animation rightOutAnimation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.translate_right);
		// �󲼾������ƶ�
		leftLayout.setAnimation(leftOutAnimation);
		// �Ҳ��������ƶ�
		rightLayout.setAnimation(rightOutAnimation);
		// ���ö���������
		leftOutAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Context useCount = null;
				try{
					useCount = createPackageContext("com.example.tieda",Context.CONTEXT_IGNORE_SECURITY);
				}
				catch (NameNotFoundException e){
					e.printStackTrace();
				}
				@SuppressWarnings("deprecation")
				SharedPreferences perfs = useCount.getSharedPreferences("count", Context.MODE_WORLD_READABLE);
				int count = perfs.getInt("count", 0);
				
				//��������ʱ�����ز���
				leftLayout.setVisibility(View.GONE);
				rightLayout.setVisibility(View.GONE);
				Intent intent = new Intent();
				if (count != 0){
					count++;
					intent.setClass(WelcomeActivity.this, MainActivity.class);
					startActivity(intent);
				}else{
					intent.setClass(WelcomeActivity.this, GuideActivity.class);
					startActivity(intent);
				}
				overridePendingTransition(0, 0);
				WelcomeActivity.this.finish();
			}
		});
	}
}
