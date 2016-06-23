package com.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdjMatrix {
	final static int INT = 32767;
	public int vexnum;
	public int arcnum;
	public int[][] arcs;
	public List<String> vex;

	public AdjMatrix(int i) {

	}

	public AdjMatrix() {
		int i, j, k, weight;
		String vex1, vex2;
		int lv1, lv2;
		// System.out.println("�������������еĶ������ͱ�����");
		Scanner scan = new Scanner(System.in);
		this.vexnum = scan.nextInt();
		this.arcnum = scan.nextInt();
		this.vex = new ArrayList<String>();
		this.arcs = new int[this.vexnum][this.vexnum];
		for (i = 0; i < this.vexnum; i++)
			for (j = 0; j < this.vexnum; j++)
				this.arcs[i][j] = INT;
		// System.out.println("�������������еĶ��㣺");
		for (i = 0; i < this.vexnum; i++) {
			String str = scan.next();
			this.vex.add(str);
		}
		// System.out.println("�������������ıߣ�");
		for (k = 0; k < this.arcnum; k++) {
			// System.out.println("No."+(k+1)+"���ߣ�	");
			vex1 = scan.next();
			vex2 = scan.next();
			// System.out.println("Ȩֵ��");
			weight = scan.nextInt();
			lv1 = Location(vex1);
			lv2 = Location(vex2);
			// System.out.println(lv1+" "+lv2);
			this.arcs[lv1][lv2] = weight;
			this.arcs[lv2][lv1] = weight;
		}
		scan.close();
	}

	// �õ�������±�
	int Location(String v) {
		int i;
		for (i = 0; i < this.vexnum; i++)
			if (this.vex.get(i).equals(v))
				return i;
		return -1;
	}

	public void output() {
		for (int i = 0; i < this.vexnum; i++) {
			for (int j = 0; j < this.vexnum; j++)
				System.out.print(this.arcs[i][j] + " ");
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// AdjMatrix G = new AdjMatrix();
		// G.output();
		// PathUtils.getShortPath(G);
		//
		// L.OutputAdj(L);
		// PathUtils.Path(L, "A", "D");
		// PathUtils.SaveSightFile(G, "E://2.txt");
		// AdjMatrix S = new AdjMatrix(INT);
		// PathUtils.ReadSightFile(S, "E://1.txt");
		// S.output();
		// AdjList L = new AdjList(INT);
		// PathUtils.change(S, L);
		// L.OutputAdj(L);
		// PathUtils.getShortPath(S);
		// PathUtils.Path(L, "����","����Է" );
	}

}

