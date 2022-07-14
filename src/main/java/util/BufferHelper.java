package util;

import java.nio.ByteBuffer;

public class BufferHelper {

    private BufferHelper(){
        throw new IllegalStateException("You cannot instantiate a utility class.");
    }
    /**
     * <a href="https://github.com/roengle/squadQuery/blob/main/src/util/BufferHelper.java">squadQuery implementation</a>
     *
     * Method to retrieve a string from a buffer, where the buffer's <b>current position</b> is at the
     * beginning of the string. The string MUST be null-terminated.
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