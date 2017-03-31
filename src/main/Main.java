package main;

import components.CheckerBoardPanel;
import eventListeners.FrameListener;
import sockets.GameClient;
import sockets.GameHost;
import sockets.multithreading.EstablishConnection;
import sockets.multithreading.SocketProtocol;
import utils.GameTimer;
import utils.Moves;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Runnable class for program and GUI
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-06
 */
public class Main {

    public static JFrame gameFrame;
    public static CheckerBoardPanel checkerBoard;

    public static GameHost host;
    public static GameClient client;
    public static SocketProtocol socketProtocol;

    private static JFrame preGameOptions;
    private final static int SOCKET_PORT = 6066;

    /**
     * Main method for program and GUI
     *
     * @param args arguments to run main with
     */
    public static void main(String[] args) {
        preGameOptions = new JFrame();
        preGameOptions.setTitle("Pre-Game Options");
        preGameOptions.setMinimumSize(new Dimension(400, 200));
        preGameOptions.setResizable(false);
        JPanel cardLayout = new JPanel(new CardLayout());

        JCheckBox forcedJumps = new JCheckBox("Forced Jumps");
        JCheckBox timedTurns = new JCheckBox("Timed Turns");
        JLabel textFieldLabel = new JLabel("Please enter the host to connect to");
        JTextField hostIpField = new JTextField(20);
        hostIpField.setToolTipText("IP Address");
        JButton startGameButton = new JButton("Start Game");

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel localGameTab = new JPanel(new GridBagLayout());
        JPanel hostGameTab = new JPanel(new GridBagLayout());
        JPanel joinGameTab = new JPanel(new GridBagLayout());
        localGameTab.setBackground(new Color(255, 226, 193));
        hostGameTab.setBackground(new Color(193, 226, 255));
        joinGameTab.setBackground(new Color(226, 255, 193));

        JPanel networkGameWaiting = new JPanel(new GridBagLayout());
        networkGameWaiting.setVisible(false);
        networkGameWaiting.setPreferredSize(new Dimension(0, 0));
        networkGameWaiting.setBackground(new Color(226, 193, 255));

        tabbedPane.addChangeListener(e -> {
            JComponent selected = (JComponent) tabbedPane.getSelectedComponent();
            if (tabbedPane.getSelectedIndex() == 0 || tabbedPane.getSelectedIndex() == 1) {
                selected.add(forcedJumps, createConstraints(
                        0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0));
                selected.add(timedTurns, createConstraints(
                        0, 1, 1, GridBagConstraints.FIRST_LINE_END, 0, 0));
            }
            GridBagConstraints buttonConstraints = createConstraints(
                    2, 0, 2, GridBagConstraints.CENTER, 0, 0);
            buttonConstraints.insets = new Insets(20, 0, 0, 0);
            selected.add(startGameButton, buttonConstraints);
        });

        startGameButton.addActionListener(event -> {
            int gameType = tabbedPane.getSelectedIndex();
            if (gameType == 0) {
                Moves.forceJumpEnabled = forcedJumps.isSelected();
                GameTimer.timedTurns = timedTurns.isSelected();

                SocketProtocol.networkGame = false;
                startGame();
            } else if (gameType == 1) {
                try {
                    tabbedPane.setVisible(false);
                    tabbedPane.setPreferredSize(new Dimension(0, 0));

                    Socket socket = new Socket();
                    JLabel ipAddress = new JLabel();
                    socket.connect(new InetSocketAddress("google.com", 80));
                    ipAddress.setText("Your IP address is " + socket.getLocalAddress().toString().substring(1));
                    socket.close();

                    JLabel status = new JLabel("Waiting for someone to join...");

                    networkGameWaiting.add(ipAddress, createConstraints(
                            0, 0, 1, GridBagConstraints.CENTER, 0, 0));
                    GridBagConstraints labelConstraints = createConstraints(
                            1, 0, 1, GridBagConstraints.CENTER, 0, 0);
                    labelConstraints.insets = new Insets(20, 0, 0, 0);
                    networkGameWaiting.add(status, labelConstraints);
                    networkGameWaiting.setVisible(true);
                    networkGameWaiting.setPreferredSize(new Dimension(400, 200));

                    Moves.forceJumpEnabled = forcedJumps.isSelected();
                    GameTimer.timedTurns = timedTurns.isSelected();
                    host = new GameHost(SOCKET_PORT);
                    new Thread(new EstablishConnection().setHost(host)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String hostIp;
                if (!(hostIp = hostIpField.getText()).isEmpty()) {
                    tabbedPane.setVisible(false);
                    tabbedPane.setPreferredSize(new Dimension(0, 0));

                    JLabel status = new JLabel("Trying to connect to host...");
                    networkGameWaiting.add(status, createConstraints(
                            0, 0, 1, GridBagConstraints.CENTER, 0, 0));
                    networkGameWaiting.setVisible(true);
                    networkGameWaiting.setPreferredSize(new Dimension(400, 200));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    client = new GameClient(hostIp, SOCKET_PORT);
                    new Thread(new EstablishConnection().setClient(client)).start();
                } else {
                    hostIpField.setBackground(new Color(255, 214, 219));
                }
            }
        });

        joinGameTab.add(textFieldLabel, createConstraints(
                0, 0, 1, GridBagConstraints.CENTER, 0, 0));
        joinGameTab.add(hostIpField, createConstraints(
                1, 0, 1, GridBagConstraints.LAST_LINE_START, 0, 0));

        tabbedPane.addTab("Local Game", localGameTab);
        tabbedPane.addTab("Host a Game", hostGameTab);
        tabbedPane.addTab("Join a Game", joinGameTab);

        cardLayout.add(tabbedPane);
        cardLayout.add(networkGameWaiting);
        preGameOptions.add(cardLayout, BorderLayout.CENTER);
        preGameOptions.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        preGameOptions.pack();
        preGameOptions.setVisible(true);
    }

    public static void startGame() {
        preGameOptions.dispose();
        gameFrame = new JFrame();
        checkerBoard = new CheckerBoardPanel();

        if (SocketProtocol.networkGame) {
            try {
                if (host != null) {
                    socketProtocol = new SocketProtocol(host.getSocket(), true);
                    checkerBoard.createNewBoard();
                    checkerBoard.startTimers();
                    gameFrame.setJMenuBar(checkerBoard.initializeMenu());
                } else {
                    socketProtocol = new SocketProtocol(client.getSocket(), false);
                }
            } catch (IOException e) {
                checkerBoard.statusLabel.setText("Failed to open stream between sockets. Please restart and try again.");
                checkerBoard.statusLabel.setForeground(new Color(255, 0, 0));
            }
        } else {
            gameFrame.setJMenuBar(checkerBoard.initializeMenu());
            gameFrame.addWindowListener(new FrameListener());
        }

        gameFrame.add(checkerBoard);
        gameFrame.setExtendedState(gameFrame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        gameFrame.setMinimumSize(new Dimension(800, 800));
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }

    public static GridBagConstraints createConstraints(int gridY, int gridX, int gridWidth, int anchor, int ipadY, int ipadX) {
        return new GridBagConstraints(gridX, gridY, gridWidth, 1,
                0, 0, anchor, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), ipadX, ipadY);
    }
}
