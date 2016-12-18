import java.util.Iterator;
import java.util.Vector;


public class EXE extends Stage {
	

  Latch latExeToMemArray;
  /** Counts the number of instruction executed  */
  int nbInstrExecuted;

  /** exeVector contains all instruction which are currently executing*/  
  Vector<ExecuteInstruction> exeVector; 
  /** memVector contains all instruction which are completed the execution
   *  and need to pass the MEM stage */
  Vector<ExecuteInstruction> memVector;
  
	
	public EXE(Processor processor)
	{
		super(processor);
		
	    nbInstrExecuted = 0;
	    latExeToMemArray = new Latch(processor);
	    exeVector = new Vector<ExecuteInstruction>();
	    memVector = new Vector<ExecuteInstruction>();

	}
	
	public void execute()
	{
	  System.out.println(" \n ================="); 	
	  System.out.println("In StageEXE Class");
	  
        long lngPC;
	    long lngNbRegister1; // Number of the register
	    long lngNbRegister2; // -
	    long lngReg1;   // Value of the register
	    long lngReg2;   // -
	    double doubReg1; // value of floating poin register.
	    double doubReg2;
	    long lngImm;
	    long lngNbRegResult;
	    long lngALUout = 0;
	    double doubALUout = 0;
	    long lngALUInstr;
	    long lngMemInstr;
	    long lngStoreVal = 0;
	    double doubStoreVal = 0;
	    
	   	    
	    
	    lngPC = processor.stgID.latPC.read();
	    lngImm = processor.stgID.latImm.read();
	    lngNbRegResult = processor.stgID.latRegResult.read();
	    lngALUInstr = processor.stgID.latALUInstrOpcode.read();
	    lngMemInstr = processor.stgID.latMemInstrOpcode.read();
	    
	    lngNbRegister1 = processor.stgID.latRegister1.read();
	    lngNbRegister2 = processor.stgID.latRegister2.read();
	    
	    
                
        if(lngALUInstr != -1 || lngMemInstr != -1){
          ExecuteInstruction ei = new ExecuteInstruction(lngPC, lngImm, lngNbRegResult,
        		                                   lngALUInstr, lngMemInstr, lngNbRegister1, lngNbRegister2,0,false);
          exeVector.add(ei);
          if ((ei.getLngALUInstr() != Constant.ALU_NOP) || (ei.getLngMemInstr() != Constant.MEM_NOP)) {
              nbInstrExecuted++;
            }
        }
	    
	    Iterator< ExecuteInstruction> it = exeVector.iterator();
        
        while(it.hasNext()){
         	
           ExecuteInstruction i = it.next();
        
           System.out.println("---------- \n");
           
	       lngPC = i.getLngPC();
           lngImm = i.getLngImm();
           lngNbRegResult = i.getLngNbRegResult();
           lngReg1 = processor.register.read(i.getLngNbReg1());
           lngReg2 = processor.register.read(i.getLngNbReg2());
           
           doubReg1 = processor.fregister.read(i.getLngNbReg1());
           doubReg2 = processor.fregister.read(i.getLngNbReg2());
           
           lngALUInstr = i.getLngALUInstr();
           lngMemInstr = i.getLngMemInstr();
           lngNbRegister1 = i.getLngNbReg1();
    	   lngNbRegister2 = i.getLngNbReg2();
           
           System.out.println("PC: " + lngPC + " Opeartion: " + Constant.opcodeToString((int)lngALUInstr) + "/" + Constant.opcodeToString((int)lngMemInstr));
           System.out.println("NbReg1: " + lngNbRegister1 + ", NbReg2: " + lngNbRegister2);

           System.out.println("Reg1: " + lngReg1 + ", Reg2: " + lngReg2);
           System.out.println("DReg1: " + doubReg1 +", DReg2:" + doubReg2 );
           System.out.println("Imm: " + lngImm + ", NbRegResult: " + lngNbRegResult);
    
           switch ((int) i.getLngALUInstr()) {
             case Constant.ALU_NOP:
               System.out.println("ALU UNIT NOP");
               // nothing to do
               //  it takes one cycle to complete.
               i.setExeComplete(true);
             
               break;
             case Constant.ALU_ADD:    // register-register
               System.out.println("ALU UNIT ADD");
               
               // +1 because instruction complete logic and pass instruction to Mem
               // is implemented here in StageEXE class.
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
            		                        get(Constant.opcodeToString(Constant.ALU_ADD)).intValue())
               {
            	 System.out.println("Setting COMPLETED TRUE ");
            	 i.setExeComplete(true);
            	 lngALUout = lngReg1 + lngReg2;
            	    
               }else{
            	   
            	 System.out.println("Setting COMPLETED FALSE ");
            	 i.setLngClkTicks(i.getLngClkTicks() + 1);
            	 i.setExeComplete(false);
               }
                              
               break;
             case Constant.ALU_SUB:    // register-register
               System.out.println("ALU UNIT SUB");
               
               // +1 because instruction complete logic and pass instruction to Mem
               // is implemented here in StageEXE class.
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
            		                        get(Constant.opcodeToString(Constant.ALU_SUB)).intValue())
               {
            	 System.out.println("Setting COMPLETED TRUE ");
            	 i.setExeComplete(true);
            	 lngALUout = lngReg1 - lngReg2;
            	    
               }else{
            	   
            	 System.out.println("Setting COMPLETED FALSE ");
            	 i.setLngClkTicks(i.getLngClkTicks() + 1);
            	 i.setExeComplete(false);
               }
               
               break;
             case Constant.ALU_FADD:
               System.out.println("ALU UNIT FADD");
               
               // +1 because instruction complete logic and pass instruction to Mem
               // is implemented here in StageEXE class.
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
              	                            get(Constant.opcodeToString(Constant.ALU_FADD)).intValue())
               {
              	 System.out.println("Setting COMPLETED TRUE ");
              	 i.setExeComplete(true);
              	 doubALUout = doubReg1 + doubReg2;
              	    
               }else{
              	   
              	 System.out.println("Setting COMPLETED FALSE ");
              	 i.setLngClkTicks(i.getLngClkTicks() + 1);
              	 i.setExeComplete(false);
               }
               break;
             case Constant.ALU_FSUB:
               System.out.println("ALU UNIT FSUB");
               // +1 because instruction complete logic and pass instruction to Mem
               // is implemented here in StageEXE class.
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
              	                            get(Constant.opcodeToString(Constant.ALU_FSUB)).intValue())
               {
              	 System.out.println("Setting COMPLETED TRUE ");
              	 i.setExeComplete(true);
              	 doubALUout = doubReg1 - doubReg2;
              	    
               }else{
              	   
              	 System.out.println("Setting COMPLETED FALSE ");
              	 i.setLngClkTicks(i.getLngClkTicks() + 1);
              	 i.setExeComplete(false);
               }               
               break;
             case Constant.ALU_MUL:
               System.out.println("ALU UNIT MUL");
               
               // +1 because instruction complete logic and pass instruction to Mem
               // is implemented here in StageEXE class.
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
            		                        get(Constant.opcodeToString(Constant.ALU_MUL)).intValue())
               {
            	 System.out.println("Setting COMPLETED TRUE ");
            	 i.setExeComplete(true);
            	 lngALUout = lngReg1 * lngReg2;
            	    
               }else{
            	   
            	 System.out.println("Setting COMPLETED FALSE ");
            	 i.setLngClkTicks(i.getLngClkTicks() + 1);
            	 i.setExeComplete(false);
               }
               
               break;
               
             case Constant.ALU_DIV:
               System.out.println("ALU UNIT DIV");
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                       get(Constant.opcodeToString(Constant.ALU_DIV)).intValue())
               {
                 System.out.println("Setting COMPLETED TRUE ");
                 i.setExeComplete(true);
                 lngALUout = lngReg1 / lngReg2;

               }else{

                 System.out.println("Setting COMPLETED FALSE ");
                 i.setLngClkTicks(i.getLngClkTicks() + 1);
                 i.setExeComplete(false);
               }
               break;
             
             case Constant.ALU_FMUL:
               System.out.println("ALU UNIT MUL");
                 
               // +1 because instruction complete logic and pass instruction to Mem
               // is implemented here in StageEXE class.
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
              	                            get(Constant.opcodeToString(Constant.ALU_FMUL)).intValue())
               {
              	 System.out.println("Setting COMPLETED TRUE ");
              	 i.setExeComplete(true);
              	 doubALUout = doubReg1 * doubReg2;
              	    
               }else{
              	   
              	 System.out.println("Setting COMPLETED FALSE ");
              	 i.setLngClkTicks(i.getLngClkTicks() + 1);
              	 i.setExeComplete(false);
               }
                 
               break;
               
             case Constant.ALU_FDIV:
               System.out.println("ALU UNIT DIV");
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                       get(Constant.opcodeToString(Constant.ALU_FDIV)).intValue())
               {
                 System.out.println("Setting COMPLETED TRUE ");
                 i.setExeComplete(true);
                 doubALUout = doubReg1 / doubReg2;

               }else{

                 System.out.println("Setting COMPLETED FALSE ");
                 i.setLngClkTicks(i.getLngClkTicks() + 1);
                 i.setExeComplete(false);
               }
               break;
               
             case Constant.ALU_BEQ:
               System.out.println("ALU UNIT BEQ");
               i.setExeComplete(true);
               break;
             case Constant.ALU_BNEQ:
               System.out.println("ALU UNIT BNEQ");
               i.setExeComplete(true);
               break;
             case Constant.ALU_BGTZ:
               System.out.println("ALU UNIT BGTZ");
               i.setExeComplete(true);
               break;
             case Constant.ALU_BLTZ:
               System.out.println("ALU UNIT BLTZ");
               i.setExeComplete(true);
               break;
         
             case Constant.ALU_FBEQ:
               System.out.println("ALU UNIT FBEQ");
               i.setExeComplete(true);
               break;
             case Constant.ALU_FBNEQ:
               System.out.println("ALU UNIT FBNEQ");
               i.setExeComplete(true);
               break;
             case Constant.ALU_FBGTZ:
               System.out.println("ALU UNIT FBGTZ");
               i.setExeComplete(true);
               break;	 
             case Constant.ALU_FBLTZ:
               System.out.println("ALU UNIT FBLTZ");
               i.setExeComplete(true);
               break;
             case Constant.ALU_MOVE:
               System.out.println("ALU UNIT MOVE");
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                       get(Constant.opcodeToString(Constant.ALU_MOVE)).intValue())
               { 
                 System.out.println("Setting COMPLETED TRUE");
                 doubALUout = (double)lngReg1;
                 i.setExeComplete(true);
               }else
               {
            	 System.out.println("Setting COMPLETED FALSE ");
                 i.setLngClkTicks(i.getLngClkTicks() + 1);
                 i.setExeComplete(false);   
               }
               break;
               
             case Constant.ALU_FMOVE:
               System.out.println("ALU UNIT FMOVE");
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                       get(Constant.opcodeToString(Constant.ALU_FMOVE)).intValue())
               { 
            	 System.out.println("Setting COMPLETED TRUE");  
                 lngALUout = (long)doubReg1;
                 i.setExeComplete(true);
               }else
               {
            	 System.out.println("Setting COMPLETED FALSE ");
                 i.setLngClkTicks(i.getLngClkTicks() + 1);
                 i.setExeComplete(false);   
               }
               
               break;	 
               
           }    
    
           switch ((int) i.getLngMemInstr()) {
    
             case Constant.MEM_NOP:
               System.out.println("ALU MEM NOP");
              // nothing to do
               i.setExeComplete(true);
               break;
             case Constant.MEM_LW:
               System.out.println("ALU MEM LW");	 
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                         get(Constant.opcodeToString(Constant.MEM_LW)).intValue())
               { 
                  System.out.println("Setting COMPLETED TRUE");
                  lngALUout = lngImm + lngReg2;       //lngReg1 + lngImm;
                  i.setExeComplete(true);
               }else
               {
               	  System.out.println("Setting COMPLETED FALSE ");
                  i.setLngClkTicks(i.getLngClkTicks() + 1);
                  i.setExeComplete(false);
               }
               break;
               
             case Constant.MEM_LWI:
               System.out.println("ALU MEM LWI");  	 
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                         get(Constant.opcodeToString(Constant.MEM_LWI)).intValue())
               { 	 
                 System.out.println("Setting COMPLETED TRUE");
                 lngALUout = lngImm;
                 i.setExeComplete(true);
               }else
               {
            	  System.out.println("Setting COMPLETED FALSE ");
                  i.setLngClkTicks(i.getLngClkTicks() + 1);
                  i.setExeComplete(false);    
               }
               break;
               
             case Constant.MEM_S:
               System.out.println("ALU MEM S");	 
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                         get(Constant.opcodeToString(Constant.MEM_S)).intValue())
               { 	 	 
            	 System.out.println("Setting COMPLETED TRUE ");
                 lngALUout = lngImm + lngReg2;       //lngReg1 + lngImm effective address;
                 lngStoreVal = lngReg1;  // value to be stored
                 i.setExeComplete(true);
               }else
               {
            	   System.out.println("Setting COMPLETED FALSE ");
                   i.setLngClkTicks(i.getLngClkTicks() + 1);
                   i.setExeComplete(false);  
               }
               break;
               
             case Constant.MEM_FLW:
               System.out.println("ALU MEM FLW");	 
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                         get(Constant.opcodeToString(Constant.MEM_FLW)).intValue())
               { 	 	  
            	  System.out.println("Setting COMPLETED TRUE ");
                  lngALUout = lngImm + lngReg2;       //lngReg1 + lngImm;
                  i.setExeComplete(true);
               }else
               {
            	   System.out.println("Setting COMPLETED FALSE ");
                   i.setLngClkTicks(i.getLngClkTicks() + 1);
                   i.setExeComplete(false);  
               }
               
               break;
             case Constant.MEM_FS:
               System.out.println("ALU MEM FS");	 
               if((i.getLngClkTicks()+1) == processor.opLatencyHTbl.
                         get(Constant.opcodeToString(Constant.MEM_FS)).intValue())
               { 	 	  	 
            	    System.out.println("Setting COMPLETED TRUE ");              
                    lngALUout = lngImm + lngReg2;       //lngReg1 + lngImm effective address;
                    doubStoreVal = doubReg1;  // value to be stored 
                    i.setExeComplete(true);
               }
               else
               {
            	   System.out.println("Setting COMPLETED FALSE ");
                   i.setLngClkTicks(i.getLngClkTicks() + 1);
                   i.setExeComplete(false); 
               }
               break;   
                
            }
           

            System.out.println("ALU Out: " + lngALUout);
            System.out.println("FALU Out: " + doubALUout);

            i.setLngALUout(lngALUout);
            i.setDoubALUout(doubALUout);
            i.setLngStoreVal(lngStoreVal);  // used in store, contains store value
            i.setDoubStoreVal(doubStoreVal);
            i.setLngNbRegResult(lngNbRegResult);
            i.setLngALUInstr(lngALUInstr);
            i.setLngMemInstr(lngMemInstr);
            
             
        }
        
	       	       
        process();
        latExeToMemArray.writeExeVector(memVector);
        
    }
	
	
	//check what instructions are finished and pass it to the MEM stage
	public void process(){
		
		System.out.println("\n ---- process execution array ---- \n");
		System.out.println("Size of the execute vector before removing :: " + exeVector.size());
		memVector.removeAllElements();
        for(int i = 0; i < exeVector.size(); i++){
        	ExecuteInstruction e = exeVector.get(i);
        	if(e.isExeComplete()){
        		System.out.println("Instruction Completed :: Number " + i + " :: " + "Opcode "+ e.getLngALUInstr());
        		exeVector.remove(i);
        		memVector.add(e);
        		i = -1;
        	}
        }
        System.out.println("Size of the execute vector after removing complted instr :: " + exeVector.size());
        System.out.println("Size of the execute/mem vector :: " + memVector.size());
                  	
	}

}
