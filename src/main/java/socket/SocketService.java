package socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Manages socket communication with a server using socket.io.
 * Provides singleton access to socket connection and messaging.
 */
public class SocketService {
    private static SocketService instance;
    private Socket socket;

    /**
     * Constructs a SocketService with specified server URL and connection options.
     *
     * @param serverUrl URL of the socket server
     * @throws URISyntaxException if the server URL is invalid
     */
    private SocketService(String serverUrl) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};
        options.timeout = 5000;
        options.reconnection = true;
        socket = IO.socket(serverUrl, options);
    }

    /**
     * Retrieves or creates the singleton SocketService instance.
     *
     * @param serverUrl URL of the socket server
     * @return SocketService instance
     * @throws URISyntaxException if the server URL is invalid
     */
    public static SocketService getInstance(String serverUrl) throws URISyntaxException {
        if (instance == null) {
            instance = new SocketService(serverUrl);
        }
        return instance;
    }

    /**
     * Retrieves the existing SocketService instance.
     *
     * @return SocketService instance
     */
    public static SocketService getInstance() {
        return instance;
    }

    /**
     * Establishes a connection to the socket server.
     */
    public void connect() {
        socket.connect();
    }

    /**
     * Terminates the socket server connection.
     */
    public void disconnect() {
        socket.disconnect();
    }

    /**
     * Checks if a connection to the socket server is active.
     *
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        return socket.connected();
    }

    /**
     * Sends a message to the socket server.
     *
     * @param event Event name
     * @param message JSON message to send
     */
    public void sendMessage(String event, JSONObject message) {
        socket.emit(event, message);
    }

    /**
     * Registers a listener for a specific socket event.
     *
     * @param event Event name to listen for
     * @param listener Callback to handle the event
     */
    public void onMessage(String event, Emitter.Listener listener) {
        socket.on(event, listener);
    }
}