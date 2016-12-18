import java.io.*;
import java.util.*;


public class Parser {
	
  private final File fFile;
 
  public Parser(String aFileName)
  {
    fFile = new File(aFileName);
  }

  /** Template method that calls {@link #processLine(String)}.  */
  public final void processLineByLine(Vector<Command> instVector, Hashtable<String, Integer> latencyhTbl) throws FileNotFoundException
  {
	//Vector<Command> insVector = new Vector<Command>();
	Hashtable<String,Integer> lblIns = new Hashtable<String, Integer>();
    Scanner scanner = new Scanner(fFile);
    try{
      while ( scanner.hasNextLine() )
      {
    	processLine( scanner.nextLine(), instVector, lblIns, latencyhTbl );
      }
      
      //second pass to replace the labels with correct instruction number.
      Iterator<Command> it = instVector.iterator();
      while(it.hasNext())
      {
    	  Command tCmd = it.next();
    	  if ( tCmd.getInstOpx() == Constant.ALU_BEQ || tCmd.getInstOpx() == Constant.ALU_FBEQ 
    		   || tCmd.getInstOpx() == Constant.ALU_BNEQ || tCmd.getInstOpx() == Constant.ALU_FBNEQ
    		   || tCmd.getInstOpx() == Constant.ALU_BGTZ || tCmd.getInstOpx() == Constant.ALU_FBGTZ
    		   || tCmd.getInstOpx()== Constant.ALU_BLTZ  || tCmd.getInstOpx() == Constant.ALU_FBLTZ
    		   )
    	  {
    		  if(lblIns.containsKey(tCmd.getBranchLabel()))
                 tCmd.setDstOp((lblIns.get(tCmd.getBranchLabel())).intValue() );
    		  else
    			System.out.println("Label Not Found!!");  
         		  
    	  }
    
      }
          
    }finally
    {
      scanner.close();
    }
  }


