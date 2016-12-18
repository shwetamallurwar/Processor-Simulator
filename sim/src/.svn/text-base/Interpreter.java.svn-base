import java.util.Iterator;
import java.util.Vector;


public class Interpreter {
	
	public Processor control;
	public Interpreter(Processor control)
	{
		this.control = control;
	}
	
	public void work() {
		
		int i =0;
		control.register.write(4, 200);
		control.register.write(5, 100);
		control.memory.write(0, 0xFF11);
		control.memory.write(8, 0x64);
		Vector<Command> insVect = control.memory.getInstVector();
		Iterator<Command> it = insVect.iterator();
		//System.out.println("ARRAY SIZE" + insVect.size());
		while(i < insVect.size())
		{
			Command c = insVect.get(i);
			long op1, op2, result;
			switch (c.getInstOpx()) {
			
			case Constant.ALU_NOP:
				System.out.println("::NO OP::");
			case Constant.ALU_ADD:
	            op1 = c.getSrcOp1();
	            op2 = c.getSrcOp2();
	            result = control.register.read(op1) + control.register.read(op2);
	                      
	            control.register.write(c.getDstOp(),result);
	            i = i+1;
	            break;
			case Constant.ALU_SUB:
				op1 = c.getSrcOp1();
	            op2 = c.getSrcOp2();
	            result = control.register.read(op1) - control.register.read(op2);
	            
	            control.register.write(c.getDstOp(),result);
	           
	            i = i+1;
				break;
			case Constant.ALU_MUL:
				op1 = c.getSrcOp1();
				op2 = c.getSrcOp2();
				result = control.register.read(op1) * control.register.read(op2);
				
				control.register.write(c.getDstOp(),result);
				i = i+1;
				break;
			case Constant.ALU_DIV:
				op1 = c.getSrcOp1();
				op2 = c.getSrcOp2();
				result = control.register.read(op1) / control.register.read(op2);
				
				control.register.write(c.getDstOp(),result);
				i = i+1;
				break;
			case Constant.MEM_LW:
				op1 = c.getSrcOp1();
				op2 = control.register.read ( c.getSrcOp2());
				//op1 is offset and op2 is value in register
				result = op1 + op2;
				
				long valueAtMloc = control.memory.readValAtMloc(result);
				control.register.write(c.getDstOp(),valueAtMloc);
				i = i+1;
				break;
			case Constant.MEM_S:
				op1 = control.register.read(c.getSrcOp1()); //src register to save in given location. 
				op2 = control.register.read(c.getSrcOp2()); // base register value
				int offset = c.getDstOp();
				control.memory.write((op2+offset), op1);
				i = i+1;
				break;
			case Constant.ALU_BEQ:
				op1 = control.register.read(c.getSrcOp1());
				op2 = control.register.read(c.getSrcOp2());
				if(op1 == op2)
				{
					i = c.getDstOp();
				}
				else{
					i = i+1;
				}
				
				break;
			case Constant.ALU_BNEQ:
				op1 = control.register.read(c.getSrcOp1());
				op2 = control.register.read(c.getSrcOp2());
				if(op1 != op2)
				{
					i = c.getDstOp();
				}
				else{
					i = i+1;
				}
				break;
			default:
				break;
			}
		} 
	}
	
	
}
