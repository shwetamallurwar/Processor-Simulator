import java.util.Iterator;


public class ID extends Stage {
	
	//info required to pass to EXE stage
	
	Latch latRegister1;
    Latch latRegister2;
	Latch latPC;
    Latch latImm;
    Latch latRegResult;
	Latch latALUInstrOpcode;
	Latch latMemInstrOpcode;
	
	
	//Branch detection
    Latch latControlTransfer; 
    Latch latNewPC; 
	
	// Value to save the state of instruction.
	  private StageIDState stageState;
	  
	  long lngNbRegResult;
	  long lngStallCnt;

	// Last instructions / register written
	  LastInstructions last;

	public ID(Processor processor)
	{
		super(processor);
		latRegResult = new Latch(processor);
		latALUInstrOpcode  = new Latch(processor);
		latMemInstrOpcode = new Latch(processor);
		latRegister1 = new Latch(processor);
	    latRegister2 = new Latch(processor);	
	    latPC = new Latch(processor);
	    latImm = new Latch(processor);
	    
	    stageState = new StageIDState();
	    
	    last = new LastInstructions(processor);
	    latControlTransfer = new Latch(processor);
	    latNewPC = new Latch(processor);
	}
	
	public void execute()
	{
		
		System.out.println(" \n ================="); 
		System.out.println("In StageID Class");
		
		 Command cmd = new Command();
		 long lngLatency;
		 long lngInstr;
  	     long lngPC;
		 long lngOp;
		 long lngNbRegister1;
		 long lngNbRegister2;
		 
		 long lngControlTransfer = -1;
		 long lngNewPC = -1;

		 long lngImm = 0;
		 lngNbRegResult = 0;
		 
		 long lngALUInstr = -1;  // set  both to -1
 	     long lngMemInstr = -1;
 	     
         
 	 // if jump is taken.
 	    if ( (processor.stgID.latControlTransfer.read() == 1) ){
 	         
 	      System.out.println("Jump is taken, flush the instrruction");

 	      System.out.println("NOP");
 	      latALUInstrOpcode.write(Constant.ALU_NOP);
 	      latMemInstrOpcode.write(Constant.MEM_NOP);
 	      latRegister1.write(-1L);
 	      latRegister2.write(-1L);
          latControlTransfer.write(0);  
 	      //last.clear();

 	      return;
 	    }
      
 	     
 	     long lngStageIFStalled = processor.stgIF.latStgIFStalled.read();
 	     System.out.println("ID STAGE :: Reading Stall Latch " + processor.stgIF.latStgIFStalled.read());

 	     // Stage stalled 
 	     if (lngStageIFStalled == 1L) {
 	       // If the stage was stalled, restore the state of the stage before the bubble
 	      
 	       processor.stgIF.latStgIFStalled.write(0L);
 	       // Search the data to execute
 	       lngInstr = stageState.lngInstr;
 	       lngPC     = stageState.lngPC;
 	       lngOp     = stageState.lngOp;
 	       lngNbRegister1 = stageState.lngNbReg1;
 	       lngNbRegister2 = stageState.lngNbReg2;
 	       lngLatency = stageState.lngLatency;
 	       lngNbRegResult = stageState.lngNbRegResult;
 	       
 	       lngStallCnt++;
 	     }
 	     else {
 	       // Reading the instr to decode from instruction register latch
 	       
 	       cmd = processor.stgIF.latIR.readInst();
 	       lngInstr = 0;
   	       //lngInstr = control.memory.latInstr.read();
           if(cmd != null){
 	       // We got the instr to decode!
 	       lngPC     = processor.stgIF.latPC.read();
 	       lngOp     = cmd.getInstOpx(); //Conversion.extractOp(lngInstr);
 	       lngNbRegister1 = cmd.getSrcOp1(); //Conversion.extractR1(lngInstr);
 	       lngNbRegister2 = cmd.getSrcOp2(); //Conversion.extractR2(lngInstr);
 	       lngLatency = cmd.getLatency();
 	       lngNbRegResult = cmd.getDstOp();
           }
           else{
        	 lngPC = 0;
      		 lngOp = -1;
      		 lngNbRegister1 = -1;
      		 lngNbRegister2 = -1;
      		 lngLatency = -1;
      		 lngNbRegResult = -1;
           }
        	   
 	     }
 	     
 	     System.out.println("Instruction :: " + Constant.opcodeToString((int)lngOp)+ "\n" +
 	    		             "PC :: "  + lngPC + "\n" +
 	    		             "Reg1 :: " + lngNbRegister1 + "\n" +
 	    		             "Reg2 :: "  + lngNbRegister2 + "\n" +
 	    		             "Latency :: " + lngLatency + "\n" +
 	    		             "Result Reg :: " + lngNbRegResult );
 	     
 	     if(lngOp == Constant.ALU_NOP)
 	     {
 	    	//Do nothing. 
 	    	System.out.println("NOP");
 	        latPC.write(lngPC);
 	        latALUInstrOpcode.write(Constant.ALU_NOP);
 	        latMemInstrOpcode.write(Constant.MEM_NOP);
 	        return;
 	     }
 	     
 	    long lngTmp1, lngTmp2, lngTmp3;

 	  
 	    if( Constant.ALU_ADD == lngOp 
 	    	|| Constant.ALU_SUB == lngOp
 	    	|| Constant.ALU_MUL == lngOp
 	    	|| Constant.ALU_DIV	== lngOp )
 	    {

 	      System.out.println(Constant.opcodeToString((int)lngOp));
 	      
 	      // true dependency for the register 1 or 2 ?
 	      lngTmp1 = testDataHazards(lngNbRegister1, this);
 	      lngTmp2 = testDataHazards(lngNbRegister2, this);
 	      // output dependency for result register ?
 	      lngTmp3 = chkOutputDependency(lngNbRegResult,lngLatency); // pass current latency.    
 	      if ((lngTmp1 == -1) ||
 	          (lngTmp2 == -1) ||
 	          (lngTmp3 == -1) ) {
 	        
 	    	System.out.println(":: BUBBLE ::");  
 	        latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
 	        latMemInstrOpcode.write(Constant.MEM_NOP);
 	        latRegister1.write(-1L);
 	        latRegister2.write(-1L);
 	        last.update();                                           // Update the last arrays
 	        processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
 	        stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
 	        
 	        return;
 	      }

 	      // Instruction Decode
 	      System.out.println("Opx: " + lngOp);
 	      lngALUInstr = lngOp;
 	    } 
 	    
 	    if(Constant.MEM_LWI == lngOp)
 	    {
 	      System.out.println(Constant.opcodeToString((int)lngOp));	
 	      // no RAW for LI
 	      // output dependency for result register ?
 	      lngTmp1 = chkOutputDependency(lngNbRegResult, lngLatency);
 	      if ((lngTmp1 == -1)){
 	 	        // Insert a bubble
 	 	    	System.out.println(":: BUBBLE ::");  
 	 	        latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
 	 	        latMemInstrOpcode.write(Constant.MEM_NOP);
 	 	        latRegister1.write(-1L);
 	 	        latRegister2.write(-1L);
 	 	        last.update();                                           // Update the last arrays
 	 	        processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
 	 	        stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
 	 	        
 	 	        return;
 	 	  }
    	  System.out.println("Opx: " + lngOp);
          lngImm = lngNbRegister1; //this store offset value for load
          lngMemInstr = lngOp;
      
 	    }
 	    
 	    if(Constant.MEM_LW == lngOp )
 	    {
 	    
          System.out.println(Constant.opcodeToString((int)lngOp));
           
     	  // true dependency for the register 2 ?
          lngTmp2 = testDataHazards(lngNbRegister2, this);
          lngTmp1 = chkOutputDependency(lngNbRegResult, lngLatency);
          if ( lngTmp2 == -1 ||
        	   lngTmp1 == -1 ) {
            
        	System.out.println(":: BUBBLE ::");  
            latALUInstrOpcode.write(Constant.ALU_NOP); // Insert NOP, nullify the inputs.
            latMemInstrOpcode.write(Constant.MEM_NOP);
            latRegister1.write(-1L);
            latRegister2.write(-1L);
 	        last.update();           // Update the last arrays
            processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
            stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency);
            return;
          }
          
          // Instruction Decode
          System.out.println("Opx: " + lngOp);
          lngImm = lngNbRegister1; //this store offset value for load
          lngMemInstr = lngOp;

 	    }
 	    
