package test5;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PCB {
	public String name;
	public int size;
	public Date arrival_time; // 到达时间
	public int burst_time; // 服务时间
	public Date finished_time; // 结束运行时间
	public int runned_time; // 已运行时间（从创建到结束的时间）
	public int cycling_time;// 周转时间
	public int need_time;// 还需要的时间
	public float turnaround_time_right;// 带权周转时间
	// public int wait_time;// 等待时间
	public PCB next;

	public PCB() {
		runned_time = 0;
		//wait_time = 0;
	}

	public boolean hasnext() {
		PCB t = this;
		if (t.next != null) {
			return true;
		} else {
			return false;
		}
	}

	public void addToTail(PCB pcb) {
		PCB t = this;
		while (t.hasnext()) {
			t = t.next;
		}
		t.next = pcb;
	}

	public void show() {
		PCB t = this;
		while (t.hasnext()) {
			t = t.next;
			System.out.println(t.toString());
		}
	}

	public static PCB findMaxRight(int hastime, PCB p,PCB head) {
		PCB pcb = head.next;
		//System.out.println(this+"");
		float MaxRight = -1;
		PCB MAX = null;
		while (pcb.hasnext()) {
			pcb = pcb.next;
			if (pcb.need_time != 0) {
				//System.out.println(p+"123");
				//System.out.println(pcb+"456");
				float t = ((p.finished_time.getTime() - pcb.arrival_time.getTime()) / 1000 + pcb.burst_time)
						/ pcb.burst_time;
				if (t >= MaxRight) {
					MaxRight = t;
					MAX = pcb;
				}
			}
		}
		MAX.runned_time = hastime+MAX.burst_time;
		MAX.need_time = 0;
		MAX.finished_time=new Date(MAX.arrival_time.getTime()+hastime*1000);
		//System.out.println(MAX.name);
		return MAX;
	}

	public void Cycling_Right() {
		PCB pcb = this;
		if (pcb.next == null) {
			System.out.println("队列为空");
			// return 0;
		} else {
			PCB p = pcb.next;
			p.finished_time = new Date(p.arrival_time.getTime() + (p.burst_time * 1000));
			p.need_time = 0;
			p.runned_time = p.burst_time;
			int hastime = 0;
			PCB MAX = p;
			//System.out.println(MAX.need_time);
			while (p.next!= null) {
				hastime = hastime + MAX.burst_time;
				MAX = findMaxRight(hastime, MAX,this);
				p=p.next;
			}

		}
	}



	public int Cycling(int time, int hasSeverTime) {
		PCB t = this;
		if (time == t.need_time /* (t.burst_time - t.runned_time) */) {
			// 已经服务完
			// System.out.println(t.name + " a " + (t.burst_time - t.runned_time));
			// t.finished_time = new Date(t.arrival_time.getTime() + (hasSeverTime * 1000));
			// t.finished_time = new Date(t.finished_time.getTime() + ((t.burst_time -
			// t.runned_time) * 1000));
			// t.runned_time = t.burst_time + hasSeverTime;
			t.runned_time = t.need_time + hasSeverTime;
			t.need_time = t.need_time - time;
			return -1;
		} else if (time > t.need_time/* (t.burst_time - t.runned_time) */) {
			// 已经服务完
			// System.out.println(t.name + " b " + (t.burst_time - t.runned_time));
			// t.finished_time = new Date(t.arrival_time.getTime() + (hasSeverTime * 1000));
			// t.finished_time = new Date(t.finished_time.getTime() + ((t.burst_time -
			// t.runned_time) * 1000));
			int a;
			if (t.burst_time == t.need_time) {
				a = (t.burst_time - t.runned_time);
			} else {
				a = (t.burst_time - t.need_time);
			}

			t.runned_time = a + hasSeverTime;
			t.need_time = t.need_time - a;
			return a;
		} else {
			// 未完
			// System.out.println(t.name + " c " + (t.burst_time - t.runned_time));
			// t.finished_time = new Date(t.arrival_time.getTime() + ((hasSeverTime + time)
			// * 1000));
			// t.runned_time = t.runned_time + time;
			t.need_time = t.need_time - time;
			return -3;
		}
	}

	public void server(PCB pcb) {
		PCB t = this;
		if (t.next == null) {
			t.next = pcb;
			// pcb.finished_time = new Date(pcb.arrival_time.getTime() + (pcb.burst_time *
			// 1000));
			// pcb.cycling_time = (int) ((pcb.finished_time.getTime() -
			// pcb.arrival_time.getTime()) / 1000);
			pcb.runned_time = pcb.burst_time;
			// pcb.turnaround_time_right = pcb.cycling_time / pcb.burst_time;
		} else {
			while (t.next.hasnext()) {
				t = t.next;
			}
			t.next.next = pcb;
			// pcb.finished_time = new Date(t.finished_time.getTime() + (pcb.burst_time *
			// 1000));
			// pcb.cycling_time = (int) ((pcb.finished_time.getTime() -
			// pcb.arrival_time.getTime()) / 1000);
			pcb.runned_time = pcb.burst_time + t.next.runned_time;
			// float a = pcb.cycling_time;
			// float b = pcb.burst_time;
			// pcb.turnaround_time_right = (a / b);
		}
	}

	@Override
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");// 设置日期格式
		String string;
		if (finished_time == null) {
			string = "PCB [名称=" + name + ", 大小=" + size + ", 到达时间=" + df.format(arrival_time) + ", 服务时间=" + burst_time
					+ "秒, 已运行时间=" + runned_time + "秒]";
		} else {
			string = "PCB [名称=" + name + ", 大小=" + size + ", 到达时间=" + df.format(arrival_time) + ", 服务时间=" + burst_time
					+ "秒, 结束运行时间=" + df.format(finished_time) + ", 已运行时间=" + runned_time + "秒, 周转时间=" + cycling_time
					+ "秒, 带权周转时间=" + turnaround_time_right + "秒]";
		}
		return string;
	}

	public float aver_cycling_time() {
		PCB pcb = this;
		float sum = 0;
		int a = 0;
		while (pcb.hasnext()) {
			pcb = pcb.next;
			sum = sum + pcb.cycling_time;
			a++;
		}
		return sum / a;
	}

	public float aver_turnaround_time_right() {
		PCB pcb = this;
		float sum = 0;
		int a = 0;
		while (pcb.hasnext()) {
			pcb = pcb.next;
			sum = sum + pcb.turnaround_time_right;
			a++;
		}
		return sum / a;
	}

	public PCB deQueue() {
		PCB t = this.next;
		if (t == null) {
			System.out.println("队列无进程");
			return null;
		} else {
			this.next = t.next;
			t.next = null;
			return t;
		}
	}

	public Date firstDate() {
		PCB t = this;
		Date firstDate = t.next.arrival_time;
		while (t.hasnext()) {
			t = t.next;
			if (t.arrival_time.getTime() <= firstDate.getTime()) {
				firstDate = t.arrival_time;
			}
		}
		return firstDate;
	}

