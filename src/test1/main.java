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
		System.out.print("请输入电脑内存大小：");
		int s = in.nextInt();
		System.out.print("请输入系统区所占内存大小：");
		int size = in.nextInt();
		System.out.println("您使用的电脑内存空间大小为" + s + "KB");
		System.out.println("系统区内存地址为1KB——" + size + "KB,大小为" + size + "KB,用户区内存地址为" + (size + 1) + "KB——" + s + "KB,大小为"
				+ (s - size) + "KB");
		System.out.println("计算机初始化完成！请选择功能！！");
		Memory memory_head = new Memory(-1, 0);
		Memory p = new Memory(size, s - size);
		memory_head.setNext(p);
		p.setBefore(memory_head);
		menu(ready_head, block_head, run_head, memory_head);
	}

	public static void menu(PCB ready_head, PCB block_head, PCB run_head, Memory memory_head) {
		System.out.println("进程控制模拟");
		System.out.println("1.创建新进程");
		System.out.println("2.执行进程时间片到");// 从执行队列进就绪队列尾 就绪队列头进执行队列
		System.out.println("3.阻塞执行进程");// 执行队列进阻塞队尾 就绪队头进执行队
		System.out.println("4.唤醒第一个阻塞进程");// 阻塞队头进就绪队尾
		System.out.println("5.终止执行进程");// 就绪队头进执行队 内存重新分配
		System.out.println("6.显示");
		System.out.println("7.结束");
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
				System.out.println("当前无阻塞进程！");
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
				System.out.println("当前无可终止进程！");
			} else {
				memory_head.endPCB(run_head.getNext());
				run_head.setNext(ready_head.deQueue());
			}
			menu(ready_head, block_head, run_head, memory_head);
			break;
		case 6:
			System.out.println("执行队列：");
			if (run_head.getNext() == null) {
				System.out.println("无执行进程！");
			} else {
				System.out.println("PCB [name=" + run_head.getNext().getName() + ", 起始地址="
						+ (run_head.getNext().getBegin() + 1) + ", 结束地址=" + run_head.getNext().getEnd() + ", 所占大小="
						+ run_head.getNext().getSize() + "]");
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
			if (memory_head.getNext().getSize() == 0) {
				System.out.println("系统无空闲区！");
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
		System.out.println("请输入进程名称，长度");
		String name = in.next();
		int size = in.nextInt();
		pcb.setName(name);
		pcb.setSize(size);
		pcb.setNext(null);
	}

}
