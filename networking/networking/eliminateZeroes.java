public class eliminateZeroes implements eliminateZeroesI {
    public byte[] eliminateTrailingZeros(byte[] array) {
        int length = array.length;
    
        // Find the index of the last non-zero byte
        int lastIndex = length - 1;
        while (lastIndex >= 0 && array[lastIndex] == 0) {
          lastIndex--;
        }
    
        // Create a new array without trailing zeros
        byte[] result = new byte[lastIndex + 1];
        System.arraycopy(array, 0, result, 0, lastIndex + 1);
    
        return result;
      }
}