//计算结束时间，周转时间，带权周转时间
	public void calculator() {
		PCB t = this;

		Date fDate = this.firstDate();

		while (t.hasnext()) {
			t = t.next;
			t.finished_time = new Date(fDate.getTime() + t.runned_time * 1000);
			t.cycling_time = (int) ((t.finished_time.getTime() - t.arrival_time.getTime()) / 1000);
			float a = t.cycling_time;
			float b = t.burst_time;
			t.turnaround_time_right = (a / b);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Date getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(Date arrival_time) {
		this.arrival_time = arrival_time;
	}

	public int getCycling_time() {
		return cycling_time;
	}

	public void setCycling_time(int cycling_time) {
		this.cycling_time = cycling_time;
	}

	public float getTurnaround_time_right() {
		return turnaround_time_right;
	}

	public void setTurnaround_time_right(int turnaround_time_right) {
		this.turnaround_time_right = turnaround_time_right;
	}

	public int getBurst_time() {
		return burst_time;
	}

	public void setBurst_time(int burst_time) {
		this.burst_time = burst_time;
	}

	public Date getFinished_time() {
		return finished_time;
	}

	public void setFinished_time(Date finished_time) {
		this.finished_time = finished_time;
	}

	public int getRunned_time() {
		return runned_time;
	}

	public void setRunned_time(int runned_time) {
		this.runned_time = runned_time;
	}

	public PCB getNext() {
		return next;
	}

	public void setNext(PCB next) {
		this.next = next;
	}

}
