package teamway.shenzhen.tms9000;

import java.io.File;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.google.common.io.Resources;

public class ComputerInfo {
	/*
	 * 初始化sigar对象
	 */
	static List<Object> list = new ArrayList<Object>();
	final static Computer computer = new Computer();
	public final static Sigar sigar = initSigar();
	private String macaddress;

	private static Sigar initSigar() {
		try {
			String file = Resources.getResource("sigar/.sigar_shellrc").getFile();
			File classPath = new File(file).getParentFile();

			String path = System.getProperty("java.library.path");
			/*
			 * 防止重复的加载到java.library.path,判断一下
			 */
			if (!path.contains(classPath.getCanonicalPath())) {

				if (OsCheck.getOperatingSystenType() == OsCheck.OSType.Windows) {
					path += ";" + classPath.getCanonicalPath();
				} else {
					path += ":" + classPath.getCanonicalPath();
				}
				System.setProperty("java.library.path", path);
			}

			return new Sigar();
		} catch (Exception e) {
			return null;
		}
	}

	public double getTimeNet(String macaddress) {

		if (list.size() ==3) {
          System.out.println("第二个元素是"+list.get(2));
			return (Double) list.get(1);
		}
		return getNet(macaddress);
	}

	/*
	 * 
	 * =================================
	 * 
	 */
	public void TimeNet(final String macaddress) throws Exception {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				double net = getNet(macaddress);
				System.out.println(net);
				list.add(net);
				System.out.println("集合的大小是"+list.size());
				if (list.size() == 3) {
					list.remove(0);
				}
			}
		};

		timer.schedule(task, 0, 1000);

	}
	// ===============================================================================================

	/*
	 * 
	 * 获取网络带宽的使用率
	 */
	public double getNet(String macaddress) {
		this.macaddress = macaddress;
		String name = "";
		try {
			long receiveBytes1 = 0L;
			long sendBytes1 = 0L;
			long receiveBytes2 = 0L;
			long sendBytes2 = 0L;
			double speed = 0; // 网络带宽Mbps
			String[] ifNames = sigar.getNetInterfaceList();

			for (int i = 0; i < +ifNames.length; i++) {
				// name网卡名字
				name = ifNames[i];
				NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
				// 格式的替换
				String mac = ifconfig.getHwaddr();
				String all = mac.replaceAll(":", "-");
				if (macaddress.contains(all)) {
					break;
				}
			}
			speed = (double) (sigar.getNetInterfaceStat(name).getSpeed() / 1000000L); // 获取该网卡的带宽，speed的单位是Maps
			// 获取网卡的状态对象io0
			NetInterfaceStat stat = sigar.getNetInterfaceStat(name);
			// 获取接受的总字节数量
			receiveBytes1 = stat.getRxBytes();
			// 获取发送的总字节数量
			sendBytes1 = stat.getTxBytes();
			// 总的通信字节数量
			long totalBytes1 = sendBytes1 + receiveBytes1;
			/*
			 * 通过前一秒的通信字节数量和后 一秒的通信字节数量的差值来计 算该时刻的网络带宽
			 */
			long startTime = System.currentTimeMillis();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			NetInterfaceStat stat2 = sigar.getNetInterfaceStat(name);
			// 获取接受的总字节数量
			receiveBytes2 = stat2.getRxBytes();
			// 获取发送的总字节数量
			sendBytes2 = stat2.getTxBytes();
			// 总的通信字节数量
			long totalBytes2 = sendBytes2 + receiveBytes2;
			// 截至时间
			long endTime = System.currentTimeMillis();
			// 两次时间差的字节数
			long totalBytes = totalBytes2 - totalBytes1;
			double interval = (double) (endTime - startTime) / 1000;
			// 网络带宽使用率
			double netspeed = totalBytes / (interval * 1000) * 8 / 1024 / speed;
			return netspeed;
			//return getForamte(netspeed);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	/*
	 * 本机的名字
	 */
	public String getname() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取CPU使用率
	 * 
	 */
	public double getCpu() {
		try {
			CpuPerc perc = sigar.getCpuPerc();
			double d = perc.getCombined();
			return getForamte(d);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * 内存使用率的方法
	 * 
	 */
	public double getMenmory() {
		try {
			Mem mem = sigar.getMem();
			// 内存总量
			long total = mem.getTotal();
			// 已使用内存
			long used = mem.getUsed();
			double d = (double) used / total;
			return getForamte(d);

		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	/*
	 * 
	 * 磁盘使用率d:/
	 * 
	 */
	public double getDisk(String diskName) {

		try {
			FileSystemUsage usage = sigar.getFileSystemUsage(diskName);
			double d = usage.getUsePercent();
			return getForamte(d);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/*
	 * 
	 */
	// ===========================================================================================

	/*
	 * 格式转换的方法
	 */
	private double getForamte(double f) {
		BigDecimal bg = new BigDecimal(f);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

}