  protected void processLine(String aLine, 
		                     Vector<Command> insVector, 
		                     Hashtable<String, 
		                     Integer> lblIns,
		                     Hashtable<String, Integer> latencyhTbl)
  {
	if(aLine.isEmpty() || aLine.trim().isEmpty())
	{
		System.out.println("EMPTY LINE");
		return;
	}
	aLine = aLine.trim();
    StringTokenizer tempStringTokenizer = new StringTokenizer(aLine, "	 ,");
    String op = (String)tempStringTokenizer.nextToken().replaceAll("\\s*", "").trim();
    System.out.println("******** op ******" + op);
    if(op.contains(":"))
    {
       //means its label
       String lbl = op.replace(":", "").replaceAll("\\s", "").trim();
       int length = insVector.size();
       System.out.println("Label :: " + op + " Instruction Number" + " " + length);
       lblIns.put(lbl, length);
       op = (String)tempStringTokenizer.nextToken().replaceAll("\\s", "").trim();
    }
    
    String opnd1="" ; String opnd2=""; String opnd3 =""; String tmpString ="";
    if(tempStringTokenizer.hasMoreTokens()){
       
       tmpString = tempStringTokenizer.nextToken().replaceAll("\\s*","" ).trim();
       if(!tmpString.contains(";")){
         opnd1 = tmpString;
         tmpString = tempStringTokenizer.nextToken().replaceAll("\\s*", "").trim();
         if(!tmpString.contains(";")){
           opnd2 = tmpString;
           if (tempStringTokenizer.hasMoreTokens())
           {
    	     tmpString =  (String)tempStringTokenizer.nextToken().replaceAll("\\s*", "").trim();
    	     if(!tmpString.contains(";"))
    	       opnd3 = tmpString;	 
           }
         }  
       }  
    }
    
    Command c1 = new Command();
    //System.out.println("OPER :: " + op);
    if( "NOOP".equals(op) )
    {
    	c1.setInstOpx(Constant.ALU_NOP );
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    	
    }else if ("ADD".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_ADD);
    	c1.setDstOp(Register.stringToNumber(opnd1));
    	c1.setSrcOp1(Register.stringToNumber(opnd2));
    	c1.setSrcOp2(Register.stringToNumber(opnd3));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("SUB".equals(op))
    {
        c1.setInstOpx(Constant.ALU_SUB);
        c1.setDstOp(Register.stringToNumber(opnd1));
    	c1.setSrcOp1(Register.stringToNumber(opnd2));
    	c1.setSrcOp2(Register.stringToNumber(opnd3));
    	c1.setLatency(latencyhTbl.get(op).intValue());
        insVector.add(c1);
    }else if ("MUL".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_MUL);
    	c1.setDstOp(Register.stringToNumber(opnd1));
    	c1.setSrcOp1(Register.stringToNumber(opnd2));
    	c1.setSrcOp2(Register.stringToNumber(opnd3));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("DIV".equals(op))
    {
        c1.setInstOpx(Constant.ALU_DIV);
        c1.setDstOp(Register.stringToNumber(opnd1));
    	c1.setSrcOp1(Register.stringToNumber(opnd2));
    	c1.setSrcOp2(Register.stringToNumber(opnd3));
    	c1.setLatency(latencyhTbl.get(op).intValue());
        insVector.add(c1);
    }else if ("BEQ".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_BEQ);
    	//int lblOffset = ((Integer)lblIns.get(opnd3)).intValue();
       	//c1.setDstOp(lblOffset);
    	c1.setBranchLabel(opnd3);
    	c1.setSrcOp1(Register.stringToNumber(opnd1));
    	c1.setSrcOp2(Register.stringToNumber(opnd2));
    	c1.setLatency(latencyhTbl.get(op).intValue());
       	insVector.add(c1);
    	    	
    }else if ("BNEQ".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_BNEQ);
       //	int lblOffset =((Integer)lblIns.get(opnd3)).intValue();
       //	c1.setDstOp(lblOffset);
    	c1.setBranchLabel(opnd3);
       	c1.setSrcOp1(Register.stringToNumber(opnd1));
    	c1.setSrcOp2(Register.stringToNumber(opnd2));
    	c1.setLatency(latencyhTbl.get(op).intValue());
       	insVector.add(c1);
    }else if("BGTZ".equals(op))
    {   
       c1.setInstOpx(Constant.ALU_BGTZ);
       c1.setBranchLabel(opnd2);
       c1.setSrcOp1(Register.stringToNumber(opnd1));
       c1.setLatency(latencyhTbl.get(op).intValue());
       insVector.add(c1);
       
    }else if("BLTZ".equals(op))
    {
       c1.setInstOpx(Constant.ALU_BLTZ);
       c1.setBranchLabel(opnd2);
       c1.setSrcOp1(Register.stringToNumber(opnd1));
       c1.setLatency(latencyhTbl.get(op).intValue());
       insVector.add(c1);
        
    }else if ("L".equals(op))
    {
    	c1.setInstOpx(Constant.MEM_LW);
    	c1.setDstOp(Register.stringToNumber(opnd1));
    	String op1 = opnd2.substring(0, opnd2.indexOf("("));
    	
    	String op2 = opnd2.substring(opnd2.indexOf("(")+1, opnd2.indexOf(")"));
    	
   //    	int offset = Integer.getInteger(op1).intValue() + Integer.getInteger(op2).intValue();
    	c1.setSrcOp1(Integer.valueOf(op1).intValue());
    	
    	c1.setSrcOp2(Register.stringToNumber(op2));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("LI".equals(op))
    {
    	c1.setInstOpx(Constant.MEM_LWI);
        c1.setDstOp(Register.stringToNumber(opnd1));
        System.out.println(opnd2);
        c1.setSrcOp1(Integer.valueOf(opnd2).intValue());
        c1.setLatency(latencyhTbl.get(op).intValue());
        insVector.add(c1);
    }else if ("S".equals(op))
    {
       	c1.setInstOpx(Constant.MEM_S);
    	c1.setSrcOp1(Register.stringToNumber(opnd1));
    	String op1 = opnd2.substring(0, opnd2.indexOf("("));
    	
    	String op2 = opnd2.substring(opnd2.indexOf("(")+1, opnd2.indexOf(")"));
    	c1.setSrcOp2(Register.stringToNumber(op2));
    	c1.setDstOp(Integer.valueOf(op1).intValue());
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("ADD.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FADD);
    	c1.setDstOp(FRegister.stringToNumber(opnd1));
    	c1.setSrcOp1(FRegister.stringToNumber(opnd2));
    	c1.setSrcOp2(FRegister.stringToNumber(opnd3));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("SUB.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FSUB);
    	c1.setDstOp(FRegister.stringToNumber(opnd1));
     	c1.setSrcOp1(FRegister.stringToNumber(opnd2));
     	c1.setSrcOp2(FRegister.stringToNumber(opnd3));
     	c1.setLatency(latencyhTbl.get(op).intValue());
        insVector.add(c1);
    	
    }else if("MUL.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FMUL);
    	c1.setDstOp(FRegister.stringToNumber(opnd1));
    	c1.setSrcOp1(FRegister.stringToNumber(opnd2));
    	c1.setSrcOp2(FRegister.stringToNumber(opnd3));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("DIV.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FDIV);
        c1.setDstOp(FRegister.stringToNumber(opnd1));
    	c1.setSrcOp1(FRegister.stringToNumber(opnd2));
    	c1.setSrcOp2(FRegister.stringToNumber(opnd3));
    	c1.setLatency(latencyhTbl.get(op).intValue());
        insVector.add(c1);
    }else if ("BEQ.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FBEQ);
    	//int lblOffset = ((Integer)lblIns.get(opnd3)).intValue();
       	//c1.setDstOp(lblOffset);
    	c1.setBranchLabel(opnd3);
    	c1.setSrcOp1(FRegister.stringToNumber(opnd1));
    	c1.setSrcOp2(FRegister.stringToNumber(opnd2));
    	c1.setLatency(latencyhTbl.get(op).intValue());
       	insVector.add(c1);
    }else if ("BNEQ.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FBNEQ);
        //	int lblOffset =((Integer)lblIns.get(opnd3)).intValue();
        //	c1.setDstOp(lblOffset);
     	c1.setBranchLabel(opnd3);
        	c1.setSrcOp1(FRegister.stringToNumber(opnd1));
     	c1.setSrcOp2(FRegister.stringToNumber(opnd2));
     	c1.setLatency(latencyhTbl.get(op).intValue());
       	insVector.add(c1);
    }else if ("BGTZ.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FBGTZ);
        c1.setBranchLabel(opnd2);
        c1.setSrcOp1(FRegister.stringToNumber(opnd1));
        c1.setLatency(latencyhTbl.get(op).intValue());
        insVector.add(c1);
    }else if ("BLTZ.D".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_FBLTZ);
        c1.setBranchLabel(opnd2);
        c1.setSrcOp1(FRegister.stringToNumber(opnd1));
        c1.setLatency(latencyhTbl.get(op).intValue());
        insVector.add(c1);
    }else if ("L.D".equals(op))
    {
    	c1.setInstOpx(Constant.MEM_FLW);
    	c1.setDstOp(FRegister.stringToNumber(opnd1));
    	String op1 = opnd2.substring(0, opnd2.indexOf("("));
    	
    	String op2 = opnd2.substring(opnd2.indexOf("(")+1, opnd2.indexOf(")"));
    	
   //    	int offset = Integer.getInteger(op1).intValue() + Integer.getInteger(op2).intValue();
    	c1.setSrcOp1(Integer.valueOf(op1).intValue());
    	
    	c1.setSrcOp2(Register.stringToNumber(op2));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("S.D".equals(op))
    {
    	c1.setInstOpx(Constant.MEM_FS);
    	c1.setSrcOp1(FRegister.stringToNumber(opnd1));
    	String op1 = opnd2.substring(0, opnd2.indexOf("("));
    	
    	String op2 = opnd2.substring(opnd2.indexOf("(")+1, opnd2.indexOf(")"));
    	c1.setSrcOp2(Register.stringToNumber(op2));
    	c1.setDstOp(Integer.valueOf(op1).intValue());
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("MOVE".equals(op))
    {
    	c1.setInstOpx(Constant.ALU_MOVE);
    	c1.setSrcOp1(Register.stringToNumber(opnd1));
    	c1.setDstOp(FRegister.stringToNumber(opnd2));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("MOVE.D".equals(op)) 
    {
    	c1.setInstOpx(Constant.ALU_FMOVE);
    	c1.setSrcOp1(FRegister.stringToNumber(opnd1));
    	c1.setDstOp(Register.stringToNumber(opnd2));
    	c1.setLatency(latencyhTbl.get(op).intValue());
    	insVector.add(c1);
    }else if ("EOF".equals(op))
    {
    	c1.setInstOpx(Constant.EOF);
    	insVector.add(c1);
    }
    
  }
}



