package vs03;

import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import vsFramework.BidirectionalPipe;
import vsFramework.Channel;

public abstract class Creator {

	private HashMap<Integer, Thread> processes;
	private LinkedBlockingQueue<Integer> Id;

	protected abstract Channel createChannel(); // method to determine the
												// channel object

	protected abstract SyncProcess createProcess();

	public Creator() {
		Id = new LinkedBlockingQueue<Integer>();
		processes = new HashMap<Integer, Thread>();

	}

	/**
	 * 
	 * Parameter: amount: amount of Processes which shall be created
	 * 
	 */

	public void creatRing(int amount, Synchronizer sync) {
		Stack<Channel> stack = new Stack<Channel>();
		Channel channel = createChannel();
		Channel channel2;
		Channel channel3;
		stack.push(channel);
		for (int i = 0; i < amount - 1; ++i) {
			channel2 = createChannel();  
			channel3 = createChannel(); 
			stack.push(channel2);
			stack.push(channel3);

		}
		stack.push(channel);
		for (int i = 0; i < amount; ++i) {

			BidirectionalPipe pp = new BidirectionalPipe();
			
			SyncProcess process = createProcess();

			process.set_in_nbr(stack.pop());
			process.set_out_nbr(stack.pop());
			process.comChannel = pp.gehtRight();
			sync.registerProcess(process, pp.gehtLeft());
			Id.add(process.getId());
			processes.put(process.getId(), new Thread(process));

		}
		
	
				
		new Thread(sync).start();
		
		for (Integer id:Id) {
			
			Thread processThread = processes.get(id);
			
			processThread.start();
			
		}

	}

}
