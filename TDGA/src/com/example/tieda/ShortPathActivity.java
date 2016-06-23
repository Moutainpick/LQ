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
				if(start.equals("ѧУ����") ){
					S= "6";
				}
				else if(start.equals("��һ��ѧ¥")){
					S="5";
				}
				else if(start.equals("����ѵ������")){
					S="0";
				}
				else if(start.equals("�ڶ���ѧ¥")){
					S="2";
				}
				else if(start.equals("������ѧ¥")){
					S="17";
				}
				else if(start.equals("������")){
					S="32";
				}
				else if(start.equals("��԰")){
					S="19";
				}
				else if(start.equals("��Ӿ��ѧ��")){
					S="45";
				}
				else if(start.equals("�ٳ�")){
					S="30";
				}
				else if(start.equals("ͼ���")){
					S="34";
				}
				else if(start.equals("��Ϣ¥")){
					S="46";
				}
				else if(start.equals("�ھ�ʵ��¥")){
					S="59";
				}
				else if(start.equals("��ͨ¥")){
					S="59";
				}
				else if(start.equals("ӣ����")){
					S="46";
				}
				else if(start.equals("����")){
					S="48";
				}
				else if(start.equals("������ѧ¥")){
					S="37";
				}
				else if(start.equals("�Ŷ�����¥")){
					S="54";
				}
				else if(start.equals("ѧ��ʳ��")){
					S="55";
				}
				else if(start.equals("��԰")){
					S="28";
				}
				else if(start.equals("�ۺϲ���")){
					S="29";
				}
				else if(start.equals("����¥")){
					S="14";
				}
				else if(start.equals("��Ԫ¥")){
					S="12";
				}
				else if(start.equals("��԰")){
					S="22";
				}
				else if(start.equals("ҽԺ")){
					S="23";
				}
				else if(start.equals("ѧУС��")){
					S="11";
				}
				else if(start.equals("������")){
					S="28";
				}

				if(end.equals("ѧУ����")){
					E="6";
				}else if(end.equals("��һ��ѧ¥")){
					E="5";
				}else if(end.equals("����ѵ������")){
					E="0";
				}else if(end.equals("�ڶ���ѧ¥")){
					E="2";
				}else if(end.equals("������ѧ¥")){
					E="17";
				}else if(end.equals("������")){
					E="32";
				}else if(end.equals("��԰")){
					E="19";
				}else if(end.equals("��Ӿ��ѧ��")){
					E="45";
				}else if(end.equals("�ٳ�")){
					E="30";
				}else if(end.equals("ͼ���")){
					E="34";
				}else if(end.equals("��Ϣ¥")){
					E="46";
				}else if(end.equals("�ھ�ʵ��¥")){
					E="59";
				}else if(end.equals("��ͨ¥")){
					E="59";
				}else if(end.equals("ӣ����")){
					E="46";
				}else if(end.equals("����")){
					E="48";
				}else if(end.equals("������ѧ¥")){
					E="37";
				}else if(end.equals("�Ŷ�����¥")){
					E="54";
				}else if(end.equals("ѧ��ʳ��")){
					E="55";
				}else if(end.equals("��԰")){
					E="28";
				}else if(end.equals("�ۺϲ���")){
					E="29";
				}else if(end.equals("����¥")){
					E="14";
				}else if(end.equals("��Ԫ¥")){
					E="12";
				}else if(end.equals("��԰")){
					E="22";
				}else if(end.equals("ҽԺ")){
					E="23";
				}else if(end.equals("ѧУС��")){
					E="11";
				}else if(end.equals("������")){
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

		path_short_name.setText("���·����ʾ���£�");
		path_short_name.setTextSize(23);

		System.out.println("OK");
		wei = getPathWeight(start, end);
		System.out.println(wei[0]);
		if (wei == null)
			Toast.makeText(getApplicationContext(), "��·��ͨ", Toast.LENGTH_LONG)
					.show();
		else {
			path_short.setTextColor(Color.BLUE);
			for (int i = 0; i <= wei.length - 1; i++) {
				path_short.append("��--" + wei[i] +  "\n");
			}
			path_short.setTextSize(23);
			path_short.append("\n��̾���Ϊ��" + Integer.toString(PathUtils.shortPath)
					+ "��");
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

