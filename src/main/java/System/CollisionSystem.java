package System;

import Entities.Boss;
import Entities.Entity;
import Entities.Player;
import Managers.GameManager;
import Managers.SoundManager;
import PrefabPattern.BaseSpawner;
import ProjectiesLogic.Bullet;
import ProjectiesLogic.BulletPool;
import Sound.SoundId;

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


    public static void CheckPlayerHit(BulletPool bulletPool , Player player){
        for (Bullet b : bulletPool.getActiveBullets()){
            if (!b.isActive()) continue;

            if (checkCollisionCircle(player.getx() , player.gety(), (player.getVisualSize() * player.getHitboxPercentage() / 2) ,b.getX() , b.getY() , b.getHitboxSize() / 2)
            && player.getInvincibleElapse() <= 0.001){
                //System.out.println(enitity.getVisualSize() * enitity.getHitboxPercentage() + "," + b.getHitboxSize());
                GameManager.getInstance().getScreenShake().trigger(.25 , 18);
                b.setActive(false);

                player.setHitpoint(player.getHitpoint() - 1);
                player.resetInvincibleElapse();
                SoundManager.instance.play(SoundId.PLAYER_HIT);

                if (DEBUG_PLAYER_HIT){
                    System.out.println("PLAYER GOT HIT");
                    System.out.println(player.getHitpoint());
                }

            }



        }
    }

    public static void CheckBossHit(BulletPool bulletPool, Boss boss) {
        for (Bullet b : bulletPool.getActiveBullets()) {
            if (!b.isActive()) continue;

            if (checkCollisionCircle(boss.getx(), boss.gety(), (boss.getVisualSize() * boss.getHitboxPercentage() / 2), b.getX(), b.getY(), b.getHitboxSize() / 2)) {
                b.setActive(false);
                boss.setHp(boss.getHp() - 20);
                //System.out.println(boss.getHp());
            }


        }


    }

    public static void CheckPlayerHitObject(BaseSpawner baseSpawner, Player player){
        if (!baseSpawner.isCanHit() || !baseSpawner.isActive()) return;
        if (checkCollisionCircle(player.getx() , player.gety(), (player.getVisualSize() * player.getHitboxPercentage() / 2) ,baseSpawner.getx() , baseSpawner.gety() , baseSpawner.getVisualSize() * baseSpawner.getHitboxPercentage() / 2) && player.getInvincibleElapse() <= 0.001){
            player.setHitpoint(player.getHitpoint() - 1);
            player.resetInvincibleElapse();
            GameManager.getInstance().getScreenShake().trigger(.25 , 18);
            SoundManager.instance.play(SoundId.PLAYER_HIT);
            if (DEBUG_PLAYER_HIT){
                System.out.println("PLAYER GOT HIT");
                System.out.println(player.getHitpoint());
            }
        }
    }
}



