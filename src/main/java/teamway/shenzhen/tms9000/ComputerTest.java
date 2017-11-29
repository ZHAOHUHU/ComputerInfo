package teamway.shenzhen.tms9000;

public class ComputerTest {

	public static void main(String[] args) throws Exception {
		ComputerImp c=new ComputerImp();
		c.initTimeNet("E0-05-C5-F1-6D-C0");
		Computer computer = c.getComputerInfo("E0-05-C5-F1-6D-C0", null);
		double net = computer.getNet();
		System.out.println("网速是"+net);
	}
}