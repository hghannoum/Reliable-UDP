import java.net.DatagramSocket;

public interface receiveDataI {
    void RecieveData(DatagramSocket socket,String directory, int portz, String filename, String address) ;
}
