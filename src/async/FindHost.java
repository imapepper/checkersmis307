package async;

import main.Main;

public class FindHost implements Runnable {

    @Override
    public void run() {
        Main.client.connect();
    }
}
