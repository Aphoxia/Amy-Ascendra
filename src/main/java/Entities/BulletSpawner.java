package Entities;

import Projecties.BulletPool;
import Projecties.BulletType;

public class BulletSpawner {

    private final BulletPool bulletPool;

    public BulletSpawner(BulletPool bulletPool){
        this.bulletPool = bulletPool;

    }

    //DIFFERENT SPAWN TYPE
    public void spawnAngle(BulletType type, double x, double y,double speed, double angleDeg,  double acceleration , boolean playerbullet) {
        bulletPool.obtain(type, x, y, speed, angleDeg , acceleration , playerbullet);
    }

    public void spawnRing(BulletType type, double x, double y, int count, double speed, double startAngleDeg , double acceleration , boolean playerbullet) {
        if (count <= 0) return;
        double step = (360 / count);
        for (int i = 0; i < count; i++) {
            double angle = startAngleDeg + (i * step);
            spawnAngle(type, x, y, speed , angle , acceleration , playerbullet);
        }
    }

}
