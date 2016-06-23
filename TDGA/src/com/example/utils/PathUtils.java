package com.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.example.utils.AdjList;
import com.example.utils.AdjMatrix;
import com.example.utils.ArcNode;
import com.example.utils.PathUtils;
import com.example.utils.VertexNode;

public class PathUtils {

	final static int MAXVEX = 20;
	final static int INT = 32767;
	final static int MAXSIZE = 100;
	static int len = 0;
	public static int shortPath = 0;
	static int visited[] = new int[MAXSIZE];
	@SuppressLint("UseSparseArrays")
	public static Map<Integer, String[]> pathShort = new HashMap<Integer, String[]>();
	public static ArrayList<Integer> list = new ArrayList<Integer>();

	@SuppressLint("UseSparseArrays")
	// ��������ѡ�����·����Ȩֵ����str��Map���У�Ȩֵ����lt��List���У��ҳ�
	// lt��Ȩֵ��С����ţ���������str�ж�Ӧ��·��
	public static String[] getBestPath(AdjMatrix G, String[] path) {
		Map<Integer, String[]> str = new HashMap<Integer, String[]>();
		ArrayList<Integer> lt = new ArrayList<Integer>();
		String[] bestPath = null;
		AdjList L = new AdjList(INT);
		PathUtils.change(G, L);
		PathUtils.Path(L, path[0], path[path.length - 1]);
		for (int i = 0; i < pathShort.size(); i++) {
			Integer x = list.get(i);
			int k = 0;
			for (int j = 0; j < pathShort.get(x).length;) {
				if (pathShort.get(x)[j].equals(path[k])) {
					k++;
					j++;
				} else
					j++;
			}
			if (k == path.length) {
				lt.add(x);
				str.put(x, pathShort.get(x));
			}

		}
		Integer min = INT;
		for (int i = 0; i < str.size(); i++) {
			Integer x = lt.get(i);
			if (x < min) {
				min = x;
			}
		}
		bestPath = new String[str.get(min).length + 1];
		for (int i = 0; i < str.get(min).length; i++) {
			bestPath[i] = str.get(min)[i];
		}
		bestPath[str.get(min).length] = min.toString() + "��";
		return bestPath;
	}

	static class Queue {
		int rear;
		int front;
		int elem[];

		void InitQueue(Queue Q) {
			Q.front = 0;
			Q.rear = 0;
			Q.elem = new int[MAXSIZE];
		}

		void EnterQueue(Queue Q, int v) {
			Q.elem[Q.rear] = v;
			Q.rear++;
		}

		void OutQueue(Queue Q, int v) {
			v = Q.elem[Q.front];
			Q.front++;
		}

		int IsEmpty(Queue Q) {
			if (Q.front == Q.rear)
				return 1;
			else
				return 0;
		}
	}

	// public static void sortPath(){
	// Map<Integer,String[]> temp = new HashMap<Integer,String[]>();
	// int min=list.get(0);
	// for(int i=0;i<pathShort.size();i++){
	// for(int j=i;j<pathShort.size();i++){
	// if(list.get(j)<list.get(j)){
	//
	// }
	// }
	// }
	// }

	// ������ȱ���ͼ
	static int visit[] = new int[MAXSIZE];

	public static void DFS(AdjList G, int v, List<String> str) {
		ArcNode p = new ArcNode();
		System.out.println(G.vertex.get(v).vexdata);
		str.add(G.vertex.get(v).vexdata);
		visit[v] = 1;
		for (p = G.vertex.get(v).firstarc; p != null; p = p.nextarc)
			if (visit[p.adjvex] != 1)
				DFS(G, p.adjvex, str);
	}

	public static void DFS_Traverse(AdjList G, List<String> str) {

		int i;
		for (i = 0; i < G.vexnum; i++)
			visit[i] = 0;
		for (i = 0; i < G.vexnum; i++)
			if (visit[i] != 1)
				DFS(G, i, str);
	}

