package sockets;

import main.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static utils.GameVariables.networkGame;

/**
 * This class is used by the player that decides to act as the game host.
 * Once the host connects to the client, this class starts the game.
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-31
 */
public class GameHost {

    private Socket host;
    private ServerSocket hostSocket;

    /**
     * Constructor for GameHost
     *
     * @param port int used to connect between sockets. Always 6066.
     * @throws IOException due to ServerSocket class
     */
    public GameHost(int port) throws IOException {
        hostSocket = new ServerSocket(port);
        hostSocket.setSoTimeout(5000);
    }

    /**
     * Get the Socket object for the host.
     * @return Socket
     */
    public Socket getSocket() {
        return host;
    }

    /**
     * Wait for a client to connect. Method waits 3 seconds in between checking.
     * Once connected, the game is started.
     */
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
        networkGame = true;
        Main.startGame();
    }
}
