package teamway.shenzhen.tms9000;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.google.common.io.Resources;

public class ComputerInfo {
	private static ComputerInfo computerinfo=new ComputerInfo();
	private ComputerInfo() {
		
	}
	public static ComputerInfo getInstance() {
		return computerinfo;
	}
	/*
	 * 初始化sigar对象
	 */
	public final static Sigar sigar = initSigar();
	private static String macaddress = null;
	private static String diskName = null;
	private static ThreadProc it;

	public static Sigar initSigar() {
		
		
		it = new ThreadProc();
		it.start();
		
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

	/*
	 * 
	 * 获取网络带宽的使用率
	 */
	public double getNet() {
		
		return it.getRate();
	  

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
	public double getDisk() {
		Properties p = new Properties();
		InputStream in2 = Object.class.getResourceAsStream("/config/diskName.properties");
		try {
			p.load(in2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		diskName = p.getProperty("diskName");
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
	 * 格式转换的方法
	 */
	public static double getForamte(double f) {
		if(!Double.isNaN(f)) {
			BigDecimal bg = new BigDecimal(f);
			return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return 0;
	}

}
