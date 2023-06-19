import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class receiveAckorError implements receiveAckorErrorI{
    public int[] ReceiveAckOrError(DatagramSocket socket, int porty) {
        byte[] buf = new byte[515];
        int[] returnValues = new int[3];
    
        int portSource = 0;
    
        DatagramPacket inPacket2 = new DatagramPacket(buf,
            buf.length);
    
        System.out.println("bb");
        try {
          socket.receive(inPacket2);
          System.out.println("aa");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        portSource = inPacket2.getPort();
        if (portSource != porty) {
          int sessionNumber = exctractSession(buf);
    
          int portz = portSource;
          returnValues[0] = 1;
          returnValues[1] = sessionNumber;
          returnValues[2] = portz;
          for (int i = 0; i < returnValues.length; i++) {
            System.out.println(returnValues[i]);
          }
          System.out.println("true credentials");
          // socket.close();
          return returnValues;
        } else {
          returnValues[0] = 0;
          byte[] error = new byte[515];
          System.arraycopy(buf, 1, error, 0, 100);
    
          String str = new String(error, StandardCharsets.UTF_8);
    
          System.out.println(str);
          socket.close();
    
          return returnValues;
    
        }
      }
    
      private static int exctractSession(byte[] inPacket) {
        int sessionNumber = (inPacket[2] & 0xFF) << 8 | (inPacket[3] & 0xFF);
        return sessionNumber;
      }
    
}
