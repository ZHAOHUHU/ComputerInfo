package teamway.shenzhen.tms9000;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class ThreadProc  extends  Thread{
	
	public final static Sigar sigar = ComputerInfo.initSigar();
	private static String macaddress = null;
	private static  double rate;
	
	public static double getRate() {
		return rate;
	}

	public static void setRate(double rate) {
		ThreadProc.rate = rate;
	}



	public void run() {
		
		
		
		for(;;) {
			
				Properties p = new Properties();
				InputStream in = Object.class.getResourceAsStream("/config/macaddress.properties");
				try {
					p.load(in);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				macaddress = p.getProperty("macaddress");

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
					//System.out.println(netspeed);
			
					 
					 setRate( ComputerInfo.getForamte(netspeed));
				} catch (SigarException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				
				
			
		}
		
		
	}

}
