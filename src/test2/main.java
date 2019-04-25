package test2;

import java.awt.Menu;
import java.util.Scanner;

import test1.PCB;

public class main {
	static bitmap bitmap = new bitmap();

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

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
		System.out.println("进程控制模拟");
		System.out.println("1.创建新进程");
		System.out.println("2.执行进程时间片到");// 从执行队列进就绪队列尾 就绪队列头进执行队列
		System.out.println("3.阻塞执行进程");// 执行队列进阻塞队尾 就绪队头进执行队
		System.out.println("4.唤醒第一个阻塞进程");// 阻塞队头进就绪队尾
		System.out.println("5.终止执行进程");// 就绪队头进执行队 内存重新分配
		System.out.println("6.显示进程,位示图");
		System.out.println("7.显示页表");
		System.out.println("8.地址变换");
		System.out.println("9.置换算法");
		System.out.println("10.查询缺页率");
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
				System.out.println("当前无阻塞进程！");
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
				System.out.println("当前无可终止进程！");
			} else {
				bitmap.end_bitmap(run_head.getNext());
				run_head.setNext(ready_head.deQueue());
			}
			menu(ready_head, block_head, run_head);
			break;
		case 6:
			System.out.println("执行队列：");
			if (run_head.getNext() == null) {
				System.out.println("无执行进程！");
			} else {
				System.out.println(
						"PCB [name=" + run_head.getNext().getName() + ", 所占大小=" + run_head.getNext().getSize() + "]");
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
			System.out.println("内存空闲区");

			bitmap.show_bitmap();
			menu(ready_head, block_head, run_head);
		case 7:
			if (run_head.next == null) {
				System.out.println("无执行进程！");
			} else {
				for (int i = 0; i < run_head.next.getPage_table().length; i++) {
					System.out.println(run_head.next.page_table[i].toString());
				}
//				System.out.println("先进先出算法访问队列：");
//				run_head.next.showFIFO();
//				System.out.println();
			}
			menu(ready_head, block_head, run_head);
		case 8:
			System.out.println("请输入执行队列内进程大小:" + run_head.next.getSize() + "之内的逻辑地址");
			int t = in.nextInt();
			if (run_head.next.page_table[t / 1024].physics_number == -1) {
				// 置换算法
				// run_head.next.page_table[t/1024].fail++;
				System.out.println("您访问的地址块不在内存，已使用FIFO算法进行置换");
				run_head.next.exchange(bitmap, t / 1024);
				System.out.println("物理地址为：" + (run_head.next.page_table[t / 1024].physics_number * 1024 + t % 1024));
			} else {
				// run_head.next.page_table[t/1024].success++;
				System.out.println("物理地址为：" + (run_head.next.page_table[t / 1024].physics_number * 1024 + t % 1024));
			}
			menu(ready_head, block_head, run_head);
		case 9:
			System.out.println("请选择置换算法：");
			System.out.println("1.LRU");
			System.out.println("2.FIFO");
			int t1 = in.nextInt();
			switch (t1) {
			case 1:
				run_head.next.LRU.clear();
				run_head.next.LRU.add(run_head.next.FIFO[2]);
				run_head.next.LRU.add(run_head.next.FIFO[1]);
				run_head.next.LRU.add(run_head.next.FIFO[0]);
				System.out.println("请输入页号序列");
				for (int i = 0; i < run_head.next.page_table.length; i++) {
					System.out.print("页号" + i + "\t");
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
				System.out.println("请输入页号序列");
				for (int i = 0; i < run_head.next.page_table.length; i++) {
					System.out.print("页号" + i + "\t");
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
			System.out.println("请输入页号");
			for (int i = 0; i < run_head.next.page_table.length; i++) {
				System.out.print("页号" + i + "\t");
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
		System.out.println("请输入进程名称，长度");
		String name = in.next();
		int size = in.nextInt();
		pcb.setName(name);
		pcb.setSize(size);
		pcb.setNext(null);
	}

}
