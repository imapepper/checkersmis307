package components;

//import eventListeners.ButtonListener;
import eventListeners.SpaceClickListener;
import main.Main;
import objects.CheckerPiece;
import utils.GameTimer;
import utils.Moves;
import utils.SoundPlayer;

import javax.json.Json;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static utils.GUIStyles.setBlackLightModeEnabled;
import static utils.GameVariables.*;

/**
 * Class extending JPanel to act as a GUI component.
 * Manages the CheckerBoard for the checkers game as well as most GUI components
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-06
 */
public class CheckerBoardPanel extends JPanel {

    private Space[][] spaces;
    private int numPlayer1Pieces;
    private int numPlayer2Pieces;
    private JLabel player1Status;
    private JLabel player2Status;
    private JFrame endGameFrame;
    private GameTimer timer;
    private JLabel timerLabel;
    private JLabel turnTimeLabel;

    public JLabel statusLabel;
    public int currentPlayer;
    public boolean gameOver;
    public JMenuBar menuBar;

    /**
     * Gets all the spaces on the board
     * @return spaces for the board
     */
    public Space[][] getSpaces() {
        return spaces;
    }

    /**
     * Set the game state and build the board and pieces
     * If no starting player, call the method to determine one
     * Then find all the moves that the current player could make
     * @param startingPlayer Start at 0, call decideWhoMovesFirst to determine order
     */
    public void prepareGame(int startingPlayer) {
        gameOver = false;
        numPlayer1Pieces = 12;
        numPlayer2Pieces = 12;
        soundsEnabled = true;

        setBackground(new Color(130, 148, 178));
        initializeSpaces();
        setLayout(new GridBagLayout());
        initializeStatus();
        initializeBoardGUI();
        if (startingPlayer == 0) {
            decideWhoMovesFirst();
        } else {
            currentPlayer = startingPlayer;
            statusLabel.setText("Player " + startingPlayer + " moves first!");
        }
        Moves.findAllMovesForPlayer(currentPlayer);
    }

    /**
     * Set and start the game timers
     */
    public void startTimers() {
        if (timer == null) {
            timer = new GameTimer(timerLabel, turnTimeLabel);
            timer.startTimer();
            timer.startTurn();
        }
    }

//    public void stopTimers() {
//        if (timer != null) {
//            timer.stopTimer();
//        }
//    }

    /**
     * Initialize all the spaces on the board and add pieces to the first two rows on both sides.
     */
    private void initializeSpaces() {
        spaces = new Space[8][8];
        int index = 0;
        while (index < 32) {
            for (int i = 0; i < 8; i++) {
                int start;
                if (i % 2 == 1) {
                    start = 0;
                } else {
                    start = 1;
                }
                for (int j = start; j < 8; j += 2) {
                    spaces[i][j] = new Space(i + 1, j + 1, true);
                    if (i < 3) {
                        spaces[i][j].setPiece(new CheckerPiece(1));
                    } else if (i > 4) {
                        spaces[i][j].setPiece(new CheckerPiece(2));
                    }
                    if (start == 0) {
                        spaces[i][j + 1] = new Space(i + 2, j + 1, false);
                    } else {
                        spaces[i][j - 1] = new Space(i, j + 1, false);
                    }
                    index++;
                }
            }
        }
    }
    
