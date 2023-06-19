
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;

public class client {

  public static void main(String[] args) {
    sendAuthI sendAuthI= (sendAuthI) new sendAuth();
    readWriteI rw= new readWrite();
    Random random = new Random();
    int portx = random.nextInt(10000 - 5000 + 1) + 5000;
    String[] credential = scanner();
    try {
      DatagramSocket socket = new DatagramSocket(portx);
      int[] TestAuth =sendAuthI.sendAuth(credential, portx, socket);
      for (int i = 0; i < TestAuth.length; i++) {
        System.out.println(TestAuth[i]);
      }
      if (TestAuth[0] == 1) {
        System.out.println(credential[4]);
        if (credential[4].trim().equals("download")) {
          System.out.println("test");
          rw.RRQ(TestAuth[1], TestAuth[2], credential[2], credential[5], socket);
        } else
          rw.WRQ(TestAuth[1], TestAuth[2], credential[2], credential[5], socket);
      } else
        System.out.println("false credentials");
    } catch (SocketException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private static String[] scanner() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter your command: ");
    String command = scanner.nextLine();
    System.out.println("command: " + command);
    String[] parts = command.split("\\s+|:|@");
    return parts;

  }
}
