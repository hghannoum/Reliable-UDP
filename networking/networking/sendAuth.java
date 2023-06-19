import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class sendAuth implements sendAuthI{
    public int[] sendAuth(String[] credential, int portx, DatagramSocket socket) {
        buildPacketI build = new buildPacket();
        receiveAckorErrorI r= (receiveAckorErrorI) new receiveAckorError();
        InetAddress IPAddress = null;
        try {
          IPAddress = InetAddress.getByName(credential[2]);
        } catch (UnknownHostException e) {
    
          e.printStackTrace();
        }
        byte[] authPacket = build.buildAuthPacket(credential[0], credential[1]);
        for (byte b : authPacket) {
          System.out.println(b);
        }
        DatagramPacket packet = new DatagramPacket(authPacket, authPacket.length, IPAddress,
            Integer.valueOf(credential[3]));
        try {
          socket.send(packet);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    
        int[] returnValues = new int[3];
        returnValues = r.ReceiveAckOrError(socket, Integer.valueOf(credential[3]));
        return returnValues;
      }
}
