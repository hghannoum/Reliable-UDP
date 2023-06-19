import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class write implements writeI {

    @Override
    public void Write(FileOutputStream fileOutputStream, HashMap<Byte, byte[]> hashMap) {
        // TODO Auto-generated method stub
           for (int j = 1; j <= hashMap.size(); j++) {
              byte[] data = hashMap.get((byte) j);
              System.out.println("datazghire.length" + data.length);
              // for (int i = 0; i < data.length; i++)
              // System.out.println(data[i]);
              try {
                fileOutputStream.write(data);
              } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
          }
    }
    

