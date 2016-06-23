package com.example.tieda;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utils.AdjList;
import com.example.utils.AdjMatrix;
import com.example.utils.PathUtils;

public class SightActivity extends Activity{
	Map<String, String> sightMap = new HashMap<String, String>();
	int[] sightPic = new int[] { R.raw.zhengmen, R.raw.jiaoxuelou1,
			R.raw.gcxlzhongxin, R.raw.jiaoxuelou2, R.raw.jiaoxuelou3,
			R.raw.tiyuguan, R.raw.lanqiuchang, R.raw.natatorium,
			R.raw.caochang, R.raw.library, R.raw.kaishuifang, R.raw.shiyanlou,
			R.raw.jiaotonglou, R.raw.yinghualin, R.raw.chaoshi, R.raw.jijiao,
			R.raw.xiyuzhongxin, R.raw.yingjizhongxin, R.raw.zeyuan,
			R.raw.canteen, R.raw.chunhuilou, R.raw.kaiyuanlou, R.raw.cuiyuan,
			R.raw.xiaoyishi, R.raw.xiaomen, R.raw.dalitang,
	};
	String[] str;
	TextView sight_info;
	ImageView sight_photo;
	AdjMatrix S;
	final int INT = 32767;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.sight_main);
		Intent intent = getIntent();
		String str = intent.getStringExtra("sight");
		getSightInfo();
		String info = sightMap.get(str);
		sight_photo = (ImageView) findViewById(R.id.sight_photo);
		sight_info = (TextView) findViewById(R.id.sight_info);
		sight_info.setText(info);
		int x = getNum(str);
		sight_photo.setBackground(getResources().getDrawable(sightPic[x]));
	}

	public void getSightInfo() {
		S = new AdjMatrix(INT);
		// if(fileIsEmpty("1.txt"))
		// ReadSightFile(S, "1.txt");
		// else
		PathUtils.ReadSightFileAssets(S, "1.txt",getApplicationContext());
		System.out.println(S.vexnum);
		AdjList L = new AdjList(INT);
		PathUtils.change(S, L);
		str = infoSight();
		for (int i = 0; i < S.vexnum; i++) {
			sightMap.put(S.vex.get(i), str[i]);
		}
	}

	public int getNum(String str) {
		for (int i = 0; i < S.vexnum; i++) {
			if (S.vex.get(i).equals(str))
				return i;
		}
		return -1;
	}

	public String[] infoSight() {
		String[] str = new String[26];
		str[0] = "学校大门,出门坐车的地方门口有（快）环2,有23路有64路（到北站），大门西侧聚集了很多快递（韵达在联通营业厅），一般从这出门都是拿快递。";
		str[1] = "第一教学楼，以前学校最大的教学楼，学生上课自习的地方,五楼有机房有耳麦也就是语音室。";
		str[2] = "工程训练中心，机械院的学生实习的场所，其他院工业认知实习时也会在此。里面有个很出名的风功能研究中心。";
		str[3] = "第二教学楼，此楼虽新但建筑不怎么地，功能基本同一教，不过五楼没有语音室。";
		str[4] = "第三教学楼，全是阶梯教室，不提供自习室。";
		str[5] = "体育馆，占用场地要提前预约，对外开放。";
		str[6] = "沁园，波光粼粼，郁郁葱葱，东侧是一个跳舞的小广场，西侧是一座大钟。";
		str[7] = "游泳教学场，暑期可以去练习游泳，对外开放，体育老师那有优惠。";
		str[8] = "操场，标准400m+两个篮球场+一个排球场。";
		str[9] = "图书馆，知识的海洋，各类图书很丰富，提供实名占座自习室。";
		str[10] = "信息楼： 信息学院教师办公楼，一楼提供自习室，开水房在206。";
		str[11] = "第九实验楼： 分南北两侧：南面是计算机实验中心，全校的网络中心，有很多机房；北是建筑艺术的实验室。";
		str[12] = "交通楼： 一楼有校史馆。";
		str[13] = "樱花林： 除了樱花还有桃花，开花时美美哒~";
		str[14] = "消费合作社： 给我们提供大部分日常用品，阿姨们一般很热情。";
		str[15] = "基础教学楼： 1-6楼是公共教室，7-18楼是自习室和数理、外语和人文学院办公的地方。";
		str[16] = "九栋宿舍楼:全校最大的宿舍楼，整栋楼呈现为一本展开的书。人比较杂，土木的信息的建艺的都有，一楼大厅提供自动售货机（刷卡、现金）。";
		str[17] = "学二食堂: 学校的食堂，价格合理，种类很多，也很实惠。";
		str[18] = "泽园： 这里有伟大的毛泽东像，人们经常说的“毛爷爷下面集合……”就是这了。";
		str[19] = "综合餐厅： 一二楼是餐厅，三楼是青春苑，还有练习乐器和舞蹈的教室。";
		str[20] = "春晖楼： 学校的各个办事处都设在这栋楼里，分为西办和东办，不要走错了。";
		str[21] = "开元楼： 1952年建的，顾名思义它是学校开元的时候建的，里面好像都是学校的领导。";
		str[22] = "翠园： 早上学生朗读英语，考试周是个背马克思什么的绝佳地点，夏天记得带防蚊花露水。";
		str[23] = "校医院： 给我们提供基本的医疗服务，学生挂号拿药报销一半，医生下班很积极，很多时候下课去拿药就没了人。";
		str[24] = "学校小门： 东侧有很多卖饭的小摊，不推荐大家购买。";
		str[25] = "大礼堂： 举办大型联欢活动的地儿，每周六放映电影（要门票6元）。";
		return str;
	}
}
