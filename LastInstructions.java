import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/** Store the array of last inflight instructions */
public class LastInstructions implements TickTheClock{
	
  
	  private Processor processor;
	  private long tmpLastInst;
	  private long tmpLastNbReg;
	  private long tmpLatency;
	  
	  public Vector<LastInstruction> lastinstrVec ;
	  

	  private boolean toUpdate;


	  /** Last arrays initialization */
	  public LastInstructions (Processor processor) {
	    lastinstrVec = new Vector<LastInstruction>();
	    processor.addClkChListner(this);
	    this.processor = processor;
	  }


	  /**
	   * Update the last instructions arrays.
	   * 
	   * Last instruction executed
	   * Last destination register written
	   */
	  public void update (long lastB, long lastR, long latency) {
		  
	    tmpLastInst = lastB;
	    tmpLastNbReg = lastR;
	    tmpLatency = latency;
	    toUpdate = true;
	  }
	  
 
	  /** Update the last arrays by adding infos without data hazards.  */
	  public void update () {
	    update(-1L, -1L, -1L);
	  }

	  
	  /** The last array are really updated here */
	  public void clockChanged () {
	    if(toUpdate) {
	      // print();
	      // click the cycle, remove the instruction from lastinstrVec,
	      // add new instruction.	
	      tickTheClock();
	      removeInstIfFinished();
	      if(tmpLastInst != -1 ){
	        LastInstruction li = new LastInstruction();
	        li.setLngOperation(tmpLastInst);
	        li.setDstRegNB(tmpLastNbReg);
	        li.setLatencyIns(tmpLatency);
	        li.setClockCyElapsed(0);
            lastinstrVec.add(li);
            //print();
	      }
	      print();
	    }
	    toUpdate = false;
	  }
	  
	  /** if instruction is finished remove it from vector */
	  public void removeInstIfFinished(){
		for(int i=0; i < lastinstrVec.size(); i++){
		  LastInstruction li = lastinstrVec.get(i);
		  if(li.getLatencyIns() == li.getClockCyElapsed()){
			lastinstrVec.remove(i);
			i = -1;
			
			System.out.println("\n =================");
			System.out.println("**** DELETE INSTRUCTION FROM LAST ARRAY ****");
			System.out.println("        | Instr | Reg | clock");
			System.out.println("last-inst| " + Constant.opcodeToString((int)li.getLngOperation()) + "  | " 
			  		                          + li.getDstRegNB()+ "    " + li.getClockCyElapsed());
		  }
		}
      }

	  /**
	   * Prints the 'last' arrays.
	   */
	  public void print () {
		System.out.println("\n =================");  
		System.out.println("Last instruction vector size :: " + lastinstrVec.size());  
		Iterator<LastInstruction> it = lastinstrVec.iterator();  
	    while(it.hasNext()){
		  LastInstruction l = it.next();
	      System.out.println("        | Instr | Reg | clock");
	      System.out.println("last-inst| " + Constant.opcodeToString((int)l.getLngOperation()) + "  | " 
	    		                          + l.getDstRegNB()+ "    " + l.getClockCyElapsed());
	      
	    }
	  }

	  // tick the clocks elapsed of each instruction.
	  public void tickTheClock(){
  
		  Iterator<LastInstruction > it = lastinstrVec.iterator();
		  while (it.hasNext()){
		    LastInstruction l = it.next();
		    l.setClockCyElapsed(l.getClockCyElapsed() + 1);
		  }
		  
	  }
	  
	  public void pipeLineMonitor(String outFileName)
	  {   
		  System.out.println("**** EOF INS PipeLine Monitor ****");
		  System.out.println("SIZE :: " + lastinstrVec.size());
		  if(lastinstrVec.isEmpty()){
			
		    System.out.println("Hurray PipeLine is Empty!!");  
		    Writer output = null;	
		    try {
				output = new BufferedWriter(new FileWriter( new File(outFileName)));
				
				output.write("Simple Pipeline Result \n");
                                System.out.println("\n         Result        ");				
				System.out.println("Number of Clock Cycles ::" + processor.cycle);
				output.write("Number of Clock Cycles ::" + processor.cycle + "\n");
				
				System.out.println("Number of Instructions Executed ::" + processor.stgEXE.nbInstrExecuted);
				output.write("Number of Instructions Executed ::" + processor.stgEXE.nbInstrExecuted + "\n");
				
				System.out.println("Number of Stalls :: " + processor.stgID.lngStallCnt);
				output.write("Number of Stalls :: " + processor.stgID.lngStallCnt + "\n");
				
	            double d1 = processor.cycle;
	            double d2 = processor.stgEXE.nbInstrExecuted;
				System.out.println("Average CPI ::" + d1/d2 );
				output.write("Average CPI ::" + d1/d2 + "\n");
				
				output.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			Thread thisthread = Thread.currentThread();
			thisthread.stop();
			System.exit(0);
		  }
	  }

}
