import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

public class receiveData implements receiveDataI {
     sendAckI s = new sendAck();
     write w = new write();
     eliminateZeroesI e= (eliminateZeroesI) new eliminateZeroes();
    @Override
    public void RecieveData(DatagramSocket socket,String directory, int portz, String filename, String address) {
        // TODO Auto-generated method stub
        boolean ContinueRecieving = true;
    String[] fileType = filename.split("\\.");
    System.out.println(fileType[0]);
    System.out.println(fileType[1]);
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(directory+"output." + fileType[1]);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    HashMap<Byte, byte[]> hashMap = new HashMap<>();

    int nbSegment = 1;
    int blocknumber = 0;
    while (ContinueRecieving) {

      byte[] buf = new byte[1031];
      DatagramPacket inPacket2 = new DatagramPacket(buf,
          buf.length);
      try {
        System.out.println("ana aand l receive");
        socket.receive(inPacket2);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      byte[] data = null;
      byte[] test;
      if (!(fileType[1].equals("mp4")) && !(fileType[1].equals("mp3"))) {

        data = e.eliminateTrailingZeros(buf);
      } else {
        test = e.eliminateTrailingZeros(buf);
        if (test.length > 1000)
          data = buf;
        else
          data = test;
      }
      System.out.println("DATA.LENGTH" + "             " + data.length);
      int sessionNumber = ((buf[2] & 0xFF) << 8) | (buf[3] & 0xFF);
      int blockNumber = ((buf[4] & 0xFF) << 8) | (buf[5] & 0xFF);
      byte segmentNumber = buf[6];
      byte[] segmentData = new byte[data.length - 7];
      System.arraycopy(data, 7, segmentData, 0, segmentData.length);
      hashMap.put(segmentNumber, segmentData);
      if (segmentData.length == 0 || segmentData.length < 1024 || hashMap.size() == 8) {
        blocknumber++;
        w.Write(fileOutputStream, hashMap);
        hashMap.clear();
      }
      if (segmentData.length == 0 || segmentData.length < 1024) {
        System.out.println("shu aam  bisir hon!!!!!!!!!!");
        ContinueRecieving = false;
      }
        s.sendAck(socket, portz, sessionNumber, blockNumber, segmentNumber, address);

    }
    }
    
}
