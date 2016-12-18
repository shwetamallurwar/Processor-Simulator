import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;


public class WriteMemory {

	private String filename;
		
	public WriteMemory(String filename){
		this.filename = filename;	
	}		
	
	private void writeMemory(){


		 try {
                        RandomAccessFile inFile = new RandomAccessFile(new File(filename), "rw");

                        for(int i=0; i<400; i++){
                        //        inFile.writeDouble(100);
				inFile.writeInt(2);
                        }
                        inFile.seek(0);
//                        System.out.println(" READ VALUE ::" + inFile.readDouble());
                        inFile.close();
                } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

		
	}
	
	// create memory dump.
	public static void main(String[] args){
		WriteMemory r = new WriteMemory("memdump.dat");
		r.writeMemory();
	}
}

