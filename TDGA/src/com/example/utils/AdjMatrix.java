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
		// System.out.println("请输入无向网中的顶点数和边数：");
		Scanner scan = new Scanner(System.in);
		this.vexnum = scan.nextInt();
		this.arcnum = scan.nextInt();
		this.vex = new ArrayList<String>();
		this.arcs = new int[this.vexnum][this.vexnum];
		for (i = 0; i < this.vexnum; i++)
			for (j = 0; j < this.vexnum; j++)
				this.arcs[i][j] = INT;
		// System.out.println("请输入无向网中的顶点：");
		for (i = 0; i < this.vexnum; i++) {
			String str = scan.next();
			this.vex.add(str);
		}
		// System.out.println("请输入无向网的边：");
		for (k = 0; k < this.arcnum; k++) {
			// System.out.println("No."+(k+1)+"条边：	");
			vex1 = scan.next();
			vex2 = scan.next();
			// System.out.println("权值：");
			weight = scan.nextInt();
			lv1 = Location(vex1);
			lv2 = Location(vex2);
			// System.out.println(lv1+" "+lv2);
			this.arcs[lv1][lv2] = weight;
			this.arcs[lv2][lv1] = weight;
		}
		scan.close();
	}

	// 得到顶点的下标
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
		// PathUtils.Path(L, "正门","旭日苑" );
	}

}

