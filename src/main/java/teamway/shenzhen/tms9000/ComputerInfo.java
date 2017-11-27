package teamway.shenzhen.tms9000;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.google.common.io.Resources;

public class ComputerInfo {
	/*
	 * 初始化sigar对象
	 */
	public final static Sigar sigar = initSigar();

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
	/*
	 * 总方法
	 */
	public Computer getComputerInfo() {
		

		return new Computer();
	}
	/*
	 * 获取CPU使用率
	 * 
	 */
	private double getCpu() {
		return 0;
		
		
	}
	/*
	 * 内存使用率的方法
	 * 
	 */
	public double getMenmory() {
		return 0;
		
	}
	/*
	 * 
	 * 磁盘使用率
	 * 
	 */
	public double getDisk() {
		double totalHD = 0;
		double usedHD = 0;
		long allTotal = 0;
		long allFree = 0;
		for (char c = 'A'; c <= 'Z'; c++) {
			String dirName = c + ":/";
			File win = new File(dirName);
			if (win.exists()) {
				long total = (long) win.getTotalSpace();
				long free = (long) win.getFreeSpace();
				allTotal = allTotal + total;
				allFree = allFree + free;
			}
		}
		Double precent = (Double) (1 - allFree * 1.0 / allTotal) * 100;
		BigDecimal b1 = new BigDecimal(precent);
		precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return precent;
	}
	/*
	 * 
	 * 获取网络带宽的使用率
	 */
	private double getNet() {
		return 0;
		
	}

	
}
