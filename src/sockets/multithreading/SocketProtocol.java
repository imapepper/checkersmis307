package sockets.multithreading;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.*;
import java.net.Socket;

import static utils.GameVariables.*;

public class SocketProtocol implements Runnable {

    public int playerNum;

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
            checkerBoard.prepareGame(0);
            playerNum = 1;
            String preGameInfo = Json.createObjectBuilder()
                                     .add("message", "preGameInfo")
                                     .add("forcedJumps", forceJumpEnabled)
                                     .add("timedTurns", timedTurns)
                                     .add("startingPlayer", checkerBoard.currentPlayer)
                                     .build().toString();
            sendMessage(preGameInfo);
            String startGame = Json.createObjectBuilder().add("message", "startGame").build().toString();
            sendMessage(startGame);
            checkerBoard.startTimers();
            gameFrame.setJMenuBar(checkerBoard.initializeMenu());
        } else {
            playerNum = 2;
        }
    }

    public boolean isHost() {
        return host;
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

    private void processMessage(String json) {
        System.out.println(json);
        JsonObject jsonObject = Json.createReader(new StringReader(json)).readObject();
        String message = jsonObject.getString("message");

        if ("preGameInfo".equals(message)) {
            forceJumpEnabled = jsonObject.getBoolean("forcedJumps");
            timedTurns = jsonObject.getBoolean("timedTurns");
            checkerBoard.prepareGame(jsonObject.getInt("startingPlayer"));
            gameFrame.setJMenuBar(checkerBoard.initializeMenu());
        } else if ("startGame".equals(message)) {
            checkerBoard.startTimers();
        } else if ("changePlayer".equals(message)) {
            checkerBoard.changePlayer(jsonObject.getBoolean("turnTimeExpired"));
        } else if ("movePiece".equals(message)) {
            checkerBoard.movePiece(
                    jsonObject.getInt("fromY"),
                    jsonObject.getInt("fromX"),
                    jsonObject.getInt("toY"),
                    jsonObject.getInt("toX")
            );
        } else if ("removePiece".equals(message)) {
            checkerBoard.removePiece(
                    jsonObject.getInt("fromY"),
                    jsonObject.getInt("fromX")
            );
        } else if ("newGame".equals(message)) {
            checkerBoard.displayEndGameOptions("Opponent wants to play again!",
                    jsonObject.getInt("startingPlayer"), true);
        } else if ("acceptNewGame".equals(message)) {
            String startGame = Json.createObjectBuilder().add("message", "startGame").build().toString();
            sendMessage(startGame);
            checkerBoard.startTimers();
        } else if ("goodbye".equals(message)) {
            checkerBoard.displayEndGameOptions("Opponent Quit", 0, false);
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
