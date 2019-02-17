package FindNumber;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class FileIO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void WriteObjectToFile(Object serObj) {
        try {
            FileOutputStream fileOut = new FileOutputStream("fn.tmp");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
//            System.out.println("Write OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Object ReadObjectToFile() {
        Object serObj = new Object();
        try {
            FileInputStream fileIn = new FileInputStream("fn.tmp");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            serObj = objectIn.readObject();
            objectIn.close();
//            System.out.println("Read OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return serObj;
    }
}

