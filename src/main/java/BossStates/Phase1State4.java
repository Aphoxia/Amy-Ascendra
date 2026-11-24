package BossStates;

import Entities.Boss;
import PrefabPattern.BaseSpawner;
import ProjectiesLogic.*;
import SpriteLoader.BulletType;
import Utils.TimedAction;
import Utils.Utility;
import javafx.animation.Interpolator;
import javafx.scene.canvas.GraphicsContext;


import java.util.List;

public class Phase1State4 implements BossState{

    private final double DURATION = 15;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner1;
    private BaseSpawner bulletspawner1;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State4(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner1,bulletspawner1);

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveToRandom(5)),
                new TimedAction(1, boss -> ringspawner1.StartSpawn()),
                new TimedAction(1.5, boss -> bulletspawner1.StartSpawn()),
                new TimedAction(5, boss -> boss.moveToRandom(5)),
                new TimedAction(10, boss -> boss.moveToRandom(5))







        );

    }
    @Override
    public void enter(Boss boss) {
        elapsed = 0;
        nextAction = 0;

        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn(); // Reset its firing timer/counters.
        }
    }

    @Override
    public boolean update(Boss boss, double dt) {

        //SET HERE ( REMIND MYSELF )
        ringspawner1.setX(boss.getx());
        ringspawner1.setY(boss.gety());

        bulletspawner1.setX(boss.getx());
        bulletspawner1.setY(boss.gety());

        //DEFAULT

        elapsed += dt;

        while (nextAction < actions.size()
                && elapsed >= actions.get(nextAction).getTime()) {
            actions.get(nextAction++).run(boss);
        }

        for (BaseSpawner spawner : spawners) {
            spawner.update(dt);
        }

        return elapsed >= DURATION;
    }

    @Override
    public void exit(Boss boss) {
        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();
        }
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    private void initSpawner(BulletPool pool){
        BulletConfig bulletConfig1 = new BulletConfig(
                List.of(BulletType.ENEMY_ORB3,
                        BulletType.ENEMY_ORB4,
                        BulletType.ENEMY_ORB5,
                        BulletType.ENEMY_ORB6,
                        BulletType.ENEMY_BSTAR7,
                        BulletType.ENEMY_ORB8,
                        BulletType.ENEMY_BSTAR9,
                        BulletType.ENEMY_ORB10,
                        BulletType.ENEMY_ORB11,
                        BulletType.ENEMY_ORB12

                        ),
                150,20,250,30,0,20,false,false);

        BulletConfig bulletConfig2 = new BulletConfig(
                List.of(BulletType.ENEMY_Bullet5,
                        BulletType.ENEMY_Bullet6,
                        BulletType.ENEMY_Bullet7,
                        BulletType.ENEMY_Bullet8

                ),
                0,350,350,0,0,4,false,true);

        BulletPattern bulletPattern1 = new BulletPattern(20,360);
        BulletPattern bulletPattern2 = new BulletPattern(1,0);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0,60,20,0,1,1);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0,0,0,.1,10,1);
        ringspawner1 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig1,
                bulletPattern1,spawnerConfig1);

        bulletspawner1 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig2,
                bulletPattern2,spawnerConfig2);

    }


}