package vs03;

import java.util.Stack;

import vsFramework.Channel;

public abstract class Creator {

	protected abstract Channel createChannel(); //method to determine the channel object 
	protected abstract SynchronizedProcess createProcess();
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
		
		for(int i = 0; i < amount;++i){
			
			SynchronizedProcess process = createProcess();
			process.set_in_nbr(stack.pop());
			process.set_out_nbr(stack.pop());
			sync.registerProcess (process, createChannel());
			
		}
		
	}
	
}
