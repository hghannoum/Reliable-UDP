import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class receiveAuth implements receiveAuthI {

    @Override
        public int[] ReceiveAuth(DatagramSocket socket) {
            byte[] buf = new byte[515];
            int[] returns = new int[3];
            DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
        
            try {
              socket.receive(inPacket);
            } catch (IOException e) {
              e.printStackTrace();
            }
        
            for (int i = 0; i < inPacket.getLength(); i++) {
              System.out.println(buf[i]);
            }
        
            String receivedMessage = new String(buf, 0, inPacket.getLength());
            System.out.println(receivedMessage);
        
            String opcode = receivedMessage.substring(0, 2);
            System.out.println(opcode);
            System.out.println(receivedMessage.indexOf('\0'));
            String username = receivedMessage.substring(2, 6);
            String password = receivedMessage.substring(5 + 1);
            System.out.println(username + " " + password);
        
            int portx = inPacket.getPort();
            System.out.println(username.equals("user"));
            System.out.println(password.trim().equals("user"));
            if (username.equals("user") && password.trim().equals("user")) {
              returns[0] = 1;
              returns[1] = portx;
            } else {
              returns[0] = 0;
              returns[1] = portx;
            }
        
            return returns;
          }
        }
    

