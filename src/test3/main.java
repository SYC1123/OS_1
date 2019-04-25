package test3;

import java.util.Scanner;

import test1.PCB;

public class main {
	static DCT DCT_head = new DCT("0", "0");
	static COCT COCT_head = new COCT("0", "0");
	static CHCT CHCT_head = new CHCT("0");
	static PCB_3 ready_head = new PCB_3("head", 0, "0");
	static PCB_3 block_head = new PCB_3("head", 0, "0");
	static PCB_3 run_head = new PCB_3("head", 0, "0");

	public static void main(String[] args) {
		init();
		menu();

	}

	public static void init() {
		DCT dct = new DCT("�豸1", "I");
		DCT dct2 = new DCT("�豸2", "I");
		DCT dct3 = new DCT("�豸3", "O");
		DCT dct4 = new DCT("�豸4", "O");
		DCT dct5 = new DCT("�豸4", "R");
		DCT_head.next = dct;
		dct.next = dct2;
		dct2.next = dct3;
		dct3.next = dct4;
		dct4.next = dct5;
		dct5.next = null;
		COCT coct = new COCT("������1", "I");
		COCT coct2 = new COCT("������2", "O");
		COCT coct3 = new COCT("������3", "R");
		COCT_head.next = coct;
		coct.next = coct2;
		coct2.next = coct3;
		coct3.next = null;
		dct.parent = (coct);
		dct2.parent = (coct);
		dct3.parent = (coct2);
		dct4.parent = (coct2);
		dct5.parent = (coct3);
		CHCT chct = new CHCT("ͨ��1");
		CHCT chct2 = new CHCT("ͨ��2");
		coct.parent = (chct);
		coct2.parent = (chct);
		coct3.parent = (chct2);
		CHCT_head.next = chct;
		ready_head.setNext(null);
		block_head.setNext(null);
		run_head.setNext(null);
	}

	public static void menu() {
		System.out.println("1.�����½���");
		System.out.println("2.��������");
		System.out.println("3.�����豸");// ִ�н��������豸
		System.out.println("4.�����豸");
		System.out.println("5.�����豸,������");
		System.out.println("6.ɾ���豸");
		System.out.println("7.��ʾ");
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		switch (a) {
		case 1:
			if (run_head.hasNext()) {
				PCB_3 pcb = new PCB_3();
				Create_PCB(pcb);
				ready_head.addToTail(pcb);
			}
			if (!ready_head.hasNext()) {
				PCB_3 pcb = new PCB_3();
				Create_PCB(pcb);
				run_head.setNext(pcb);
			}
			menu();
			break;
		case 2:
			block_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			menu();
			break;
		case 3:
			allocation(run_head.getNext());
			menu();
			break;
		case 4:
			System.out.println("������Ҫ�ͷ��豸�Ľ�������");
			String name = in.next();
			// String name1 = in.nextLine();
			PCB_3 pcb_3 = block_head.findPCB(name);// ���ҽ���
			// System.out.println(pcb_3.getName());
			DCT tDct = DCT_head.findDCT(pcb_3);// �����豸
			System.out.println(tDct.name);
			if (tDct.process == pcb_3) {

				// �����豸
				freeDCT(tDct);
				// ���տ�����
				COCT coct = (COCT) tDct.parent;
				int flag;
				flag = freeCOCT(coct, pcb_3);
				if (flag == 2) {
					CHCT chct = (CHCT) coct.parent;
					if (chct.process == pcb_3) {
						if (chct.waitinglist.size() == 0) {
							chct.process = null;
						} else {// Ϊ�� 1 ���ȴ����̷����������ͨ��
							chct.process = chct.waitinglist.get(0);
							chct.waitinglist.remove(0);
						}
					}
					System.out.println("���ս���");
				}

			} else {
				System.out.println("����");
			}
			
			if (run_head.getNext() == null) {
				run_head.setNext(pcb_3);
			} else {
				ready_head.addToTail(pcb_3);
			}

			System.out.println(
					tDct.process + " " + tDct.parent.process + " " + tDct.parent.parent.process);

			menu();
			break;
		case 5:
			System.out.println("�������豸������������������");
			String dctName = in.nextLine();
			String coctName = in.nextLine();
			String type = in.nextLine();
			DCT dct = new DCT(dctName, type);
			COCT coct = new COCT(coctName, type);
			dct.parent = coct;
			DCT_head.addToTail(dct);
			coct.addNewCOCT(COCT_head);
			menu();
			break;
		case 6:
			System.out.println("�������豸����");
			String name1 = in.next();
			DCT_head.deleteDCT(name1);
			menu();
			break;
		case 7:
			System.out.println("ִ�ж��У�");
			if (run_head.getNext() == null) {
				System.out.println("��ִ�н��̣�");
			} else {
				System.out.println("PCB [name=" + run_head.getNext().getName() + ",��ռ��С=" + run_head.getNext().getSize()
						+ "����=" + run_head.getNext().getType() + "]");
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
			menu();
			break;

		default:
			break;
		}

	}

	public static int freeCOCT(COCT coct, PCB pcb_3) {
		if (coct.process == pcb_3) {
			if (coct.waitinglist.size() == 0) {
				coct.process = null;
			} else {// Ϊ�� 1 ���ȴ����̷����������ͨ��
				coct.process = coct.waitinglist.get(0);
				coct.waitinglist.remove(0);
				coct.addCHCT(CHCT_head, run_head, ready_head, block_head);

			}
			return 2;
		} else {
			System.out.println("���ս���");
			return 1;
		}
	}

	public static void freeDCT(DCT tDct) {
		if (tDct.waitinglist.size() == 0) {
			tDct.process = null;
			// Ѱ�ҿ�����
			System.out.println("Ѱ�ҿ�����" + tDct.parent.name);
		} else {// Ϊ�� 1 ���ȴ����̷����豸����������ͨ��
			// allocation(tDct.waitinglist.get(0));
			tDct.process = tDct.waitinglist.get(0);
			tDct.waitinglist.remove(0);
			COCT coct = tDct.addCOCT(COCT_head, run_head, ready_head, block_head);
			if (coct != null) {
				coct.addCHCT(CHCT_head, run_head, ready_head, block_head);
			}
		}
	}

//�����豸����������ͨ��
	public static void allocation(PCB_3 pcb_3) {
		DCT dct = pcb_3.addDCT(DCT_head, run_head, ready_head, block_head);
		if (dct != null) {
			COCT coct = dct.addCOCT(COCT_head, run_head, ready_head, block_head);
			if (coct != null) {
				coct.addCHCT(CHCT_head, run_head, ready_head, block_head);
			}
		}
	}

	public static void Create_PCB(PCB_3 pcb) {
		Scanner in = new Scanner(System.in);
		System.out.println("�������������");
		String name = in.nextLine();
		System.out.println("�������������");
		String type = in.nextLine();
		System.out.println("��������̴�С");
		int size = in.nextInt();
		pcb.setName(name);
		pcb.setSize(size);
		pcb.setType(type);
		pcb.setNext(null);
	}
}
