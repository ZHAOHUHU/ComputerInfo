package teamway.shenzhen.tms9000;

public class ComputerTest {

	public static void main(String[] args) throws Exception {
		ComputerImp c = new ComputerImp();
		Computer computer = c.getComputerInfo();
		String string = computer.toString();
		System.out.println(string);
	}
}