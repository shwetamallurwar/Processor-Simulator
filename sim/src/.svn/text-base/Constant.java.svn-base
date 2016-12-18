
public class Constant {
	
	  // ALU instructions
	  public static final int ALU_NOP =  0;
	  public static final int ALU_ADD =  1;
	  public static final int ALU_SUB =  2;
	  public static final int ALU_MUL =  3;
	  public static final int ALU_DIV =  4;
	  public static final int ALU_BEQ =  5;
	  public static final int ALU_BNEQ =  6;
	  public static final int ALU_BGTZ =  7;
	  public static final int ALU_BLTZ = 8;

	  // MEM instructions
	  public static final int MEM_NOP =  0;
	  public static final int MEM_LW =   9;
	  public static final int MEM_LWI =  10;
	  public static final int MEM_S  = 11;
	  
	  
	  public static final int ALU_FADD = 21;
	  public static final int ALU_FSUB = 22;
	  public static final int ALU_FMUL = 23;
	  public static final int ALU_FDIV = 24;
	  public static final int ALU_FBEQ = 25;
	  public static final int ALU_FBNEQ = 26;
	  public static final int ALU_FBGTZ = 27;
	  public static final int ALU_FBLTZ = 28;
	  
	  
	  public static final int MEM_FLW = 29;
	  public static final int MEM_FLWI = 30;
	  public static final int MEM_FS  = 31;
	  
	  public static final int ALU_MOVE = 12;
	  public static final int ALU_FMOVE = 32;
	  
	  public static final int EOF = 100;


	  /** Converts a constants to a string */
	  public static String opcodeToString (int opcode) {
		  switch (opcode) {
		    case ALU_ADD:  return "ADD";
		    case ALU_SUB:  return "SUB";
		    case ALU_MUL:  return "MUL";
		    case ALU_DIV:  return "DIV";
		    case ALU_BEQ:  return "BEQ";
		    case ALU_BNEQ: return "BNEQ";
		    case ALU_BGTZ: return "BGTZ";
		    case ALU_BLTZ:  return "BLTZ";   
		    case MEM_LW:   return "L";
		    case MEM_LWI:  return "LI";
		    case MEM_S:    return "S";
		    case ALU_FADD:  return "ADD.D";
		    case ALU_FSUB:  return "SUB.D";
		    case ALU_FMUL:  return "MUL.D";
		    case ALU_FDIV:  return "DIV.D";
		    case ALU_FBEQ:  return "BEQ.D";
		    case ALU_FBNEQ: return "BNEQ.D";
		    case ALU_FBGTZ: return "BGTZ.D";
		    case ALU_FBLTZ:  return "BLTZ.D";   
		    case MEM_FLW:   return "L.D";
		    case MEM_FLWI:  return "FLI";
		    case MEM_FS:    return "S.D";
		    case ALU_MOVE: return "MOVE";
		    case ALU_FMOVE: return "MOVE.D";
		    case EOF:      return "EOF"; 
		    
		    default:        return Integer.toString(opcode);
		  }
	  }
 
}
