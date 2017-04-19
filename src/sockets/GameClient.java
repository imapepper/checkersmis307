package sockets;

import main.Main;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import static utils.GameVariables.networkGame;

/**
 * This class is used by the player that decides to act as the game client.
 * Once the client connects to the host, this class starts the game.
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-31
 */
public class GameClient {

    private Socket client;
    private String hostName;
    private int port;

    /**
     * Constructor for GameClient
     *
     * @param hostName String used for the hostname in order to connect
     * @param port int used to connect between sockets. Always 6066.
     */
    public GameClient(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    /**
     * Get the Socket object for the client.
     * @return Socket
     */
    public Socket getSocket() {
        return client;
    }

    /**
     * Attempts to connect to the host and waits three seconds in between tries.
     * Once connected, the game is started.
     */
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
        networkGame = true;
        Main.startGame();
    }
}
