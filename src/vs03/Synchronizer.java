package vs03;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;


import vsFramework.ByteArrayMessage;
import vsFramework.Channel;
import vsFramework.Message;

public class Synchronizer
	implements Runnable,Synchro
{
	/* to_DO:	use only one port for communication with all clients
	 * 
	 */
	
	// manage registered Processes
	private LinkedBlockingQueue<SynchronizedProcess> registeredProcesses;
	// for UdpChannels we need to manage clients addresses also
	private HashMap<SynchronizedProcess, Channel> endPoints;
	private int round;

	/*
	 * 
	 */
	public Synchronizer () {
		registeredProcesses = new LinkedBlockingQueue<SynchronizedProcess>();
		endPoints = new HashMap<SynchronizedProcess, Channel>(256);
		round = 0;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run () {
		while (round < 5) {			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// send a Message to every registered Process
			int num = 0;
			for (int phase = 0; phase < 3; phase++) {
				for (SynchronizedProcess synp : registeredProcesses) {
					Channel comChannel = endPoints.get(synp);				
					comChannel.send(new SyncMessage(round, phase, new ByteArrayMessage("Test".getBytes())));
					num++;
					
					Message m = waitForAck(comChannel);
					System.out.println(this+"   "+new String(m.getData()));
				}
			}
			
			round++;
		}
		for (SynchronizedProcess synp : registeredProcesses) {
			Channel comChannel = endPoints.get(synp);				
			comChannel.send(new SyncMessage(round, 4, new ByteArrayMessage("Ende".getBytes()))); // terminate all Processes
		}
	}
	
	/*
	 * registerProcess needs to be overwritten by concrete Synchronizer, as every Synchronizer
	 * should be adjusted to its communication method;
	 * so hide communication method into abstract CommunicationMethod Class
	 */
	public void registerProcess (SynchronizedProcess synp, Channel toChannel) {
		endPoints.put(synp, toChannel);
		registeredProcesses.add(synp);
	}
	
	private Message waitForAck(Channel c){
		
		Message m = null;
		
		while(m == null){
			m = c.recv();
		}
		
		return m;
	}

	//protected abstract boolean isRunning();
	
}
