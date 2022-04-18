package rcon;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Robert Engle
 * @version 1.0.1
 *
 * https://github.com/roengle/SimpleRcon
 *
 * A class describing a single packet that can be sent/received to/from a RCON server.
 *
 * This class is designed according to the
 * <a href="https://developer.valvesoftware.com/wiki/Source_RCON_Protocol">Source RCON Protocol</a>.
 *
 * The following are the possible <code>requestId</code> values and what they correspond to:
 * <ul>
 *     <li>0 - Value if data is broadcasted from RCON server</li>
 *     <li>-1 - Value from SERVERDATA_AUTH_RESPONSE packet if authentication fails</li>
 *     <li>Anything else - Value issued from original command sent to RCON server.</li>
 * </ul>
 *
 * The following are the possible <code>type</code> values and what they correspond to:
 * <ul>
 *     <li>0 - SERVERDATA_RESPONSE_VALUE</li>
 *     <li>1 - Broadcast from RCON server (only for some games, such as Squad)</li>
 *     <li>2 - SERVERDATA_EXECCOMMAND or SERVERDATA_AUTH_RESPONSE</li>
 *     <li>3 - SERVERDATA_AUTH</li>
 * </ul>
 */
public class RconPacket {
    private final Date timestamp;
    private final int size;
    private final int requestId;
    private final int type;
    private final byte[] payload;

    /**
     * Constructs a {@link RconPacket} instance with the given fields.
     *
     * The <code>timestamp</code> field isn't technically a part of the attributes of an RCON packet as defined by
     * Valve, but it is included in in the case that the timestamp of when the packet is retrieved is needed to be known.
     *
     * @param timestamp a {@link Date} of the time the packet was received
     * @param size a 32-bit signed integer. is needed to be able to distinguish the payload from headers
     * @param requestId a 32-bit signed integer which mirrors the requestId back (if a command was issued.) See documentation
     *                  for the class to see what this value can be.
     * @param type a 32-bit signed integer which represents the type of packet. See documentation for this class for possible <code>type</code>
     *             values. One exception will be a value of 1, which represents a broadcast, and isn't defined in the
     *             Source RCON protocol.
     * @param payload a byte array of the payload of the packet. Can be converted to a string by using
     *                <code>new String(payload, StandardCharsts.UTF_8)</code>
     */
    protected RconPacket(Date timestamp, int size, int requestId, int type, byte[] payload) {
        this.timestamp = timestamp;
        this.size = size;
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    /**
     * Constructs a {@link RconPacket} instance with the given fields WITHOUT a {@link Date} object for
     * a timestamp.
     *
     * @param size a 32-bit signed integer. is needed to be able to distinguish the payload from headers
     * @param requestId a 32-bit signed integer which mirrors the requestId back (if a command was issued.) See documentation
     *                  for the class to see what this value can be.
     * @param type a 32-bit signed integer which represents the type of packet. See documentation for this class for possible <code>type</code>
     *             values. One exception will be a value of 1, which represents a broadcast, and isn't defined in the
     *             Source RCON protocol.
     * @param payload a byte array of the payload of the packet. Can be converted to a string by using
     *                <code>new String(payload, StandardCharsts.UTF_8)</code>
     */
    protected RconPacket(int size, int requestId, int type, byte[] payload){
        this(null, size, requestId, type, payload);
    }

    /**
     * Gets the timestamp of the RCON packet.
     *
     * @return a {@link Date} of the timestamp for the packet if it was given one, <code>null</code> if not
     */
    public Date getTimestamp(){
        return timestamp;
    }

    /**
     * Gets the size of the packet represent in bytes as an integer.
     *
     * @return the size of the packet in bytes.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the requestId of the packet as an integer.
     *
     * @return the requestId of the packet
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Gets the type of the packet as an integer.
     *
     * See documentation for this class to see what these values mean.
     *
     * @return the type of packet
     */
    public int getType() {
        return type;
    }

    /**
     * Gets the payload for the packet in an array of bytes
     *
     * @return the paylaod for the packet
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * Gets the payload for the packet as a {@link String}.
     *
     * Assumes use of the UTF_8 charset.
     *
     * @return the payload for the packet as a {@link String}
     */
    public String getPayloadAsString(){
        return new String(payload, StandardCharsets.UTF_8);
    }

    /**
     * Tests if the packet is a reponse to a command
     *
     * @return true if packet is response to command, false if not
     */
    public boolean isResponseToCommand(){
        return type == 0 && payload != null && payload.length > 1;
    }

    @Override
    public String toString() {
        /* String representation of RconPacket */
        final StringBuilder sb = new StringBuilder("RconPacket{");
        sb.append("timestamp=").append(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(timestamp));
        sb.append(", size=").append(size);
        sb.append(", requestId=").append(requestId);
        sb.append(", type=").append(type);
        sb.append(", payload=");
        if (payload == null) sb.append("null");
        else if (new String(payload, StandardCharsets.UTF_8).equals("")) sb.append("empty(null-terminator)");
        else {
            sb.append("\"");
            sb.append(new String(payload, StandardCharsets.UTF_8));
            sb.append("\"");
        }
        sb.append('}');
        return sb.toString();
    }
}