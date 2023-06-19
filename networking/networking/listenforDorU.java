import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class listenforDorU implements listenforDorUI {
    
  public String[] listenforDrU(DatagramSocket socket1) {
    byte[] buf = new byte[300];
    String[] req = new String[2];
    DatagramPacket inPacket = new DatagramPacket(buf,
        buf.length);

    try {
      System.out.println("bb");
      socket1.receive(inPacket);
      System.out.println("00");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String receivedMessage = new String(inPacket.getData(), 0, inPacket.getLength());
    String opcode = receivedMessage.substring(0, 2);
    String fileName = receivedMessage.substring(4, receivedMessage.length() - 1);
    System.out.println(opcode);
    byte[] firstTwoBytes = new byte[2];
    firstTwoBytes[0] = buf[0];
    firstTwoBytes[1] = buf[1];
    if ((firstTwoBytes[0] == 0x00) && (firstTwoBytes[1] == 0x02)) {
      req[0] = "1";
      req[1] = fileName;
      return req;
    }

    else {
      req[0] = "0";
      req[1] = fileName;
      return req;
    }

  }
}
