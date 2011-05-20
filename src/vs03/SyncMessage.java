package vs03;

import vsFramework.ByteArrayMessage;
import vsFramework.Message;

public class SyncMessage
	implements Message
{
	public final static int RECV_PHASE = 0;
	public final static int SEND_PHASE = 1;
	public final static int STATE_PHASE = 2;
	public final static int TERMINATE_PHASE = 3;
	
	private byte[] data;
	
	// build new Message containing round and phase
	public SyncMessage (long round, int phase, Message m) {
		data = new byte[12+m.getLength()]; // 8 + 4 Bytes
		
		// write round
		for (int i = 0; i < 8; i++) {
			data[i] = (byte)((round&(0xFF<<(8*(7-i))))>>(8*(7-i)));
		}
		
		// write phase
		for (int i = 0; i < 4; i++) {
			data[8+i] = (byte)((phase&(0xFF<<(8*(3-i))))>>(8*(3-i)));
		}
		
		// write Message
		System.arraycopy(m.getData(), 0, data, 12, m.getLength());
	}

	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return data.length;
	}
	
	//
	public static long getRound (Message m) {
		long readRound = 0;
		
		for (int i = 0; i < 8; i++) {
			readRound |= (m.getData()[i]<<(8*(7-i)));
		}
		if (readRound < 0) {	// prevent negative sign
			readRound = (readRound+256)%256;
		}
		
		return readRound;
	}
	
	//
	public static int getPhase (Message m) {
		int readPhase = 0;
		
		for (int i = 0; i < 4; i++) {
			readPhase |= (256+(m.getData()[8+i]<<(8*(3-i))))%256;
		}
		if (readPhase < 0) {	// prevent negative sign
			readPhase = (readPhase+256)%256;
		}
		
		return readPhase;
	}
	
	//
	public static Message getMessage (Message m) {
		byte[] newData = new byte[m.getLength()-12];
		System.arraycopy(m.getData(), 12, newData, 0, m.getLength()-12);
		
		return new ByteArrayMessage(newData);
	}
}
