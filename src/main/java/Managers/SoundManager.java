package Managers;

import Sound.SoundBus;
import Sound.SoundId;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

public final class SoundManager {
    public static final SoundManager instance = new SoundManager();

    private final Map<SoundId, AudioClip> clips = new EnumMap<>(SoundId.class);
    private final Map<SoundId, Long> lastPlayed = new EnumMap<>(SoundId.class);
    private final Map<SoundBus, Double> busVolumes = new EnumMap<>(SoundBus.class);

    private SoundManager() {
        for (SoundBus bus : SoundBus.values()) {
            busVolumes.put(bus, 1.0);
        }
    }

    // Call once during game setup, after JavaFX starts.
    public void load() {
        for (SoundId id : SoundId.values()) {
            URL resource = SoundManager.class.getResource("/" + id.path);

            if (resource == null) {
                throw new IllegalStateException("Sound resource not found: " + id.path);
            }

            clips.put(id, new AudioClip(resource.toExternalForm()));
        }
    }

    // Returns false if the sound is still inside its cooldown.
    public boolean play(SoundId id) {
        AudioClip clip = clips.get(id);
        if (clip == null) {
            throw new IllegalStateException("SoundManager.load() has not loaded: " + id);
        }

        long now = System.nanoTime();
        Long previous = lastPlayed.get(id);
        long intervalNanos = id.minimumIntervalMs * 1_000_000L;

        if (previous != null && now - previous < intervalNanos) {
            return false;
        }

        lastPlayed.put(id, now);
        double volume = busVolumes.get(id.bus) * id.volume;
        clip.play(volume);
        return true;
    }

    public void setBusVolume(SoundBus bus, double volume) {
        busVolumes.put(bus, Math.max(0.0, Math.min(1.0, volume)));
    }

    private MediaPlayer musicPlayer;

    public void playMusic(String path) {
        URL resource = SoundManager.class.getResource("/" + path);

        if (resource == null) {
            throw new IllegalArgumentException("Music resource not found: " + path);
        }

        // Stop and release the previous track if changing music.
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.dispose();
        }

        Media media = new Media(resource.toExternalForm());
        musicPlayer = new MediaPlayer(media);


        musicPlayer.setOnError(() ->
                System.err.println("Player error: " + musicPlayer.getError())
        );
        musicPlayer.setOnPlaying(() ->
                System.out.println("Player started")
        );


        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.setVolume(busVolumes.get(SoundBus.MUSIC));
        musicPlayer.play();
        System.out.println("MMUSIC PLAY SUCCESSFULLY!!!!!!");
    }

    public void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}