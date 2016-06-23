package com.example.slidemenu;

import com.example.slidemenu.widget.SlideMenu;
import com.example.tieda.AboutActivity;
import com.example.tieda.GPSLocation;
import com.example.tieda.PathActivity;
import com.example.tieda.R;
import com.example.tieda.SightActivity;
import com.example.utils.AdjMatrix;
import com.example.utils.PathUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener,OnGestureListener {

	@SuppressWarnings("unused")
	private SlideMenu slideMenu;
	SharedPreferences perferences;
	Spinner spinner;
	Button search_sight;
	Button search_path;
	Button search_map;
	final static int INT = 32767;
	 int c = 0;
	ViewFlipper flipper;
	// 定义手势检测器实例
	GestureDetector detector;
	// 定义一个动画数组，用于为ViewFlipper指定切换动画效果
	Animation[] animations = new Animation[4];
	// 定义手势动作两点之间的最小距离
	final int FLIP_DISTANCE = 50;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		/*
		 * SharedPreferences记录打开程序次数
		 * 用于检测是否第一次打开程序
		 * 以此判断引导页面的显示
		 */
		perferences = getSharedPreferences("count" ,MODE_PRIVATE);
		int count = perferences.getInt("count", 0);
		Editor editor = perferences.edit();
		editor.putInt("count", ++count);
		editor.commit();
		
		detector = new GestureDetector(this, (OnGestureListener) this);
		// 获得ViewFlipper实例
		
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
		animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
		animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);
		spinner = (Spinner) findViewById(R.id.spinner);

		String[] str = getData();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, str);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		/* 添加Spinner事件监听
		 * 除去显示按钮，使得选择选项后不必再点击确定按钮
		 * 修改日期：2015/6/3
		*/
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (c != 0) {
					//System.out.println("c="+c);
					String str = spinner.getSelectedItem().toString();
					if (isExits(str, getApplicationContext())) {
						Intent intent = new Intent(getApplicationContext(),
								SightActivity.class);
						intent.putExtra("sight", str);
						startActivity(intent);
					} else
						Toast.makeText(getApplicationContext(), "暂无此景点信息...",
								Toast.LENGTH_LONG).show();
				}
				else
					c++;
				arg0.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});

	//侧滑菜单定位按钮
		findViewById(R.id.locationbutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GPSLocation.class);
				MainActivity.this.startActivity(intent);
			}
		});
		//侧滑菜单查询按钮
		findViewById(R.id.findroadbutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
				intent.setClass(MainActivity.this, PathActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		findViewById(R.id.fengcaibutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "努力开发中...", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.yishibutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "努力开发中...", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.about).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AboutActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
	}

	//数据导入
	public static boolean isExits(String name, Context context) {

		AdjMatrix S = new AdjMatrix(INT);
		PathUtils.ReadSightFileAssets(S, "1.txt", context);
		for (int i = 0; i < S.vexnum; i++) {
			if (S.vex.get(i).equals(name))
				return true;
		}
		return false;
	}

	public String[] getData() {
		AdjMatrix S = new AdjMatrix(INT);
		if (PathUtils.fileIsEmpty("1.txt"))
			PathUtils.ReadSightFile(S, "1.txt");
		else
			PathUtils.ReadSightFileAssets(S, "1.txt",getApplicationContext());
		String[] str = new String[S.vexnum];
		for (int i = 0; i < S.vexnum; i++)
			str[i] = S.vex.get(i);
		return str;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2,
			float velocityX, float velocityY) {
		// 如果第一个触点事件的X座标大于第二个触点事件的X座标超过FLIP_DISTANCE
		// 也就是手势从右向左滑。
		if (event1.getX() - event2.getX() > FLIP_DISTANCE) {
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[0]);
			flipper.setOutAnimation(animations[1]);
			flipper.showPrevious();
			return true;
		}
		// 如果第二个触点事件的X座标大于第一个触点事件的X座标超过FLIP_DISTANCE
		// 也就是手势从右向左滑。
		else if (event2.getX() - event1.getX() > FLIP_DISTANCE) {
			// 为flipper设置切换的的动画效果
			flipper.setInAnimation(animations[2]);
			flipper.setOutAnimation(animations[3]);
			flipper.showNext();
			return true;
		}
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return true;
		}
		return false;
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(event);
	}
	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
