package sockets.multithreading;

import sockets.GameClient;
import sockets.GameHost;

public class EstablishConnection implements Runnable {

    private GameHost host;
    private GameClient client;

    public EstablishConnection setHost(GameHost host) {
        this.host = host;
        return this;
    }

    public EstablishConnection setClient(GameClient client) {
        this.client = client;
        return this;
    }

    @Override
    public void run() {
        if (host != null) {
            host.waitForConnection();
        } else if (client != null) {
            client.connect();
        }
    }
}
