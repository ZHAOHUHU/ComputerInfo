package teamway.shenzhen.tms9000;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ComputerServer {

	private static final int port = 10725;

	// private static final int clientTimeout = null;
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("action.......");
		try {
			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
			// 设置协议工厂为 TBinaryProtocol.Factory
			// 关联处理器与 Hello 服务的实现
			TProcessor tprocessor = new ComputerService.Processor(new ComputerImp());
			// 异步IO，需要使用TFramedTransport，它将分块缓存读取。
			TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
			tArgs.processor(tprocessor);
			tArgs.transportFactory(new TFramedTransport.Factory());
			// 使用二进制协议
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			// 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
			TServer server = new TNonblockingServer(tArgs);
			System.out.println("Start server on " + port);
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}
