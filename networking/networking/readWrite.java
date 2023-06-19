import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.DirectoryStream;

import javafx.stage.DirectoryChooser;

public class readWrite implements readWriteI{
    receiveDataI r = new receiveData();
    buildPacketI b = new buildPacket();
    senddataI s= new sendData();
    @Override
    public void WRQ(int sessionNumber, int portz, String address, String filename, DatagramSocket socket){
        // TODO Auto-generated method stub
        
        byte[] RRQ = b.buildWRQPacket(sessionNumber, filename);
        InetAddress IPAddress = null;
        try {
          IPAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        DatagramPacket packetRRQ = new DatagramPacket(RRQ, RRQ.length, IPAddress, portz);
    
        try {
          socket.send(packetRRQ);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        byte[] buffer = new byte[7];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            
            
        try {
          socket.receive(packet);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        s.sendData(socket, sessionNumber, System.getProperty("user.dir") , portz, IPAddress, filename);
        
      
    }

    @Override
    public void RRQ(int sessionNumber, int portz, String address, String filename, DatagramSocket socket) {
         
          
          byte[] RRQ = b.buildRRQPacket(sessionNumber, filename);
          InetAddress IPAddress = null;
          try {
            IPAddress = InetAddress.getByName(address);
          } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          DatagramPacket packetRRQ = new DatagramPacket(RRQ, RRQ.length, IPAddress, portz);
      
          try {
            socket.send(packetRRQ);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
      
          r.RecieveData(socket,"", portz, filename, address);
        }
    
}
