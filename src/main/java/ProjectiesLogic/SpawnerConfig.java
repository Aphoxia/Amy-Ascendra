package ProjectiesLogic;

public class SpawnerConfig {
    private double baseAngle; //SPAWN AT THIS ANGLE IN DEGREE ( 0 = RIGHT , 90 = DOWN , 180 = LEFT , 270 = UP )
    private double spinSpeed; //SPIN THE BASE ANGLE
    private double spawnRandomAngle;

    private double burstInterval; // TIME BETWEEN INTERVAL
    private int shotsPerBurst;  // ? PER BURST
    private double cooldownTime;

    public SpawnerConfig(
            double baseAngle
            ,double spinSpeed
            ,double spawnRandomAngle

            , double burstInterval
            , int shotsPerBurst
            , double cooldownTime){

        this.spinSpeed = spinSpeed;
        this.baseAngle = baseAngle;
        this.spawnRandomAngle = spawnRandomAngle;

        this.burstInterval = burstInterval;
        this.shotsPerBurst = shotsPerBurst;
        this.cooldownTime = cooldownTime;
    }

    public double getBaseAngle() {
        return baseAngle;
    }

    public double getSpawnRandomAngle() {
        return spawnRandomAngle;
    }

    public double getCooldownTime() {
        return cooldownTime;
    }

    public double getSpinSpeed() {
        return spinSpeed;
    }

    public int getShotsPerBurst() {
        return shotsPerBurst;
    }

    public double getBurstInterval() {
        return burstInterval;
    }


}
