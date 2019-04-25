package test1;

import java.util.List;
import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		PCB ready_head = new PCB("head", 0);
		ready_head.setNext(null);
		PCB block_head = new PCB("head", 0);
		block_head.setNext(null);
		PCB run_head = new PCB("head", 0);
		run_head.setNext(null);
		Scanner in = new Scanner(System.in);
		System.out.print("����������ڴ��С��");
		int s = in.nextInt();
		System.out.print("������ϵͳ����ռ�ڴ��С��");
		int size = in.nextInt();
		System.out.println("��ʹ�õĵ����ڴ�ռ��СΪ" + s + "KB");
		System.out.println("ϵͳ���ڴ��ַΪ1KB����" + size + "KB,��СΪ" + size + "KB,�û����ڴ��ַΪ" + (size + 1) + "KB����" + s + "KB,��СΪ"
				+ (s - size) + "KB");
		System.out.println("�������ʼ����ɣ���ѡ���ܣ���");
		Memory memory_head = new Memory(-1, 0);
		Memory p = new Memory(size, s - size);
		memory_head.setNext(p);
		p.setBefore(memory_head);
		menu(ready_head, block_head, run_head, memory_head);
	}

	public static void menu(PCB ready_head, PCB block_head, PCB run_head, Memory memory_head) {
		System.out.println("���̿���ģ��");
		System.out.println("1.�����½���");
		System.out.println("2.ִ�н���ʱ��Ƭ��");// ��ִ�ж��н���������β ��������ͷ��ִ�ж���
		System.out.println("3.����ִ�н���");// ִ�ж��н�������β ������ͷ��ִ�ж�
		System.out.println("4.���ѵ�һ����������");// ������ͷ��������β
		System.out.println("5.��ִֹ�н���");// ������ͷ��ִ�ж� �ڴ����·���
		System.out.println("6.��ʾ");
		System.out.println("7.����");
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		switch (a) {
		case 1:
			if (run_head.hasNext()) {
				PCB pcb = new PCB();
				Create_PCB(pcb);
				int p = memory_head.addPCB(pcb);
				if (p == 1) {
					ready_head.addToTail(pcb);
				}
			}
			if (!ready_head.hasNext()) {
				PCB pcb = new PCB();
				Create_PCB(pcb);
				pcb.setEnd(pcb.getBegin() + pcb.getSize());
				int p = memory_head.addPCB(pcb);
				if (p == 1) {
					run_head.setNext(pcb);
				}

			}
			// System.out.println(memory_head.getNext().getSize() + " " +
			// memory_head.getNext().getBegin());
			// System.out.println(memory_head.getNext().getNext());
			menu(ready_head, block_head, run_head, memory_head);
			break;
		case 2:
			ready_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			menu(ready_head, block_head, run_head, memory_head);
			break;
		case 3:
			block_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			menu(ready_head, block_head, run_head, memory_head);
			break;
		case 4:
			PCB tPcb=block_head.deQueue();
			if ( tPcb== null) {
				System.out.println("��ǰ���������̣�");
			} else {
				if (run_head.getNext() == null) {
					run_head.setNext(tPcb);
				} else {
					ready_head.addToTail(tPcb);
				}
			}
			menu(ready_head, block_head, run_head, memory_head);
			break;
		case 5:
			if (run_head.getNext() == null) {
				System.out.println("��ǰ�޿���ֹ���̣�");
			} else {
				memory_head.endPCB(run_head.getNext());
				run_head.setNext(ready_head.deQueue());
			}
			menu(ready_head, block_head, run_head, memory_head);
			break;
		case 6:
			System.out.println("ִ�ж��У�");
			if (run_head.getNext() == null) {
				System.out.println("��ִ�н��̣�");
			} else {
				System.out.println("PCB [name=" + run_head.getNext().getName() + ", ��ʼ��ַ="
						+ (run_head.getNext().getBegin() + 1) + ", ������ַ=" + run_head.getNext().getEnd() + ", ��ռ��С="
						+ run_head.getNext().getSize() + "]");
			}
			System.out.println("�������У�");
			if (ready_head.getNext() == null) {
				System.out.println("��������Ϊ�գ�");
			} else {
				ready_head.show();
			}
			System.out.println("�������У�");
			if (block_head.getNext() == null) {
				System.out.println("��������Ϊ�գ�");
			} else {
				block_head.show();
			}
			System.out.println("�ڴ������");
			if (memory_head.getNext().getSize() == 0) {
				System.out.println("ϵͳ�޿�������");
			} else {
				memory_head.show();
			}
			menu(ready_head, block_head, run_head, memory_head);
		case 7:
			System.exit(0);
		default:
			break;
		}
	}

	public static void Create_PCB(PCB pcb) {
		Scanner in = new Scanner(System.in);
		System.out.println("������������ƣ�����");
		String name = in.next();
		int size = in.nextInt();
		pcb.setName(name);
		pcb.setSize(size);
		pcb.setNext(null);
	}

}
