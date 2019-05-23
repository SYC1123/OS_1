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
		System.out.println("1.��������");
		System.out.println("2.FCFS����");
		System.out.println("3.��ʾ������Ϣ");
		System.out.println("4.��ʾ������Ϣ");
		System.out.println("5.ʱ��Ƭ��ת");
		System.out.println("6.����Ӧ�������㷨");
		System.out.println("7.���м��㷨");
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
			System.out.println("�������");
			menu();
			break;
		case 3:
			if (finished_head.next == null) {
				System.out.println("δ��ʼ����");
			} else {
				finished_head.calculator();
				finished_head.show();
				System.out.println("ƽ����תʱ��Ϊ��" + finished_head.aver_cycling_time());
				System.out.println("ƽ����Ȩ��תʱ�䣺" + finished_head.aver_turnaround_time_right());
			}
			menu();
			break;
		case 4:
			System.out.println("ִ�ж��У�");
			if (running_head.getNext() == null) {
				System.out.println("��ִ�н��̣�");
			} else {
				running_head.show();
			}
			System.out.println("�������У�");
			if (ready_head.getNext() == null) {
				System.out.println("��������Ϊ�գ�");
			} else {
				ready_head.show();
			}
			menu();
			break;
		case 5:
			// δ�����һ��
			System.out.println("������ʱ��Ƭ��С");
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
			System.out.println("�������");
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
			System.out.println("T0ʱ�̰�ȫ����Ϊ��");
			bank.showSafeQueue();
			System.out.println();
			System.out.println("������������Դ�Ľ������кţ�");
			int p=in.nextInt();
			System.out.println("����������ABC��Դ����");
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
		System.out.println("�����������");
		String name = scanner.next();
		pcb.name = name;
		System.out.println("������̴�С");
		int size = scanner.nextInt();
		pcb.size = size;
		System.out.println("�������ʱ�䣺����S");
		int s = scanner.nextInt();
		pcb.burst_time = s;
		pcb.need_time = pcb.burst_time - pcb.runned_time;
	}

}
