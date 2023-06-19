import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.function.Function;

public class ThreadMain extends Thread {
    private DatagramSocket socket;
    private InetAddress IPaddress;
    private int portx;
    private String directory;
    private String address;
    private static void ReceiveData(buildPacketI b, String directory, int session, String address, int portx,
            String filename,
            DatagramSocket socket) {
        receiveDataI r = new receiveData();
        byte[] buf = b.buildAckPacket(session, 0, (byte) 0);
        InetAddress add = null;
        try {
            add = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DatagramPacket packet = new DatagramPacket(buf, buf.length, add, portx);
        try {
            socket.send(packet);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        r.RecieveData(socket, directory + "\\", portx, filename, address);

    }

    public ThreadMain(DatagramSocket socket, InetAddress IPaddress, int port, String directory,String address) {
        this.socket = socket;
        this.address = address;
        this.portx = port;
        this.directory = directory;
        this.IPaddress=IPaddress;
    }

    @Override
    public void run() {
        Random random = new Random();
        listenforDorUI l = new listenforDorU();
        senddataI send = new sendData();
        sendAckI sendAckI = new sendAck();
        buildPacketI b = new buildPacket();

        int portz = random.nextInt(10000 - 5000 + 1) + 5000;
        ;
        DatagramSocket socket1 = null;
        socket.close();

        try {
            int session = random.nextInt(10000 - 5000 + 1) + 5000;
            socket1 = new DatagramSocket(portz);
            sendAckI.sendAck2(socket1, portx, IPaddress, session);
            System.out.println("mesha l hal aw laa!!!!!");
            String[] req = l.listenforDrU(socket1);
            String fileName = req[1];
            System.out.println("file name" + fileName);
            if (req[0] == "1") {
                System.out.print("sendData");
                send.sendData(socket1, session, directory, portx, IPaddress, fileName);

            } else
                ReceiveData(b, directory, session, address, portx, fileName, socket1);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
