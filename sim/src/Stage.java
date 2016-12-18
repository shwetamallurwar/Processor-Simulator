import javax.swing.JFrame;


/**
 * base class.
 *
 */
public abstract class Stage extends JFrame {
  /** The control object */
  protected Processor processor;


  /**
   * 
   * should be implemented by subclasses.
   */
  public void execute () {
  }


  /**
   * Constructor 
   */
  public Stage (Processor processor) {
    this.processor = processor;
  }

  
 
}