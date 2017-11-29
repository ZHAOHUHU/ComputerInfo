package teamway.shenzhen.tms9000;

public class ComputerImp {
	/*
	 * 初始化
	 */
	public void initTimeNet(String macaddress) throws Exception {
		ComputerInfo c=new ComputerInfo();
		c.TimeNet(macaddress);
	}

	/*
	 * 
	 */
	public Computer getComputerInfo(String macaddress, String diskName) throws Exception {
		ComputerInfo c=new ComputerInfo();
             double net = c.getTimeNet(macaddress);
             Computer computer = new Computer();
             computer.setNet(net);
		return computer;
	}

}
