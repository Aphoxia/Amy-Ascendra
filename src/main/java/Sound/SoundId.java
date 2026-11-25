package Sound;

public enum SoundId {
    STAR_SHOT("Sound/ShootingSound/StarShoot1.wav", SoundBus.SFX, 100, 0.2),
    PLAYER_SHOT("Sound/ShootingSound/PlayerShoot.wav", SoundBus.SFX, 30,.1),
    BOSSCLEARSCREEN("Sound/Effects/BossClearScreen.wav", SoundBus.SFX, 30,.8),
    SPAWNSFX1("Sound/Effects/SpawnSFX1.wav", SoundBus.SFX, 30,.7),
    PLAYER_HIT("Sound/Effects/PlayerDamage.wav", SoundBus.SFX, 30,1);

    public final String path;
    public final SoundBus bus;
    public final long minimumIntervalMs;
    public final double volume;

    SoundId(String path, SoundBus bus, long minimumIntervalMs, double volume) {
        this.path = path;
        this.bus = bus;
        this.minimumIntervalMs = minimumIntervalMs;
        this.volume = volume;
    }
}