    /**
     * Initialize and setup all status objects, like those that monitor
     * pieces remaining, game timer, whose turn it is, and so on
     */
    private void initializeStatus() {
    	statusLabel = new JLabel("Initialized board");
    	statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 14));
    	add(statusLabel, Main.createConstraints(0, 0, 8, GridBagConstraints.CENTER, 50, 50));

    	player1Status = new JLabel("Player 1 Pieces: " + numPlayer1Pieces);
        player1Status.setHorizontalAlignment(SwingConstants.CENTER);
        player1Status.setFont(new Font("SansSerif", Font.BOLD, 12));
        player1Status.setOpaque(true);
        player1Status.setBackground(new Color(182, 182, 182));
        player1Status.setBorder(BorderFactory.createLineBorder(new Color(92, 92, 92), 5));
    	add(player1Status, Main.createConstraints(4, 0, 1, GridBagConstraints.CENTER, 50, 50));

    	player2Status = new JLabel("Player 2 Pieces: " + numPlayer2Pieces);
    	player2Status.setHorizontalAlignment(SwingConstants.CENTER);
        player2Status.setFont(new Font("SansSerif", Font.BOLD, 12));
        player2Status.setOpaque(true);
        player2Status.setForeground(new Color(200, 0, 0));
        player2Status.setBackground(new Color(103, 0, 0));
        player2Status.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    	add(player2Status, Main.createConstraints(5, 0, 1, GridBagConstraints.CENTER, 50, 50));

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 12));
        add(timerLabel, Main.createConstraints(1, 0, 1, GridBagConstraints.CENTER, 50, 50));
    	turnTimeLabel = new JLabel();
        turnTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnTimeLabel.setFont(new Font("Serif", Font.BOLD, 12));
    	add(turnTimeLabel, Main.createConstraints(2, 0, 1, GridBagConstraints.CENTER, 50, 50));
    }
    
    /**
     * Populate the game board initialize the necessary listeners
     */
    private void initializeBoardGUI() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Space space = spaces[i][j];
                space.addMouseListener(new SpaceClickListener());
                add(space, Main.createConstraints(i + 1, j + 1, 1, GridBagConstraints.CENTER, 50, 50));
            }
        }
    }
    
    /**
     * Randomizing who moves first
     */
    private void decideWhoMovesFirst() {
        int random = new Random().nextInt(2);
        if (random == 0) {
        	currentPlayer = 1;
        	statusLabel.setText("Player 1 moves first!");
        } else {
        	currentPlayer = 2;
        	statusLabel.setText("Player 2 moves first!");
        }
    }
    
    /**
     * Initializes our game's menu and menu bar
     * Handles the toggles for player's selections with action listener
     * @return the menuBar so that the menuBar is added to the game client
     */
    public JMenuBar initializeMenu() {
    	menuBar = new JMenuBar();
    	JMenu settings = new JMenu("Settings");

    	JCheckBox toggleSounds = new JCheckBox("Sounds?");
    	JCheckBox toggleBlackLightMode = new JCheckBox("Black light Mode?");
    	JCheckBox forcedJumpsBox = new JCheckBox("Forced Jumps?");
        JCheckBox timedTurnsBox = new JCheckBox("Timed Turns?");

    	toggleSounds.setSelected(true);
    	toggleBlackLightMode.setSelected(false);
    	forcedJumpsBox.setSelected(forceJumpEnabled);
    	timedTurnsBox.setSelected(timedTurns);

    	forcedJumpsBox.setEnabled(false);
    	timedTurnsBox.setEnabled(false);

    	toggleSounds.addActionListener(e -> {
            soundsEnabled = toggleSounds.isSelected();
    	});
    	toggleBlackLightMode.addActionListener(e -> {
    	    boolean blackLightMode = toggleBlackLightMode.isSelected();
    	    if(blackLightMode) {
                setBackground(new Color(0, 110, 12));
                statusLabel.setForeground(Color.WHITE);
                timerLabel.setForeground(Color.WHITE);
                turnTimeLabel.setForeground(Color.WHITE);
    	    	player1Status.setForeground(new Color(170, 0, 255));
                player1Status.setBackground(new Color(74, 0, 119));
                player1Status.setBorder(BorderFactory.createLineBorder(new Color(57, 0, 87), 5));
    	    	player2Status.setForeground(new Color(255, 0, 170));
                player2Status.setBackground(new Color(119, 0, 79));
                player2Status.setBorder(BorderFactory.createLineBorder(new Color(112, 0, 57), 5));
    	    } else {
                setBackground(new Color(130, 148, 178));
                statusLabel.setForeground(Color.BLACK);
                timerLabel.setForeground(Color.BLACK);
                turnTimeLabel.setForeground(Color.BLACK);
    	    	player1Status.setForeground(Color.BLACK);
                player1Status.setBackground(new Color(182, 182, 182));
                player1Status.setBorder(BorderFactory.createLineBorder(new Color(92, 92, 92), 5));
                player2Status.setForeground(new Color(200, 0, 0));
                player2Status.setBackground(new Color(103, 0, 0));
                player2Status.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    	    }
    	    setBlackLightModeEnabled(blackLightMode);
        });

        settings.add(toggleSounds);
        settings.add(toggleBlackLightMode);
        settings.add(new JSeparator());
        settings.add(forcedJumpsBox);
        settings.add(timedTurnsBox);
        menuBar.add(settings);
        return menuBar;
    }
    
    /**
     * Method for handling our end-game pop-up. 
     * @param title - Set the title to who won the game
     * @param losingPlayer - Displays the int of player who won or lost
     * @param playAgainOption - Boolean for checking whether each player selected the play again option on the network
     */
    public void displayEndGameOptions(String title, int losingPlayer, boolean playAgainOption) {
        SoundPlayer.victorySoundEffect();
        JButton playAgain = new JButton("Play Again");
        JButton exit = new JButton("Exit");

        endGameFrame = new JFrame();
        endGameFrame.setTitle(title);
        endGameFrame.setSize(250, 75);
        endGameFrame.setLayout(new BorderLayout());
        if (playAgainOption) {
            endGameFrame.add(playAgain, BorderLayout.NORTH);
        }
        endGameFrame.add(exit, BorderLayout.SOUTH);
        endGameFrame.setLocation(400, 350);
        endGameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        endGameFrame.setResizable(false);
        endGameFrame.setVisible(true);

        playAgain.addActionListener(e -> {
            checkerBoard = new CheckerBoardPanel();

            checkerBoard.prepareGame(losingPlayer);
            gameFrame.setContentPane(checkerBoard);
            gameFrame.invalidate();
            gameFrame.validate();
            endGameFrame.dispose();
            if (networkGame && socketProtocol.isHost()) {
                socketProtocol.sendMessage(
                        Json.createObjectBuilder()
                            .add("message", "newGame")
                            .add("startingPlayer", losingPlayer)
                            .build().toString()
                );
            } else if (networkGame) {
                socketProtocol.sendMessage(
                        Json.createObjectBuilder()
                            .add("message", "acceptNewGame")
                            .build().toString()
                );
            }
        });
        exit.addActionListener(e -> {
            if (networkGame && playAgainOption) {
                socketProtocol.sendMessage(
                        Json.createObjectBuilder()
                            .add("message", "goodbye")
                            .build().toString()
                );
            }
            System.exit(0);
        });
    }

    /**
     * Whenever called, set the text of the player's remaining pieces
     */
    private void updatePlayerStatus() {
        player1Status.setText("Player 1 Pieces: " + numPlayer1Pieces);
        player2Status.setText("Player 2 Pieces: " + numPlayer2Pieces);
    }
    
    /**
     * Move the piece properly. Handles exceptions in the case of a bug.
     * @param fromY - Y coordinate of where the piece is moving from
     * @param fromX - X coordinate of where the piece is moving from
     * @param toY - Y coordinate of where the piece is moving to
     * @param toX - X coordinate of where the piece is moving to
     * @return
     */
    public Space movePiece(int fromY, int fromX, int toY, int toX) {
        if (networkGame && socketProtocol.playerNum == currentPlayer) {
            socketProtocol.sendMessage(
                    Json.createObjectBuilder()
                        .add("message", "movePiece")
                        .add("fromY", fromY)
                        .add("fromX", fromX)
                        .add("toY", toY)
                        .add("toX", toX)
                        .build().toString()
            );
        }
        if (spaces[fromY - 1][fromX - 1].getPiece() == null) {
            throw new IllegalArgumentException("There is no piece to move on that space");
        }
        if (spaces[toY - 1][toX - 1].getPiece() != null) {
            throw new IllegalArgumentException("Space already has a piece");
        }
        CheckerPiece piece = spaces[fromY - 1][fromX - 1].removePiece();
        Space newSpace = spaces[toY - 1][toX - 1];
        newSpace.setPiece(piece);
        SoundPlayer.moveSoundEffect();
        return newSpace;
    }

    /**
     * Remove the piece if needs to be removed.
     * Ticks down the count of the players pieces remaining.
     * @param fromY - Remove the piece at this Y
     * @param fromX - Remove the piece at this X
     */
    public void removePiece(int fromY, int fromX) {
        if (networkGame && socketProtocol.playerNum == currentPlayer) {
            socketProtocol.sendMessage(
                    Json.createObjectBuilder()
                        .add("message", "removePiece")
                        .add("fromY", fromY)
                        .add("fromX", fromX)
                        .build().toString()
            );
        }
        CheckerPiece removedPiece = spaces[fromY - 1][fromX - 1].removePiece();
        if (removedPiece.getPlayer() == 1) {
            numPlayer1Pieces--;
        } else {
            numPlayer2Pieces--;
        }
        updatePlayerStatus();
    }
    
    /**
     * Handles changing the player correctly. 
     * Handles the network aspect of changing the player.
     * Updates the status label correctly as to whose turn it is. 
     * @param turnTimeExpired - Boolean checking if the turn time expired or not
     */
    public void changePlayer(boolean turnTimeExpired) {
        if (networkGame && socketProtocol.playerNum == currentPlayer) {
            socketProtocol.sendMessage(
                    Json.createObjectBuilder()
                        .add("message", "changePlayer")
                        .add("turnTimeExpired", turnTimeExpired)
                        .build().toString()
            );
        }
        checkGameOver();
        if (!gameOver) {
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            if (!turnTimeExpired) {
                statusLabel.setText("Player " + currentPlayer + "\'s turn!");
            } else {
                statusLabel.setText("Turn time expired. Player " + currentPlayer + "\'s turn!");
            }
            Space[] movesForPlayer = Moves.findAllMovesForPlayer(currentPlayer);
            if (movesForPlayer.length == 0) {
                gameOver = true;
                displayEndGameOptions("Player " + currentPlayer + " Forfeits.", currentPlayer, true);
                timer.stopTimer();
            }
            timer.startTurn();
        }
    }
    
    /**
     * Check whether the game is over or not.
     * If the game is over, then display who has won the game.
     */
    private void checkGameOver() {
        if (numPlayer1Pieces == 0) {
            String title = "Player 2 Wins!";
            statusLabel.setText(title);
            gameOver = true;
            if (!networkGame || socketProtocol.isHost()) {
                displayEndGameOptions(title, 1, true);
            }
            timer.stopTimer();
        }
        if (numPlayer2Pieces == 0) {
            String title = "Player 1 Wins!";
            statusLabel.setText(title);
            gameOver = true;
            if (!networkGame || socketProtocol.isHost()) {
                displayEndGameOptions(title, 2, true);
            }
            timer.stopTimer();
        }
    }
}