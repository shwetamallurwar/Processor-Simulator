/** this class is used to store the instructions in EX stage */

public class ExecuteInstruction {
	
	// in parameters to execute instructions
	private long lngPC;
	private long lngImm;
	private long lngNbRegResult; 
	private long lngALUInstr ;
	private long lngMemInstr ;
	private long lngNbReg1 ; 
    private long lngNbReg2 ;
    private long lngClkTicks;
    
    private boolean exeComplete;
    
    //out param for MEM
    private long lngALUout;
    private long lngStoreVal ;
    private long lngRegResult;
    
    private double doubALUout;
    private double doubStoreVal;
    
    //out param for WB
    private long lngLWD;
    private long lngAluValue; //reg to reg
    private double doubLWD;
    private double doubAluValue;
    
    
    public ExecuteInstruction() {
	  lngPC = -1;
	  lngImm = -1;
	  lngNbRegResult = -1;
      lngALUInstr = -1;
      lngMemInstr = -1;
      lngNbReg1 = -1;
      lngNbReg2 = -1;
      lngALUout = -1;
      lngStoreVal = -1;
      lngRegResult = -1;
      doubALUout = -1;
      doubStoreVal = -1;
	}
    
    public ExecuteInstruction( long lngPC ,
	                         long lngImm,
	                         long lngNbRegResult, 
                             long lngALUInstr ,
                             long lngMemInstr ,
                             long lngNbReg1 ,
                             long lngNbReg2,
                             long lngClkTicks,
                             boolean done)
    {
    	 this.lngPC = lngPC;
   	     this.lngImm = lngImm;
   	     this.lngNbRegResult = lngNbRegResult;
         this.lngALUInstr = lngALUInstr;
         this.lngMemInstr = lngMemInstr;
         this.lngNbReg1 = lngNbReg1;
         this.lngNbReg2 = lngNbReg2;
         this.lngClkTicks = lngClkTicks;
    	 this.exeComplete = done;
    }
    
    public long getLngALUout() {
		return lngALUout;
	}
	public void setLngALUout(long lngALUout) {
		this.lngALUout = lngALUout;
	}
	public long getLngStoreVal() {
		return lngStoreVal;
	}
	public void setLngStoreVal(long lngStoreVal) {
		this.lngStoreVal = lngStoreVal;
	}
	public long getLngRegResult() {
		return lngRegResult;
	}
	public void setLngRegResult(long lngRegResult) {
		this.lngRegResult = lngRegResult;
	}
	   
        
	public void setLngNbReg2(long lngNbReg2) {
		this.lngNbReg2 = lngNbReg2;
	}
	public long getLngNbReg2() {
		return lngNbReg2;
	}
	public void setLngNbReg1(long lngNbReg1) {
		this.lngNbReg1 = lngNbReg1;
	}
	public long getLngNbReg1() {
		return lngNbReg1;
	}
	public void setLngMemInstr(long lngMemInstr) {
		this.lngMemInstr = lngMemInstr;
	}
	public long getLngMemInstr() {
		return lngMemInstr;
	}
	public void setLngALUInstr(long lngALUInstr) {
		this.lngALUInstr = lngALUInstr;
	}
	public long getLngALUInstr() {
		return lngALUInstr;
	}
	public void setLngNbRegResult(long lngNbRegResult) {
		this.lngNbRegResult = lngNbRegResult;
	}
	public long getLngNbRegResult() {
		return lngNbRegResult;
	}
	public void setLngImm(long lngImm) {
		this.lngImm = lngImm;
	}
	public long getLngImm() {
		return lngImm;
	}
	public void setLngPC(long lngPC) {
		this.lngPC = lngPC;
	}
	public long getLngPC() {
		return lngPC;
	}

	public void setLngClkTicks(long lngClkTicks) {
		this.lngClkTicks = lngClkTicks;
	}

	public long getLngClkTicks() {
		return lngClkTicks;
	}

	public void setExeComplete(boolean exeComplete) {
		//System.out.println("In Set function :: " + exeComplete);
		this.exeComplete = exeComplete;
	}

	public boolean isExeComplete() {
	  //	System.out.println("In Get function :: " + exeComplete);
		return exeComplete;
	}

	public void setLngAluValue(long lngAluValue) {
		this.lngAluValue = lngAluValue;
	}

	public long getLngAluValue() {
		return lngAluValue;
	}

	public void setLngLWD(long lngLWD) {
		this.lngLWD = lngLWD;
	}

	public long getLngLWD() {
		return lngLWD;
	}

	public void setDoubALUout(double doubALUout) {
		this.doubALUout = doubALUout;
	}

	public double getDoubALUout() {
		return doubALUout;
	}

	public void setDoubStoreVal(double doubStoreVal) {
		this.doubStoreVal = doubStoreVal;
	}

	public double getDoubStoreVal() {
		return doubStoreVal;
	}

	public void setDoubLWD(double doubLWD) {
		this.doubLWD = doubLWD;
	}

	public double getDoubLWD() {
		return doubLWD;
	}

	public void setDoubAluValue(double doubAluValue) {
		this.doubAluValue = doubAluValue;
	}

	public double getDoubAluValue() {
		return doubAluValue;
	}
	
	

}
