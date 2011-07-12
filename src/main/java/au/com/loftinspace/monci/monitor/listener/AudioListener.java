package au.com.loftinspace.monci.monitor.listener;

import au.com.loftinspace.monci.monitor.event.UpdateEvent;
import au.com.loftinspace.monci.monitor.event.UpdateEventNotificationException;
import au.com.loftinspace.monci.domain.BuildResult;

import javax.sound.sampled.*;
import java.io.IOException;

public class AudioListener implements UpdateEventListener {
    private static final String SUCCESS_SOUND_FILENAME = "/sound/success.wav";
    private static final String FAILURE_SOUND_FILENAME = "/sound/failure.wav";
    private Clip clip;

    public AudioListener() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        this.clip = AudioSystem.getClip();
    }

    public void notifyInitialBuild(UpdateEvent event) throws UpdateEventNotificationException {
    }

    public void notifyRebuiltStatus(UpdateEvent event) throws UpdateEventNotificationException {
        playSoundBasedOnOverallStatus(event);
    }

    public void notifyUnchangedStatus(UpdateEvent event) throws UpdateEventNotificationException {
    }

    public void notifyChangedStatus(UpdateEvent event) throws UpdateEventNotificationException {
        playSoundBasedOnOverallStatus(event);
    }

    public void notifyBuildAborted(UpdateEvent event) throws UpdateEventNotificationException {
    }

    private void playSoundBasedOnOverallStatus(UpdateEvent event) throws UpdateEventNotificationException {
        if (event instanceof UpdateEvent) {
            BuildResult result = ((UpdateEvent) event).getOverallBuildResult();
            try {
                switch (result) {
                    case FAILURE:
                        play(FAILURE_SOUND_FILENAME);
                        break;
                    case SUCCESS:
                        play(SUCCESS_SOUND_FILENAME);
                        break;
                }
            } catch (Exception e) {
                throw new UpdateEventNotificationException(e);
            }
        }
    }

    @SuppressWarnings({"EmptyCatchBlock"})
    private void play(String resourcePath) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream sound = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(resourcePath));
        clip.open(sound);
        clip.start();
        while (clip.isRunning()) {
            try {
                Thread.sleep(50l);
            } catch (InterruptedException e) {
            }
        }
        clip.close();
    }
}
