package vs03;

import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import vsFramework.Channel;

public abstract class Creator {

	private HashMap<Integer,SynchronizedProcess > processes;
	private LinkedBlockingQueue<Integer> Id;
	
	protected abstract Channel createChannel(); //method to determine the channel object 
	protected abstract SynchronizedProcess createProcess();
	
	
	
	public Creator(){
		Id = new LinkedBlockingQueue<Integer>();
		processes = new HashMap<Integer,SynchronizedProcess >();
		
	}
	
	
	/**
	 * 
	 * Parameter:
	 * amount: amount of Processes which shall be created
	 * 
	 */
	
	public void creatRing(int amount, Synchro sync){
		Stack<Channel> stack = new Stack<Channel>();
		Channel channel = createChannel();
		Channel channel2;
		stack.push(channel);
		for(int i = 0; i < amount-1;++i){
			channel2 = createChannel();
			stack.push(channel2);
			stack.push(channel2);
			
		}
		stack.push(channel);
		boolean pop = true;
		for(int i = 0; i < amount;++i){
			
			SynchronizedProcess process = createProcess();
			while(pop){
			try {
				process.set_in_nbr(stack.pop());
				process.set_out_nbr(stack.pop());
				pop = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			sync.registerProcess (process, createChannel());
			Id.add(process.getId());
			processes.put(process.getId(),process);
			
		}
		
	}
	
}
