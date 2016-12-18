
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;


public class FRegister extends JFrame {

  
public static HashMap<String,Integer> hashMap = new HashMap();

  
public Processor processor;
// Number of register
  private static final int REGISTER_NUMBERS = 16;

  // The arrays to store the register values
  private double values[] = new double[REGISTER_NUMBERS];

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
  public FRegister (Processor processor) {
    //super(processor);
	this.processor = processor;  
    initWindow();
    initMap();
  }


  /** Initialize the window for the register. */
  private void initWindow () {
    setTitle("F Register");


    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setOpaque(true);
    getContentPane().add(scrollPane);

    JTable table = new JTable();
    scrollPane.getViewport().add(table);
    setVisible(true);
    setSize(20, 300);
   // setLocation(1100,40);
    setBounds(1100, 20, 200, 300);

    names = new String[2];
    names[0] = "Address";
    names[1] = "Value";
    data = new String[REGISTER_NUMBERS][2];
    for (int i = 0; i < REGISTER_NUMBERS; i++) {
      data[i][0] = Long.toString(i);
      data[i][1] = Double.toString(0);
    }
    table.setModel(dataModel);

//    this.show();
  }


  /**
   * Reads a register.
   *
   * regNb  Number of the register to read.
   * return Value of the register.
   */
  public double read (long regNb) {
    // Test if the register is valid
	  regNb = regNb - 20;
    if ((regNb < 0L) || (regNb >= REGISTER_NUMBERS)) {
      return 0;  // covers the R0 = 0
    } else {
      return values[(int)regNb];
    }
  }


  /**
   * 
   *  regNb  Number of the register to write
   *  adjust the regNb in case double register.
   *  Numbering of double register is different
   *  to avoid conflicts during hazard detection.
   */
  public void write (long regNb, double value) {
	regNb = regNb - 20;
	
    // Test if the register and the value are valid
    if (!((regNb < 0L) || (regNb >= REGISTER_NUMBERS)))
    {
      values[(int)regNb] = value;
      System.out.println("***DEBUG ***");
      System.out.println(regNb);
    }
    dataModel.setValueAt(Double.toString(value), (int)regNb, 1);
    dataModel.fireTableCellUpdated((int)regNb, 1);
    
  }
  
  public static int stringToNumber(String reg)
  {
	  int result = hashMap.get(reg).intValue();
	  return result;
  }
  public void initMap()
  {
	hashMap.put("F0", 20);
	hashMap.put("F1", 21);
	hashMap.put("F2", 22);
	hashMap.put("F3", 23);
	hashMap.put("F4", 24);
	hashMap.put("F5", 25);
	hashMap.put("F6", 26);
	hashMap.put("F7", 27);
	hashMap.put("F8", 28);
	hashMap.put("F9", 29);
	hashMap.put("F10", 30);
	hashMap.put("F11", 31);
	hashMap.put("F12", 32);
	hashMap.put("F13", 33);
	hashMap.put("F14", 34);
	hashMap.put("F15", 35);
  }
}
