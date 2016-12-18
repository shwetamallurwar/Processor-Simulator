import java.io.*;
import java.util.*;
public class Processor  {

  // stores instruction and latency	
  private Hashtable<String,Integer> latencyHashTbl = new Hashtable<String, Integer>();
  public Hashtable<String, Integer> opLatencyHTbl = new Hashtable<String, Integer>();
  
  // The vector to store the latches
  private Vector<TickTheClock> flipTheLatches = new Vector<TickTheClock>();

  // store the output filename.
  public String outputFile;
  

  // processor elements.
  public Memory    memory;
  public Register  register;
  public FRegister fregister;
  public Interpreter interpreter;
  public IF   stgIF;
  public ID   stgID;
  public EXE  stgEXE;
  public MEM  stgMEM;
  public WB   stgWB;
  
  
  /** A counter for number of cycle executed */
  public int cycle = 0;
 
  public static void main(String[] args) throws FileNotFoundException {
    String usage = " -help | "
      + "arch_params_file init_mem_file input_assembly_file output_file";

    if (!(ArgChk.isValid(usage, args))) {
      System.out.println("usage: " + usage);
      return;
    }

    if (ArgChk.switchFound("-help", args)) {
      System.out.println("usage: java Processor " + usage);
      return;
    }

       
    
    Processor processor = new Processor(args[3]);

    processor.initProcElements();
    
    // If the user gave a data file name to load
    processor.memory.load(args[1]);
	
	    
    //Read Latency File
    //program expects latency of all operations, if user forget to mention the latency
    //of particular operation program reads it from default file.
    processor.readDefaultLatencyFile("defaultLatency.txt");
    processor.readLatencyFile(args[0]);
    
    //Read Program File 
    Parser par = new Parser(args[2]);
    par.processLineByLine(processor.memory.getInstVector(),processor.latencyHashTbl );
    
    processor.memory.readAllInstruction();
    
    processor.start();
    
  }


/** Construct processor */
public Processor(String outFile) {
  this.outputFile = outFile;
}


/** Initialize the processor elements. */
private void initProcElements () {
  fregister = new FRegister(this);	
  register = new Register(this);
  stgIF = new IF(this);
  stgID = new ID(this);
  stgEXE = new EXE(this);
  stgMEM = new MEM(this);
  stgWB = new WB(this);
  memory = new Memory(this);
  interpreter = new Interpreter(this);
}


public void addClkChListner(TickTheClock clockChangeListener) {
  flipTheLatches.addElement(clockChangeListener);
}


private void doWork () {
   
  stgIF.execute();
  stgID.execute();
  stgEXE.execute();
  stgMEM.execute();
  stgWB.execute(); 
  cycle++;
  
}


private void processClockChange () {

  for (Enumeration<TickTheClock> e = flipTheLatches.elements(); e.hasMoreElements();) {
    ((TickTheClock)(e.nextElement())).clockChanged();
  }
}


/** Start the processor */
public void start () {

	 while (true) {
	   doWork();
	   processClockChange();
	   System.out.println("=========================");
	   System.out.println("*************************");
	   System.out.println("Clock Cycle :: " + cycle);
	   System.out.println("**************************");
	 }
	
}

// to run the interpreter
public void runInterpreter()
{
	interpreter.work();
}

/** reads the user param file and put it in the hashtable */

public void readLatencyFile(String fname) throws FileNotFoundException
{
	 Scanner scanner = new Scanner(new File (fname));
     try{
	   while ( scanner.hasNextLine() )
	   {
	   	 StringTokenizer tempStringTokenizer = new StringTokenizer(scanner.nextLine(), " ");
	   	 
	   	 String opn = tempStringTokenizer.nextToken().replaceAll("\\s*", "").trim();
	   	 String lat = tempStringTokenizer.nextToken().replaceAll("\\s*", "").trim();
	   	 
	   	 Integer latency =  Integer.parseInt(lat);
	   	 System.out.println("operation :: " + opn + "  latency :: " + latency);
	   	 if(!latencyHashTbl.containsKey(opn)){
		   	   latencyHashTbl.put(opn, latency+2);
		   	   opLatencyHTbl.put(opn,latency);  // adding if the key is not present in default hashtable
		 }
		 else
		 {
		    latencyHashTbl.remove(opn); 
		    opLatencyHTbl.remove(opn); // removing and then adding; overwriting it.
		    latencyHashTbl.put(opn, latency+2);
		    opLatencyHTbl.put(opn, latency);
		   		 
		    System.out.println("OverWriting the latency :: " + opn + " " +opLatencyHTbl.get(opn).intValue() );
		 }
	   }
	 }finally
     {
       scanner.close();
     }
}

/** read default param file and put in the hashtable */
public void readDefaultLatencyFile(String fname) throws FileNotFoundException
{
	Scanner scanner = new Scanner(new File (fname));
    try{
	   while ( scanner.hasNextLine() )
	   {
	   	 StringTokenizer tempStringTokenizer = new StringTokenizer(scanner.nextLine(), " ");
	   	 
	   	 String opn = tempStringTokenizer.nextToken().replaceAll("\\s*", "").trim();
	   	 String lat = tempStringTokenizer.nextToken().replaceAll("\\s*", "").trim();
	   	 
	   	 Integer latency =  Integer.parseInt(lat);
	   	 
	   	 System.out.println("operation :: " + opn + "  latency :: " + latency);
	   	 latencyHashTbl.put(opn, latency+2);
	   	 opLatencyHTbl.put(opn,latency);
	   }
	 }finally
    {
      scanner.close();
    }
	
}

} // class ends.
