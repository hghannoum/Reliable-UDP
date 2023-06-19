import java.net.DatagramSocket;
import java.net.InetAddress;

public interface sendAckI {
    void sendAck(DatagramSocket socket, int portz, int sessionNumber, int blockNumber,
      byte segmentNumber, String address);
      void sendAck2(DatagramSocket socket1, int portx, InetAddress address, int session);
}
