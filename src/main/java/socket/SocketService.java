package socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * The SocketService class provides a singleton wrapper for managing socket connections
 * using the Socket.IO library. It supports connecting to a server, sending messages,
 * and handling events through listeners.
 *
 * <p>This class ensures only one instance of the socket connection is used throughout the application.</p>
 * 
 * <p>Key features include:
 * <ul>
 *   <li>Customizable connection options such as transport, timeout, and reconnection.</li>
 *   <li>Methods for sending messages and registering event listeners.</li>
 *   <li>Singleton pattern to maintain a single connection instance.</li>
 * </ul>
 * </p>
 * 
 * @author [Your Name]
 * @version 1.0
 * @since 2024-11-30
 */
public class SocketService {
    private static SocketService instance;
    private Socket socket;

    /**
     * Private constructor to initialize the socket connection with the specified server URL and options.
     *  
     * @param serverUrl The URL of the server to connect to.
     * @throws URISyntaxException If the server URL is invalid.
     */
    private SocketService(String serverUrl) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"}; // Use WebSocket transport for the connection.
        options.timeout = 5000; // Set connection timeout in milliseconds.
        options.reconnection = true; // Enable automatic reconnection.

        socket = IO.socket(serverUrl, options);
    }

    /**
     * Returns the singleton instance of the SocketService, initializing it if necessary.
     *
     * @param serverUrl The URL of the server to connect to (used for the first initialization only).
     * @return The singleton instance of SocketService.
     * @throws URISyntaxException If the server URL is invalid.
     */
    public static SocketService getInstance(String serverUrl) throws URISyntaxException {
        if (instance == null) {
            instance = new SocketService(serverUrl);
        }
        return instance;
    }

    /**
     * Returns the existing instance of the SocketService. 
     * This method assumes the instance has already been initialized.
     *
     * @return The singleton instance of SocketService, or null if not initialized.
     */
    public static SocketService getInstance() {
        return instance;
    }

    /**
     * Connects to the server using the configured socket connection.
     */
    public void connect() {
        socket.connect();
    }

    /**
     * Disconnects from the server and closes the socket connection.
     */
    public void disconnect() {
        socket.disconnect();
    }

    /**
     * Checks if the socket is currently connected to the server.
     *
     * @return True if the socket is connected, otherwise false.
     */
    public boolean isConnected() {
        return socket.connected();
    }

    /**
     * Sends a message to the server under the specified event name.
     *
     * @param event   The name of the event to emit.
     * @param message The message payload to send, represented as a JSONObject.
     */
    public void sendMessage(String event, JSONObject message) {
        socket.emit(event, message);
    }

    /**
     * Registers a listener for the specified event to handle incoming messages.
     *
     * @param event    The name of the event to listen for.
     * @param listener The callback function to handle the event.
     */
    public void onMessage(String event, Emitter.Listener listener) {
        socket.on(event, listener);
    }
}
