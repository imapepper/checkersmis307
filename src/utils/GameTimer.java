package utils;


import eventListeners.SpaceClickListener;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import static utils.GameVariables.timedTurns;

/**
 * Designed to track the time elapsed during a game.
 * 
 * @author Anthony Tuel
 * @author Chase Erickson
 * @author Joey Hage
 * 2017-03-20
 */
public class GameTimer {
	private long startTime;
	private long stopTime;

    private String formattedTime;
    private Integer currentTime;
    private Integer turnStartTime;
    private JLabel timerLabel;
    private JLabel turnTimerLabel;
    private Font boldFont = new Font("Serif", Font.BOLD, 14);
	private Timer timer;

	public GameTimer(JLabel timerLabel, JLabel turnTimerLabel) {
	    this.timerLabel = timerLabel;
	    this.turnTimerLabel = turnTimerLabel;
        turnTimerLabel.setFont(boldFont);
        ActionListener timerListener;
        timerListener = e -> {
            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
            formattedTime = df.format(System.currentTimeMillis() - startTime);
            currentTime = Integer.parseInt(formattedTime.substring(0, 2)) * 60 + Integer.parseInt(formattedTime.substring(3));
            if(turnStartTime != null) {
                int turnTime = 30 - (currentTime - turnStartTime);
                if(turnTime <= 0) {
                    SpaceClickListener.changePlayer(true);
                }
                turnTimerLabel.setText("Turn Time: " + turnTime);
            }
            timerLabel.setText("Time Elapsed: " + formattedTime);
        };
 		timer = new Timer(1000, timerListener);
	}

	public void startTurn() {
	    if (timedTurns) {
            if (currentTime == null) {
                String formattedTime = new SimpleDateFormat("mm:ss").format(0);
                turnStartTime = Integer.parseInt(formattedTime.substring(0, 2)) * 60 + Integer.parseInt(formattedTime.substring(3));
            } else {
                turnStartTime = currentTime;
            }
            turnTimerLabel.setText("Turn Time: 30");
        }
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
		timer.start();
		timerLabel.setText("Time Elapsed: 00:00");
		
	}

	public void resumeTimer() {
	    startTime = System.currentTimeMillis() - (stopTime - startTime);
	    timer.start();
    }

	public void stopTimer() {
		timer.stop();
		stopTime = System.currentTimeMillis();
	}
}
