package test5;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class main {

	private static PCB running_head = new PCB();
	private static PCB blocked_head = new PCB();
	private static PCB ready_head = new PCB();
	private static PCB finished_head = new PCB();
	// private static PCB wait_head = new PCB();
	private static Calendar calendar = Calendar.getInstance();
	private static Date date = calendar.getTime();
	private static Bank bank = new Bank();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		running_head.next = null;
		blocked_head.next = null;
		ready_head.next = null;
		finished_head.next = null;
		bank.init();
		menu();
	}

	@SuppressWarnings("static-access")
	public static void menu() {
		System.out.println("1.创建进程");
		System.out.println("2.FCFS服务");
		System.out.println("3.显示服务信息");
		System.out.println("4.显示队列信息");
		System.out.println("5.时间片轮转");
		System.out.println("6.高响应比优先算法");
		System.out.println("7.银行家算法");
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		switch (a) {
		case 1:
			PCB pcb = new PCB();
			pcb.next = null;
			CreatePcb(pcb);
			if (!running_head.hasnext()) {
				running_head.addToTail(pcb);
			} else {
				ready_head.addToTail(pcb);
			}
			menu();
			break;
		case 2:
			while (running_head.getNext() != null) {
				PCB pcb2 = running_head.next;
				finished_head.server(pcb2);
				running_head.setNext(ready_head.deQueue());
			}
			System.out.println("服务结束");
			menu();
			break;
		case 3:
			if (finished_head.next == null) {
				System.out.println("未开始服务！");
			} else {
				finished_head.calculator();
				finished_head.show();
				System.out.println("平均周转时间为：" + finished_head.aver_cycling_time());
				System.out.println("平均带权周转时间：" + finished_head.aver_turnaround_time_right());
			}
			menu();
			break;
		case 4:
			System.out.println("执行队列：");
			if (running_head.getNext() == null) {
				System.out.println("无执行进程！");
			} else {
				running_head.show();
			}
			System.out.println("就绪队列：");
			if (ready_head.getNext() == null) {
				System.out.println("就绪队列为空！");
			} else {
				ready_head.show();
			}
			menu();
			break;
		case 5:
			// 未算最后一次
			System.out.println("请输入时间片大小");
			int time = in.nextInt();
			int hasSeverTime = 0;
			while (running_head.getNext() != null) {
				PCB pcb2 = running_head.next;
				int a1 = pcb2.Cycling(time, hasSeverTime);
				if (a1 == -1) {
					finished_head.addToTail(pcb2);
					running_head.setNext(ready_head.deQueue());
					hasSeverTime = hasSeverTime + time;
				} else if (a1 == -3) {
					ready_head.addToTail(pcb2);
					running_head.setNext(ready_head.deQueue());
					hasSeverTime = hasSeverTime + time;
				} else {
					finished_head.addToTail(pcb2);
					running_head.setNext(ready_head.deQueue());
					hasSeverTime += a1;
				}
			}
			System.out.println("服务结束");
			menu();
			break;
		case 6:
			while (running_head.getNext() != null) {
				PCB pcb2 = running_head.next;
				finished_head.addToTail(pcb2);
				running_head.setNext(ready_head.deQueue());
			}
			finished_head.Cycling_Right();
			menu();
			break;
		case 7:
			bank.safeCheck();
			System.out.println("T0时刻安全队列为：");
			bank.showSafeQueue();
			System.out.println();
			System.out.println("请输入申请资源的进程序列号：");
			int p=in.nextInt();
			System.out.println("请输入申请ABC资源数：");
			int A=in.nextInt();
			int B=in.nextInt();
			int C=in.nextInt();
			int[] request=new int [3];
			request[0]=A;request[1]=B;request[2]=C;
			bank.ruquestRes(request, p);
//			bank.safeCheck();
//			bank.showSafeQueue();
			System.out.println();
			menu();
			break;
		default:
			break;
		}

	}

	private static void CreatePcb(PCB pcb) {
		Scanner scanner = new Scanner(System.in);
		pcb.arrival_time = date;
		date = new Date(date.getTime() + 1000);
		System.out.println("输入进程名称");
		String name = scanner.next();
		pcb.name = name;
		System.out.println("输入进程大小");
		int size = scanner.nextInt();
		pcb.size = size;
		System.out.println("输入服务时间：——S");
		int s = scanner.nextInt();
		pcb.burst_time = s;
		pcb.need_time = pcb.burst_time - pcb.runned_time;
	}

}
