package vs03;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import vsFramework.UdpChannel;
import vsFramework.UdpChannelFactory;
import vsFramework.BidirectionalPipe;

public class TestClass {

	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		// at first we do some test, in order to develop a synchronizer system
		Synchronizer sync = new Synchronizer(1,2);
		SyncProcess p1 = new SyncProcess(1);	// Test process 1
		SyncProcess p2 = new SyncProcess(2);	// Test process 2
		
		// create Communication Channels
		BidirectionalPipe pp1 = new BidirectionalPipe();
		BidirectionalPipe pp2 = new BidirectionalPipe();
		
		// set communication Channels
		p1.comChannel = pp1.gehtRight();
		p2.comChannel = pp2.gehtRight();
		
		// register Processes in non-generic manner
		// for generic one: use some interface that generates both Channels and register with these
		sync.registerProcess(p1, pp1.gehtLeft());
		sync.registerProcess(p2, pp2.gehtLeft());
		
		new Thread(sync).start();
		new Thread(p1).start();
		new Thread(p2).start();
	}

}
