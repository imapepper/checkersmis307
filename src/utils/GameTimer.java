package utils;


import eventListeners.SpaceClickListener;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

import static utils.GameVariables.blackLightModeEnabled;
import static utils.GameVariables.timedTurns;

/**
 * Designed to track the time elapsed during a game and maintain turn 
 * countdown timer if enabled by user.
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
    private Timer timer;

    /**
     * Game timer that implements JLabel to update the label on our game frame. 
     * @param timerLabel - Label we use for the "Time elapsed" on the game frame.
     * @param turnTimerLabel - Label we use for the "Turn timer" on the game frame.
     */
	public GameTimer(JLabel timerLabel, JLabel turnTimerLabel) {
	    this.timerLabel = timerLabel;
	    this.turnTimerLabel = turnTimerLabel;
 		timer = new Timer(1000, e -> {
            SimpleDateFormat df = new SimpleDateFormat("mm:ss");
            formattedTime = df.format(System.currentTimeMillis() - startTime);
            currentTime = Integer.parseInt(formattedTime.substring(0, 2)) * 60 + Integer.parseInt(formattedTime.substring(3));
            if (turnStartTime != null) {
                int turnTime = 30 - (currentTime - turnStartTime);
                if (turnTime <= 0) {
                    SpaceClickListener.changePlayer(true);
                } else if (turnTime <= 10) {
                    if (blackLightModeEnabled) {
                        if (turnTime % 2 == 0) turnTimerLabel.setForeground(Color.YELLOW);
                        else turnTimerLabel.setForeground(Color.BLACK);
                    } else {
                        if (turnTime % 2 == 0) turnTimerLabel.setForeground(Color.RED);
                        else turnTimerLabel.setForeground(Color.WHITE);
                    }
                }
                turnTimerLabel.setText("Turn Time: " + turnTime);
            }
            timerLabel.setText("Time Elapsed: " + formattedTime);
        });
	}

	/**
	 * Checks whether timed turns is on or not.
	 * If they are, then format the time nicely for our frame.
	 * Force set the label to 30 so that it looks nice.
	 */
	public void startTurn() {
	    if (timedTurns) {
            if (currentTime == null) {
                String formattedTime = new SimpleDateFormat("mm:ss").format(0);
                turnStartTime = Integer.parseInt(formattedTime.substring(0, 2)) * 60 + Integer.parseInt(formattedTime.substring(3));
            } else {
                turnStartTime = currentTime;
            }
            turnTimerLabel.setText("Turn Time: 30");
            if (blackLightModeEnabled) turnTimerLabel.setForeground(Color.WHITE);
            else turnTimerLabel.setForeground(Color.BLACK);
        }
    }

	/**
	 * Method we use to start the timer when the game loads.
	 * Force set the timerLabel to look nice. 
	 */
    public void startTimer() {
        startTime = System.currentTimeMillis();
		timer.start();
		timerLabel.setText("Time Elapsed: 00:00");
		
	}

    /**
     * Sometimes we need to pause the timer, such as when the game is saved down.
     * In this method, we are subtracting the time at stop vs the initial game startTime
     * and then we re-start the timer.
     */
	public void resumeTimer() {
	    startTime = System.currentTimeMillis() - (stopTime - startTime);
	    timer.start();
    }

	/**
	 * Stop the timer and record the stop time.
	 */
	public void stopTimer() {
		timer.stop();
		stopTime = System.currentTimeMillis();
	}
}
