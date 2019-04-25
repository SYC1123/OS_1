package test3;

public class COCT extends IoNode {

	public COCT(String name, String type) {
		super(name, type);
		// TODO 自动生成的构造函数存根
	}

	public COCT() {
		// TODO 自动生成的构造函数存根
		super();
	}

	public void addNewCOCT(COCT head) {
		COCT coct = (COCT) head.next;
		while (coct.hasnext()) {
			if (coct.type.equals(this.type)) {
				this.parent = coct.parent;
				break;
			}
		}
	}

	public CHCT addCHCT(CHCT chct_head, PCB_3 run_head, PCB_3 ready_head, PCB_3 block_head) {
		if (this.parent.process == null) {
			this.parent.process = this.process;
			System.out.println("通道分配成功   " + this.parent.name);
			block_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			return (CHCT) this.parent;

		} else {
			this.parent.waitinglist.add(this.process);
			block_head.addToTail(run_head.getNext());
			run_head.setNext(ready_head.deQueue());
			System.out.println("通道分配失败   " + ",大小=" + this.parent.waitinglist.size());
			return null;

		}

	}

}
