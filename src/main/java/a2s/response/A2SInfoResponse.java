package a2s.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import util.BufferHelper;

import javax.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * <a href="https://github.com/roengle/squadQuery/blob/main/src/response/A2SInfoResponse.java">squadQuery implementation</a>
 *
 * Class to represent a response from a A2S_INFO query.
 *
 * See the following for technical documentation for A2S_INFO queries:
 *
 * <a href="https://developer.valvesoftware.com/wiki/Server_queries#A2S_INFO">A2S_INFO Documentation</a>
 *
 * @author Robert Engle
 */
@Getter
@ToString
public class A2SInfoResponse extends Response{
    private final byte protocol;
    private final String name;
    private final String map;
    private final String folder;
    private final String game;
    private final short id;
    private final byte players;
    private final byte maxPlayers;
    private final byte bots;
    private final byte serverType;
    private final byte environment;
    private final byte visibility;
    private final byte vac;
    private final String version;
    private final byte edf;

    /* May be null depending on value of EDF*/
    //EDF & 0x80
    @Getter(AccessLevel.NONE)
    @Nullable
    private final Short port;
    //EDF & 0x10
    @Getter(AccessLevel.NONE)
    @Nullable
    private final Long steamID;
    //EDF & 0x40
    @Getter(AccessLevel.NONE)
    @Nullable
    private final Short sourceTVPort;
    @Getter(AccessLevel.NONE)
    @Nullable
    private final String sourceSpectatorName;
    //EDF & 0x20
    @Getter(AccessLevel.NONE)
    @Nullable
    private final String keywords;
    //EDF & 0x01
    @Getter(AccessLevel.NONE)
    @Nullable
    private final Long gameID;

    /**
     * Constructs a {@link A2SInfoResponse} given byte data of the response.
     *
     * The data <b>MUST</b> have the first four <code>FF</code> bytes included. For example:
     *
     * <code>FF FF FF FF 49 .. ..</code>
     *
     * See the <a href="https://developer.valvesoftware.com/wiki/Server_queries#A2S_INFO">A2S_INFO Documentation</a>
     * for more info.
     *
     * @param data an array of bytes that the A2S query responded with
     */
    private A2SInfoResponse(byte[] data){
        super(data);

        //Offset to ignore the first four FF bytes
        ByteBuffer buffer = ByteBuffer.wrap(data, 4, data.length - 4);
        //Header
        buffer.get();
        //Protocol
        this.protocol = buffer.get();
        //Name
        this.name = BufferHelper.getStringFromBuffer(buffer);
        //Map
        this.map = BufferHelper.getStringFromBuffer(buffer);
        //Folder
        this.folder = BufferHelper.getStringFromBuffer(buffer);
        //Game
        this.game = BufferHelper.getStringFromBuffer(buffer);
        //Game id
        this.id = buffer.getShort();
        //Player count
        this.players = buffer.get();
        //Max player count
        this.maxPlayers = buffer.get();
        //Bot count
        this.bots = buffer.get();
        //Server type
        this.serverType = buffer.get();
        //Environment
        this.environment = buffer.get();
        //Visibility
        this.visibility = buffer.get();
        //VAC Enabled
        this.vac = buffer.get();
        //Version
        this.version = BufferHelper.getStringFromBuffer(buffer);
        //Extra data flag (EDF)
        this.edf = buffer.get();
        //EDF & 0x80
        if((this.edf & 0x80) > 0){
            this.port = buffer.getShort();
        }else{
            this.port = null;
        }
        //EDF & 0x10
        if((this.edf & 0x10) > 0){
            this.steamID = buffer.getLong();
        }else{
            this.steamID = null;
        }
        //EDF & 0x40
        if((this.edf & 0x40) > 0){
            this.sourceTVPort = buffer.getShort();
            this.sourceSpectatorName = BufferHelper.getStringFromBuffer(buffer);
        }else{
            this.sourceTVPort = null;
            this.sourceSpectatorName = null;
        }
        //EDF & 0x20
        if((this.edf & 0x20) > 0){
            this.keywords = BufferHelper.getStringFromBuffer(buffer);
        }else{
            this.keywords = null;
        }
        //EDF & 0x01
        if((this.edf & 0x01) > 0){
            this.gameID = buffer.getLong();
        }else{
            this.gameID = null;
        }
    }

    /**
     * Constructs a {@link A2SInfoResponse} given response data AFTER the challenge response has been sent.
     *
     * When providing the raw data, make sure to <b>INCLUDE</b> the first four <code>FF</code> bytes.
     *
     * For example, your packet data should start with: <code>FF FF FF FF 49 ...</code>
     *
     * @param rawData the data received for the A2S_INFO query, with the conditions above met
     * @return an {@link A2SInfoResponse} describing the response
     */
    public static A2SInfoResponse from(byte[] rawData){
        return new A2SInfoResponse(rawData);
    }

    public Optional<Short> getPort() throws NoSuchElementException {
        return Optional.ofNullable(this.port);
    }

    public Optional<Long> getSteamID() throws NoSuchElementException{
        return Optional.ofNullable(this.steamID);
    }

    public Optional<Short> getSourceTVPort() throws NoSuchElementException{
        return Optional.ofNullable(this.sourceTVPort);
    }

    public Optional<String> getSourceSpectatorName() throws NoSuchElementException{
        return Optional.ofNullable(this.sourceSpectatorName);
    }

    public Optional<String> getKeywords() throws NoSuchElementException{
        return Optional.ofNullable(this.keywords);
    }

    public Optional<Long> getGameID() throws NoSuchElementException{
        return Optional.ofNullable(this.gameID);
    }
}