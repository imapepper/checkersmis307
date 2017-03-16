package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public abstract class SoundPlayer {

    public static void kingSoundEffect() {
        playSoundEffect("resources/sounds/king.wav");
    }

    public static void moveSoundEffect() {
        playSoundEffect("resources/sounds/move.wav");
    }

    public static void victorySoundEffect() {
        playSoundEffect("resources/sounds/victory.wav");
    }

    private static void playSoundEffect(String path) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
