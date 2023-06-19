public interface buildPacketI {
     byte[] buildDataPacket(int sessionNumber, int blockNumber, byte segmentNumber, byte[] segmentData) ;
     byte[] buildAckPacket(int session, int b, byte c);
     byte[] buildErrorPacket(String errorMessage);

      byte[] buildRRQPacket(int sessionNumber, String filename);
      byte[] buildWRQPacket(int sessionNumber, String filename);
   byte[] buildAuthPacket(String username, String password);
    }
