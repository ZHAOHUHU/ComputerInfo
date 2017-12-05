
package teamway.shenzhen.tms9000;

import java.util.Date;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class ComputerClient {

	public static void main(String[] args) throws Exception {
		 try {
	           //设置传输通道，对于非阻塞服务，需要使用TFramedTransport，它将数据分块发送
	            TTransport transport = new TFramedTransport(new TSocket("localhost", 10725));
	            transport.open();
	            // 协议要和服务端一致
	            //HelloTNonblockingServer
	           
	            TProtocol protocol = new TBinaryProtocol(transport);
	            ComputerService.Client client = new ComputerService.Client(protocol);
	            
	            for(int i=0;i<1000;i++) {
	            	long start=System.currentTimeMillis();
	            	   Computer computer = client.getComputerInfo();
	  	             String cpu = computer.toString();
	  	            
	  	             System.out.println(cpu);
	  	             
	            	 Thread.sleep(5);
	  	             
	  	             long end=System.currentTimeMillis();
	  	             System.out.println(end-start);
	            }
	         
	            transport.close();
	        } catch (TTransportException e) {
	            e.printStackTrace();
	        } catch (TException e) {
	            e.printStackTrace();
	        }
	    }
}
