package vs03;

import java.util.concurrent.LinkedBlockingQueue;

import vsFramework.Channel;

/*
 * this rather should be an abstract class or such
 */
public interface SynchronizedProcess {
	/**
	 * 
	 */
	public void sync_recv();

	/**
	 * 
	 */
	public void sync_send();

	/**
	 * 
	 */
	public int getId(); //it is necessary to create a Constructor with at least one parameter for the Id
	
	public void sync_stateChange();

	public void set_out_nbr(Channel channel);
	
	public LinkedBlockingQueue<Channel> get_out_nbr() ;

	public void set_in_nbr(Channel channel);
	
	public LinkedBlockingQueue<Channel> get_in_nbr();
}
