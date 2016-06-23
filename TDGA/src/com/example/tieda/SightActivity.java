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
		str[0] = "ѧУ����,���������ĵط��ſ��У��죩��2,��23·��64·������վ������������ۼ��˺ܶ��ݣ��ϴ�����ͨӪҵ������һ�������Ŷ����ÿ�ݡ�";
		str[1] = "��һ��ѧ¥����ǰѧУ���Ľ�ѧ¥��ѧ���Ͽ���ϰ�ĵط�,��¥�л����ж���Ҳ���������ҡ�";
		str[2] = "����ѵ�����ģ���еԺ��ѧ��ʵϰ�ĳ���������Ժ��ҵ��֪ʵϰʱҲ���ڴˡ������и��ܳ����ķ繦���о����ġ�";
		str[3] = "�ڶ���ѧ¥����¥���µ���������ô�أ����ܻ���ͬһ�̣�������¥û�������ҡ�";
		str[4] = "������ѧ¥��ȫ�ǽ��ݽ��ң����ṩ��ϰ�ҡ�";
		str[5] = "�����ݣ�ռ�ó���Ҫ��ǰԤԼ�����⿪�š�";
		str[6] = "��԰���������ԣ������дУ�������һ�������С�㳡��������һ�����ӡ�";
		str[7] = "��Ӿ��ѧ�������ڿ���ȥ��ϰ��Ӿ�����⿪�ţ�������ʦ�����Żݡ�";
		str[8] = "�ٳ�����׼400m+��������+һ�����򳡡�";
		str[9] = "ͼ��ݣ�֪ʶ�ĺ��󣬸���ͼ��ܷḻ���ṩʵ��ռ����ϰ�ҡ�";
		str[10] = "��Ϣ¥�� ��ϢѧԺ��ʦ�칫¥��һ¥�ṩ��ϰ�ң���ˮ����206��";
		str[11] = "�ھ�ʵ��¥�� ���ϱ����ࣺ�����Ǽ����ʵ�����ģ�ȫУ���������ģ��кܶ���������ǽ���������ʵ���ҡ�";
		str[12] = "��ͨ¥�� һ¥��Уʷ�ݡ�";
		str[13] = "ӣ���֣� ����ӣ�������һ�������ʱ������~";
		str[14] = "���Ѻ����磺 �������ṩ�󲿷��ճ���Ʒ��������һ������顣";
		str[15] = "������ѧ¥�� 1-6¥�ǹ������ң�7-18¥����ϰ�Һ��������������ѧԺ�칫�ĵط���";
		str[16] = "�Ŷ�����¥:ȫУ��������¥������¥����Ϊһ��չ�����顣�˱Ƚ��ӣ���ľ����Ϣ�Ľ��յĶ��У�һ¥�����ṩ�Զ��ۻ�����ˢ�����ֽ𣩡�";
		str[17] = "ѧ��ʳ��: ѧУ��ʳ�ã��۸��������ܶ࣬Ҳ��ʵ�ݡ�";
		str[18] = "��԰�� ������ΰ���ë�������Ǿ���˵�ġ�ëүү���漯�ϡ������������ˡ�";
		str[19] = "�ۺϲ����� һ��¥�ǲ�������¥���ഺԷ��������ϰ�������赸�Ľ��ҡ�";
		str[20] = "����¥�� ѧУ�ĸ������´��������ⶰ¥���Ϊ����Ͷ��죬��Ҫ�ߴ��ˡ�";
		str[21] = "��Ԫ¥�� 1952�꽨�ģ�����˼������ѧУ��Ԫ��ʱ�򽨵ģ����������ѧУ���쵼��";
		str[22] = "��԰�� ����ѧ���ʶ�Ӣ��������Ǹ������˼ʲô�ľ��ѵص㣬����ǵô����û�¶ˮ��";
		str[23] = "УҽԺ�� �������ṩ������ҽ�Ʒ���ѧ���Һ���ҩ����һ�룬ҽ���°�ܻ������ܶ�ʱ���¿�ȥ��ҩ��û���ˡ�";
		str[24] = "ѧУС�ţ� �����кܶ�������С̯�����Ƽ���ҹ���";
		str[25] = "�����ã� �ٰ����������ĵض���ÿ������ӳ��Ӱ��Ҫ��Ʊ6Ԫ����";
		return str;
	}
}
