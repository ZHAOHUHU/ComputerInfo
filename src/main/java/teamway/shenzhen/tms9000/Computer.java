package teamway.shenzhen.tms9000;

public class Computer implements Runnable{
	/*
	 * CPU 内存 硬盘 网络带宽（多个线程轮番访问） 计算机名字
	 */
	//主机名称
	private String name;
	//CPU使用率
	private double CPU;
	//内存使用率
	private double menmory;
	//硬盘使用率
	private double disk;
	//网络带宽
	private double net;
	public Computer() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCPU() {
		return CPU;
	}
	public void setCPU(double cPU) {
		CPU = cPU;
	}
	public double getMenmory() {
		return menmory;
	}
	public void setMenmory(double menmory) {
		this.menmory = menmory;
	}
	public double getDisk() {
		return disk;
	}
	public void setDisk(double disk) {
		this.disk = disk;
	}
	public double getNet() {
		return net;
	}
	public void setNet(double net) {
		this.net = net;
	}
	public Computer(String name, double cPU, double menmory, double disk, double net) {
		super();
		this.name = name;
		CPU = cPU;
		this.menmory = menmory;
		this.disk = disk;
		this.net = net;
	}
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
