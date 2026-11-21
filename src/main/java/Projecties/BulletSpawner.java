package Projecties;

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
        private double baseAngle ;
        private double spinSpeed ;     // SPIN THE SHOOTING DIRECTION
        private int bulletCount ;
        private double bulletLifetime;
        private double spreadAngle;   // FAN SHAPE OFFEST
        private BulletType bulletType;

        //BULLET SETTINGS
        private double bulletSpeed ;
        private double bulletAngularVel ;

        //TIMER
        public double burstInterval = 0.05; // Time between shots in a burst
        public int shotsPerBurst = 5;       // How many volleys per burst
        public double cooldownTime = 1.0;
        public double timer;
        private int shotsFired;
        private boolean inCooldown;

        public  BulletSpawner(BulletPool bulletPool ,double x ,double y , double Speed , double acceleration , double baseAngle
                ,double spinSpeed , int bulletCount , double spreadAngle , double bulletSpeed , double bulletAngularVel , double fireInterval ,
                             int shotsPerBurst , double cooldownTime , double bulletLifetime , BulletType bulletType){
            this.bulletPool = bulletPool;
            this.x = x;
            this.y = y;
            this.acceleration = acceleration;
            this.baseAngle = baseAngle;
            this.acceleration = acceleration;
            this.currentSpeed = Speed;
            this.spinSpeed = spinSpeed;
            this.bulletCount = bulletCount;
            this.spreadAngle = spreadAngle;
            this.bulletSpeed = bulletSpeed;
            this.bulletAngularVel = bulletAngularVel;
            this.burstInterval = fireInterval;
            this.shotsPerBurst = shotsPerBurst;
            this.cooldownTime = cooldownTime;
            this.bulletType = bulletType;
            this.bulletLifetime = bulletLifetime;
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
                fire(bulletPool);
                shotsFired++;

                if (shotsFired >= shotsPerBurst) {
                    inCooldown = true;
                    timer = 0.0; //RESET
                }
            }
        }



        private void fire(BulletPool bulletPool) {
            //System.out.println(baseAngle);
            double startAngle = baseAngle - (spreadAngle / 2.0);
            double step = (bulletCount > 1) ? spreadAngle / (bulletCount - 1) : 0;

            if (Math.abs(spreadAngle - 360) < 0.001) {
                step = spreadAngle / bulletCount;
            }

            for (int i = 0; i < bulletCount; i++) {
                Bullet b = bulletPool.obtain();
                if (b == null)return;
                b.reset(bulletType,x,y,bulletSpeed,startAngle + (step * i),acceleration,bulletAngularVel,
                        false,bulletLifetime);
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
