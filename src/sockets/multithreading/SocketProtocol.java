package sockets.multithreading;

import utils.GameTimer;
import utils.Moves;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.net.Socket;

import static main.Main.checkerBoard;
import static main.Main.gameFrame;

public class SocketProtocol implements Runnable {

    public static boolean networkGame;

    private Socket socket;
    private boolean host;
    private BufferedReader in;
    private PrintWriter out;
    private boolean activeGame;

    public SocketProtocol(Socket socket, boolean host) throws IOException {
        this.socket = socket;
        this.host = host;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        activeGame = true;
        new Thread(this).start();

        if (host) {
            String preGameOptions = Json.createObjectBuilder()
                                 .add("message", "preGameOptions")
                                 .add("forcedJumps", Moves.forceJumpEnabled)
                                 .add("timedTurns", GameTimer.timedTurns)
                                 .build().toString();
            sendMessage(preGameOptions);
            String startGame = Json.createObjectBuilder().add("message", "startGame").build().toString();
            sendMessage(startGame);
        }
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
        while (activeGame) {
            while (socket.isConnected()) {
                try {
                    String input = in.readLine();
                    if (input != null) {
                        processMessage(input);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!socket.isConnected()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void processMessage(String json) {
        JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
        String message = jsonObject.getString("message");

        if ("preGameOptions".equals(message)) {
            Moves.forceJumpEnabled = jsonObject.getBoolean("forcedJumps");
            GameTimer.timedTurns = jsonObject.getBoolean("timedTurns");
            checkerBoard.createNewBoard();
            gameFrame.setJMenuBar(checkerBoard.initializeMenu());
        } else if ("startGame".equals(message)) {
            checkerBoard.startTimers();
        }
    }

    public void sendMessage(String message) {
        while (activeGame) {
            if (socket.isConnected()) {
                out.println(message);
                return;
            } else {
                try {
                    Thread.sleep(3000);
                    System.out.println("Trying to connect...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
