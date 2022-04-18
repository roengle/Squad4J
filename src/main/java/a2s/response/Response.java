package a2s.response;

/**
 * @author Robert Engle
 *
 * https://github.com/roengle/squadQuery/blob/main/src/response/Response.java
 *
 * Abstract class to represent a response from a query.
 */
public abstract class Response {
    private final byte[] rawData;

    public Response(byte[] rawData){
        this.rawData = rawData;
    }

    public final byte[] getRawData() {
        return rawData;
    }
}