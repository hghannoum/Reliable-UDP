import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class buildPacket implements buildPacketI {

    @Override
    public byte[] buildDataPacket(int sessionNumber, int blockNumber, byte segmentNumber, byte[] segmentData) {
        // TODO Auto-generated method stub
        // Calculate the total length of the packet
        int packetLength = 2 + 2 + 2 + 1 + segmentData.length;

        // Create a ByteBuffer with the packet length
        ByteBuffer buffer = ByteBuffer.allocate(packetLength);

        // Set the opcode
        buffer.putShort((short) 4);

        // Set the session number
        buffer.putShort((short) sessionNumber);

        // Set the block number
        buffer.putShort((short) blockNumber);

        // Set the segment number
        buffer.put(segmentNumber);

        // Set the segment data
        buffer.put(segmentData);

        // Convert the ByteBuffer to a byte array
        return buffer.array();
    }

    @Override
    public byte[] buildAckPacket(int session, int b, byte c) {
        int packetLength = 2 + 2 + 2 + 1;

        // Create a ByteBuffer with the packet length
        ByteBuffer buffer = ByteBuffer.allocate(packetLength);

        // Set the opcode
        buffer.putShort((short) 5);

        // Set the session number
        buffer.putShort((short) session);

        // Set the block number
        buffer.putShort((short) b);

        // Set the segment number
        buffer.put(c);

        // Convert the ByteBuffer to a byte array
        return buffer.array();
    }

    @Override
    public byte[] buildErrorPacket(String errorMessage) {
        // TODO Auto-generated method stub
        int packetLength = 1 + errorMessage.length() + 1;

        // Create a ByteBuffer with the packet length
        ByteBuffer buffer = ByteBuffer.allocate(packetLength);

        // Set the opcode
        buffer.put((byte) 6);

        // Set the error message
        byte[] errorMessageBytes = errorMessage.getBytes(StandardCharsets.UTF_8);
        buffer.put(errorMessageBytes);

        // Set the padding byte
        buffer.put((byte) 0x00);

        // Convert the ByteBuffer to a byte array
        return buffer.array();
    }

    public  byte[] buildRRQPacket(int sessionNumber, String filename) {
        int packetLength = 2 + 2 + filename.length() + 1;
    
        // Create a ByteBuffer with the packet length
        ByteBuffer buffer = ByteBuffer.allocate(packetLength);
    
        // Set the opcode
        buffer.putShort((short) 2);
    
        // Set the session number
        buffer.putShort((short) sessionNumber);
    
        // Set the filename
        buffer.put(filename.getBytes(StandardCharsets.UTF_8));
    
        // Set the padding byte
        buffer.put((byte) 0x00);
    
        // Convert the ByteBuffer to a byte array
        return buffer.array();
      }
      public  byte[] buildWRQPacket(int sessionNumber, String filename) {
        int packetLength = 2 + 2 + filename.length() + 1;
    
        // Create a ByteBuffer with the packet length
        ByteBuffer buffer = ByteBuffer.allocate(packetLength);
    
        // Set the opcode
        buffer.putShort((short) 3);
    
        // Set the session number
        buffer.putShort((short) sessionNumber);
    
        // Set the filename
        buffer.put(filename.getBytes(StandardCharsets.UTF_8));
    
        // Set the padding byte
        buffer.put((byte) 0x00);
    
        // Convert the ByteBuffer to a byte array
        return buffer.array();
      }
      
  public  byte[] buildAuthPacket(String username, String password) {
    // Calculate the total length of the packet
    int packetLength = 2 + username.length() + 1 + password.length() + 1;

    // Create a ByteBuffer with the packet length
    ByteBuffer buffer = ByteBuffer.allocate(packetLength);

    // Set the opcode
    buffer.putShort((short) 1);

    // Set the username
    byte[] usernameBytes = username.getBytes(StandardCharsets.UTF_8);
    buffer.put(usernameBytes);
    buffer.put((byte) 0x00); // Padding byte

    // Set the password
    byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
    buffer.put(passwordBytes);
    buffer.put((byte) 0x00); // Padding byte

    // Convert the ByteBuffer to a byte array
    return buffer.array();
  }
}
