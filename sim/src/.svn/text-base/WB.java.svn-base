import java.util.Iterator;
import java.util.Vector;


public class WB extends Stage {
	
	public WB(Processor processor)
	{
		super(processor);
	}
	
	public void execute()
	{
		System.out.println(" \n ================="); 
		System.out.println("In StageWB Class");
		
		long lngLWD;
	    double doubLWD;
		long lngALUInstr;
	    long lngMemInstr;
	    long lngValue;
	    double doubValue;
	    long lngRegResult;
	    
	    	    
	    Vector<ExecuteInstruction>  exeWbVector = processor.stgMEM.latMemToWbArray.readExeVector();
	    
	    if(exeWbVector.isEmpty()){
		      System.out.println("**** NO INSTRUCTION TO EXECUTE *****");
		      return;
		 }
		    System.out.println("*** Size of vector :: " + exeWbVector.size());
		      
		Iterator<ExecuteInstruction> it = exeWbVector.iterator();
		while (it.hasNext()){
			
		  ExecuteInstruction e = it.next();	
    	  // read MEM/WB latches
		  lngLWD = e.getLngLWD();
		  doubLWD = e.getDoubLWD();
	      //lngLWD  = control.stageMEM.latLWD.read();  // value read from memory.
		  
		  lngALUInstr = e.getLngALUInstr(); 
	      lngMemInstr = e.getLngMemInstr();
	      lngValue = e.getLngAluValue();
	      doubValue = e.getDoubAluValue(); // value computed with Arithmatic operation
	      lngRegResult = e.getLngNbRegResult(); // dst register
	      
	   
	    	    	    
	      switch ((int) lngALUInstr) {
	        case Constant.ALU_NOP:
	        break;
	        case Constant.ALU_ADD:    
	        case Constant.ALU_SUB:    
	        case Constant.ALU_MUL:    
	        case Constant.ALU_DIV:
	        case Constant.ALU_FMOVE:	
	          System.out.println("WB: Value: " + lngValue);
		      System.out.println("WB: Reg Result: " + lngRegResult);	
	          processor.register.write(lngRegResult, lngValue);
	          break;
	        
	        case Constant.ALU_FADD:    
	        case Constant.ALU_FSUB:    
	        case Constant.ALU_FMUL:    
	        case Constant.ALU_FDIV:
	        case Constant.ALU_MOVE:
	        	System.out.println("WB: Value: " + doubValue);
			    System.out.println("WB: Reg Result: " + lngRegResult);	
		        processor.fregister.write(lngRegResult, doubValue);
		        break;	
	        case Constant.ALU_BEQ:    
	        case Constant.ALU_BNEQ:
	        case Constant.ALU_BLTZ:	
	        case Constant.ALU_BGTZ:	
	          // nothing to do
	          break;
	      }
	    
	      switch ((int) lngMemInstr) {
	        case Constant.MEM_NOP:
	          // nothing to do
	          break;
	        case Constant.MEM_LW:    // Load
	          System.out.println("::MEM_LW::");	
	          System.out.println("WB: Value: " + lngLWD);
		      System.out.println("WB: Reg Result: " + lngRegResult);
	          System.out.println("Memory read: " + lngLWD);
	          processor.register.write(lngRegResult, lngLWD);
	          break;
	        case Constant.MEM_FLW:
	          System.out.println("::MEM_FLW::");	
		      System.out.println("WB: Value: " + doubLWD);
			  System.out.println("WB: Reg Result: " + lngRegResult);
		      System.out.println("Memory read: " + doubLWD);
		      processor.fregister.write(lngRegResult, doubLWD);
		      break;	
	        case Constant.MEM_LWI:  // Load imm
	          System.out.println("::MEM_LW::");		
	          System.out.println("WB: Value: " + lngLWD);
		      System.out.println("WB: Reg Result: " + lngRegResult);
		      processor.register.write(lngRegResult, lngLWD);
		      break;
	       case Constant.MEM_S:    // Store
	          System.out.println("::MEM_S::");		
		      System.out.println("WB: Value: " + lngLWD);
		      System.out.println("WB: Reg Result: " + lngRegResult);
		      break;
	       case Constant.MEM_FS:
	    	  System.out.println("::MEM_FS::");		
			  System.out.println("WB: Value: " + doubLWD);
			  System.out.println("WB: Reg Result: " + lngRegResult);
			  break; 
	      }
	    }//while end  
	}//function end

}//class end
