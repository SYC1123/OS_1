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
		DCT dct = new DCT("设备1", "I");
		DCT dct2 = new DCT("设备2", "I");
		DCT dct3 = new DCT("设备3", "O");
		DCT dct4 = new DCT("设备4", "O");
		DCT dct5 = new DCT("设备4", "R");
		DCT_head.next = dct;
		dct.next = dct2;
		dct2.next = dct3;
		dct3.next = dct4;
		dct4.next = dct5;
		dct5.next = null;
		COCT coct = new COCT("控制器1", "I");
		COCT coct2 = new COCT("控制器2", "O");
		COCT coct3 = new COCT("控制器3", "R");
		COCT_head.next = coct;
		coct.next = coct2;
		coct2.next = coct3;
		coct3.next = null;
		dct.parent = (coct);
		dct2.parent = (coct);
		dct3.parent = (coct2);
		dct4.parent = (coct2);
		dct5.parent = (coct3);
		CHCT chct = new CHCT("通道1");
		CHCT chct2 = new CHCT("通道2");
		coct.parent = (chct);
		coct2.parent = (chct);
		coct3.parent = (chct2);
		CHCT_head.next = chct;
		ready_head.setNext(null);
		block_head.setNext(null);
		run_head.setNext(null);
	}

	public static void menu() {
		System.out.println("1.创建新进程");
		System.out.println("2.阻塞进程");
		System.out.println("3.申请设备");// 执行进程申请设备
		System.out.println("4.回收设备");
		System.out.println("5.创建设备,控制器");
		System.out.println("6.删除设备");
		System.out.println("7.显示");
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
			System.out.println("输入你要释放设备的进程名：");
			String name = in.next();
			// String name1 = in.nextLine();
			PCB_3 pcb_3 = block_head.findPCB(name);// 查找进程
			// System.out.println(pcb_3.getName());
			DCT tDct = DCT_head.findDCT(pcb_3);// 查找设备
			System.out.println(tDct.name);
			if (tDct.process == pcb_3) {

				// 回收设备
				freeDCT(tDct);
				// 回收控制器
				COCT coct = (COCT) tDct.parent;
				int flag;
				flag = freeCOCT(coct, pcb_3);
				if (flag == 2) {
					CHCT chct = (CHCT) coct.parent;
					if (chct.process == pcb_3) {
						if (chct.waitinglist.size() == 0) {
							chct.process = null;
						} else {// 为第 1 个等待进程分配控制器、通道
							chct.process = chct.waitinglist.get(0);
							chct.waitinglist.remove(0);
						}
					}
					System.out.println("回收结束");
				}

			} else {
				System.out.println("出错");
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
			System.out.println("请输入设备名，控制器名，类型");
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
			System.out.println("请输入设备名：");
			String name1 = in.next();
			DCT_head.deleteDCT(name1);
			menu();
			break;
		case 7:
			System.out.println("执行队列：");
			if (run_head.getNext() == null) {
				System.out.println("无执行进程！");
			} else {
				System.out.println("PCB [name=" + run_head.getNext().getName() + ",所占大小=" + run_head.getNext().getSize()
						+ "类型=" + run_head.getNext().getType() + "]");
			}
			System.out.println("就绪队列：");
			if (ready_head.getNext() == null) {
				System.out.println("就绪队列为空！");
			} else {
				ready_head.show();
			}
			System.out.println("阻塞队列：");
			if (block_head.getNext() == null) {
				System.out.println("阻塞队列为空！");
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
			} else {// 为第 1 个等待进程分配控制器、通道
				coct.process = coct.waitinglist.get(0);
				coct.waitinglist.remove(0);
				coct.addCHCT(CHCT_head, run_head, ready_head, block_head);

			}
			return 2;
		} else {
			System.out.println("回收结束");
			return 1;
		}
	}

	public static void freeDCT(DCT tDct) {
		if (tDct.waitinglist.size() == 0) {
			tDct.process = null;
			// 寻找控制器
			System.out.println("寻找控制器" + tDct.parent.name);
		} else {// 为第 1 个等待进程分配设备、控制器、通道
			// allocation(tDct.waitinglist.get(0));
			tDct.process = tDct.waitinglist.get(0);
			tDct.waitinglist.remove(0);
			COCT coct = tDct.addCOCT(COCT_head, run_head, ready_head, block_head);
			if (coct != null) {
				coct.addCHCT(CHCT_head, run_head, ready_head, block_head);
			}
		}
	}

//分配设备、控制器、通道
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
		System.out.println("请输入进程名称");
		String name = in.nextLine();
		System.out.println("请输入进程类型");
		String type = in.nextLine();
		System.out.println("请输入进程大小");
		int size = in.nextInt();
		pcb.setName(name);
		pcb.setSize(size);
		pcb.setType(type);
		pcb.setNext(null);
	}
}
