package sockets;

import main.Main;

import javax.json.Json;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class GameClient {

    private Socket client;
    private String hostName;
    private int port;

    public GameClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public void connect() {
        while (client == null) {
            try {
                client = new Socket(hostName, port);
                break;
            } catch (ConnectException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Main.startGame(true);
    }

    public void sendMessage(String message) {
        message = Json.createObjectBuilder()
                      .add("action", "movePiece")
                      .add("fromY", 1)
                      .add("fromX", 2)
                      .add("toY", 3)
                      .add("toX", 4)
                      .build().toString();
        try {
            System.out.println("Connected to " + client.getRemoteSocketAddress());
            PrintWriter outToServer = new PrintWriter(client.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String input = "";
            while (!"Quit".equals(input)) {
                outToServer.println(message);
                System.out.println("Server says " + inFromServer.readLine());
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
