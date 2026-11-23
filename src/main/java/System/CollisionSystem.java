package System;

import Entities.Boss;
import Entities.Entity;
import Entities.Player;
import Managers.GameManager;
import ProjectiesLogic.Bullet;
import ProjectiesLogic.BulletPool;
import Utils.Settings;

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


    public static void CheckBullet(BulletPool bulletPool , Entity enitity){
        for (Bullet b : bulletPool.getBullets()){
            if (!b.isActive()) continue;

            if (checkCollisionCircle(enitity.getx() , enitity.gety(), (enitity.getSize() * enitity.getHitboxPercentage()) ,b.getX() , b.getY() , b.getHitboxSize())){
            GameManager.getInstance().getScreenShake().trigger(.25 , 12);
            }

            if (DEBUG_PLAYER_HIT){
                System.out.println("PLAYER HOT HIT");
            }

        }
    }

    public static void CheckEntity(Player player , Boss boss){

    }
}



