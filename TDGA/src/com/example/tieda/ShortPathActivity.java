package com.example.tieda;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tieda.FindingRoads;
import com.example.utils.AdjList;
import com.example.utils.AdjMatrix;
import com.example.utils.PathUtils;

public class ShortPathActivity extends Activity {
	String start;
	String end;
	String[] wei = new String[MAXSIZE];
	TextView path_short;
	TextView path_short_name;
	final static int INT = 32767;
	final static int MAXSIZE = 100;
	Button mapbutton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.short_path);
		mapbutton = (Button)findViewById(R.id.mapbutton);
		final Intent intent = getIntent();
		start = intent.getStringExtra("start");
		end = intent.getStringExtra("end");

		mapbutton.setOnClickListener (new OnClickListener(){
			String S = null;
			String E = null;
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(start.equals("学校大门") ){
					S= "6";
				}
				else if(start.equals("第一教学楼")){
					S="5";
				}
				else if(start.equals("工程训练中心")){
					S="0";
				}
				else if(start.equals("第二教学楼")){
					S="2";
				}
				else if(start.equals("第三教学楼")){
					S="17";
				}
				else if(start.equals("体育馆")){
					S="32";
				}
				else if(start.equals("沁园")){
					S="19";
				}
				else if(start.equals("游泳教学场")){
					S="45";
				}
				else if(start.equals("操场")){
					S="30";
				}
				else if(start.equals("图书馆")){
					S="34";
				}
				else if(start.equals("信息楼")){
					S="46";
				}
				else if(start.equals("第九实验楼")){
					S="59";
				}
				else if(start.equals("交通楼")){
					S="59";
				}
				else if(start.equals("樱花林")){
					S="46";
				}
				else if(start.equals("超市")){
					S="48";
				}
				else if(start.equals("基础教学楼")){
					S="37";
				}
				else if(start.equals("九栋宿舍楼")){
					S="54";
				}
				else if(start.equals("学二食堂")){
					S="55";
				}
				else if(start.equals("泽园")){
					S="28";
				}
				else if(start.equals("综合餐厅")){
					S="29";
				}
				else if(start.equals("春晖楼")){
					S="14";
				}
				else if(start.equals("开元楼")){
					S="12";
				}
				else if(start.equals("翠园")){
					S="22";
				}
				else if(start.equals("医院")){
					S="23";
				}
				else if(start.equals("学校小门")){
					S="11";
				}
				else if(start.equals("大礼堂")){
					S="28";
				}

				if(end.equals("学校大门")){
					E="6";
				}else if(end.equals("第一教学楼")){
					E="5";
				}else if(end.equals("工程训练中心")){
					E="0";
				}else if(end.equals("第二教学楼")){
					E="2";
				}else if(end.equals("第三教学楼")){
					E="17";
				}else if(end.equals("体育馆")){
					E="32";
				}else if(end.equals("沁园")){
					E="19";
				}else if(end.equals("游泳教学场")){
					E="45";
				}else if(end.equals("操场")){
					E="30";
				}else if(end.equals("图书馆")){
					E="34";
				}else if(end.equals("信息楼")){
					E="46";
				}else if(end.equals("第九实验楼")){
					E="59";
				}else if(end.equals("交通楼")){
					E="59";
				}else if(end.equals("樱花林")){
					E="46";
				}else if(end.equals("超市")){
					E="48";
				}else if(end.equals("基础教学楼")){
					E="37";
				}else if(end.equals("九栋宿舍楼")){
					E="54";
				}else if(end.equals("学二食堂")){
					E="55";
				}else if(end.equals("泽园")){
					E="28";
				}else if(end.equals("综合餐厅")){
					E="29";
				}else if(end.equals("春晖楼")){
					E="14";
				}else if(end.equals("开元楼")){
					E="12";
				}else if(end.equals("翠园")){
					E="22";
				}else if(end.equals("医院")){
					E="23";
				}else if(end.equals("学校小门")){
					E="11";
				}else if(end.equals("大礼堂")){
					E="28";
				}
				Intent intent = new Intent(getApplicationContext(),
						FindingRoads.class);
				intent.putExtra("start", S);
				intent.putExtra("end", E);
				startActivity(intent);
			}			
		});
		System.out.println(start);
		System.out.println(end);

		path_short = (TextView) findViewById(R.id.short_path);
		path_short_name = (TextView) findViewById(R.id.short_path_name);

		path_short_name.setText("最短路线显示如下：");
		path_short_name.setTextSize(23);

		System.out.println("OK");
		wei = getPathWeight(start, end);
		System.out.println(wei[0]);
		if (wei == null)
			Toast.makeText(getApplicationContext(), "此路不通", Toast.LENGTH_LONG)
					.show();
		else {
			path_short.setTextColor(Color.BLUE);
			for (int i = 0; i <= wei.length - 1; i++) {
				path_short.append("↓--" + wei[i] +  "\n");
			}
			path_short.setTextSize(23);
			path_short.append("\n最短距离为：" + Integer.toString(PathUtils.shortPath)
					+ "米");
		}
	}

	public String[] getPathWeight(String start, String end) {
		AdjMatrix S = new AdjMatrix(INT);
		if (PathUtils.fileIsEmpty("1.txt"))
			PathUtils.ReadSightFile(S, "1.txt");
		else
			PathUtils.ReadSightFileAssets(S, "1.txt",getApplicationContext());
		AdjList L = new AdjList(INT);
		PathUtils.change(S, L);
		int l = PathUtils.Path(L, start, end);
		System.out.println(l);

		if (l == 0)
			return null;
		else {
			String[] str = new String[l];
			str = PathUtils.getPath();

			return str;
		}
	}

	@SuppressLint("UseSparseArrays")
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		path_short.setText("");
		PathUtils.pathShort = new HashMap<Integer, String[]>();
		PathUtils.list = new ArrayList<Integer>();
	}
}

