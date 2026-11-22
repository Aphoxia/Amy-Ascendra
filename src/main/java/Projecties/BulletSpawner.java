package Projecties;

import Entities.Player;
import Pattern.BulletPattern;
import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.Collection;
import java.util.List;

public class BulletSpawner{
//SPAWNER SETTINGS
private final BulletPool bulletPool;
        private boolean isActive;
        private double x, y;
        private double vx , vy;
        private double currentSpeed;
        private double acceleration;

        private double baseAngle;
        private double spinSpeed;




        //PATTERN SETTINGS
        private BulletPattern bpattern;

        //BULLET SETTINGS
        private BulletConfig bConfig;

        //TIMER
        public double burstInterval = 0.05; // Time between shots in a burst
        public int shotsPerBurst = 5;       // How many volleys per burst
        public double cooldownTime = 1.0;
        public double timer;

        //COUNTER
        private int shotsFired;
        private boolean inCooldown;

        //TEST FOR NOW
        URL resource =  ClassLoader.getSystemResource("Sound/ShootingSound/StarShoot1.wav");
        AudioClip clip = new AudioClip(resource.toExternalForm());

        public  BulletSpawner(
                BulletPool bulletPool

                , double x
                , double y

                , double Speed
                , double acceleration

                , double baseAngle
                ,double spinSpeed

                , double burstInterval
                , int shotsPerBurst
                , double cooldownTime

                , BulletConfig bConfig
                , BulletPattern bPattern){



            this.bulletPool = bulletPool;

            this.x = x;
            this.y = y;

            this.currentSpeed = Speed;
            this.acceleration = acceleration;

            this.spinSpeed = spinSpeed;
            this.baseAngle = baseAngle;

            this.burstInterval = burstInterval;
            this.shotsPerBurst = shotsPerBurst;
            this.cooldownTime = cooldownTime;

            this.bConfig = bConfig;
            this.bpattern = bPattern;

            timer = 0;
            shotsFired = 0;
            inCooldown = false;
            isActive = true;

        }

        public void update(double dt) {
            if (!isActive) return;
            timer += dt;
            baseAngle += spinSpeed * dt;

            if (inCooldown) {
                if (timer >= cooldownTime) {
                    timer -= cooldownTime;
                    inCooldown = false;
                    shotsFired = 0;
                }
                return;
            }

            while (timer >= burstInterval && !inCooldown) {
                timer -= burstInterval;
                fire();
                shotsFired++;

                if (shotsFired >= shotsPerBurst) {
                    inCooldown = true;
                    timer = 0.0; //RESET
                }
            }
        }



        private void fire() {
            //System.out.println(baseAngle);

            double spreadAngle = bpattern.getSpreadAngle();
            int bulletCount = bpattern.getBulletCount();

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

                double angle = bulletCount == 1
                        ? baseAngle
                        : startAngle + step * i;

                b.reset(bConfig.getRandomType(), x , y , bConfig.getRandomSpeed() ,angle, bConfig.getAcceleration(), bConfig.getAngularVelocity(),
                        bConfig.isPlayerBullet(),bConfig.getLifetime());

                //TEST AUDIO CLIP
                clip.setVolume(.5);
                clip.play();

            }
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void setBaseAngle(double baseAngle){
            this.baseAngle = baseAngle;
        }

        public void setActive(boolean active){
            this.isActive = active;
        }
}
