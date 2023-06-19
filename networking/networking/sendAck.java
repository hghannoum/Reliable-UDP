import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class sendAck implements sendAckI {
    buildPacketI b= new buildPacket() ;
    
    @Override
    public  void sendAck(DatagramSocket socket, int portz, int sessionNumber, int blockNumber,
    byte segmentNumber, String address) {
  InetAddress IPAddress = null;
  try {
    IPAddress = InetAddress.getByName(address);
  } catch (UnknownHostException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  byte[] ackData = b.buildAckPacket(sessionNumber, blockNumber, segmentNumber);
  DatagramPacket packet = new DatagramPacket(ackData, ackData.length, IPAddress, portz);

  try {
    socket.send(packet);
  } catch (IOException e) {
    e.printStackTrace();
  }
}
public void sendAck2(DatagramSocket socket1, int portx, InetAddress address, int session) {
  byte[] Data = new byte[7]; // buffer vide

  buildPacketI b = new buildPacket();
  Data = b.buildAckPacket(session, (byte) 00, (byte) 00);

  DatagramPacket outPacket = new DatagramPacket(Data, Data.length, address, portx);
  try {
    socket1.send(outPacket);
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
}

    
}
