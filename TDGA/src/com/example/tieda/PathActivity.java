package com.example.tieda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.slidemenu.MainActivity;
import com.example.tieda.ShortPathActivity;
import com.example.utils.AdjList;
import com.example.utils.AdjMatrix;
import com.example.utils.PathUtils;

public class PathActivity extends Activity{
	AdjMatrix S;
	Spinner path_start;
	Spinner path_end;
	Button path_short;
	final static int INT = 32767;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.path_main);
		path_start = (Spinner) findViewById(R.id.path_start);
		path_end = (Spinner) findViewById(R.id.path_end);
		path_short = (Button) findViewById(R.id.path_short);

		String[] str = getData();
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, str);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		path_start.setAdapter(adapter1);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_item, str);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		path_end.setAdapter(adapter2);

		// 求最短路线
		path_short.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ShortPathActivity.class);
				String start = path_start.getSelectedItem().toString();
				String end = path_end.getSelectedItem().toString();

				if (!start.equals(end)) {
					if (getPathWeight(start, end) == null) {
						Toast.makeText(getApplicationContext(), "此路不通",
								Toast.LENGTH_LONG).show();

					} else {
						intent.putExtra("start", start);
						intent.putExtra("end", end);
						startActivity(intent);
					}
				} else
					Toast.makeText(getApplicationContext(), "请选择不同的起点和终点",
							Toast.LENGTH_SHORT).show();

			}
		});

		findViewById(R.id.showviewbutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PathActivity.this, MainActivity.class);
				PathActivity.this.startActivity(intent);
			}
		});
		findViewById(R.id.locationbutton).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PathActivity.this, GPSLocation.class);
				PathActivity.this.startActivity(intent);
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
				intent.setClass(PathActivity.this, AboutActivity.class);
				PathActivity.this.startActivity(intent);
			}
		});
	}

	public String[] getData() {
		S = new AdjMatrix(INT);
		if (PathUtils.fileIsEmpty("1.txt"))
			PathUtils.ReadSightFile(S, "1.txt");
		else
			PathUtils.ReadSightFileAssets(S, "1.txt", getApplicationContext());
		String[] str = new String[S.vexnum];
		for (int i = 0; i < S.vexnum; i++)
			str[i] = S.vex.get(i);
		return str;
	}

	public String[] getPathWeight(String start, String end) {
		AdjMatrix S = new AdjMatrix(INT);
		if (PathUtils.fileIsEmpty("1.txt"))
			PathUtils.ReadSightFile(S, "1.txt");
		else
			PathUtils.ReadSightFileAssets(S, "1.txt", getApplicationContext());
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
}
