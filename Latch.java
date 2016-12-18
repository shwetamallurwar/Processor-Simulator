import java.util.Iterator;
import java.util.Vector;


public class Latch implements TickTheClock {

	
  private long value;
  private long  tmpValforcurClock;
  private Command latInstr;
  private Command tmplatInstr;
  private Vector<ExecuteInstruction> exETOMVector;
  private Vector<ExecuteInstruction> tmpexETOMvector;

  /** Constructor */
  public Latch (Processor processor) {
    processor.addClkChListner(this);
    latInstr = new Command();
    tmplatInstr = new Command();
    exETOMVector = new Vector<ExecuteInstruction>();
    tmpexETOMvector = new Vector<ExecuteInstruction>();
    value = -1;
    tmpValforcurClock = -1;
  }

 
  public void clockChanged() {
		
	  
		value = tmpValforcurClock;
		latInstr = tmplatInstr;
		
		exETOMVector.removeAllElements();
		 Iterator<ExecuteInstruction> it = tmpexETOMvector.iterator(); 
    	 while(it.hasNext()){
		   ExecuteInstruction e = it.next();
		   exETOMVector.add(e);
		 }
		
	  }
  
  /**
   * Reads the value of the latch.
   */
  public long read () {
	 return value;
  }

  /**
   * Writes the value in the latch.
   * 
   */
  public void write (long v) {
	  tmpValforcurClock = v;
  }

  public void writeInst (Command ins)
  {	    
	  tmplatInstr = ins;
  }
  
  public Command readInst ()
  {	     
	  return latInstr;
  }
  
  public void writeExeVector(Vector<ExecuteInstruction> ex)
  {
	  tmpexETOMvector.removeAllElements();
	  Iterator<ExecuteInstruction> it = ex.iterator(); 
	  while(it.hasNext()){
		  ExecuteInstruction e = it.next();
		  tmpexETOMvector.add(e);
	  }
  }
  
  public Vector<ExecuteInstruction> readExeVector()
  {
	  
	 return exETOMVector; 
  }
}
