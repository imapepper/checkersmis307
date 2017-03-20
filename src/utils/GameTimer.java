package utils;


import main.Main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

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
	private boolean countUp;
	private Timer timer;

	public GameTimer(JLabel label, String timerLabel, boolean countUp) {
	    this.countUp = countUp;
        ActionListener timerListener;
		if(countUp) {
            timerListener = e -> {
                SimpleDateFormat df = new SimpleDateFormat("mm:ss");
                label.setText(timerLabel + ": " + df.format(System.currentTimeMillis() - startTime));
            };
        } else {
            timerListener = e -> {
                SimpleDateFormat df = new SimpleDateFormat("ss");
                long timeRemaining = 30000 - (System.currentTimeMillis() - startTime);
                if(timeRemaining <= 0) {
                    Main.checkerBoard.changePlayer(true);
                } else {
                    label.setText(timerLabel + ": " + df.format(timeRemaining));
                }
            };
        }
 		timer = new Timer(1000, timerListener);
	}

	public void startTimer() {
        startTime = System.currentTimeMillis();
	    if(!countUp) {
            if(startTime % 1000 != 0) {
                startTime += 1000;
            }
	        startTime = (startTime / 1000 * 1000) + (startTime % 1000);
        }
		timer.start();
	}

	public void endTimer() {
		timer.stop();
	}
}
