package com.example.tieda;

import java.util.List;
import com.example.slidemenu.MainActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

 


public class GPSLocation extends Activity {

	static public double x = 0;
	static public double y = 0;
	static String result = null;
	static String Str = null;

	public float currentX, currentY;
	Canvas canvas;
	Bitmap resizedBitmap1;
	Paint paint;


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// setContentView(mGameView);
		setContentView(R.layout.gps_main);
		LinearLayout root = (LinearLayout) findViewById(R.id.root);
		final CircleView draw = new CircleView(this);
		draw.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.xuexiao5));// ���õı���ͼƬ
		draw.setMinimumHeight(300);
		draw.setMinimumWidth(500);
		root.addView(draw);
		LocationManager locationManager = (LocationManager) GPSLocation.this
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 10, 1,
					new TestLocationListener());
			Toast.makeText(getBaseContext(), "���ڶ�λ...", BIND_AUTO_CREATE)
					.show();
		} else {
			Toast.makeText(getBaseContext(), "���GPS", BIND_AUTO_CREATE).show();
		}
		findViewById(R.id.showviewbutton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(GPSLocation.this, MainActivity.class);
						GPSLocation.this.startActivity(intent);
					}
				});
		findViewById(R.id.fengcaibutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Ŭ��������...", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.yishibutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Ŭ��������...", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.about).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(GPSLocation.this, AboutActivity.class);
				GPSLocation.this.startActivity(intent);
			}
		});
		findViewById(R.id.findroadbutton).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(GPSLocation.this, PathActivity.class);
						GPSLocation.this.startActivity(intent);
					}

				});
	}

	private class TestLocationListener implements LocationListener {

		// @Override
		public void onLocationChanged(Location location) {
			x = location.getLongitude();
			y = location.getLatitude();
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
	}

	@SuppressLint("DrawAllocation")
	class CircleView extends View implements Runnable {
		public float currentX, currentY;
		Canvas canvas;
		Bitmap resizedBitmap1;
		Bitmap mGeo,mYun;
		Paint paint;

		DisplayMetrics dm = new DisplayMetrics();

		int view_w;
		int view_h;

		CircleView(Context context)// ���캯��
		{

			super(context);
			getWindowManager().getDefaultDisplay().getMetrics(dm); // ��ȡ�ֻ���Ļ�Ĵ�С
			view_w = dm.widthPixels;
			view_h = dm.heightPixels;
			paint = new Paint();
			paint.setColor(Color.RED);
			mGeo = ((BitmapDrawable) getResources().getDrawable(
					R.drawable.icon_geo3_1)).getBitmap();
			mYun = ((BitmapDrawable) getResources().getDrawable(
					R.drawable.yun)).getBitmap();
			resizedBitmap1 = Bitmap.createBitmap(view_w, view_h,
					Config.ARGB_8888);
			canvas = new Canvas(resizedBitmap1);// ���캯���У���resizedBitmap1��Ϊ��������
			new Thread(this).start();
		}

		@SuppressLint("DrawAllocation")
		@Override
		public void onDraw(Canvas canvas) {

			super.onDraw(canvas);

			Paint paint1 = new Paint();
			paint1.setColor(Color.BLUE);

			float a = (float) ((float) (((float) y - 38.08052716f) * 192944) + 145.89269);
			float b = (float) ((float) (((float) x - 114.50485552f) * 192944) + 1241.2784);
			// �������ͼ��
			a = a * view_w / 1080;
			b = b * view_h / 1920;
			//float c = (float) (a * 2.3 % 1080);
			float d = (float) (b * 0.5) % 1080;
			// װ�ض�λͼƬ��Դ
			canvas.drawBitmap(mGeo, a, b, null);
			//canvas.drawBitmap(mYun, c, 50, null);
			canvas.drawBitmap(mYun, d, 30, null);
			/*
			 * ���ƶ�λͼ�� Path path1 = new Path(); path1.moveTo(a, b);
			 * path1.lineTo(a + 40, b - 15); path1.lineTo(a + 40, b + 15);
			 * path1.close();// ��� canvas.drawPath(path1, paint1);
			 * canvas.drawCircle(a + 40, b, 15, paint1);
			 * paint1.setColor(Color.WHITE); canvas.drawCircle(a + 40,b, 5,
			 * paint1); paint1.setColor(Color.BLACK); canvas.drawText("У԰����",
			 * 200, 300, paint1); canvas.drawBitmap(resizedBitmap1, 0, 0,
			 * paint1);// ��ʾ�����ϵĲ���
			 */
		}

		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				// postInvalidateֱ�����߳��и��½���
				postInvalidate();
			}
		}
	}
}
