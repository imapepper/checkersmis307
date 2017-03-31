package async;

import main.Main;

public class AwaitJoinRequest implements Runnable {

    @Override
    public void run() {
        Main.host.waitForConnection();
    }
}
