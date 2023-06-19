import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class sendData implements senddataI {
    buildPacketI b = new buildPacket();

    @Override
    public void sendData(DatagramSocket socket1, int session, String directory, int portx, InetAddress address,
            String fileName) {

        HashMap<Byte, Boolean> hashMapBool = new HashMap<>();
        HashMap<Byte, byte[]> hashMapData = new HashMap<>();
        Semaphore semaphore = new Semaphore(1);

        File file = new File(directory, fileName);
        long fileSize = file.length();
        float var = (float) fileSize / (float) 8192;
        int numFullBlocks = (int) var;

        int numFullSeg = (int) ((var - numFullBlocks) * 8);
        System.out.println(fileSize + " " + numFullBlocks + "  " + numFullSeg);
        float lastSegment = (var - numFullBlocks) * 8 - numFullSeg;
        System.out.println(lastSegment);
        threadAck(socket1, hashMapBool, semaphore);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Calculate the number of full blocks
        int blockNumber = 0;
        int[] key = new int[8];
        SenderThreadWithTimeout[] threads;
        threads = new SenderThreadWithTimeout[8];
        for (blockNumber = 0; blockNumber < numFullBlocks; blockNumber++) {
            System.out.println("block");
            for (int segmentNumber = 1; segmentNumber <= 8; segmentNumber++) {
                byte[] segmentData = new byte[1024];
                try {
                    // Read the segment data from the file
                    int bytesRead = fileInputStream.read(segmentData);
                    if (bytesRead == -1) {
                        // Reached the end of the file
                        break;
                    }
                    hashMapData.put((byte) segmentNumber, segmentData);
                    byte[] Data = new byte[1031];
                    Data = b.buildDataPacket(session, blockNumber, (byte) segmentNumber, segmentData);
                    SenderThread s = new SenderThread(socket1, address, portx, Data);
                    s.start();
                    // Create a new thread to send the segment
                    System.out
                            .println("ra2em l packet" + blockNumber + "                                  "
                                    + segmentNumber + "   "
                                    + Data.length);
                    // Wait for a short delay before sending the next segment
                    Thread.sleep(100); // Adjust the delay duration as needed

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // try {
            // Thread.sleep(15000);
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            System.out.println(hashMapBool.size());
            // try {
            // System.out.println("abl l acquire");
            // semaphore.acquire();
            // System.out.println("baad l acquire");
            // } catch (InterruptedException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }

            for (int i = 0; i < 8; i++) {
                key[i] = 10;
            }

            for (int i = 1; i <= 8; i++) {
                System.out.println(" the bool" + i + " " + hashMapBool.get((byte) i));
                byte[] missed = new byte[1024];
                byte[] missedPacket = new byte[1031];

                if (!hashMapBool.containsKey((byte) i)) {
                    missed = hashMapData.get((byte) i);
                    // semaphore.release();
                    missedPacket = b.buildDataPacket(session, blockNumber, (byte) i, missed);
                    threads[i - 1] = new SenderThreadWithTimeout(socket1, address, portx,
                            missedPacket, semaphore, hashMapBool,
                            i);
                    threads[i - 1].start();
                    key[i - 1] = i - 1;

                }

            }
            for (int i = 0; i < 8; i++) {
                if (key[i] == i)
                    try {
                        System.out.println("ana aand l join");
                        threads[i].join();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }

            hashMapBool.clear();

        }
        for (int i = 1; i <= numFullSeg; i++) {
            System.out.println("segment");
            byte[] segmentData = new byte[1024];
            try {
                // Read the segment data from the file
                int bytesRead = fileInputStream.read(segmentData);
                if (bytesRead == -1) {
                    // Reached the end of the file
                    break;
                }
                hashMapData.put((byte) i, segmentData);
                byte[] Data = new byte[1031];
                Data = b.buildDataPacket(session, blockNumber, (byte) i, segmentData);
                SenderThread s = new SenderThread(socket1, address, portx, Data);
                s.start();
                // Create a new thread to send the segment

                // Wait for a short delay before sending the next segment
                Thread.sleep(1000); // Adjust the delay duration as needed

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 8; i++) {
            key[i] = 10;
        }

        for (int i = 1; i <= numFullSeg; i++) {
            System.out.println(" the bool" + i + " " + hashMapBool.get((byte) i));
            byte[] missed = new byte[1024];
            byte[] missedPacket = new byte[1031];

            if (!hashMapBool.containsKey((byte) i)) {
                missed = hashMapData.get((byte) i);
                // semaphore.release();
                missedPacket = b.buildDataPacket(session, blockNumber, (byte) i, missed);
                threads[i - 1] = new SenderThreadWithTimeout(socket1, address, portx,
                        missedPacket, semaphore, hashMapBool,
                        i);
                threads[i - 1].start();
                key[i - 1] = i - 1;

            }

        }
        for (int i = 0; i < 8; i++) {
            if (key[i] == i)
                try {
                    System.out.println("ana aand l join");
                    threads[i].join();
                    hashMapBool.clear();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

        byte[] segmentsmallData = null;
        if (lastSegment == 0) {
            byte[] segmentData = new byte[7];
            segmentData[0] = (byte) ((0 >> 8) & 0xFF); // OPCODE DATA MSB
            segmentData[1] = (byte) (4 & 0xFF); // OPCODE DATA LSB
            segmentData[2] = (byte) ((session >> 8) & 0xFF); // Block Number MSB
            segmentData[3] = (byte) (session & 0xFF);
            segmentData[4] = (byte) ((numFullBlocks + 1 >> 8) & 0xFF); // Block Number MSB
            segmentData[5] = (byte) (numFullBlocks + 1 & 0xFF); // Block Number LSB
            segmentData[6] = (byte) (numFullSeg + 1);
            DatagramPacket segmentPacket = new DatagramPacket(segmentData, segmentData.length, address, portx);
            try {
                socket1.send(segmentPacket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            int size;
            if ((lastSegment * 1024 - (int) (lastSegment * 1024) == 0.0))
                size = (int) (lastSegment * 1024);
            else
                size = (int) (lastSegment * 1024) + 1;
            segmentsmallData = new byte[size];
            try {
                fileInputStream.read(segmentsmallData);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            byte[] data = new byte[size + 7];
            data = b.buildDataPacket(session, numFullBlocks + 1, (byte) (numFullSeg + 1), segmentsmallData);
            DatagramPacket last = new DatagramPacket(data, data.length, address, portx);
            try {
                System.out.println("ana l last");
                socket1.send(last);
                System.out.println("ana l last");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        int j = 10;

        // System.out.println(" the bool" + + " " + hashMapBool.get((byte) i));
        byte[] missed;
        byte[] missedPacket = new byte[1031];

        if (lastSegment != 0) {
            if (hashMapBool.size() == 0) {
                missed = segmentsmallData;
                // semaphore.release();
                missedPacket = b.buildDataPacket(session, numFullBlocks + 1, (byte) (numFullSeg + 1), missed);
                threads[0] = new SenderThreadWithTimeout(socket1, address, portx,
                        missedPacket, semaphore, hashMapBool,
                        0);
                threads[0].start();
                try {
                    threads[0].join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    
        // Close the file input stream
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void threadAck(DatagramSocket socket1, HashMap<Byte, Boolean> hashMap, Semaphore semaphore) {
        Thread acknowledgmentThread = new Thread(() -> {
            // Initialize to true

            while (true) {
                // Receive ACK packet from the client
                byte[] receiveData = new byte[7]; // Corrected array size
                DatagramPacket ackPacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    socket1.receive(ackPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                // Extract the segment number from the ACK packet
                byte segmentNumber = receiveData[6];
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Update the HashMap with the acknowledgment status
                hashMap.put(segmentNumber, true);

                // Check if all segments have been acknowledged
                semaphore.release();
                // Perform necessary actions if all segments have been acknowledged

            }
        });

        acknowledgmentThread.start();
    }

}
