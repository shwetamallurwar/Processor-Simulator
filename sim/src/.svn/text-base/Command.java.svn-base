
public class Command {
	
    public int getInstOpx() {
		return instOpx;
	}
  
	public void setInstOpx(int instOpx) {
		this.instOpx = instOpx;
	}
	
	public int getSrcOp1() {
		return srcOp1;
	}
	
	public void setSrcOp1(int srcOp1) {
		this.srcOp1 = srcOp1;
	}
	
	public int getSrcOp2() {
		return srcOp2;
	}
	public void setSrcOp2(int srcOp2) {
		this.srcOp2 = srcOp2;
	}
	public int getDstOp() {
		return dstOp;
	}
	public void setDstOp(int dstOp) {
		this.dstOp = dstOp;
	}
	
	public String getBranchLabel(){
		return branchLabel;
	}
	public void setBranchLabel(String branchLabel){
		this.branchLabel = branchLabel;
	}
	
	
  private int instOpx;
  private int srcOp1;
  private int srcOp2;
  private int dstOp;
  private int jmpOffset;
  private int latency;
  
  private String branchLabel;
  Command()
  {
    instOpx = -1;
    srcOp1 = -1;
    srcOp2 = -1;
    dstOp = -1;
    setLatency(-1);
    branchLabel = "NULL STRING";
  }
  
  Command(int opx, int op1, int op2, int dst, int lat)
  {
    instOpx = opx;
    srcOp1  = op1;
    srcOp2  = op2;
    dstOp   = dst;
    setLatency(lat);
  }

public void setLatency(int latency) {
	this.latency = latency;
}

public int getLatency() {
	return latency;
}
  
}
