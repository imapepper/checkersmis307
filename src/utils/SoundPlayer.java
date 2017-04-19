package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

import static utils.GameVariables.soundsEnabled;

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
        playSoundEffect(SoundPlayer.class.getClass().getResource("/sounds/king.wav"));
    }

    /**
     * Sound file for when a player makes a move
     */
    public static void moveSoundEffect() {
        playSoundEffect(SoundPlayer.class.getClass().getResource("/sounds/move.wav"));
    }

    /**
     * Sound file for when the game is over and a player wins
     */
    public static void victorySoundEffect() {
        playSoundEffect(SoundPlayer.class.getClass().getResource("/sounds/victory.wav"));
    }

    /**
     * Check to see if sounds are enabled. If enabled, play the sound.
     *
     * @param path a String value passed in that represents the path of the sound file
     */
    private static void playSoundEffect(URL path) {
        try {
        	if(soundsEnabled) {
        		AudioInputStream ais = AudioSystem.getAudioInputStream(path);
        		Clip clip = AudioSystem.getClip();
        		clip.open(ais);
        		clip.start();
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