	// ������ȱ���
	static int visited1[] = new int[MAXSIZE];

	static void BFS(AdjList G, int v, List<String> str) {
		int w = 0;
		ArcNode p;
		Queue Q = new Queue();
		Q.InitQueue(Q);
		// printf("%c  ", G->vertex[v].vexdata);
		str.add(G.vertex.get(v).vexdata);
		visited[v] = 1;
		Q.EnterQueue(Q, v);
		while (Q.IsEmpty(Q) != 0) {
			Q.OutQueue(Q, w);
			for (p = G.vertex.get(w).firstarc; p != null; p = p.nextarc)
				if (visited1[p.adjvex] != 0) {
					// printf("%c  ", G->vertex[p->adjvex].vexdata);
					visited[p.adjvex] = 1;
					Q.EnterQueue(Q, p.adjvex);
				}
		}
	}

	public static void BFS_Traverse(AdjList G, List<String> str) {
		int i;
		for (i = 0; i < G.vexnum; i++)
			visited[i] = 0;
		for (i = 0; i < G.vexnum; i++)
			if (visited[i] != 1)
				BFS(G, i, str);
	}

	// �õ�������±�
	public static int LocationList(AdjList G, String v) {
		int i;
		for (i = 0; i < G.vexnum; i++) {
			if (G.vertex.get(i).vexdata.equals(v))
				return i;
		}
		return -1;
	}

	static// ������·���ĵݹ麯��
	int count = 0;

	// �õ���������֮������·������·����Ȩֵ����Map�����У���Ȩֵ����List������
	public static void PathAll(AdjList G, int v1, int v2, String path[],
			int length) {
		int i, index1, index2 = 0;
		int weight;
		ArcNode p, q1 = null;
		weight = 0;
		if (v1 == v2) // ��v1==v2ʱ���ѵõ�һ������·�����������
		{
			String[] str = new String[length + 1];
			count++;
			for (i = 0; i < length; i++) {
				str[i] = path[i];
				index1 = LocationList(G, path[i]);
				if (i < length - 1)
					index2 = LocationList(G, path[i + 1]);

				q1 = G.vertex.get(index1).firstarc;
				if (i < length - 1) {
					while (q1.adjvex != index2)
						q1 = q1.nextarc;
					weight += q1.weight;
				}
			}
			str[i] = G.vertex.get(v1).vexdata;
			while (q1.adjvex != v1)
				q1 = q1.nextarc;
			weight += q1.weight;
			pathShort.put(weight, str);
			list.add(weight);
		} else {
			visited[v1] = 1;
			path[length] = G.vertex.get(v1).vexdata;
			for (p = G.vertex.get(v1).firstarc; p != null; p = p.nextarc)
				if (visited[p.adjvex] != 1) {
					visited[p.adjvex] = 1;
					PathAll(G, p.adjvex, v2, path, length + 1);
					visited[p.adjvex] = 0;
				}
		}
	}

	// �ҳ�List��������С��Ȩֵ����������Map�ж�Ӧ��·��
	public static String[] getPath() {
		int m = 32767;
		for (int i = 0; i < list.size(); i++) {
			if ((int) list.get(i) < m) {
				m = (int) list.get(i);
			}
		}
		len = pathShort.get((Integer) m).length;
		shortPath = m;
		return pathShort.get((Integer) m);

	}

	// �ڽӾ���ת��Ϊ�ڽӱ�
	public static void change(AdjMatrix M, AdjList L) {
		int i, j;

		L.arcnum = M.arcnum;
		L.vexnum = M.vexnum;
		L.vertex = new ArrayList<VertexNode>();
		for (i = 0; i < L.vexnum; i++) {
			VertexNode n = new VertexNode();
			n.firstarc = null;
			n.vexdata = new String();
			L.vertex.add(n);
		}
		for (i = 0; i < M.vexnum; i++)
			L.vertex.get(i).firstarc = null;
		for (i = 0; i < M.vexnum; i++)
			L.vertex.get(i).vexdata = M.vex.get(i);
		for (i = 0; i < M.vexnum; i++) {
			for (j = 0; j < M.vexnum; j++) {
				if (M.arcs[i][j] != INT) {
					Insert(L.vertex.get(i), j, M.arcs[i][j]);
				}
			}
		}
	}

