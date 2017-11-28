package teamway.shenzhen.tms9000;

public class ComputerTest {

	public static void main(String[] args) throws Exception {
		ComputerInfo c=new ComputerInfo();
		Computer computer = c.getComputerInfo("E0-05-C5-F1-6D-C0", "f:/");
		double net = computer.getNet();
		double cpu = computer.getCPU();
		System.out.println(net);
		System.out.println("cpu的利用率是"+cpu);
	}
}
