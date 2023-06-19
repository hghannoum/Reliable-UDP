import java.net.DatagramSocket;

public interface sendAuthI {
     int[] sendAuth(String[] credential, int portx, DatagramSocket socket);
}
