import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class SenderThreadWithTimeout extends Thread {
  private DatagramSocket socket;
  private InetAddress address;
  private int port;
  private byte[] data;
  private Semaphore semaphore;
  private HashMap hashMap;
  private int i;

  public SenderThreadWithTimeout(DatagramSocket socket, InetAddress address, int port, byte[] data, Semaphore semaphore,
      HashMap hashMapBool, int i) {
    this.socket = socket;
    this.address = address;
    this.port = port;
    this.data = data;
    this.semaphore = semaphore;
    this.hashMap = hashMapBool;
    this.i = i;
  }

  @Override
  public void run() {
    try {
      boolean isAck = false;
      // Create a DatagramPacket
      DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
      while (!isAck) {
        // Send the packet
        socket.send(packet);
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        // try {
        // semaphore.acquire();
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        if ((boolean) hashMap.get(i))
          isAck = true;
        // semaphore.release();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
