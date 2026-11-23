package System;

import Entities.Boss;
import Entities.Entity;
import Entities.Player;
import Managers.GameManager;
import ProjectiesLogic.Bullet;
import ProjectiesLogic.BulletPool;

import static Utils.Settings.DEBUG_PLAYER_HIT;

public class CollisionSystem {

    public static boolean checkCollisionCircle(
            double x1,
            double y1,
            double radius1,
            double x2,
            double y2,
            double radius2)
    {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double combinedRadius = radius1 + radius2;
        return dx * dx + dy * dy <= combinedRadius * combinedRadius;
    }


    public static void CheckPlayerHit(BulletPool bulletPool , Entity enitity){
        for (Bullet b : bulletPool.getActiveBullets()){
            if (!b.isActive()) continue;

            if (checkCollisionCircle(enitity.getx() , enitity.gety(), (enitity.getVisualSize() * enitity.getHitboxPercentage() / 2) ,b.getX() , b.getY() , b.getHitboxSize() / 2)){
                //System.out.println(enitity.getVisualSize() * enitity.getHitboxPercentage() + "," + b.getHitboxSize());
                GameManager.getInstance().getScreenShake().trigger(.25 , 12);
                b.setActive(false);
            }

            if (DEBUG_PLAYER_HIT){
                System.out.println("PLAYER HOT HIT");
            }

        }
    }

    public static void CheckBossHit(BulletPool bulletPool, Boss boss) {
        for (Bullet b : bulletPool.getActiveBullets()) {
            if (!b.isActive()) continue;

            if (checkCollisionCircle(boss.getx(), boss.gety(), (boss.getVisualSize() * boss.getHitboxPercentage() / 2), b.getX(), b.getY(), b.getHitboxSize() / 2)) {
                b.setActive(false);
                boss.setHp(boss.getHp() - 20);
                System.out.println(boss.getHp());
            }


        }


    }
}



