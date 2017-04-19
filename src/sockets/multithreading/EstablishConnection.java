package sockets.multithreading;

import sockets.GameClient;
import sockets.GameHost;

/**
 * Asynchronous class that manages the initial network connection.
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-31
 */
public class EstablishConnection implements Runnable {

    private GameHost host;
    private GameClient client;


    /**
     * Set host object in order to be run.
     *
     * @param host object that contains the Socket.
     * @return EstablishConnection for method chaining.
     */
    public EstablishConnection setHost(GameHost host) {
        this.host = host;
        return this;
    }

    /**
     * Set client object in order to be run.
     *
     * @param client object that contains the Socket.
     * @return EstablishConnection for method chaining.
     */
    public EstablishConnection setClient(GameClient client) {
        this.client = client;
        return this;
    }

    /**
     * Asynchronous run method for Runnable.
     * If player is a host, starts waiting for a connection
     * Else client tried to connect to a host
     */
    @Override
    public void run() {
        if (host != null) {
            host.waitForConnection();
        } else if (client != null) {
            client.connect();
        }
    }
}
