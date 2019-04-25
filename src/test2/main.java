package test2;

import java.awt.Menu;
import java.util.Scanner;

import test1.PCB;

public class main {
	static bitmap bitmap = new bitmap();

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������

		bitmap.init_bitmap();
		bitmap.show_bitmap();
		PCB_2 ready_head = new PCB_2("head", 0);
		ready_head.setNext(null);
		PCB_2 block_head = new PCB_2("head", 0);
		block_head.setNext(null);
		PCB_2 run_head = new PCB_2("head", 0);
		run_head.setNext(null);
		menu(ready_head, block_head, run_head);
	}

	public static void menu(PCB_2 ready_head, PCB_2 block_head, PCB_2 run_head) {
		System.out.println("���̿���ģ��");
		System.out.println("1.�����½���");
		System.out.println("2.ִ�н���ʱ��Ƭ��");// ��ִ�ж��н���������β ��������ͷ��ִ�ж���
		System.out.println("3.����ִ�н���");// ִ�ж��н�������β ������ͷ��ִ�ж�
		System.out.println("4.���ѵ�һ����������");// ������ͷ��������β
		System.out.println("5.��ִֹ�н���");// ������ͷ��ִ�ж� �ڴ����·���
		System.out.println("6.��ʾ����,λʾͼ");
		System.out.println("7.��ʾҳ��");
		System.out.println("8.��ַ�任");
		System.out.println("9.�û��㷨");
		System.out.println("10.��ѯȱҳ��");
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		switch (a) {
		case 1:
			if (run_head.hasNext()) {
				PCB_2 pcb = new PCB_2();
				Create_PCB(pcb);
				int p = bitmap.add_bitmap(pcb);
				if (p == 1) {
					ready_head.addToTail(pcb);
				}
			} else {
				PCB_2 pcb = new PCB_2();
				Create_PCB(pcb);
				int p = bitmap.add_bitmap(pcb);
				if (p == 1) {
					run_head.setNext(pcb);
				}
			}
			menu(ready_head, block_head, run_head);
			break;
		case 2:
			ready_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			menu(ready_head, block_head, run_head);
			break;
		case 3:
			block_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			menu(ready_head, block_head, run_head);
			break;
		case 4:
			PCB_2 tPcb = block_head.deQueue();
			if (tPcb == null) {
				System.out.println("��ǰ���������̣�");
			} else {
				if (run_head.getNext() == null) {
					run_head.setNext(tPcb);
				} else {
					ready_head.addToTail(tPcb);
				}
			}
			menu(ready_head, block_head, run_head);
			break;
		case 5:

			if (run_head.getNext() == null) {
				System.out.println("��ǰ�޿���ֹ���̣�");
			} else {
				bitmap.end_bitmap(run_head.getNext());
				run_head.setNext(ready_head.deQueue());
			}
			menu(ready_head, block_head, run_head);
			break;
		case 6:
			System.out.println("ִ�ж��У�");
			if (run_head.getNext() == null) {
				System.out.println("��ִ�н��̣�");
			} else {
				System.out.println(
						"PCB [name=" + run_head.getNext().getName() + ", ��ռ��С=" + run_head.getNext().getSize() + "]");
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

			bitmap.show_bitmap();
			menu(ready_head, block_head, run_head);
		case 7:
			if (run_head.next == null) {
				System.out.println("��ִ�н��̣�");
			} else {
				for (int i = 0; i < run_head.next.getPage_table().length; i++) {
					System.out.println(run_head.next.page_table[i].toString());
				}
//				System.out.println("�Ƚ��ȳ��㷨���ʶ��У�");
//				run_head.next.showFIFO();
//				System.out.println();
			}
			menu(ready_head, block_head, run_head);
		case 8:
			System.out.println("������ִ�ж����ڽ��̴�С:" + run_head.next.getSize() + "֮�ڵ��߼���ַ");
			int t = in.nextInt();
			if (run_head.next.page_table[t / 1024].physics_number == -1) {
				// �û��㷨
				// run_head.next.page_table[t/1024].fail++;
				System.out.println("�����ʵĵ�ַ�鲻���ڴ棬��ʹ��FIFO�㷨�����û�");
				run_head.next.exchange(bitmap, t / 1024);
				System.out.println("�����ַΪ��" + (run_head.next.page_table[t / 1024].physics_number * 1024 + t % 1024));
			} else {
				// run_head.next.page_table[t/1024].success++;
				System.out.println("�����ַΪ��" + (run_head.next.page_table[t / 1024].physics_number * 1024 + t % 1024));
			}
			menu(ready_head, block_head, run_head);
		case 9:
			System.out.println("��ѡ���û��㷨��");
			System.out.println("1.LRU");
			System.out.println("2.FIFO");
			int t1 = in.nextInt();
			switch (t1) {
			case 1:
				run_head.next.LRU.clear();
				run_head.next.LRU.add(run_head.next.FIFO[2]);
				run_head.next.LRU.add(run_head.next.FIFO[1]);
				run_head.next.LRU.add(run_head.next.FIFO[0]);
				System.out.println("������ҳ������");
				for (int i = 0; i < run_head.next.page_table.length; i++) {
					System.out.print("ҳ��" + i + "\t");
				}
				System.out.println();
				while (in.hasNextInt()) {
					int w = in.nextInt();
					run_head.next.exchange_LRU(bitmap, w);
					for(int i=0;i<run_head.next.LRU.size();i++) {
						System.out.println(run_head.next.LRU.get(i));
					}
				}
				break;
			case 2:
				// String[] numbers = in.nextLine().split(" ");
				System.out.println("������ҳ������");
				for (int i = 0; i < run_head.next.page_table.length; i++) {
					System.out.print("ҳ��" + i + "\t");
				}
				System.out.println();
				while (in.hasNextInt()) {
					int w = in.nextInt();
					run_head.next.exchange_FIFO(bitmap, w);
					for(int i=0;i<3;i++) {
						System.out.println(run_head.next.FIFO[i]);
					}
				}
				break;
			default:
				break;
			}
			menu(ready_head, block_head, run_head);
		case 10:
			System.out.println("������ҳ��");
			for (int i = 0; i < run_head.next.page_table.length; i++) {
				System.out.print("ҳ��" + i + "\t");
			}
			System.out.println();
			int e=in.nextInt();
			run_head.next.page_table[e].f();
			menu(ready_head, block_head, run_head);
		default:
			break;
		}
	}

	public static void Create_PCB(PCB_2 pcb) {
		Scanner in = new Scanner(System.in);
		System.out.println("������������ƣ�����");
		String name = in.next();
		int size = in.nextInt();
		pcb.setName(name);
		pcb.setSize(size);
		pcb.setNext(null);
	}

}
