import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

public class Memory extends JFrame {
	
	public static final int MEMORY_SIZE = 100000;

	public Vector<Command> instVector;
	public Processor processor;
	private short values[] = new short[MEMORY_SIZE];

	private String[] names;
    private Object[][] data;

	private AbstractTableModel dataModel = new AbstractTableModel() {
    
	  public int getColumnCount () {
        return names.length;
      }
      public int getRowCount () {
         return data.length;
      }
      public Object getValueAt (int row, int col) {
        return data[row][col];
      }

      public String getColumnName (int column) {
         return names[column];
      }
      public Class getColumnClass (int col) {
         return getValueAt(0,col).getClass();
      }
      public void setValueAt (Object aValue, int row, int column) {
         data[row][column] = aValue;
      }
    };
    
    private void initJFrame () {
        setTitle("Memory");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setOpaque(true);
        getContentPane().add(scrollPane);

        JTable table = new JTable();
        scrollPane.getViewport().add(table);
        setVisible(true);
        setSize(20, 500);
        setLocation(500,40);
        setBounds(500, 20, 200, 600);

        names = new String[2];
        names[0] = "Address";
        names[1] = "Value";
        data = new Object[MEMORY_SIZE][2];
        for (int i = 0; i < MEMORY_SIZE; i++) {
          data[i][0] = new Integer(i);
          data[i][1] = new Integer(values[i]);
        }
        table.setModel(dataModel);

      }
    
    public void write (long address, long value) {
    
//        if (!((value < 0) || (value >= 0xFFFFFFFFL)) &&
//            !((address < 0) || (address > values.length))) {
          values[(int)address + 3] = (short) (value & 0xFF);

          values[(int)address + 2] = (short) ((value & 0xFF00)>>8);

          values[(int)address + 1] = (short) ((value & 0xFF0000)>>16);

          values[(int)address] = (short) ((value & 0xFF000000)>>24);

//        }
//        System.out.println("After Write" + values[(int)address]);         
        dataModel.setValueAt(new Long(values[(int)address]), (int)address, 1);
        dataModel.setValueAt(new Long(values[(int)address + 1]), (int)address + 1, 1);
        dataModel.setValueAt(new Long(values[(int)address + 2]), (int)address + 2, 1);
        dataModel.setValueAt(new Long(values[(int)address + 3]), (int)address + 3, 1);
        dataModel.fireTableCellUpdated((int)address, 1);
        dataModel.fireTableCellUpdated((int)address + 1, 1);
        dataModel.fireTableCellUpdated((int)address + 2, 1);
        dataModel.fireTableCellUpdated((int)address + 3, 1);

      }
    
    public void writeDouble(long address, double val )
    { 
      int i = 0; 	 
      long lngval= Double.doubleToLongBits(val);
      //System.out.println("write double" + lngval);
      //System.out.println(Long.toBinaryString(lngval));
        if(Long.toBinaryString(lngval).length() <=32)
        {
        	System.out.println("Result shift");
        	lngval = lngval << 32;
        }
                      
    	short b = (short)((lngval & 0xFF00000000000000L) >> 56 ); 
    	writeByte(address,b);
    	
    	b = (short)((lngval & 0x00FF000000000000L) >> 48 );
    	writeByte( address+1, b);
    	
    	b = (short)((lngval & 0x0000FF0000000000L) >> 40);
    	writeByte( address+2, b);
    	
    	b = (short)((lngval & 0x000000FF00000000L) >> 32 );
    	writeByte( address+3, b);
    	
    	b = (short)((lngval & 0x00000000FF000000L) >> 24 );
    	writeByte( address+4, b);
    	
    	b = (short)((lngval & 0x0000000000FF0000L) >> 16);
    	writeByte( address+5, b);
    	
    	b = (short)((lngval & 0x000000000000FF00L) >> 8 );
    	writeByte( address+6, b);
    	
    	b = (short)(lngval & 0x00000000000000FFL);
    	writeByte( address+7, b);
    	
            
    } 
    
    public void writeByte(long address, short b)
    {
    	
    	if (!(address < 0) || (address > values.length))
    	{
    		values[(int)address] = (short) b;
    		//System.out.println("::Byte Value::" + b);
    	}
    	
    	dataModel.setValueAt(new Long(values[(int)address]), (int)address, 1);
    	dataModel.fireTableCellUpdated((int)address, 1);
    	
   } 
    
    
    
    // reads double value at the memory location.    
    public double readDValAtMloc(long address)
    {
    	
    	int intAddress = (int)address;  
    	long result= (values[intAddress] << 56 ) | 
    	             (values[intAddress+1] << 48) |
    	             (values[intAddress+2] << 40) |
    	             (values[intAddress+3] << 32) |
    	             (values[intAddress+4] << 24 ) | 
    	             (values[intAddress+5] << 16) |
    	             (values[intAddress+6] << 8) |
    	             (values[intAddress+7] ) ;
    	System.out.println(Long.toBinaryString(Double.doubleToLongBits(100)));
    	if(Long.toBinaryString(result).length() <= 32){
    		System.out.println("Result shift");
    	result = result << 32;
    	}
    	System.out.println(Long.toBinaryString(result));
    	double d = Double.longBitsToDouble(result);
    	
       
  //    System.out.println("Read Result :: " + result);	             
    	return d;
      	
    }
    
    public long readValAtMloc(long address)
    {
    	int intAddress = (int)address;  
    	long result= (values[intAddress] << 24 ) | 
    	             (values[intAddress+1] << 16) |
    	             (values[intAddress+2] << 8) |
    	             (values[intAddress+3]);
  //      System.out.println("Read Result :: " + result);	             
    	return result;
    }
    
    /** Constructor */
	public Memory (Processor processor)
	{
	
		this.processor = processor;
		instVector = new Vector<Command>();
	    initJFrame();	
	}
	
	public Vector<Command> getInstVector(){
		return instVector;
	}
	
	public void readAllInstruction()
	{
		System.out.println("Read All Instructions");
	  	Iterator<Command> it = instVector.iterator();
	  	while (it.hasNext())
	  	{
	  		Command cmd = it.next();
	  		System.out.println(Constant.opcodeToString(cmd.getInstOpx())+ " " 
	  				           + cmd.getDstOp() + " " + cmd.getSrcOp1()+ " " 
	  				           + cmd.getSrcOp2());
	  	}
	}
	
	public Command readOneInst(long pc)
	{
		
		return instVector.get((int)pc);
	}
	
	public void load(String filename)
	{
		
	   try {
		   
			RandomAccessFile inFile = new RandomAccessFile(filename, "rw");
			//for(int i=0; i < inFile.length()/4 ; i++)
			for(int i=0; i < inFile.length() ; i++)
			{
			  int val = inFile.readUnsignedByte();
			  writeByte(i,(short)val);
			}
			
			//System.out.println("DOUBLE ::::  " + readDValAtMloc(0));
			//System.out.println("INT ::::: " + readValAtMloc(8));
			inFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File No Found");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	

}
