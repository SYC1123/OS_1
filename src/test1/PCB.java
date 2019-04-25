package test1;

import java.util.Scanner;

public class PCB {
	protected String name;
	protected int begin;
	protected int end;
	protected int size;
	protected PCB next;

	public PCB(String name, int size) {
		// TODO 自动生成的构造函数存根
		this.name = name;
		this.size = size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + begin;
		result = prime * result + end;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PCB other = (PCB) obj;
		if (begin != other.begin)
			return false;
		if (end != other.end)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	public PCB() {
		// TODO 自动生成的构造函数存根
	}

	public PCB(PCB pcb) {
		// TODO 自动生成的构造函数存根
		this.name=pcb.name;
		this.size=pcb.size;
	}

	public String getName() {
		return name;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PCB getNext() {
		return next;
	}

	public void setNext(PCB next) {
		this.next = next;
	}

	public boolean hasNext() {
		return this.next == null ? false : true;
	}

	public void addToTail(PCB pcb) {
		PCB t = this;
		while (t.hasNext()) {
			t = t.next;
		}
		t.next = pcb;

	}

	public void show() {
		PCB t = this;
		while (t.hasNext()) {
			t = t.next;
			System.out.println(
					"PCB [name=" + t.name + ", 起始地址=" + (t.begin+1) + ", 结束地址=" + t.end + ", 所占大小=" + t.size + "]");
		}
	}

	// 出队
	public PCB deQueue() {
		PCB t = this.next;
		if (t == null) {
			System.out.println("队列无进程！");
			return null;
		} else {
			this.next = t.next;
			t.next = null;
			return t;
		}
		
	}

}
