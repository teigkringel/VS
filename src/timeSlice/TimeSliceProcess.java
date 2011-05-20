package timeSlice;

import vs03.SyncMessage;
import vsFramework.BidirectionalPipe;
import vsFramework.ByteArrayMessage;
import vsFramework.Channel;

public class TimeSliceProcess extends vs03.SyncProcess {

	public TimeSliceProcess(int id) {
		super(id);
		BidirectionalPipe pp1 = new BidirectionalPipe();

		comChannel = pp1.gehtRight();
	}

	@Override
	public void sync_recv() {
		// TODO Auto-generated method stub
		
		System.out.println("recv");
	}

	@Override
	public void sync_send() {
		// TODO Auto-generated method stub
		try {
			Channel ch = get_out_nbr().take();
			ch.send(new SyncMessage(0, 1, new ByteArrayMessage("Hallo Nachbar".getBytes())));
			get_out_nbr().put(ch);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("send");
	}

	@Override
	public void sync_stateChange() {
		// TODO Auto-generated method stub
		System.out.println("stateChange");
	}

}
