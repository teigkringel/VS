package vs03;

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
	public void sync_stateChange();

	public void set_out_nbr(Channel channel);
	
	public void get_out_nbr(Channel channel);

	public void set_in_nbr(Channel channel);
	
	public void get_in_nbr(Channel channel);
}