 	    if(Constant.MEM_S == lngOp){
 	      System.out.println(Constant.opcodeToString((int)lngOp));
 	      // data hazards for the register 2 ?
 	      lngTmp1 = testDataHazards(lngNbRegister1, this);
          lngTmp2 = testDataHazards(lngNbRegister2, this);
          if ( lngTmp2 == -1 ||
        	   lngTmp1 == -1 ) {
             
          	System.out.println(":: BUBBLE ::");  
            latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
            latMemInstrOpcode.write(Constant.MEM_NOP);
            latRegister1.write(-1);
	        latRegister2.write(-1);
   	        last.update();           // Update the last arrays
            processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
            stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency);
            return;
          }
          // Instruction Decode
          System.out.println("Opx: " + lngOp);
          lngImm = lngNbRegResult; //this store offset value for load
          lngMemInstr = lngOp;
          //lngOp = -1; // dont save this instruction in last instruction array
 	    }
 	    	
 	    if( Constant.ALU_BEQ == lngOp 
       		|| Constant.ALU_BNEQ ==lngOp )
          {
   		   lngTmp1 = testDataHazards(lngNbRegister1, this);
 	       lngTmp2 = testDataHazards(lngNbRegister2, this);
 	       if ((lngTmp1 == -1) ||
 	           (lngTmp2 == -1)) {
 	         
 	         System.out.println(":: BUBBLE ::");  
 	         latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
 	         latMemInstrOpcode.write(Constant.MEM_NOP);
 	         latRegister1.write(-1);
 	         latRegister2.write(-1);
 	         last.update();                                           // Update the last arrays
 	         processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
 	         stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
 	         return;
 	       }else{
 	    	   
 	    	 long tmpR1 = processor.register.read(lngNbRegister1);
 	    	 long tmpR2 = processor.register.read(lngNbRegister2);
 	    	 
 	    	 switch((int)lngOp){
 	    	 
 	    	 case Constant.ALU_BEQ :
 	    	   if(tmpR1 == tmpR2){
 	    	     lngControlTransfer = 1;
 	    	     lngNewPC = lngNbRegResult ; //this stores target ins
 	    	    	                            //for branch
 	    	     System.out.println(" **************");
 	    	     System.out.println(" lngNewPC :: " + lngNewPC);
 	    	     System.out.println("Control transfer :: " + lngControlTransfer);
 	    	   }
 	    	   else{
           	     lngControlTransfer = 0;
           	     lngNewPC = lngOp + 1;
 	    	   }
 	    	   break;
 	    	 
 	    	 case Constant.ALU_BNEQ:
  	    	   if(tmpR1 != tmpR2){
  	 	    	 lngControlTransfer = 1;
  	 	    	 lngNewPC = lngNbRegResult ; //this stores target ins
  	 	    	    	                            //for branch
  	 	    	 System.out.println(" **************");
  	 	    	 System.out.println(" lngNewPC :: " + lngNewPC);
  	 	    	 System.out.println("Control transfer :: " + lngControlTransfer);
  	 	       }
  	 	       else{
  	           	 lngControlTransfer = 0;
  	           	 lngNewPC = lngOp + 1;
      	       } 
  	    	   break; 
 	    	 }
 	       }
      	  
       	   System.out.println("Opx: " + lngOp);
       	   lngALUInstr = lngOp;
       	   lngOp = -1;   // dont save this instruction in last instruction array.
 		  
        }
 	    
 	    if( Constant.ALU_BGTZ == lngOp
 	       		|| Constant.ALU_BLTZ == lngOp )
 	    {
 	      lngTmp1 = testDataHazards(lngNbRegister1, this);
  	      if ( lngTmp1 == -1 ){
  	      
  	        
  	        System.out.println(":: BUBBLE ::");  
  	        latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
  	        latMemInstrOpcode.write(Constant.MEM_NOP);
  	        latRegister1.write(-1);
  	        latRegister2.write(-1);
  	        last.update();                                           // Update the last arrays
  	        processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
  	        stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
  	        return;
  	      }else{
  	    	long tmpR1 = processor.register.read(lngNbRegister1);
	    
	    	switch((int)lngOp){
	    	 
	    	  case Constant.ALU_BGTZ :
	    	    if(tmpR1 > 0){
	    	      lngControlTransfer = 1;
	    	      lngNewPC = lngNbRegResult ; //this stores target ins
	    	    	                            //for branch
	    	      System.out.println(" **************");
	    	      System.out.println(" lngNewPC :: " + lngNewPC);
	    	      System.out.println("Control transfer :: " + lngControlTransfer);
	    	    }
	    	    else{
          	      lngControlTransfer = 0;
          	      lngNewPC = lngOp + 1;
	    	    }
	    	    break;
	    	  case Constant.ALU_BLTZ:
	  	    	if(tmpR1 < 0){
	  	 	      lngControlTransfer = 1;
	  	 	      lngNewPC = lngNbRegResult ; //this stores target ins
	  	 	    	    	                            //for branch
	  	 	      System.out.println(" **************");
	  	 	      System.out.println(" lngNewPC :: " + lngNewPC);
	  	 	      System.out.println("Control transfer :: " + lngControlTransfer);
	  	 	    }
	  	 	    else{
	  	          lngControlTransfer = 0;
	  	          lngNewPC = lngOp + 1;
	      	    } 
	  	    	break; 
	 	    }  
  	      }
  	      
  	      System.out.println("Opx: " + lngOp);
    	  lngALUInstr = lngOp;
    	  lngOp = -1;   // dont save this instruction in last instruction array.
  	    }
 	    
 	    if( Constant.ALU_FADD == lngOp 
 	 	    	|| Constant.ALU_FSUB == lngOp
 	 	    	|| Constant.ALU_FMUL == lngOp
 	 	    	|| Constant.ALU_FDIV	== lngOp )
 	 	{
 	 	   
 	 	   System.out.println(Constant.opcodeToString((int)lngOp));
 	 	      
 	 	   // Test if data hazards for the register 1 or 2
 	 	   lngTmp1 = testDataHazards(lngNbRegister1, this);
 	 	   lngTmp2 = testDataHazards(lngNbRegister2, this);
 	 	   lngTmp3 = chkOutputDependency(lngNbRegResult,lngLatency); // pass current latency.    
 	 	   if ((lngTmp1 == -1) ||
 	 	       (lngTmp2 == -1) ||
 	 	       (lngTmp3 == -1) ) {
 	 	      
 	 	      System.out.println(":: BUBBLE ::");  
 	 	      latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
 	 	      latMemInstrOpcode.write(Constant.MEM_NOP);
 	 	      latRegister1.write(-1L);
 	 	      latRegister2.write(-1L);
 	 	      last.update();                                           // Update the last arrays
 	 	      processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
 	 	      stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
 	 	        
 	 	      return;
 	 	    }
 	 	      // Instruction Decode
 	 	      System.out.println("Opx: " + lngOp);
 	 	      lngALUInstr = lngOp;
 	 	} // for Arithmatic operation
 	    
 	   if(Constant.MEM_FLWI == lngOp)
	    {
	      System.out.println(Constant.opcodeToString((int)lngOp));	
	      // no RAW for LI
	    
	      lngTmp1 = chkOutputDependency(lngNbRegResult, lngLatency);
	      if ((lngTmp1 == -1)){
	 	        
	 	    	System.out.println(":: BUBBLE ::");  
	 	        latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
	 	        latMemInstrOpcode.write(Constant.MEM_NOP);
	 	        latRegister1.write(-1L);
	 	        latRegister2.write(-1L);
	 	        last.update();                                           // Update the last arrays
	 	        processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
	 	        stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
	 	        
	 	        return;
	 	  }
   	      System.out.println("Opx: " + lngOp);
          lngImm = lngNbRegister1; //this store offset value for load
          lngMemInstr = lngOp;
     
	    } // if end for MEM_FLWI
 	    
 	    
 	    if( Constant.ALU_MOVE == lngOp )
 	    {
 	      System.out.println(Constant.opcodeToString((int)lngOp));
 	      
 	      lngTmp1 = testDataHazards(lngNbRegister1, this);
 	      lngTmp2 = chkOutputDependency(lngNbRegResult, lngLatency);
 	      if ( lngTmp1 == -1 ||
 	    	   lngTmp2 == -1 ) {
 	    	 System.out.println(":: BUBBLE ::");
 	    	 latALUInstrOpcode.write(Constant.ALU_NOP); // Insert NOP, nullify the inputs.
             latMemInstrOpcode.write(Constant.MEM_NOP);
             latRegister1.write(-1L);
             latRegister2.write(-1L);
 	         last.update();           // Update the last arrays
             processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
             stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency);
             return;
 	      }
 	      
 	          // Instruction Decode
	 	      System.out.println("Opx: " + lngOp);
	 	      lngALUInstr = lngOp;
 	    }
 	    
 	    if( Constant.ALU_FMOVE == lngOp)
 	    {
 	       lngTmp1 = testDataHazards(lngNbRegister1, this);
 	       lngTmp2 = chkOutputDependency(lngNbRegResult, lngLatency);
 	       if ( lngTmp1 == -1 ||
 	 	    	   lngTmp2 == -1 ) {
 	 	    	 System.out.println(":: BUBBLE ::");
 	 	    	 latALUInstrOpcode.write(Constant.ALU_NOP); // Insert NOP, nullify the inputs.
 	             latMemInstrOpcode.write(Constant.MEM_NOP);
 	             latRegister1.write(-1L);
 	             latRegister2.write(-1L);
 	 	         last.update();           // Update the last arrays
 	             processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
 	             stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency);
 	             return;
 	 	    }
 	          // Instruction Decode
	 	      System.out.println("Opx: " + lngOp);
	 	      lngALUInstr = lngOp;  
 	    }
 	    
    	if(Constant.MEM_FLW == lngOp )
	    {
	    
          System.out.println(Constant.opcodeToString((int)lngOp));
         
   	      // data hazards for the register 2 ?
          lngTmp2 = testDataHazards(lngNbRegister2, this);
          lngTmp1 = chkOutputDependency(lngNbRegResult, lngLatency);
          if ( lngTmp2 == -1 ||
      	       lngTmp1 == -1 ) {
            
      	    System.out.println(":: BUBBLE ::");  
            latALUInstrOpcode.write(Constant.ALU_NOP); // Insert NOP, nullify the inputs.
            latMemInstrOpcode.write(Constant.MEM_NOP);
            latRegister1.write(-1L);
            latRegister2.write(-1L);
	        last.update();           // Update the last arrays
            processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
            stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency);
            return;
          }
        
          // Instruction Decode
          System.out.println("Opx: " + lngOp);
          lngImm = lngNbRegister1; //this store offset value for load
          lngMemInstr = lngOp;

	    } //if ends MEM_FLW 
 	    
    	if(Constant.MEM_FS == lngOp){
    	  System.out.println(Constant.opcodeToString((int)lngOp));
    	  // data hazards for the register 2 ?
    	  lngTmp1 = testDataHazards(lngNbRegister1, this);
          lngTmp2 = testDataHazards(lngNbRegister2, this);
          if ( lngTmp2 == -1 ||
           	   lngTmp1 == -1 ) {
             
             System.out.println(":: BUBBLE ::");  
             latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
             latMemInstrOpcode.write(Constant.MEM_NOP);
      	     last.update();           // Update the last arrays
             processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
             stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency);
             return;
           }
             // Instruction Decode
           System.out.println("Opx: " + lngOp);
           lngImm = lngNbRegResult; //this store offset value for load
           lngMemInstr = lngOp;
           //lngOp = -1;      // dont store this instruction in last instruction.
    	}
    	
    	if( Constant.ALU_FBEQ == lngOp 
    		|| Constant.ALU_FBNEQ ==lngOp )
    	{
    	   lngTmp1 = testDataHazards(lngNbRegister1, this);
    	   lngTmp2 = testDataHazards(lngNbRegister2, this);
    	   if ((lngTmp1 == -1) ||
    	       (lngTmp2 == -1)) {
    	      
    	      System.out.println(":: BUBBLE ::");  
    	      latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
    	      latMemInstrOpcode.write(Constant.MEM_NOP);
    	      latRegister1.write(-1);
    	      latRegister2.write(-1);
    	      last.update();                                           // Update the last arrays
    	      processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
    	      stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
    	      return;
    	   }else{
    	 	    	   
    	 	 double tmpR1 = processor.fregister.read(lngNbRegister1);
    	 	 double tmpR2 = processor.fregister.read(lngNbRegister2);
    	 	    	 
    	 	 switch((int)lngOp){
    	 	    	 
    	 	    case Constant.ALU_FBEQ :
    	 	      if(tmpR1 == tmpR2){
    	 	        lngControlTransfer = 1;
    	 	        lngNewPC = lngNbRegResult ; //this stores target ins
    	 	    	    	                            //for branch
    	 	        System.out.println(" **************");
    	 	        System.out.println(" lngNewPC :: " + lngNewPC);
    	 	        System.out.println("Control transfer :: " + lngControlTransfer);
    	 	      }
    	 	      else{
    	            lngControlTransfer = 0;
    	            lngNewPC = lngOp + 1;
    	 	      }
    	 	      break;
    	 	    	 
    	 	    case Constant.ALU_FBNEQ:
    	  	      if(tmpR1 != tmpR2){
    	  	 	   	 lngControlTransfer = 1;
    	  	 	   	 lngNewPC = lngNbRegResult ; //this stores target ins
    	  	 	    	    	                            //for branch
    	  	 	   	 System.out.println(" **************");
    	  	 	   	 System.out.println(" lngNewPC :: " + lngNewPC);
    	  	 	   	 System.out.println("Control transfer :: " + lngControlTransfer);
    	  	 	  }
    	  	 	  else{
    	  	      	 lngControlTransfer = 0;
    	  	      	 lngNewPC = lngOp + 1;
    	      	  } 
    	  	      break; 
    	 	 }// siwtch ends
    	   } // else ends
    	      	  
    	     System.out.println("Opx: " + lngOp);
    	     lngALUInstr = lngOp;
    	     lngOp = -1;   // dont save this instruction in last instruction array.
    	 		  
    	} // if for BEQ and BNEQ ends
    	
    	if( Constant.ALU_FBGTZ == lngOp
   	    	|| Constant.ALU_FBLTZ == lngOp )
   	    {
   	      lngTmp1 = testDataHazards(lngNbRegister1, this);
    	  if ( lngTmp1 == -1 ){
    	      
    	     System.out.println(":: BUBBLE ::");  
    	     latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
    	     latMemInstrOpcode.write(Constant.MEM_NOP);
    	     latRegister1.write(-1);
    	     latRegister2.write(-1);
    	     last.update();                                           // Update the last arrays
    	     processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
    	     stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency );
    	     return;
    	  }else{
    	    double tmpR1 = processor.fregister.read(lngNbRegister1);
  	    
  	    	switch((int)lngOp){
  	    	 
  	    	  case Constant.ALU_FBGTZ :
  	    	    if(tmpR1 > 0){
  	    	      lngControlTransfer = 1;
  	    	      lngNewPC = lngNbRegResult ; //this stores target ins
  	    	    	                            //for branch
  	    	      System.out.println(" **************");
  	    	      System.out.println(" lngNewPC :: " + lngNewPC);
  	    	      System.out.println("Control transfer :: " + lngControlTransfer);
  	    	    }
  	    	    else{
            	      lngControlTransfer = 0;
            	      lngNewPC = lngOp + 1;
  	    	    }
  	    	    break;
  	    	  case Constant.ALU_FBLTZ:
  	  	    	if(tmpR1 < 0){
  	  	 	      lngControlTransfer = 1;
  	  	 	      lngNewPC = lngNbRegResult ; //this stores target ins
  	  	 	    	    	                            //for branch
  	  	 	      System.out.println(" **************");
  	  	 	      System.out.println(" lngNewPC :: " + lngNewPC);
  	  	 	      System.out.println("Control transfer :: " + lngControlTransfer);
  	  	 	    }
  	  	 	    else{
  	  	          lngControlTransfer = 0;
  	  	          lngNewPC = lngOp + 1;
  	      	    } 
  	  	    	break; 
  	 	    }  //switch ends
    	  } //else ends
    	      
    	    System.out.println("Opx: " + lngOp);
      	    lngALUInstr = lngOp;
      	    lngOp = -1;   // dont save this instruction in last instruction array.
        } // if ends    	    
    	
 	    if(Constant.EOF == lngOp)
 	    {
 	    	latALUInstrOpcode.write(Constant.ALU_NOP);                   // Insert NOP
            latMemInstrOpcode.write(Constant.MEM_NOP);
            latRegister1.write(-1L);
            latRegister2.write(-1L);
            processor.stgIF.latStgIFStalled.write(1L); // Stall the IF stage
            System.out.println("EOF :: STALLING IF FROM ID " + processor.stgIF.latStgIFStalled.read());
            stageState.store(lngInstr, lngPC, lngOp, lngNbRegister1, lngNbRegister2, lngNbRegResult, lngLatency);
            last.update();
            last.pipeLineMonitor(processor.outputFile);
            return;
 	    }
 	    
 	    if(Constant.MEM_S == lngOp ||  Constant.MEM_FS == lngOp){
     	  
 	      last.update(lngOp, -1, lngLatency);
 	    }
 	    else
 	    {
 	      //  Update the last arrays	
 	      last.update(lngOp, lngNbRegResult,lngLatency);
 	    }
 	    
    	latPC.write(lngPC);
 	    latRegister1.write(lngNbRegister1);
 	    latRegister2.write(lngNbRegister2);
 	    latImm.write(lngImm);
 	    latRegResult.write(lngNbRegResult);
 	    latALUInstrOpcode.write(lngALUInstr);
 	    latMemInstrOpcode.write(lngMemInstr);
 	    //Branch Tranfser
        latControlTransfer.write(lngControlTransfer);
        latNewPC.write(lngNewPC);
 	     	     
	}
	
	public long testDataHazards (long nbReg, Stage s) {
	     
		long result = 0;
	    if (nbReg == 0L) {
	      result = nbReg;
	    }
	    long tmp = chkTrueDependancy(nbReg);
	    if(tmp == -1){
	      result = -1;	
	    }
	    return result;
	} 
	
	//if true dependency present return -1 otherwise 0; -- RAW 
	public long chkTrueDependancy(long nbReg)
	{   
		System.out.println(" \n TRUE DEPENDANCY Check ");
		long result=0;
		Iterator<LastInstruction> it = last.lastinstrVec.iterator();
		while(it.hasNext()){
		  LastInstruction l = it.next();
		  System.out.println("TRUE DEP cur register :: " + nbReg + " prev instr reg:: " +l.getDstRegNB() );
		  if(nbReg == l.getDstRegNB()){
			  result = -1;
			  System.out.println("True Dependency Detected!!");
			  break;
		  }
		}
		return result; 
	}
	
	//if Anti dependency present return -1 otherwise 0 - WAW
	public long chkOutputDependency(long nbReg, long latency)
	{
	  System.out.println("\n OUTPUT DEPENDANCY Check");	
	  long result=0;
	  Iterator<LastInstruction> it = last.lastinstrVec.iterator();
	  while(it.hasNext()){
		LastInstruction l = it.next();
		System.out.println("O/P DEP cur register :: " + nbReg + " prev instr reg:: " +l.getDstRegNB());
		if(nbReg == l.getDstRegNB()){
		  //long latency = this.control.stageIF.latIR.readInst().getLatency();
		  System.out.println("Latency of current instruction ::" + latency);
		  System.out.println("Cycles required to finish ins in pipeline ::" + (l.getLatencyIns() - l.getClockCyElapsed()));
		  if(latency < (l.getLatencyIns() - l.getClockCyElapsed()-1) ){
			 // -1 because in this cycle it will tick one more cycle. 
	         result = -1;
	         System.out.println(" Anti Dependency detected!!");
	         break;
		  }
		}
	  }
	  return result;
	}
	
	
	private class StageIDState {
	  long lngLatency;	
	  long lngInstr;
	  long lngPC;
	  long lngOp;
	  long lngNbReg1;
	  long lngNbReg2;
      long lngNbRegResult;
      
	
	  void store (long lngInstr,long lngPC, 
			      long lngOp,long lngNbReg1, 
			      long lngNbReg2, long lngNbRegResult,
			      long lngLatency) {
	    this.lngInstr = lngInstr;
	    this.lngPC = lngPC;
	    this.lngOp = lngOp;
	    this.lngNbReg1 = lngNbReg1;
	    this.lngNbReg2 = lngNbReg2;
	    this.lngNbRegResult = lngNbRegResult;
	    this.lngLatency = lngLatency;
	  }
	} // StageIDState

}
