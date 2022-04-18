package util;

import java.nio.ByteBuffer;

public class BufferHelper {
    /**
     * https://github.com/roengle/squadQuery/blob/main/src/util/BufferHelper.java
     *
     * Method to retrieve a string from a buffer, where the buffer's current position is at the
     * beginning of the string. The string MUST be null-terminated.
     *
     *
     * @param buffer the buffer to get the string from
     * @return the string from the buffer
     */
    public static String getStringFromBuffer(ByteBuffer buffer){
        StringBuilder sb = new StringBuilder("");
        byte tmp;
        while((tmp = buffer.get()) != (byte)0){
            sb.append((char)tmp);
        }
        return sb.toString();
    }
}