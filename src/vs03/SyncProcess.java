package vs03;

import java.util.concurrent.LinkedBlockingQueue;

import vsFramework.ByteArrayMessage;
import vsFramework.Channel;
import vsFramework.Message;

public abstract class SyncProcess
	implements Runnable, SynchronizedProcess
{
	public Channel comChannel;	// for now it's public, may be changed later
	private LinkedBlockingQueue<Channel> out_nbrs;
	private LinkedBlockingQueue<Channel> in_nbrs;
	
	int id; 
	
	public SyncProcess (int id) {
		in_nbrs = new LinkedBlockingQueue<Channel>();
		out_nbrs = new LinkedBlockingQueue<Channel>();
		this.id = id;
	}
	
	public void run () {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			Message msg = comChannel.recv();
			
			if (msg != null) {	
					
					int phase = SyncMessage.getPhase(msg);
					
					System.out.println(this+"  : "+new String(SyncMessage.getMessage(msg).getData())+", "+SyncMessage.getRound(msg)+", "+phase+" ID:"+ id);
					conductPhase(phase);
					
					comChannel.send(new ByteArrayMessage("ACK".getBytes()));
				
			
				//System.out.println(this+"  : "+new String(msg.getData()));	
			}
		}
	}
	
	//
	private void conductPhase (int phase) {
		switch (phase) {
			case SyncMessage.RECV_PHASE:
				sync_recv();
				break;
			case SyncMessage.SEND_PHASE:
				sync_send();
				break;
			case SyncMessage.STATE_PHASE:
				sync_stateChange();
				break;
		}
	}
//=============================Abstract Methods==============================================
	@Override
	public abstract void sync_recv();

	@Override
	public abstract void sync_send();

	@Override
	public abstract void sync_stateChange(); 
//=============================Getter and Setter==============================================
	@Override
	public void set_in_nbr(Channel channel) {
		 
		in_nbrs.add(channel);
		
	}

	@Override
	public void set_out_nbr(Channel channel) {
		
		out_nbrs.add(channel);
	}

	@Override
	public  LinkedBlockingQueue<Channel> get_in_nbr(){
		return in_nbrs; 
	}

	@Override
	public  LinkedBlockingQueue<Channel> get_out_nbr(){
		return out_nbrs; 
		
	}
	
	public int getId(){
		
		return this.id;
		
	}


}
