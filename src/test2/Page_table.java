package test2;

import java.text.DecimalFormat;

public class Page_table {
	public int table_number;// ҳ���
	public int physics_number;// �ڴ��ַ��
	public int P;// ����ַ��
	public int success;// �ɹ�����
	public int fail;// ʧ�ܴ���

	public Page_table() {
		// TODO �Զ����ɵĹ��캯�����
		table_number = 0;
		physics_number = 0;
		P = 0;
		success = 0;
		fail = 0;
	}

	public void f() {
		DecimalFormat df = new DecimalFormat("0.00%");
		double b = fail + success;
		double a = fail / b;
		System.out.println(df.format(a));
	}

	@Override
	public String toString() {
		return "Page_table [ҳ��=" + table_number + ", �ڴ��=" + physics_number + ", ����=" + P + "]";
	}

	public int getTable_number() {
		return table_number;
	}

	public void setTable_number(int table_number) {
		this.table_number = table_number;
	}

	public int getPhysics_number() {
		return physics_number;
	}

	public void setPhysics_number(int physics_number) {
		this.physics_number = physics_number;
	}

	public int getP() {
		return P;
	}

	public void setP(int p) {
		P = p;
	}

}
