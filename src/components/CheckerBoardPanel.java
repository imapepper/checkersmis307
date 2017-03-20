package components;

//import eventListeners.ButtonListener;
import eventListeners.SpaceClickListener;
import main.Main;
import objects.CheckerPiece;
import utils.GUIStyles;
import utils.Moves;
import utils.SoundPlayer;
import utils.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

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

    JLabel statusLabel;
    public int currentPlayer;
    public boolean gameOver;
    public JMenuBar menuBar;

    public Space[][] getSpaces() {
        return spaces;
    }

    public void createNewBoard() {
        gameOver = false;
        numPlayer1Pieces = 12;
        numPlayer2Pieces = 12;
        Moves.forceJumpEnabled = true;
        SoundPlayer.soundsEnabled = true;

        initializeSpaces();
        setLayout(new GridBagLayout());
        initializeStatus();
        initializeBoardGUI();
        decideWhoMovesFirst();
        initializeMenu();
        Moves.findAllMovesForPlayer(currentPlayer);
    }

    public void startTimers() {
        if(timer == null) {
            timer = new GameTimer(timerLabel, turnTimeLabel);
            timer.startTimer();
            timer.startTurn();
        }
    }

    public void stopTimers() {
        if(timer != null) {
            timer.stopTimer();
        }
    }

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
                    if(i < 3) {
                        spaces[i][j].setPiece(new CheckerPiece(1));
                    } else if(i > 4) {
                        spaces[i][j].setPiece(new CheckerPiece(2));
                    }
                    if(start == 0) {
                        spaces[i][j + 1] = new Space(i + 2, j + 1, false);
                    } else {
                        spaces[i][j - 1] = new Space(i, j + 1, false);
                    }
                    index++;
                }
            }
        }
    }
    
    private void initializeStatus() {
    	statusLabel = new JLabel("Initialized board");
    	statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	add(statusLabel, createConstraints(0, 0, 8));
        timerLabel = new JLabel();
        add(timerLabel, createConstraints(1, 0, 1));
    	player1Status = new JLabel("Player 1 Pieces: " + numPlayer1Pieces);
    	add(player1Status, createConstraints(3, 0, 1));
    	player2Status = new JLabel("Player 2 Pieces: " + numPlayer2Pieces);
    	add(player2Status, createConstraints(4, 0, 1));
    	turnTimeLabel = new JLabel();
    	add(turnTimeLabel, createConstraints(2, 0, 1));
    }
    
    private void initializeBoardGUI() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Space space = spaces[i][j];
                space.addMouseListener(new SpaceClickListener());
                add(space, createConstraints(i + 1, j + 1, 1));
            }
        }
    }
    
    private void decideWhoMovesFirst() {
        int random = new Random().nextInt(2);
        if(random == 0) {
        	currentPlayer = 1;
        	statusLabel.setText("Player 1 moves first!");
        } else {
        	currentPlayer = 2;
        	statusLabel.setText("Player 2 moves first!");
        }
    }
    
    private void initializeMenu() {
    	menuBar = new JMenuBar();
    	JMenu settings = new JMenu("Settings");

    	JCheckBox toggleSounds = new JCheckBox("Sounds?");
    	JCheckBox toggleJumps = new JCheckBox("Forced Jumps?");
    	JCheckBox toggleBlackLightMode = new JCheckBox("Black light Mode?");

    	toggleSounds.setSelected(true);
    	toggleJumps.setSelected(true);
    	toggleBlackLightMode.setSelected(false);

    	toggleJumps.addActionListener(e -> {
    		JCheckBox toggle = (JCheckBox) e.getSource();
            Moves.forceJumpEnabled = toggle.isSelected();
    	});
    	toggleSounds.addActionListener(e -> {
    		JToggleButton toggle = (JToggleButton) e.getSource();
            SoundPlayer.soundsEnabled = toggle.isSelected();
    	});
    	toggleBlackLightMode.addActionListener(e -> {
    	    JToggleButton toggle = (JToggleButton) e.getSource();
    	    GUIStyles.setBlackLightModeEnabled(toggle.isSelected());
        });

        settings.add(toggleJumps);
        settings.add(toggleSounds);
        settings.add(toggleBlackLightMode);
        menuBar.add(settings);
    }
    
    private void displayEndGameOptions() {
        SoundPlayer.victorySoundEffect();
        JButton playAgain = new JButton("Play Again");
        JButton exit = new JButton("Exit");

        endGameFrame.setSize(250, 75);
        endGameFrame.setVisible(true);
        endGameFrame.setLayout(new BorderLayout());
        endGameFrame.add(playAgain, BorderLayout.NORTH);
        endGameFrame.add(exit, BorderLayout.SOUTH);
        endGameFrame.setLocation(400, 350);     
        endGameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        endGameFrame.setResizable(false);

        playAgain.addActionListener(e -> {
            CheckerBoardPanel checkerBoard = new CheckerBoardPanel();
            Main.checkerBoard = checkerBoard;
            checkerBoard.createNewBoard();
            Main.frame.setContentPane(checkerBoard);
            Main.frame.invalidate();
            Main.frame.validate();
            endGameFrame.dispose();
        });
        exit.addActionListener(e -> System.exit(0));
      } 

    private GridBagConstraints createConstraints(int gridY, int gridX, int gridWidth) {
        return new GridBagConstraints(gridX, gridY, gridWidth, 1,
                0, 0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 50, 50);
    }

    private void updatePlayerStatus() {
        player1Status.setText("Player 1 Pieces: " + numPlayer1Pieces);
        player2Status.setText("Player 2 Pieces: " + numPlayer2Pieces);
    }

    public Space movePiece(int fromY, int fromX, int toY, int toX) {
        if(spaces[fromY - 1][fromX - 1].getPiece() == null) {
            throw new IllegalArgumentException("There is no piece to move on that space");
        }
        if(spaces[toY - 1][toX - 1].getPiece() != null) {
            throw new IllegalArgumentException("Space already has a piece");
        }
        CheckerPiece piece = spaces[fromY - 1][fromX - 1].removePiece();
        Space newSpace = spaces[toY - 1][toX - 1];
        newSpace.setPiece(piece);
        SoundPlayer.moveSoundEffect();
        return newSpace;
    }

    public void removePiece(int fromY, int fromX) {
        CheckerPiece removedPiece = spaces[fromY - 1][fromX - 1].removePiece();
        if(removedPiece.getPlayer() == 1) {
            numPlayer1Pieces--;
        } else {
            numPlayer2Pieces--;
        }
        updatePlayerStatus();
    }
    
    public void changePlayer(boolean turnTimeExpired) {
        checkGameOver();
        if(!gameOver) {
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            if(!turnTimeExpired) {
                statusLabel.setText("Player " + currentPlayer + "\'s turn!");
            } else {
                statusLabel.setText("Turn time expired. Player " + currentPlayer + "\'s turn!");
            }
            Moves.findAllMovesForPlayer(currentPlayer);
            timer.startTurn();
        }
    }

    private void checkGameOver() {
        if(numPlayer1Pieces == 0) {
            statusLabel.setText("Player 2 Wins!");
            endGameFrame = new JFrame();
            endGameFrame.setTitle("Player 2 Wins!");
            gameOver = true;
            displayEndGameOptions();
            timer.stopTimer();
        }
        if(numPlayer2Pieces == 0) {
            statusLabel.setText("Player 1 Wins!");
            endGameFrame = new JFrame();
            endGameFrame.setTitle("Player 1 Wins!");
            gameOver = true;
            displayEndGameOptions();
            timer.stopTimer();
        }
    }
}