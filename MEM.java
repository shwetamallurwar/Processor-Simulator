import java.util.Iterator;
import java.util.Vector;


public class MEM extends Stage {
	
 
    Latch latMemToWbArray;
    
    Vector<ExecuteInstruction> memToWbVector;
    
	
	MEM(Processor processor)
	{
	  super(processor);	

      latMemToWbArray = new Latch(processor);
      memToWbVector = new Vector(); 
	}
	
	public void execute()
	{
		System.out.println(" \n ================="); 
		System.out.println("In StageMEM Class");
		
		long lngALUout;
		double doubALUout;
	    long lngStoreVal;
	    double doubStoreVal;
	    long lngRegResult;
	    long lngALUInstr;
	    long lngMemInstr;
	    long lngLWD=0;
	    double doubLWD=0;
	    
	    Vector<ExecuteInstruction> exeMemVector = processor.stgEXE.latExeToMemArray.readExeVector();
	    memToWbVector.removeAllElements(); // MemToWB array flushed here.
         	    
	    if(exeMemVector.isEmpty()){
	      System.out.println("**** NO INSTRUCTION TO EXECUTE *****");
	      return;
	    }
	      System.out.println("*** Size of vector :: " + exeMemVector.size());
	      
	    Iterator<ExecuteInstruction> it = exeMemVector.iterator();
	    while (it.hasNext()){
	    	ExecuteInstruction e = it.next();
	 
	    	lngALUout = e.getLngALUout();
	    	doubALUout = e.getDoubALUout();
	    	lngStoreVal = e.getLngStoreVal();
	    	doubStoreVal = e.getDoubStoreVal();   	
	        lngRegResult = e.getLngNbRegResult();
	        lngALUInstr = e.getLngALUInstr();
	        lngMemInstr = e.getLngMemInstr();
	    
	      switch ((int) lngALUInstr) {
  	        case Constant.ALU_NOP:
	        case Constant.ALU_ADD:    
	        case Constant.ALU_SUB:
	        case Constant.ALU_MUL:
	        case Constant.ALU_DIV:
	        case Constant.ALU_MOVE:	
	        	
	        case Constant.ALU_FADD:
	        case Constant.ALU_FSUB:
	        case Constant.ALU_FMUL:
	        case Constant.ALU_FDIV:
	        case Constant.ALU_FMOVE:   
	           // nothing to do
	    	   System.out.println(":: ARITHMATIC OPERATION ::"); 
	           break;
	         case Constant.ALU_BEQ:    
	         case Constant.ALU_BNEQ:
	         case Constant.ALU_BLTZ:
	         case Constant.ALU_BGTZ:
	         
	           // nothing to do
	    	   System.out.println(":: BRANCH OPERATION ::");
	           break;
	          }
	    
	       switch ((int) lngMemInstr) {
  	         case Constant.MEM_NOP:
	           // nothing to do
	           break;
	         case Constant.MEM_LW:    // Load
	           System.out.println(":: MEMORY LOAD ::");	
	           System.out.println("Load from: " + lngALUout);
	           System.out.println("Dest Reg: " + lngRegResult);
	                         
	           lngLWD = processor.memory.readValAtMloc(lngALUout);
	           System.out.println("READ Value from Memory: " + lngLWD);
	           break;
	         case Constant.MEM_FLW:
	           System.out.println(":: MEMORY LOAD ::");	
		       System.out.println("Load from: " + lngALUout);
		       System.out.println("Dest Reg: " + lngRegResult);
		               
		       doubLWD = processor.memory.readDValAtMloc(lngALUout);
		       System.out.println("READ Value from Memory: " + lngLWD);
		       break;	 
	         case Constant.MEM_LWI:
	    	   System.out.println(":: MEMORY LOAD IMM::");	
		       System.out.println("Load from: " + lngALUout);
		       System.out.println("Dest Reg: " + lngRegResult);
		       lngLWD = lngALUout;
	    	   break;
	         case Constant.MEM_S:    // Store
	           System.out.println(":: MEMORY F STORE ::");	
	           System.out.println("Store " + lngStoreVal + " at " + lngALUout);
	           processor.memory.write(lngALUout, lngStoreVal);
	           break;
	         case Constant.MEM_FS:
	           System.out.println(":: MEMORY F STORE ::");	
		       System.out.println("Store " + doubStoreVal + " at " + lngALUout);
		       processor.memory.writeDouble(lngALUout, doubStoreVal);
		       break;	 
	       }

           // MEM/WB Latches write
	       ExecuteInstruction ei = new ExecuteInstruction();
	       ei.setLngLWD(lngLWD);
	       ei.setDoubLWD(doubLWD);
	       //latLWD.write(lngLWD);
	       ei.setLngAluValue(lngALUout);
	       ei.setDoubAluValue(doubALUout);
	       ei.setLngNbRegResult(lngRegResult); // number of destination register
	       
	       ei.setLngALUInstr(lngALUInstr);
	       ei.setLngMemInstr(lngMemInstr);
	       memToWbVector.add(ei);
	    } // while end
	    
	    latMemToWbArray.writeExeVector(memToWbVector);
 	}//function end

}//class end
