package test3;

import java.util.ArrayList;
import java.util.List;

public class IoNode {
	String name;
	String type;
	IoNode next;
	PCB_3 process = null;
	List<PCB_3> waitinglist = new ArrayList<>();
	IoNode parent;

	public IoNode(String name, String type) {
		// TODO 自动生成的构造函数存根
		this.name = name;
		this.type = type;
	}

	public IoNode(String name) {
		// TODO 自动生成的构造函数存根
		this.name = name;
	}

	public IoNode() {
		// TODO 自动生成的构造函数存根
	}

	public boolean hasnext() {
		if (this.next != null) {
			return true;
		} else {
			return false;
		}
	}

	public void addToTail(IoNode ioNode) {
		IoNode t = this;
		while (t.hasNext()) {
			t = t.next;
		}
		t.next = ioNode;
	}

	public boolean hasNext() {
		return this.next == null ? false : true;
	}

	public IoNode minWaitingList(String type) {
		IoNode t = this.next;
		int min = 100000000;
		IoNode minIoNode = t;
		while (t.hasnext()) {
			if (t.waitinglist.size() <= min && t.type.equals(type)) {
				min = t.waitinglist.size();
				minIoNode = t;
			}
			t = t.next;
		}
		return minIoNode;
	}
}
