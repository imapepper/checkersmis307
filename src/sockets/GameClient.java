package sockets;

import main.Main;
import sockets.multithreading.SocketProtocol;

import java.io.IOException;
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

    public Socket getSocket() {
        return client;
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
        SocketProtocol.networkGame = true;
        Main.startGame();
    }
}
