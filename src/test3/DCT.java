package test3;

public class DCT extends IoNode {

	public DCT(String name, String type) {
		super(name, type);
		// TODO 自动生成的构造函数存根
	}

	public DCT() {
		// TODO 自动生成的构造函数存根
		super();
	}

	public COCT addCOCT(COCT coct_head, PCB_3 run_head, PCB_3 ready_head, PCB_3 block_head) {
		if (this.parent.process == null) {
			this.parent.process = this.process;
			System.out.println("控制器分配成功   " + this.parent.name);
			// 添加通道
			return (COCT) this.parent;
		} else {
			this.parent.waitinglist.add(this.process);
			block_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			System.out.println("控制器分配失败   " + ",大小=" + this.parent.waitinglist.size());
			return null;
		}

	}
	
	public void deleteDCT(String name) {
		DCT t=(DCT) this.next;
		DCT be=this;
		while(t.hasNext()) {
			if(t.name.equals(name)) {
				be.next=t.next;
			}
			t=(DCT) t.next;
			be=(DCT) be.next;
		}
	}

	public DCT findDCT(PCB_3 name) {
		DCT t = (DCT) this.next;
		while (t.hasnext()) {
			if (t.process == name) {
				return t;
			}
			t = (DCT) t.next;
		}
		return null;
	}

	public DCT findMinWaitingList(String type) {
		DCT t = (DCT) this.next;
		int min = 1000000;
		DCT m = t;
		while (t.hasnext()) {
			if (t.waitinglist.size() <= min && t.type.equals(type)) {
				min = t.waitinglist.size();
				m = t;
			}
			t = (DCT) t.next;
		}
		return m;
	}

}
