package sockets;

import main.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GameHost extends Thread {

    private Socket server;
    private ServerSocket serverSocket;

    public GameHost(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(5000);
    }

    public void waitForConnection() {
        while (server == null) {
            try {
                server = serverSocket.accept();
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
        Main.startGame(true);
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            PrintWriter out = new PrintWriter(server.getOutputStream(), true);
            System.out.println("Connected to " + server.getRemoteSocketAddress());

            SocketProtocol socketProtocol = new SocketProtocol();

            String input;
            while (!"Quit".equals(input = in.readLine())) {
                socketProtocol.processInput(input);
                out.println("Message received");
            }
            try {
                System.out.println("Client disconnected");
                out.println("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
