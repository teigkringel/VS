package timeSlice;

import java.util.Random;

import vs03.SyncProcess;
import vsFramework.BidirectionalPipe;
import vsFramework.Channel;

public class TimeSliceCreator extends vs03.Creator {

	
	private Random random;

	public TimeSliceCreator(){
		
		random = new Random();
		random.setSeed(30);
	}
	
	@Override
	protected Channel createChannel() {
		
		BidirectionalPipe pp1 = new BidirectionalPipe();
		
		return pp1.gehtRight();
	}

	@Override
	protected SyncProcess createProcess() {

		return new TimeSliceProcess(random.nextInt(90));
	}

}
