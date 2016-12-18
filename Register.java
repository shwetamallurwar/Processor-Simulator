
import java.awt.Font;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Vector;

public class Register extends JFrame {

  
public static HashMap<String,Integer> hashMap = new HashMap();

// Number of register
  private static final int REGISTER_NUMBERS = 16;

  // The arrays to store the register values
  private long values[] = new long[REGISTER_NUMBERS];
  
  public Processor processor;

  private String[] names;
  private Object[][] data;

  // The data model for the table
  private AbstractTableModel dataModel = new AbstractTableModel() {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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


  /**
   * Constructor for Register.
   *
   * 
   */
  public Register (Processor processor) {
    //super(processor);
	this.processor = processor;  
    initWindow();
    initMap();
  }


  /** Initialize the window for the register. */
  private void initWindow () {
    setTitle("Register");


    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setOpaque(true);
    getContentPane().add(scrollPane);

    JTable table = new JTable();
    scrollPane.getViewport().add(table);
    setVisible(true);
    setSize(20, 300);
    //setLocation(900,40);
    setBounds(800, 20, 200, 300);

    names = new String[2];
    names[0] = "Address";
    names[1] = "Value";
    data = new String[REGISTER_NUMBERS][2];
    for (int i = 0; i < REGISTER_NUMBERS; i++) {
      data[i][0] = Long.toString(i);
      data[i][1] = Long.toString(0);
    }
    table.setModel(dataModel);

//    this.show();
  }


  /**
   * Reads a register.
   *
   */
  public long read (long regNb) {
    // Test if the register is valid
    if ((regNb < 1L) || (regNb >= REGISTER_NUMBERS)) {
      return 0;  // covers the R0 = 0
    } else {
      return values[(int)regNb];
    }
  }


  /**
   * Writes the value in the register.
   *
   */ 
  public void write (long regNb, long value) {
    // Test if the register and the value are valid
    if (!((regNb < 1L) || (regNb >= REGISTER_NUMBERS)) &&
        !((value > 0xffffffffL))) {
      values[(int)regNb] = value;
    }
    dataModel.setValueAt(Long.toString(value), (int)regNb, 1);
    dataModel.fireTableCellUpdated((int)regNb, 1);
    
  }
  
  public static int stringToNumber(String reg)
  {
	  int result = hashMap.get(reg).intValue();
	  return result;
  }
  public void initMap()
  {
	hashMap.put("R0", 0);
	hashMap.put("R1", 1);
	hashMap.put("R2", 2);
	hashMap.put("R3", 3);
	hashMap.put("R4", 4);
	hashMap.put("R5", 5);
	hashMap.put("R6", 6);
	hashMap.put("R7", 7);
	hashMap.put("R8", 8);
	hashMap.put("R9", 9);
	hashMap.put("R10", 10);
	hashMap.put("R11", 11);
	hashMap.put("R12", 12);
	hashMap.put("R13", 13);
	hashMap.put("R14", 14);
	hashMap.put("R15", 15);
  }
}
