package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.utils.AdjList;
import com.example.utils.ArcNode;
import com.example.utils.VertexNode;

public class AdjList {
	List<VertexNode> vertex;
	int vexnum;
	int arcnum;

	// �ڽӱ���������
	public AdjList(int i) {
		this.arcnum = 0;
		this.vexnum = 0;
	}

	public AdjList() {
		// TODO Auto-generated constructor stub
		int i, w;
		int k, j;
		int v, a;
		VertexNode vn;
		String v1, v2;
		System.out.println("�����붥�����ͱ�����");
		Scanner scan = new Scanner(System.in);
		v = scan.nextInt();
		a = scan.nextInt();
		this.vexnum = v;
		this.arcnum = a;
		this.vertex = new ArrayList<VertexNode>();
		// G = new AdjList(v, a);
		System.out.println("�����붥����Ϣ��\n");
		for (i = 0; i < this.vexnum; i++) {
			vn = new VertexNode();
			vn.vexdata = scan.next();
			vn.firstarc = null;
			this.vertex.add(vn);
		}
		System.out.println("���������Ϣ��\n");
		System.out.println(this.vexnum + "  " + this.arcnum);
		for (k = 0; k < this.arcnum; k++) {
			System.out.println("�������" + (k + 1) + "���ߣ�");
			v1 = scan.next();
			v2 = scan.next();
			System.out.println("������Ȩֵ��");
			w = scan.nextInt();
			i = GetLocation(v1);
			j = GetLocation(v2);
			System.out.println(i + "  " + j);
			Insert(this.vertex.get(i), j, w);
			Insert(this.vertex.get(j), i, w);
		}
		System.out.println("OK");
		scan.close();
	}

	// ��ӡ�ڽӱ�
	public void OutputAdj(AdjList G) {
		int i;
		ArcNode p;
		for (i = 0; i < G.vexnum; i++) {
			System.out.print(G.vertex.get(i).vexdata + "  ");
			for (p = G.vertex.get(i).firstarc; p != null; p = p.nextarc) {
				// if (p->weight != INT)
				System.out.print(p.adjvex + "  ");
			}
			System.out.println();
		}
	}

	// �õ�������±�
	int GetLocation(String v) {
		int i;
		for (i = 0; i < this.vexnum; i++)
			if (this.vertex.get(i).vexdata.equals(v))
				return i;
		return -1;
	}

	// ����
	void Insert(VertexNode a, int m, int n) {
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
	// public static void main(String[] args) {
	// AdjList G = new AdjList();
	// PathUtils.Path( G,"A", "D");
	// }
}

final class VertexNode {
	String vexdata;
	ArcNode firstarc;

	public VertexNode() {
		vexdata = new String();
		firstarc = new ArcNode();
	}
}

final class ArcNode {
	int adjvex;
	int weight;
	ArcNode nextarc;

	public ArcNode() {
	}
}

