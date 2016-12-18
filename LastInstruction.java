
public class LastInstruction {

	private long lngOperation;
	private long dstRegNB;
	private long latencyIns;
	private long clockCyElapsed;
	
	public LastInstruction(){
		lngOperation = -1;
		dstRegNB = -1;
		latencyIns = -1;
		clockCyElapsed = -1;
	}
	
	public void setClockCyElapsed(long clockCyElapsed) {
		this.clockCyElapsed = clockCyElapsed;
	}
	public long getClockCyElapsed() {
		return clockCyElapsed;
	}
	public void setLatencyIns(long latencyIns) {
		this.latencyIns = latencyIns;
	}
	public long getLatencyIns() {
		return latencyIns;
	}
	public void setDstRegNB(long dstRegNB) {
		this.dstRegNB = dstRegNB;
	}
	public long getDstRegNB() {
		return dstRegNB;
	}
	public void setLngOperation(long lngOperation) {
		this.lngOperation = lngOperation;
	}
	public long getLngOperation() {
		return lngOperation;
	}
	
	
	
}
