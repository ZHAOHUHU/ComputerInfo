package teamway.shenzhen.tms9000;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.sun.management.OperatingSystemMXBean;

public class ComputerInformation {
	// 用于CPU沉睡多长时间
	private static final int CPUTIME = 500;
	// 百分比
	private static final int PERCENT = 100;
	private static final int FAULTLENGTH = 10;

	public Computer getComputerInfo() {
		//主机名称
		String name=System.getProperty("user.name");
		//cpu
		double cpu = getCpu();

		return new Computer();
	}

	/*
	 * 
	 * 
	 * 获取CPU的使用率的方法
	 * 
	 * 
	 * 
	 */
	private double getCpu() {
		try {
			String procCmd = System.getenv("windir")
					+ "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));// 第一次读取CPU信息
			Thread.sleep(CPUTIME);// 睡500ms
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));// 第二次读取CPU信息
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];// 空闲时间
				long busytime = c1[1] - c0[1];// 使用时间
				Double cpusage = Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime));
				BigDecimal b1 = new BigDecimal(cpusage);
				double cpuUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				return cpuUsage;
			} else {
				return 0.0;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return 0.0;
		}
	}

	/*
	 * 读取cpu的方法
	 * 
	 * 
	 */
	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;// 读取物理设备时间
			long usertime = 0;// 执行代码占用时间
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount
				String caption = substring(line, capidx, cmdidx - 1).trim();
				// System.out.println("caption:"+caption);
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				// System.out.println("cmd:"+cmd);
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = substring(line, kmtidx, rocidx - 1).trim();
				String s2 = substring(line, umtidx, wocidx - 1).trim();
				List<String> digitS1 = getDigit(s1);
				List<String> digitS2 = getDigit(s2);

				// System.out.println("s1:"+digitS1.get(0));
				// System.out.println("s2:"+digitS2.get(0));

				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0) {
						if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
							idletime += Long.valueOf(digitS1.get(0)).longValue();
						}
					}
					if (s2.length() > 0) {
						if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
							idletime += Long.valueOf(digitS2.get(0)).longValue();
						}
					}
					continue;
				}
				if (s1.length() > 0) {
					if (!digitS1.get(0).equals("") && digitS1.get(0) != null) {
						kneltime += Long.valueOf(digitS1.get(0)).longValue();
					}
				}

				if (s2.length() > 0) {
					if (!digitS2.get(0).equals("") && digitS2.get(0) != null) {
						kneltime += Long.valueOf(digitS2.get(0)).longValue();
					}
				}
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;

			return retn;
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return null;
	}

	/*
	 * 内存使用率的方法
	 * 
	 */
	public double getMenmory() {
		try {
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			// 总的物理内存+虚拟内存
			long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
			// 剩余的物理内存
			long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
			Double usage = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
			BigDecimal b1 = new BigDecimal(usage);
			double memoryUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return memoryUsage;
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0.0;
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

	/**
	 * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
	 * 
	 * 要截取的字符串
	 * 
	 * @param start_idx
	 *            开始坐标（包括该坐标)
	 * @param end_idx
	 *            截止坐标（包括该坐标）
	 * @return
	 */
	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

	/**
	 * 从字符串文本中获得数字
	 * 
	 * @param text
	 * @return
	 */
	private static List<String> getDigit(String text) {
		List<String> digitList = new ArrayList<String>();
		digitList.add(text.replaceAll("\\D", ""));
		return digitList;
	}

}
