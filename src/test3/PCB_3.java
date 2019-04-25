package test3;

import test1.PCB;

public class PCB_3 extends PCB {
	public PCB_3 next;
	public String type;

	public PCB_3(String string, int i, String type) {
		// TODO 自动生成的构造函数存根
		super(string, i);
		this.type = type;
	}

	public PCB_3() {
		// TODO 自动生成的构造函数存根
	}

	public void show() {
		PCB_3 t = this;
		while (t.hasNext()) {
			t = t.getNext();
			System.out.println("PCB [name=" + t.getName() + ", 所占大小=" + t.getSize() + ", 类型=" + t.getType() + "]");
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean hasNext() {
		return this.next == null ? false : true;
	}

	public DCT addDCT(DCT dct_head, PCB_3 run_head, PCB_3 ready_head, PCB_3 block_head) {
		DCT t = (DCT) dct_head.next;
		int flag = 0;
		while (t.hasnext()) {
			if (t.process == null && t.type.equals(this.type)) {
				t.process = this;

				System.out.println("设备分配成功   " + t.name);
				flag = 1;
				return t;
				// 寻找控制器
			}
			t = (DCT) t.next;
		}
		if (flag == 0) {
			DCT min = (DCT) dct_head.minWaitingList(this.type);
			min.waitinglist.add(this);
			block_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());

			System.out.println("设备分配失败   " + min.name + ",大小=" + min.waitinglist.size());

		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PCB_3 other = (PCB_3) obj;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public void addToTail(PCB_3 pcb) {
		PCB_3 t = this;
		while (t.hasNext()) {
			t = t.next;
		}
		t.next = pcb;
	}

	public PCB_3 getNext() {
		return next;
	}

	public void setNext(PCB_3 next) {
		this.next = next;
	}

	public PCB_3 findPCB(String name) {
		PCB_3 pcb_3 = this.next;
		PCB_3 be = this;
		if (pcb_3.next == null) {
			System.out.println(111111);
			if (pcb_3.name.equals(name)) {
				be.next = pcb_3.next;
				return pcb_3;
			}
		} else {
			while (pcb_3.hasNext()) {
				if (pcb_3.name.equals(name)) {
					be.next = pcb_3.next;
					return pcb_3;
				}
				be = be.next;
				pcb_3 = pcb_3.next;
			}
		}
		return null;
	}

	// 出队
	public PCB_3 deQueue() {
		PCB_3 t = this.next;
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
