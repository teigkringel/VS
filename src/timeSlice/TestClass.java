package timeSlice;

import vs03.Synchronizer;

public class TestClass {
	
	public static void main(String[] args) {
		
		TimeSliceCreator create = new TimeSliceCreator();
	
		Synchronizer sync = new Synchronizer();
		
		create.creatRing(1, sync );
		
		
		
		
	}

}
