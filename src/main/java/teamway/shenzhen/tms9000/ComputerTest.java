package teamway.shenzhen.tms9000;

public class ComputerTest {

	public static void main(String[] args) throws Exception {
		ComputerInfo c=new ComputerInfo();
		Computer computer = c.getComputerInfo("E0-05-C5-F1-6D-C0", "f:/");
		String string = computer.toString();
		System.out.println(string);
	}
}