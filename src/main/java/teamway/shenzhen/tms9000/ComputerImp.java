package teamway.shenzhen.tms9000;

import org.apache.thrift.TException;

import teamway.shenzhen.tms9000.ComputerService.Iface;

public class ComputerImp implements Iface {

	@Override
	public Computer getComputerInfo() {
		ComputerInfo c = new ComputerInfo();
		double net = c.getNet();
		double cpu = c.getCpu();
		double memmory = c.getMenmory();
		String name = c.getname();
		double disk = c.getDisk();
		return new Computer(name, cpu, memmory, disk, net);
	}

}
