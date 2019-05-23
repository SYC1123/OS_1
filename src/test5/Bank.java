package test5;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private int[] Available;// 可利用资源向量 Available
	private int[] Work;// :Work=Available(表示系统可提供给进程继续运行所需要的各类资源数目)
	private int[][] MAX;// n 个进程中的每一个进程对 m 类资源的最大需求
	private int[][] Allocation;// 每一类资源当前已分配给每一进程的资源数
	private int[][] Need;// 每一个进程尚需的各类资源数
	private boolean[] Finish;
	private List<Integer> safeQueue;

	public Bank() {
		// TODO Auto-generated constructor stub
		Available = new int[] { 10, 5, 7 };
		Work = new int[3];
		MAX = new int[][] { { 7, 5, 3 }, { 3, 2, 2 }, { 9, 0, 2 }, { 2, 2, 2 }, { 4, 3, 3 } };
		Allocation = new int[][] { { 0, 1, 0 }, { 2, 0, 0 }, { 3, 0, 2 }, { 2, 1, 1 }, { 0, 0, 2 } };
		Need = new int[][] { { 7, 4, 3 }, { 1, 2, 2 }, { 6, 0, 0 }, { 0, 1, 1 }, { 4, 3, 1 } };
		safeQueue = new ArrayList<Integer>();
		Finish = new boolean[5];
	}

	public void init() {
		for (int j = 0; j < 3; j++) {
			int sum = 0;
			for (int i = 0; i < 5; i++) {
				sum = sum + Allocation[i][j];
			}
			Available[j] = Available[j] - sum;
		}

		for (int j = 0; j < 3; j++) {
			Work[j] = Available[j];
		}

	}

	public void showSafeQueue() {
		for (int i = 0; i < safeQueue.size(); i++) {
			System.out.print(safeQueue.get(i) + "\t");
		}
	}

	public boolean safeCheck() {
		int row = 0;
		for (int i = 0; i < 5; i++) {
			if (!this.Finish[i] && OK(this.Work, this.Need, i)) {
				Add(i);
				this.Finish[i] = true;
				this.safeQueue.add(i);
				row = i;
				safeCheck();
			}
		}
		if (checkFinish()) {
			return true;
		} else {
			Minus(row);
			// System.out.println("safeQueue.size()" + safeQueue.size());
			if (safeQueue.size() == 0) {
				System.out.println("无安全序列！");
			} else {
				this.safeQueue.remove(this.safeQueue.size() - 1);
				Finish[row]=true;
			}
			return false;
		}
	}

	public void Add(int row) {
		for (int j = 0; j < this.Work.length; j++) {
			this.Work[j] += this.Allocation[row][j];
		}
	}

	public void Minus(int row) {
		for (int j = 0; j < this.Work.length; j++) {
			this.Work[j] -= this.Allocation[row][j];
		}
	}

	public boolean OK(int[] work, int[][] need, int row) {
		int a = 0;
		for (int j = 0; j < work.length; j++) {
			if (work[j] >= need[row][j]) {
				a++;
			}
		}
		if (a == work.length) {
			return true;
		} else {
			return false;
		}
	}

	public boolean OK1(int[] request, int[][] need, int row) {
		int a = 0;
		for (int j = 0; j < request.length; j++) {
			if (request[j] <= need[row][j]) {
				a++;
			}
		}
		if (a == request.length) {
			return true;
		} else {
			return false;
		}
	}

	public boolean OK2(int[] request, int[] available) {
		int a = 0;
		for (int i = 0; i < request.length; i++) {
			if (request[i] <= available[i]) {
				a++;
			}
		}
		if (a == request.length) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkFinish() {
		int a = 0;
		for (int i = 0; i < this.Finish.length; i++) {
			if (this.Finish[i]) {
				a++;
			}
		}
		if (a == Finish.length) {
			return true;
		} else {
			return false;
		}
	}

	public void ruquestRes(int[] request, int p) {
		if (OK1(request, Need, p)) {
			if (OK2(request, Available)) {
				for (int j = 0; j < Available.length; j++) {
					this.Available[j] -= request[j];
					//System.out.println("Available[j]----" + Available[j]);
					this.Allocation[p][j] += request[j];
					//System.out.println("Allocation[p][j]----" + Allocation[p][j]);
					this.Need[p][j] -= request[j];
					//System.out.println("Need[p][j]----" + Need[p][j]);
				}
				for (int j = 0; j < Work.length; j++) {
					this.Work[j] = this.Available[j];
				}
				for (int i = safeQueue.size() - 1; i >= 0; i--) {
					safeQueue.remove(i);
				}
				for (int i = 0; i < Finish.length; i++) {
					Finish[i] = false;
				}
				safeCheck();
				showSafeQueue();
				// System.out.println();
			} else {
				System.out.println("等待！");
			}
		} else {
			System.out.println("请求资源数大于需求数！");
		}
	}

}
