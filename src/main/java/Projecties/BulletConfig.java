package Projecties;

import java.util.List;
import java.util.Random;

import SpriteLoader.BulletType16;

public final class BulletConfig {

    private final List<BulletType16> types;

    private final double minSpeed;
    private final double maxSpeed;

    private final double acceleration;
    private final double angularVelocity;

    private final double lifetime;

    private final boolean playerBullet;
    private final boolean Lockon;







    Random random = new Random(); // RANDOM PARAMETER

    public BulletConfig(
            List<BulletType16> types,
            double minSpeed,
            double maxSpeed,
            double acceleration,
            double angularVelocity,
            double lifetime,
            boolean playerBullet,
            boolean lockON
    ) {
        this.types = List.copyOf(types);
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.angularVelocity = angularVelocity;
        this.lifetime = lifetime;
        this.playerBullet = playerBullet;
        this.Lockon = lockON;
    }

    public List<BulletType16> getTypes() {
        return types;
    }

    public BulletType16 getRandomType() {
        return types.get(random.nextInt(types.size()));
    }

    public double getRandomSpeed() {
        return minSpeed + random.nextDouble() * (maxSpeed - minSpeed);
    }

    public double getAcceleration() { return acceleration; }
    public double getAngularVelocity() { return angularVelocity; }
    public double getLifetime() { return lifetime; }
    public boolean isPlayerBullet() { return playerBullet; }
    public boolean isLockon() { return Lockon;
    }
}