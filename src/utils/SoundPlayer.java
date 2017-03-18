package utils;

import components.CheckerBoardPanel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Utility class to manage playing sounds and sound file locations
 *
 * @author Chase Erickson
 * @author Joseph Hage
 * @author Anthony Tuel
 * 2017-03-15
 */
public abstract class SoundPlayer {

    /**
     * Sound file for when a piece turns into a king
     */
    public static void kingSoundEffect() {
        playSoundEffect("resources/sounds/king.wav");
    }

    /**
     * Sound file for when a player makes a move
     */
    public static void moveSoundEffect() {
        playSoundEffect("resources/sounds/move.wav");
    }

    /**
     * Sound file for when the game is over and a player wins
     */
    public static void victorySoundEffect() {
        playSoundEffect("resources/sounds/victory.wav");
    }

    /**
     * Check to see if sounds are enabled. If enabled, play the sound.
     *
     * @param path a String value passed in that represents the path of the sound file
     */
    private static void playSoundEffect(String path) {
        try {
        	if(CheckerBoardPanel.sounds) {
        		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
        		Clip clip = AudioSystem.getClip();
        		clip.open(ais);
        		clip.start();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
