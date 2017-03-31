package sockets.multithreading;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.net.Socket;

public class SocketProtocol implements Runnable {

    public static boolean networkGame;

    private Socket socket;
    private boolean host;
    private BufferedReader in;
    private PrintWriter out;

    public SocketProtocol(Socket socket, boolean host) throws IOException {
        this.socket = socket;
        this.host = host;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isHost() {
        return host;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String input = in.readLine();
                processMessage(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void processMessage(String json) {
        JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
        String message = jsonObject.getString("message");


    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
