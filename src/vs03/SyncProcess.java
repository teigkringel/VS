package vs03;

import java.util.concurrent.LinkedBlockingQueue;

import vsFramework.ByteArrayMessage;
import vsFramework.Channel;
import vsFramework.Message;

public class SyncProcess
	implements Runnable, SynchronizedProcess
{
	public Channel comChannel;	// for now it's public, may be changed later
	public LinkedBlockingQueue<Channel> out_nbrs;
	public LinkedBlockingQueue<Channel> in_nbrs;
	
	int id; 
	
	public SyncProcess (int id) {
		in_nbrs = new LinkedBlockingQueue<Channel>();
		
		this.id = id;
	}
	
	public void run () {
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
	public void sync_recv() {
		// TODO Auto-generated method stub
		System.out.println("recv");
	}

	@Override
	public void sync_send() {
		// TODO Auto-generated method stub
		System.out.println("send");
	}

	@Override
	public void sync_stateChange() {
		// TODO Auto-generated method stub
		System.out.println("stateChange");
	}
//=============================Getter and Setter==============================================
	@Override
	public void set_in_nbr(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set_out_nbr(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_in_nbr(Channel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void get_out_nbr(Channel channel) {
		// TODO Auto-generated method stub
		
	}


}
