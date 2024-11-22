package socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketService {
    private static SocketService instance;
    private Socket socket;

    private SocketService(String serverUrl) throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"}; 
        options.timeout = 5000; 
        options.reconnection = true; 
        socket = IO.socket(serverUrl, options);
    }

    public static SocketService getInstance(String serverUrl) throws URISyntaxException {
        if (instance == null) {
            instance = new SocketService(serverUrl);
        }
        return instance;
    }

    public static SocketService getInstance() {
        return instance;
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    public boolean isConnected() {
        return socket.connected();
    }

    public void sendMessage(String event, JSONObject message) {
        socket.emit(event, message);
    }

    public void onMessage(String event, Emitter.Listener listener) {
        socket.on(event, listener);
    }
}