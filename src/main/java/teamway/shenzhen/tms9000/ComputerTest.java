package teamway.shenzhen.tms9000;

public class ComputerTest {

	public static void main(String[] args) throws Exception {
		ComputerInfo c=new ComputerInfo();
		Computer computer = c.getComputerInfo("BC-AE-C5-6E-B8-FA", "f:/");
		double net = computer.getNet();
		System.out.println(net);
	}
}
