package a2s;

import a2s.response.A2SInfoResponse;
import a2s.response.A2SRulesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * @author Robert Engle
 *
 * https://github.com/roengle/squadQuery/blob/main/src/query/Query.java
 *
 * Class that implements Source Engine Queries. Provides methods to send A2S_INFO and A2S_RULES queries.
 *
 * For technical documentation on Source Engine Queries, see the following:
 *
 * https://developer.valvesoftware.com/wiki/Server_queries
 */

//TODO: Improve implementation for slower connections
public class QueryImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryImpl.class);

    private DatagramSocket socket;
    private final String address;
    private final Integer port;

    protected QueryImpl(String address, Integer port) throws IOException{
        this.socket = new DatagramSocket();
        this.address = address;
        this.port = port;
    }

    private void newSocket(){
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            LOGGER.error("Error reassigning socket.", e);
        }
    }

    protected A2SInfoResponse queryInfo() throws IOException {
        InetAddress inetAddress = InetAddress.getByName(this.address);

        String payload = "Source Engine Query\0";

        ByteBuffer buffer = ByteBuffer.allocate(5 + payload.getBytes(StandardCharsets.UTF_8).length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)'T');
        buffer.put(payload.getBytes(StandardCharsets.UTF_8));

        send(this.socket, inetAddress, this.port, buffer.array());

        DatagramPacket receivingPacket = receiveAsync(this.socket);

        if(receivingPacket == null){
            return null;
        }

        byte[] receivedData = receivingPacket.getData();
        if(receivedData[4] == (byte)0x41){
            buffer = ByteBuffer.allocate(buffer.capacity() + 4);
            buffer.put((byte) 0xFF);
            buffer.put((byte) 0xFF);
            buffer.put((byte) 0xFF);
            buffer.put((byte) 0xFF);
            buffer.put((byte) 'T');
            buffer.put(payload.getBytes(StandardCharsets.UTF_8));
            for (int i = 5; i <= 8; i++) {
                buffer.put(receivedData[i]);
            }
            send(this.socket, inetAddress, this.port, buffer.array());

            DatagramPacket receivedPacket = receiveAsync(this.socket);
            if(receivedPacket != null){
                receivedData = receivedPacket.getData();
                return A2SInfoResponse.from(receivedData);
            }else{
                return null;
            }

        }else{
            return A2SInfoResponse.from(receivedData);
        }
    }

    protected A2SRulesResponse queryRules() throws IOException {
        InetAddress address = InetAddress.getByName(this.address);

        ByteBuffer buffer = ByteBuffer.allocate(9);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)0x56);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);
        buffer.put((byte)0xFF);

        send(socket, address, port, buffer.array());

        DatagramPacket receivingPacket = receiveAsync(this.socket);
        if(receivingPacket == null){
            return null;
        }
        byte[] receivedData = receivingPacket.getData();
        if(receivedData[4] == (byte)0x41){
            buffer = ByteBuffer.allocate(buffer.capacity());
            buffer.put((byte) 0xFF);
            buffer.put((byte) 0xFF);
            buffer.put((byte) 0xFF);
            buffer.put((byte) 0xFF);
            buffer.put((byte)0x56);
            for(int i = 5; i < 9; i++){
                buffer.put(receivedData[i]);
            }

            send(this.socket, address, this.port, buffer.array());
            receivedData =  receiveAsync(this.socket).getData();

            return A2SRulesResponse.from(receivedData);
        }else{
            return A2SRulesResponse.from(receivedData);
        }
    }

    private static void send(DatagramSocket socket, InetAddress address, Integer port, byte[] payload) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(payload, payload.length, address, port);
        socket.send(sendPacket);
    }

    private static DatagramPacket receiveAsync(DatagramSocket socket){
        CompletableFuture<DatagramPacket> future = getFuture(socket);

        try {
            return future.get(4, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error(e.getMessage());
        } catch(TimeoutException timeout){
            LOGGER.warn("Query timed out after 2 seconds, retrying.");
            try{
                socket = new DatagramSocket();
                CompletableFuture<DatagramPacket> newFuture = getFuture(socket);
                return newFuture.get(4, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException e) {
                LOGGER.error(e.getMessage());
            } catch (TimeoutException retryTimeout) {
                LOGGER.error("Query timed out again after retrying.", retryTimeout);
            } catch (SocketException e) {
                LOGGER.error("Error reassigning socket.", e);
            }
        }
        return null;
    }

    private static CompletableFuture<DatagramPacket> getFuture(DatagramSocket socket){
        return CompletableFuture.supplyAsync(() -> {
            try {
                return receive(socket);
            } catch (IOException e) {
                LOGGER.error("Error receiving socket data.");
                LOGGER.error(e.getMessage());
            }
            return null;
        });
    }

    private static DatagramPacket receive(DatagramSocket socket) throws IOException {
        byte[] receivingBuffer = new byte[1400];

        DatagramPacket receivingPacket = new DatagramPacket(receivingBuffer, receivingBuffer.length);

        socket.receive(receivingPacket);

        return receivingPacket;
    }

}