	static void Insert(VertexNode a, int m, int n) {
		ArcNode p, q;
		p = new ArcNode();
		p.adjvex = m;
		p.nextarc = null;
		p.weight = n;
		if (a.firstarc == null)
			a.firstarc = p;
		else {
			q = a.firstarc;
			while (q.nextarc != null)
				q = q.nextarc;
			q.nextarc = p;
		}
	}

	// ��ȡ����·������
	public static int Path(AdjList G, String v1, String v2) {
		String path[] = new String[25];
		int i, j;
		for (i = 0; i < MAXSIZE; i++)
			// ��ʼ��visited����
			visited[i] = 0;
		i = LocationList(G, v1);
		j = LocationList(G, v2);
		PathAll(G, i, j, path, 0);
		return count;
	}

	// �ӣӣĿ���ȡ
	public static void ReadSightFile(AdjMatrix G, String strFile) {
		FileInputStream fis;
		BufferedReader br = null;
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				// ��ȡָ���ļ���Ӧ��������
				fis = new FileInputStream(sdCardDir.getCanonicalPath() + "/"
						+ strFile);
				// InputStream is =
				// this.getResources().getAssets().open(strFile);
				br = new BufferedReader(new InputStreamReader(fis, "gbk"));

				G.vexnum = Integer.parseInt(br.readLine());
				G.arcnum = Integer.parseInt(br.readLine());
				G.arcs = new int[G.vexnum][G.vexnum];
				G.vex = new ArrayList<String>();
				for (int i = 0; i < G.vexnum; i++) {
					String str = br.readLine();
					G.vex.add(str);
				}

				for (int i = 0; i < G.vexnum; i++) {
					for (int j = 0; j < G.vexnum; j++) {
						G.arcs[i][j] = Integer.parseInt(br.readLine());
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static boolean fileIsEmpty(String strFile) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File sdCardDir = Environment.getExternalStorageDirectory();
			try {
				File file = new File(sdCardDir.getCanonicalPath() + "/"
						+ strFile);
				if (file.exists())
					return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	// ��assets��ȡ
	public static void ReadSightFileAssets(AdjMatrix G, String strFile,
			Context context) {
		BufferedReader br = null;
		try {
			InputStream is = context.getResources().getAssets().open(strFile);
			br = new BufferedReader(new InputStreamReader(is, "gbk"));
			G.vexnum = Integer.parseInt(br.readLine());
			G.arcnum = Integer.parseInt(br.readLine());
			G.arcs = new int[G.vexnum][G.vexnum];
			G.vex = new ArrayList<String>();
			for (int i = 0; i < G.vexnum; i++) {
				String str = br.readLine();
				G.vex.add(str);
			}

			for (int i = 0; i < G.vexnum; i++) {
				for (int j = 0; j < G.vexnum; j++) {
					G.arcs[i][j] = Integer.parseInt(br.readLine());
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ��������Ϣ���浽�ļ�
	public static void SaveSightFile(AdjMatrix G, String strFile) {
		FileOutputStream fis = null;
		PrintStream fw;
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				// ��ȡָ���ļ���Ӧ��������
				fis = new FileOutputStream(sdCardDir.getCanonicalPath() + "/"
						+ strFile);

				fw = new PrintStream(fis, true, "gbk");
				fw.println(Integer.toString(G.vexnum));
				fw.println(Integer.toString(G.arcnum));
				for (int i = 0; i < G.vexnum; i++) {
					fw.println(G.vex.get(i));
				}
				for (int i = 0; i < G.vexnum; i++) {
					for (int j = 0; j < G.vexnum; j++)
						fw.println(Integer.toString(G.arcs[i][j]));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
