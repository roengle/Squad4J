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

    /**
     * Constructs a {@link Response}.
     *
     * @param rawData an array of bytes representing the raw data sent for the A2S response
     */
    public Response(byte[] rawData){
        this.rawData = rawData;
    }

    /**
     * Gets the raw data for the response.
     *
     * @return a byte array representing the raw data of the response.
     */
    public final byte[] getRawData() {
        return rawData;
    }
}