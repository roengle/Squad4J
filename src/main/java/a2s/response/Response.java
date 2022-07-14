package a2s.response;

import lombok.Getter;

/**
 * <a href="https://github.com/roengle/squadQuery/blob/main/src/response/Response.java">squadQuery implementation</a>
 *
 * Abstract class to represent a response from a query.
 *
 * @author Robert Engle
 */
@Getter
public abstract class Response {
    private final byte[] rawData;

    /**
     * Constructs a {@link Response}.
     *
     * @param rawData an array of bytes representing the raw data sent for the A2S response
     */
    protected Response(byte[] rawData){
        this.rawData = rawData;
    }
}