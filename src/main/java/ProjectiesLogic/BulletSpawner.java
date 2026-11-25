package ProjectiesLogic;

import Entities.Player;
import Managers.SoundManager;
import Sound.SoundId;
import Utils.Utility;
import javafx.scene.media.AudioClip;
import java.net.URL;

public class BulletSpawner{

        //SPAWNER SETTINGS
        private final BulletPool bulletPool;

        private boolean isActive;
        private double x, y; //SPAWNER POS

        //SPAWNER BEHAVIOR :>
        private double baseAngle; //SPAWN AT THIS ANGLE IN DEGREE ( 0 = RIGHT , 90 = DOWN , 180 = LEFT , 270 = UP )
        private double spinSpeed; //SPIN THE BASE ANGLE

        private double spawnRandomAngle;

        //PATTERN SETTINGS
        private BulletPattern bPattern;

        //BULLET SETTINGS
        private BulletConfig bConfig;

        //TIMER
        public double burstInterval; // TIME BETWEEN INTERVAL
        public int shotsPerBurst;       // ? PER BURST
        public double cooldownTime;
        public double elapse; //OBJECT INSIDE TIMER

        //COUNTER
        private int shotsFired;
        private boolean inCooldown;

        private SoundId soundId;

        //TEST FOR NOW

        public  BulletSpawner(
                BulletPool bulletPool

                , double x
                , double y

                , SpawnerConfig spawnerConfig
                , BulletConfig bConfig
                , BulletPattern bPattern){



            this.bulletPool = bulletPool;

            this.x = x;
            this.y = y;

            this.spinSpeed = spawnerConfig.getSpinSpeed();
            this.baseAngle = spawnerConfig.getBaseAngle();
            this.spawnRandomAngle = spawnerConfig.getSpawnRandomAngle();

            this.burstInterval = spawnerConfig.getBurstInterval();
            this.shotsPerBurst = spawnerConfig.getShotsPerBurst();
            this.cooldownTime = spawnerConfig.getCooldownTime();

            this.bConfig = bConfig;
            this.bPattern = bPattern;

            elapse = 0; //time gone by since reset. Incase I forgot
            shotsFired = 0;
            inCooldown = false;
            isActive = true;

            soundId = SoundId.STAR_SHOT;


        }

        public void update(double dt) {
            if (!isActive) return;
            elapse += dt;
            baseAngle += spinSpeed * dt;

            if (inCooldown) {
                if (elapse >= cooldownTime) {
                    elapse -= cooldownTime;
                    inCooldown = false;
                    shotsFired = 0;
                }
                return;
            }

            while (elapse >= burstInterval && !inCooldown) {
                elapse -= burstInterval;
                fire();
                shotsFired++;

                if (shotsFired >= shotsPerBurst) {
                    inCooldown = true;
                    elapse = 0.0; //HOW THIS WORK IS THAT EVERYTIME ITS INCOOLDOWN RESET ELAPSE
                }
            }
        }



        private void fire() {

            double spreadAngle = bPattern.getSpreadAngle();
            int bulletCount = bPattern.getBulletCount();

            if (bConfig.isLockon()){
                baseAngle =  (Math.toDegrees(Math.atan2(Player.getInstance().gety() - y, Player.getInstance().getx() - x)));
            }

            double startAngle = baseAngle - (spreadAngle / 2.0);
            double step = (bulletCount > 1) ? spreadAngle / (bulletCount - 1) : 0;

            if (Math.abs(spreadAngle - 360) < 0.001) {
                step = spreadAngle / bulletCount;
            }

            for (int i = 0; i < bulletCount; i++) {
                Bullet b = bulletPool.obtain();
                if (b == null) return;

                double angle = bulletCount == 1 ? baseAngle : startAngle + step * i;

                if (spawnRandomAngle > 0){
                    angle += Utility.RandomDouble(-spawnRandomAngle , spawnRandomAngle);
                }

                b.reset(bConfig.getRandomType(),bConfig.getVisualSpinSpeed(), x , y , bConfig.getRandomSpeed() ,angle, bConfig.getAcceleration(), bConfig.getAngularVelocity(),
                        bConfig.isPlayerBullet(),bConfig.getLifetime());

                //TEST AUDIO CLIP
                if (soundId != null){
                    SoundManager.instance.play(soundId);
                }

            }
        }

        //RESET FUNC
        public void start(){
            if (isActive) return;

            isActive = true;
            elapse = 0;
            shotsFired = 0;
            inCooldown = false;
        }

        public void stop(){
            isActive = false;

        }

        public BulletConfig getbConfig(){
            return bConfig;
        }

        public void setbConfig(BulletConfig bconfig){
            if (bconfig == null) return;
            this.bConfig = bconfig;
        }

        public BulletPattern getbPattern(){
            return bPattern;
        }

        public void setbPattern(BulletPattern bulletPattern){
            if (bulletPattern == null) return;
            this.bPattern = bulletPattern;
        }

        public BulletPool getBulletPool() {
            return bulletPool;
        }

        public void setActive(boolean active){
            this.isActive = active;
        }

        public boolean isActive(){
            return isActive;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getX() {
            return x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getY() {
            return y;
        }

        public void setBaseAngle(double baseAngle){
            this.baseAngle = baseAngle;
        }

        public double getBaseAngle(){
            return baseAngle;
        }

        public double getSpawnRandomAngle() {
            return spawnRandomAngle;
        }

        public void setSpawnRandomAngle(double spawnRandomAngle){
            this.spawnRandomAngle = spawnRandomAngle;
        }

        public void setSpinSpeed(double spinSpeed){
                this.spinSpeed = spinSpeed;
        }

        public double getSpinSpeed(){
            return spinSpeed;
        }

        public void setBurstInterval(double burstInterval) {
            this.burstInterval = burstInterval;
        }

        public double getBurstInterval() {
            return burstInterval;
        }

        public void setShotsPerBurst(int shotsPerBurst) {
            this.shotsPerBurst = shotsPerBurst;
        }

        public int getShotsFired() {
            return shotsFired;
        }

        public void setCooldownTime(double cooldownTime) {
            this.cooldownTime = cooldownTime;
        }

        public double getCooldownTime() {
            return cooldownTime;
        }

        public boolean isInCooldown() {
            return inCooldown;
        }

        public double getElapse() {
            return elapse;
        }

    public void setSoundId(SoundId soundId) {
        this.soundId = soundId;
    }
}
