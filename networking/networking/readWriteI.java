import java.net.DatagramSocket;

public interface readWriteI {
   void WRQ(int sessionNumber, int portz, String address, String filename, DatagramSocket socket);
  
   void RRQ(int sessionNumber, int portz, String address, String filename, DatagramSocket socket);
}
