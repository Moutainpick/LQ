package com.example.tieda;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FindingRoads extends Activity {

	static public double x = 0;
	static public double y = 0;
	static String result = null;
	static String Str = null;

	public float currentX, currentY;
	Canvas canvas;
	Bitmap resizedBitmap1;
	Paint paint;


	@SuppressLint("HandlerLeak")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.roads_finding);
		LinearLayout finding = (LinearLayout)findViewById(R.id.finding);
		final CircleView draw = new CircleView(this);
		draw.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.xuexiao5));// 设置的背景图片
		draw.setMinimumHeight(300);
		draw.setMinimumWidth(500);
		finding.addView(draw);
		// 获取GPS
				LocationManager locationManager = (LocationManager) FindingRoads.this
						.getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
						0, new TestLocationListener());

				// 接收传来的消息并保存在Str
				try {
					new Handler() {
						@Override
						public void handleMessage(Message msg) {
							Str = msg.obj.toString();
						}
					};

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
	private class TestLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
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
		Bitmap resizedBitmap1,mGeo;
		Paint paint;

		DisplayMetrics dm = new DisplayMetrics();

		int view_w;
		int view_h;

		CircleView(Context context)// 构造函数
		{

			super(context);
			getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
			view_w = dm.widthPixels;
			view_h = dm.heightPixels;
			paint = new Paint();
			paint.setColor(Color.RED);
			mGeo = ((BitmapDrawable) getResources().getDrawable(
					R.drawable.icon_geo3_1)).getBitmap();
			resizedBitmap1 = Bitmap.createBitmap(view_w, view_h,
					Config.ARGB_8888);
			canvas = new Canvas(resizedBitmap1);// 构造函数中，将resizedBitmap1作为画布背景
			new Thread(this).start();
		}

		@SuppressLint("DrawAllocation")
		@Override
		public void onDraw(Canvas canvas) {

			super.onDraw(canvas);
			float a = (float) ((float) (((float) y - 38.08052716f) * 192944) + 145.89269);
			float b = (float) ((float) (((float) x - 114.50485552f) * 192944) + 1241.2784);
			// 画坐标点图标
			a = a * view_w / 1080;
			b = b * view_h / 1920;
			// 装载定位图片资源
			canvas.drawBitmap(mGeo, a, b, null);
			
			Point S = new Point();
			Point E = new Point();
			//canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			Graph G = new Graph(1);
			CreatG(G);

			Intent intent = getIntent();
			String start = intent.getStringExtra("start");
			String end = intent.getStringExtra("end");
			S.x = locate(start).x;
			S.y = locate(start).y;
			E.x = locate(end).x;
			E.y = locate(end).y;
			ShortPath(G, locate2(locate(start)),
					locate2(locate(end)));

			Paint paint2 = new Paint();
			paint2.setColor(Color.BLUE);
			canvas.drawCircle(S.x, S.y, 10, paint2);
			canvas.drawCircle(E.x, E.y, 10, paint2);
			canvas.drawBitmap(resizedBitmap1, 0, 0, paint2);// 时刻显示画布上的布景

		}

		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				// 使用postInvalidate可以直接在线程中更新界面
				postInvalidate();
			}
		}

		@SuppressLint("ClickableViewAccessibility")
		public boolean onTouchEvent(MotionEvent event) {
			
			Point S = new Point();
			Point E = new Point();

			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			Graph G = new Graph(1);
			CreatG(G);

			//setTitle("点击坐标 = " + "[" + currentX + "," + currentY + "]");
			Intent intent = getIntent();
			String start = intent.getStringExtra("start");
			String end = intent.getStringExtra("end");
			S.x = locate(start).x;
			S.y = locate(start).y;
			E.x = locate(end).x;
			E.y = locate(end).y;
			ShortPath(G, locate2(locate(start)),
					locate2(locate(end)));

			Paint paint2 = new Paint();
			paint2.setColor(Color.BLUE);
			canvas.drawCircle(S.x, S.y, 10, paint2);
			canvas.drawCircle(E.x, E.y, 10, paint2);

			// 返回true表明处理方法已经处理该事件
			return true;
		}

		class Graph {
			public String[] vexs;
			public int[][] arcs;
			public int vexnum, arcnum;

			public Graph(int a) {
				vexnum = 70;
				arcnum = 100;
				arcs = new int[100][100];
				vexs = new String[70];
			}
		}

		public void CreatG(Graph G) {

			G.vexs[0] = "0";
			G.vexs[1] = "1";
			G.vexs[2] = "2";
			G.vexs[3] = "3";
			G.vexs[4] = "4";
			G.vexs[5] = "5";
			G.vexs[6] = "6";
			G.vexs[7] = "7";
			G.vexs[8] = "8";
			G.vexs[9] = "9";
			G.vexs[10] = "10";
			G.vexs[11] = "ll";
			G.vexs[12] = "12";
			G.vexs[13] = "13";
			G.vexs[14] = "14";
			G.vexs[15] = "15";
			G.vexs[16] = "16";
			G.vexs[17] = "17";
			G.vexs[18] = "18";
			G.vexs[19] = "19";
			G.vexs[20] = "20";
			G.vexs[21] = "21";
			G.vexs[22] = "22";
			G.vexs[23] = "23";
			G.vexs[24] = "24";
			G.vexs[25] = "25";
			G.vexs[26] = "26";
			G.vexs[27] = "27";
			G.vexs[28] = "28";
			G.vexs[29] = "29";
			G.vexs[30] = "30";
			G.vexs[31] = "31";
			G.vexs[32] = "32";
			G.vexs[33] = "33";
			G.vexs[34] = "34";
			G.vexs[35] = "35";
			G.vexs[36] = "36";
			G.vexs[37] = "37";
			G.vexs[38] = "38";
			G.vexs[39] = "39";
			G.vexs[40] = "40";
			G.vexs[41] = "41";
			G.vexs[42] = "42";
			G.vexs[43] = "43";
			G.vexs[44] = "44";
			G.vexs[45] = "45";
			G.vexs[46] = "46";
			G.vexs[47] = "47";
			G.vexs[48] = "48";
			G.vexs[49] = "49";
			G.vexs[50] = "50";
			G.vexs[51] = "51";
			G.vexs[52] = "52";
			G.vexs[53] = "53";
			G.vexs[54] = "54";
			G.vexs[55] = "55";
			G.vexs[56] = "56";
			G.vexs[57] = "57";
			G.vexs[58] = "58";
			G.vexs[59] = "59";
			G.vexs[60] = "60";
			G.vexs[61] = "61";
			G.vexs[62] = "62";
			G.vexs[63] = "63";
			G.vexs[64] = "64";
			G.vexs[65] = "65";
			G.vexs[66] = "66";
			G.vexs[67] = "67";
			G.vexs[68] = "68";

			for (int j = 0; j < G.vexnum; j++)
				// 默认权值32767
				for (int m = 0; m < G.vexnum; m++)
					G.arcs[j][m] = 10000;
			
			G.arcs[0][1] = 88;
			G.arcs[1][16] = 118;
			G.arcs[1][2] = 114;
			G.arcs[2][3] = 106;
			G.arcs[3][4] = 48;		
			G.arcs[3][18] = 136;
			G.arcs[4][5] = 134;
			G.arcs[5][6] = 133;
			G.arcs[5][7] = 131;
			G.arcs[7][8] = 185;		
			G.arcs[7][21] = 120;
			G.arcs[8][9] = 72;
			G.arcs[9][10] = 117;
			G.arcs[10][11] = 74;
			G.arcs[11][12] = 76;		
			G.arcs[12][13] = 69;
			G.arcs[8][13] = 264;
			G.arcs[13][14] = 76;
			G.arcs[14][15] = 265;
			G.arcs[14][25] = 64;
			G.arcs[15][26] = 57;		
			G.arcs[16][17] = 50;
			G.arcs[16][31] = 124;
			G.arcs[17][18] = 149;
			G.arcs[18][33]=138;
			G.arcs[18][19] = 136;		
			G.arcs[19][20] = 117;
			G.arcs[20][21] = 50;
			G.arcs[20][35] = 125;
			G.arcs[21][22] = 113;
			G.arcs[22][23] = 135;		
			G.arcs[23][24] = 183;
			G.arcs[24][25] = 27;
			G.arcs[24][28] = 77;
			G.arcs[25][26] = 261;
			G.arcs[26][27]=44;		
			G.arcs[27][29] = 91;
			G.arcs[28][38] = 89;
			G.arcs[29][40] = 76;
			G.arcs[30][31] = 158;
			G.arcs[31][32] = 100;		
			G.arcs[32][33]=114;	
			G.arcs[33][45] = 178;
			G.arcs[33][34] = 83;
			G.arcs[34][35] = 173;
			G.arcs[35][36] = 95;			
			G.arcs[35][46] = 188;
			G.arcs[36][37] = 201;
			G.arcs[37][38]= 122;
			G.arcs[38][41] = 86;
			G.arcs[38][39] = 181;
			G.arcs[39][42] = 97;
			G.arcs[39][40] = 170;
			G.arcs[40][43] = 86;
			G.arcs[41][42] = 191;
			G.arcs[41][50] = 91;			
			G.arcs[42][51] = 85;
			G.arcs[42][43] = 173;
			G.arcs[43][52] = 97;
			G.arcs[44][45] = 229;
			G.arcs[45][56] = 126;			
			G.arcs[45][46] = 245;
			G.arcs[46][47] = 172;
			G.arcs[46][57] = 117;
			G.arcs[47][48] = 72;
			G.arcs[48][49] = 76;			
			G.arcs[47][50] = 258;
			G.arcs[50][53] = 69;
			G.arcs[50][51] = 172;
			G.arcs[51][54] = 78;
			G.arcs[51][52] = 180;			
			G.arcs[52][55] = 67;
			G.arcs[53][61] = 141;
			G.arcs[53][54] = 189;
			G.arcs[54][55] = 174;
			G.arcs[55][58] = 69;			
			G.arcs[56][57] = 249;
			G.arcs[56][65] = 198;
			G.arcs[57][59] = 120;
			G.arcs[58][64] = 75;
			G.arcs[59][67] = 94;			
			G.arcs[60][61] = 110;
			G.arcs[61][68] = 120;
			G.arcs[61][62] = 99;
			G.arcs[62][63] = 194;
			G.arcs[63][64] = 77;
			G.arcs[65][66] = 114;
			G.arcs[66][67] = 130;
			G.arcs[67][68]=409;
			
			int a = 1920 * 1920 + 1080 * 1080;
			for (int j = 0; j < G.vexnum; j++) {
				for (int m = 0; m < G.vexnum; m++){
					G.arcs[m][j] =(int) (G.arcs[m][j]/Math.sqrt(a));
					G.arcs[m][j] = G.arcs[j][m];
				}
			}

		}

		public int[] S = new int[100];
		public int[] D = new int[100];
		public int[] path = new int[100];

		public void ShortPath(Graph G, int v0, int v2) {

			int n = 0;
			int v;

			n = G.vexnum;
			for (v = 0; v < n; v++) {
				S[v] = 0;
				D[v] = G.arcs[v0][v];
				if (D[v] < 10000) {
					path[v] = v0;
				} else {
					path[v] = -1;
				}

			}
			S[v0] = 1;
			D[v0] = 0;
			for (int i = 1; i < n; i++) {
				int min = 10000;
				for (int w = 0; w < n; w++) {
					if (D[w] < min && S[w] != 1) {
						v = w;
						min = D[w];
					}
				}
				S[v] = 1;
				for (int w = 0; w < n; w++) {
					if (S[w] != 1 && (D[v] + G.arcs[v][w]) < D[w]) {
						D[w] = D[v] + G.arcs[v][w];
						path[w] = v;
					}
				}

			}

			Log.e("str", G.vexs[v2] + "->");
			String str = "";
			while (true) {
				if (path[v2] != v0 && v2 != v0) {
					str = str + G.vexs[path[v2]] + "->";
					paint.setStrokeWidth(10);
					canvas.drawLine(locate(G.vexs[v2]).x, locate(G.vexs[v2]).y,
							locate(G.vexs[path[v2]]).x,
							locate(G.vexs[path[v2]]).y, paint);
					v2 = path[v2];
				} else {
					if (v2 != v0) {
						canvas.drawLine(locate(G.vexs[v2]).x,
								locate(G.vexs[v2]).y,
								locate(G.vexs[path[v2]]).x,
								locate(G.vexs[path[v2]]).y, paint);

						str = str + G.vexs[path[v2]] + "";
						Log.e("str", str);
					}
					break;
				}

			}
		}

		public Point locate(String st) {
			if (st.equals("0")) {
				return new Point(340 * view_w/1080, 103 * view_h/1920);
			}
			if (st.equals("1")) {
				return new Point(320 * view_w/1080, 189 * view_h/1920);
			}
			if (st.equals("2")) {
				return new Point(291 * view_w/1080, 300 * view_h/1920);
			}
			if (st.equals("3")) {
				return new Point(270 * view_w/1080, 404 * view_h/1920);
			}
			if (st.equals("4")) {
				return new Point(224 * view_w/1080, 391 * view_h/1920);
			}
			if (st.equals("5")) {
				return new Point(199 * view_w/1080, 523 * view_h/1920);
			}
			if (st.equals("6")) {
				return new Point(71 * view_w/1080, 486 * view_h/1920);
			}
			if (st.equals("7")) {
				return new Point(182 * view_w/1080, 653 * view_h/1920);
			}
			if (st.equals("8")) {
				return new Point(135 * view_w/1080, 832 * view_h/1920);
			}
			if (st.equals("9")) {
				return new Point(63 * view_w/1080, 823 * view_h/1920);
			}
			if (st.equals("10")) {
				return new Point(25 * view_w/1080, 934 * view_h/1920);
			}
			if (st.equals("11")) {
				return new Point(17 * view_w/1080, 1008 * view_h/1920);
			}
			if (st.equals("l2")) {
				return new Point(15 * view_w/1080, 1084 * view_h/1920);
			}
			if (st.equals("13")) {
				return new Point(84 * view_w/1080, 1091 * view_h/1920);
			}
			if (st.equals("14")) {
				return new Point(157 * view_w/1080, 1111 * view_h/1920);
			}
			if (st.equals("15")) {
				return new Point(105 * view_w/1080, 1371 * view_h/1920);
			}
			if (st.equals("16")) {
				return new Point(435 * view_w/1080, 217 * view_h/1920);
			}
			if (st.equals("17")) {
				return new Point(426 * view_w/1080, 266 * view_h/1920);
			}
			if (st.equals("18")) {
				return new Point(406 * view_w/1080, 414 * view_h/1920);
			}
			if (st.equals("19")) {
				return new Point(367 * view_w/1080, 544 * view_h/1920);
			}
			if (st.equals("20")) {
				return new Point(350 * view_w/1080, 660 * view_h/1920);
			}
			if (st.equals("21")) {
				return new Point(301 * view_w/1080, 669 * view_h/1920);
			}
			if (st.equals("22")) {
				return new Point(279 * view_w/1080, 780 * view_h/1920);
			}
			if (st.equals("23")) {
				return new Point(254 * view_w/1080, 913 * view_h/1920);
			}
			if (st.equals("26")) {
				return new Point(162 * view_w/1080, 1374 * view_h/1920);
			}
			if (st.equals("24")) {
				return new Point(228 * view_w/1080, 1094 * view_h/1920);
			}
			if (st.equals("25")) {
				return new Point(220 * view_w/1080, 1120 * view_h/1920);
			}
			if (st.equals("27")) {
				return new Point(159 * view_w/1080, 1418 * view_h/1920);
			}
			if (st.equals("28")) {
				return new Point(305 * view_w/1080, 1102 * view_h/1920);
			}
			if (st.equals("29")) {
				return new Point(246 * view_w/1080, 1443 * view_h/1920);
			}
			if (st.equals("30")) {
				return new Point(602 * view_w/1080, 89 * view_h/1920);
			}
			if (st.equals("31")) {
				return new Point(557 * view_w/1080, 241 * view_h/1920);
			}
			if (st.equals("32")) {
				return new Point(528 * view_w/1080, 337 * view_h/1920);
			}
			if (st.equals("33")) {
				return new Point(540 * view_w/1080, 450 * view_h/1920);
			}
			if (st.equals("34")) {
				return new Point(512 * view_w/1080, 528 * view_h/1920);
			}
			if (st.equals("35")) {
				return new Point(470 * view_w/1080, 696 * view_h/1920);
			}
			if (st.equals("36")) {
				return new Point(461 * view_w/1080, 791 * view_h/1920);
			}
			if (st.equals("37")) {
				return new Point(413 * view_w/1080, 987 * view_h/1920);
			}
			if (st.equals("38")) {
				return new Point(394 * view_w/1080, 1108 * view_h/1920);
			}
			if (st.equals("39")) {
				return new Point(354 * view_w/1080, 1285 * view_h/1920);
			}
			if (st.equals("40")) {
				return new Point(322 * view_w/1080, 1452 * view_h/1920);
			}
			if (st.equals("41")) {
				return new Point(480 * view_w/1080, 1109 * view_h/1920);
			}
			if (st.equals("42")) {
				return new Point(450 * view_w/1080, 1298 * view_h/1920);
			}
			if (st.equals("43")) {
				return new Point(407 * view_w/1080, 1466 * view_h/1920);
			}
			if (st.equals("44")) {
				return new Point(774 * view_w/1080, 253 * view_h/1920);
			}
			if (st.equals("45")) {
				return new Point(717 * view_w/1080, 475 * view_h/1920);
			}
			if (st.equals("46")) {
				return new Point(657 * view_w/1080, 713 * view_h/1920);
			}
			if (st.equals("47")) {
				return new Point(617 * view_w/1080, 880 * view_h/1920);
			}
			if (st.equals("48")) {
				return new Point(681 * view_w/1080, 912 * view_h/1920);
			}
			if (st.equals("49")) {
				return new Point(660 * view_w/1080, 985 * view_h/1920);
			}
			if (st.equals("50")) {
				return new Point(568 * view_w/1080, 1134 * view_h/1920);
			}
			if (st.equals("51")) {
				return new Point(535 * view_w/1080, 1303 * view_h/1920);
			}
			if (st.equals("52")) {
				return new Point(503 * view_w/1080, 1480 * view_h/1920);
			}
			if (st.equals("53")) {
				return new Point(637 * view_w/1080, 1142 * view_h/1920);
			}
			if (st.equals("54")) {
				return new Point(609 * view_w/1080, 1329 * view_h/1920);
			}
			if (st.equals("55")) {
				return new Point(568 * view_w/1080, 1498 * view_h/1920);
			}
			if (st.equals("56")) {
				return new Point(841 * view_w/1080, 495 * view_h/1920);
			}
			if (st.equals("57")) {
				return new Point(772 * view_w/1080, 734 * view_h/1920);
			}
			if (st.equals("58")) {
				return new Point(637 * view_w/1080, 1501 * view_h/1920);
			}
			if (st.equals("59")) {
				return new Point(892 * view_w/1080, 742 * view_h/1920);
			}
			if (st.equals("60")) {
				return new Point(813 * view_w/1080, 1043 * view_h/1920);
			}
			if (st.equals("61")) {
				return new Point(778 * view_w/1080, 1147 * view_h/1920);
			}
			if (st.equals("62")) {
				return new Point(766 * view_w/1080, 1245 * view_h/1920);
			}
			if (st.equals("63")) {
				return new Point(728 * view_w/1080, 1436 * view_h/1920);
			}
			if (st.equals("64")) {
				return new Point(711 * view_w/1080, 1511 * view_h/1920);
			}
			if (st.equals("65")) {
				return new Point(1036 * view_w/1080, 532 * view_h/1920);
			}
			if (st.equals("66")) {
				return new Point(1005 * view_w/1080, 642 * view_h/1920);
			}
			if (st.equals("67")) {
				return new Point(982 * view_w/1080, 770 * view_h/1920);
			}
			if (st.equals("68")) {
				return new Point(896 * view_w/1080, 1170 * view_h/1920);
			} else {
				return new Point(0, 0);
			}

		}

		public int locate2(Point p) {
			if (p.x == 340 * view_w/1080 && p.y == 103 * view_h/1920) {
				return 0;
			} else if (p.x == 320 * view_w/1080 && p.y == 189 * view_h/1920)
			{
				return 1;
			} else if (p.x == 291 * view_w/1080 && p.y == 300 * view_h/1920)
			{
				return 2;
			} else if (p.x == 270 * view_w/1080 && p.y == 404 * view_h/1920)
			{
				return 3;
			} else if (p.x == 224 * view_w/1080 && p.y == 391 * view_h/1920)
			{
				return 4;
			} else if (p.x == 199 * view_w/1080 && p.y == 523 * view_h/1920)
			{
				return 5;
			} else if (p.x == 71 * view_w/1080 && p.y == 486 * view_h/1920)
			{
				return 6;
			} else if (p.x == 182 * view_w/1080 && p.y == 653 * view_h/1920)
			{
				return 7;
			} else if (p.x == 135 * view_w/1080 && p.y == 832 * view_h/1920)
			{
				return 8;
			} else if (p.x == 63 * view_w/1080 && p.y == 823 * view_h/1920)
			{
				return 9;
			} else if (p.x == 25 * view_w/1080 && p.y == 934 * view_h/1920)
			{
				return 10;
			} else if (p.x == 17 * view_w/1080 && p.y == 1008 * view_h/1920)
			{
				return 11;
			} else if (p.x == 15 * view_w/1080 && p.y == 1084 * view_h/1920)
			{
				return 12;
			} else if (p.x == 84 * view_w/1080 && p.y == 1091 * view_h/1920)
			{
				return 13;
			} else if (p.x == 157 * view_w/1080 && p.y == 1111 * view_h/1920)
			{
				return 14;
			} else if (p.x == 105 * view_w/1080 && p.y == 1371 * view_h/1920)
			{
				return 15;
			} else if (p.x == 435 * view_w/1080 && p.y == 217 * view_h/1920)
			{
				return 16;
			} else if (p.x == 426 * view_w/1080 && p.y == 266 * view_h/1920)
			{
				return 17;
			} else if (p.x == 406 * view_w/1080 && p.y == 414 * view_h/1920)
			{
				return 18;
			} else if (p.x == 367 * view_w/1080 && p.y == 544 * view_h/1920)
			{
				return 19;
			} else if (p.x == 350 * view_w/1080 && p.y == 660 * view_h/1920)
			{
				return 20;
			} else if (p.x == 301 * view_w/1080 && p.y == 669 * view_h/1920)
			{
				return 21;
			} else if (p.x == 279 * view_w/1080 && p.y == 780 * view_h/1920)
			{
				return 22;
			} else if (p.x == 254 * view_w/1080 && p.y == 913 * view_h/1920)
			{
				return 23;
			} else if (p.x == 162 * view_w/1080 && p.y == 1374 * view_h/1920)
			{
				return 24;
			} else if (p.x == 228 * view_w/1080 && p.y == 1094 * view_h/1920)
			{
				return 25;
			} else if (p.x == 220 * view_w/1080 && p.y == 1120 * view_h/1920)
			{
				return 26;
			} else if (p.x == 159 * view_w/1080 && p.y == 1418 * view_h/1920)
			{
				return 27;
			} else if (p.x == 305 * view_w/1080 && p.y == 1102 * view_h/1920)
			{
				return 28;
			} else if (p.x == 246 * view_w/1080 && p.y == 1443 * view_h/1920)
			{
				return 29;
			} else if (p.x == 602 * view_w/1080 && p.y == 89 * view_h/1920) 
			{
				return 30;
			} else if (p.x == 557 * view_w/1080 && p.y == 241 * view_h/1920)
			{
				return 31;
			} else if (p.x == 528 * view_w/1080 && p.y == 337 * view_h/1920)
			{
				return 32;
			} else if (p.x == 540 * view_w/1080 && p.y == 450 * view_h/1920)
			{
				return 33;
			} else if (p.x == 512 * view_w/1080 && p.y == 528 * view_h/1920)
			{
				return 34;
			} else if (p.x == 470 * view_w/1080 && p.y == 696 * view_h/1920)
			{
				return 35;
			} else if (p.x == 461 * view_w/1080 && p.y == 791 * view_h/1920)
			{
				return 36;
			} else if (p.x == 413 * view_w/1080 && p.y == 987 * view_h/1920)
			{
				return 37;
			} else if (p.x == 394 * view_w/1080 && p.y == 1108 * view_h/1920)
			{
				return 38;
			} else if (p.x == 354 * view_w/1080 && p.y == 1285 * view_h/1920)
			{
				return 39;
			} else if (p.x == 322 * view_w/1080 && p.y == 1452 * view_h/1920)
			{
				return 40;
			} else if (p.x == 480 * view_w/1080 && p.y == 1109 * view_h/1920)
			{
				return 41;
			} else if (p.x == 450 * view_w/1080 && p.y == 1298 * view_h/1920)
			{
				return 42;
			} else if (p.x == 407 * view_w/1080 && p.y == 1466 * view_h/1920)
			{
				return 43;
			} else if (p.x == 774 * view_w/1080 && p.y == 253 * view_h/1920)
			{
				return 44;
			} else if (p.x == 717 * view_w/1080 && p.y == 475 * view_h/1920)
			{
				return 45;
			} else if (p.x == 657 * view_w/1080 && p.y == 713 * view_h/1920)
			{
				return 46;
			} else if (p.x == 617 * view_w/1080 && p.y == 880 * view_h/1920)
			{
				return 47;
			} else if (p.x == 681 * view_w/1080 && p.y == 912 * view_h/1920)
			{
				return 48;
			} else if (p.x == 660 * view_w/1080 && p.y == 985 * view_h/1920)
			{
				return 49;
			} else if (p.x == 568 * view_w/1080 && p.y == 1134 * view_h/1920)
			{
				return 50;
			} else if (p.x == 535 * view_w/1080 && p.y == 1303 * view_h/1920)
			{
				return 51;
			} else if (p.x == 503 * view_w/1080 && p.y == 1480 * view_h/1920)
			{
				return 52;
			} else if (p.x == 637 * view_w/1080 && p.y == 1142 * view_h/1920)
			{
				return 53;
			} else if (p.x == 609 * view_w/1080 && p.y == 1329 * view_h/1920)
			{
				return 54;
			} else if (p.x == 568 * view_w/1080 && p.y == 1498 * view_h/1920)
			{
				return 55;
			} else if (p.x == 841 * view_w/1080 && p.y == 495 * view_h/1920)
			{
				return 56;
			} else if (p.x == 772 * view_w/1080 && p.y == 734 * view_h/1920)
			{
				return 57;
			} else if (p.x == 637 * view_w/1080 && p.y == 1501 * view_h/1920)
			{
				return 58;
			} else if (p.x == 892 * view_w/1080 && p.y == 742 * view_h/1920)
			{
				return 59;
			} else if (p.x == 813 * view_w/1080 && p.y == 1043 * view_h/1920)
			{
				return 60;
			} else if (p.x == 778 * view_w/1080 && p.y == 1147 * view_h/1920)
			{
				return 61;
			} else if (p.x == 766 * view_w/1080 && p.y == 1245 * view_h/1920)
			{
				return 62;
			} else if (p.x == 728 * view_w/1080 && p.y == 1436 * view_h/1920)
			{
				return 63;
			} else if (p.x == 711 * view_w/1080 && p.y == 1511 * view_h/1920)
			{
				return 64;
			} else if (p.x == 1036 * view_w/1080 && p.y == 532 * view_h/1920)
			{
				return 65;
			} else if (p.x == 1005 * view_w/1080 && p.y == 642 * view_h/1920)
			{
				return 66;
			} else if (p.x == 982 * view_w/1080 && p.y == 770 * view_h/1920)
			{
				return 67;
			} else if (p.x == 896 * view_w/1080 && p.y == 1170 * view_h/1920)
			{
				return 68;
			} else {
				return 0;
			}

		}

		String[] a = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				"31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
				"41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
				"51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
				"61", "62", "63", "64", "65", "66", "67", "68" };

		public Point shortestPoint(Point p, Graph G) {
			double min = 10000;
			int k = 0;
			for (int i = 0; i < a.length; i++) {
				double d = Math.sqrt((p.x - locate(a[i]).x)
						* (p.x - locate(a[i]).x) + (p.y - locate(a[i]).y)
						* (p.y - locate(a[i]).y));
				if (d < min) {
					min = d;
					k = i;
				}
			}
			return locate(a[k]);
		}
	}
}

