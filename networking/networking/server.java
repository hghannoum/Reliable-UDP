
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class server {
  public static void main(String[] args) {
    
    String[] credential = scanner();
    
      
    receiveAuthI receiveAuth = new receiveAuth();
    sendAckI sendAckI = new sendAck();
    listenforDorUI listenforDorUI = (listenforDorUI) new listenforDorU();
    String username;
    String password;
    buildPacketI b = new buildPacket();
    senddataI send = new sendData();
    username = credential[0].trim();
    password = credential[1].trim();
    System.out.println(username);
    System.out.println(password);
    
    if (username.equals("user") && password.equals("user")) {
       
      while(true){

        System.out.println(1);
        DatagramSocket socket = null;
        int porty = Integer.valueOf(credential[2]);
        String directory = credential[3].trim();
        System.out.println("directory" + directory);
        try {
          socket = new DatagramSocket(porty);
        } catch (SocketException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        int[] receiveInfo = receiveAuth.ReceiveAuth(socket);
        for (int i : receiveInfo) {
          System.out.println(i);
        }
        String ipAddress = "127.0.0.1";
        int portx = receiveInfo[1];
        InetAddress address = null;
        try {
          address = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        System.out.println(address);
        System.out.println(ipAddress);
        if (receiveInfo[0] == 1) {
           ThreadMain Unkown=new ThreadMain(socket, address, portx, directory, ipAddress);
           Unkown.start();
        } else {
          byte[] error = b.buildErrorPacket("false crddentials auth");

          DatagramPacket packet = new DatagramPacket(error, error.length, address, portx);
          try {
            socket.send(packet);
            socket.close();
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          socket.close();
        }
      
    }
  }}
  
  

  private static String[] scanner() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter your command: ");
    String command = scanner.nextLine();
    System.out.println("command: " + command);
    String[] parts = command.split(" ");

    return parts;
  }
}
