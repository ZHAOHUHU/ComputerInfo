package teamway.shenzhen.tms9000;

public class ComputerImp {
	
	/*
	 * 
	 */
	public Computer getComputerInfo() throws Exception {
		ComputerInfo c=new ComputerInfo();
             double net = c.getNet();
             double cpu = c.getCpu();
             double memmory = c.getMenmory();
             String name = c.getname();
             double disk = c.getDisk();
		return new Computer(name, cpu, memmory, disk, net);
	}

}
