
public class IF extends Stage {
	
   /**
    *  stall the IF stage ?
    */
	Latch latStgIFStalled;

	/** instruction register latch */
	Latch latIR;
	
    /** PC */
    Latch latPC;
	
    // Next PC
    private long lngPC = 0;

	public IF(Processor processor)
	{
		super(processor);
		latIR = new Latch(processor);
    	latPC = new Latch(processor);
    	latStgIFStalled = new Latch(processor);
    	
	}

	public void execute()
	{
		System.out.println(" \n ================="); 
		System.out.println("In StageIF Class");
		System.out.println("PC=" + lngPC);
		System.out.println(" ARRAY SIZE ::" + processor.memory.instVector.size());
        
    	long lngStageIFStalled = latStgIFStalled.read();
    	System.out.println("STALL DETECTION in IF::" + lngStageIFStalled);
        if (lngStageIFStalled == 1L) {
          System.out.println("Stage stalled");
          return;
        }  
        
        
        long lngControlTransfer = processor.stgID.latControlTransfer.read();
        // ID calculates the new PC in case of branch transfer
        if (lngControlTransfer == 1) {
        	 
        	lngPC = processor.stgID.latNewPC.read();
            System.out.println("Control transfer, new PC=" + lngPC);
        }
        
        
        latPC.write(lngPC);
        
        latIR.writeInst((processor.memory.readOneInst(lngPC)));
        //increment PC; by one because its array of instructions.
        lngPC = lngPC + 1;
	}
}
