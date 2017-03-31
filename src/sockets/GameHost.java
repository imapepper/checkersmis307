package sockets;

import main.Main;
import sockets.multithreading.SocketProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GameHost {

    private Socket host;
    private ServerSocket hostSocket;

    public GameHost(int port) throws IOException {
        hostSocket = new ServerSocket(port);
        hostSocket.setSoTimeout(5000);
    }

    public Socket getSocket() {
        return host;
    }

    public void waitForConnection() {
        while (host == null) {
            try {
                host = hostSocket.accept();
                break;
            } catch (SocketTimeoutException ignored) {
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
