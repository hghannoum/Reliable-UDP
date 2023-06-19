import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SenderThread extends Thread {
  private DatagramSocket socket;
  private InetAddress address;
  private int port;
  private byte[] data;

  public SenderThread(DatagramSocket socket, InetAddress address, int port, byte[] data) {
    this.socket = socket;
    this.address = address;
    this.port = port;
    this.data = data;

  }

  @Override
  public void run() {
    try {
      // Create a DatagramPacket
      DatagramPacket packet = new DatagramPacket(data, data.length, address, port);

      // Send the packet
      socket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
