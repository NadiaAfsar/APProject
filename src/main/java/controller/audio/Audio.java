package controller.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
    private Clip clip;
    private AudioInputStream sound;
    private static float soundValue;
    FloatControl soundControl;
    public Audio(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File music = new File(path);
        sound = AudioSystem.getAudioInputStream(music);
        clip = AudioSystem.getClip();
        clip.open(sound);
        soundControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        soundControl.setValue(soundValue);
        clip.start();
    }
    public void setRepeat() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void controlSound(float value){
        value = (float) -(80 - (0.86*value));
        soundValue = value;
        soundControl.setValue(soundValue);
    }

    public static float getSoundValue() {
        return soundValue;
    }
}
