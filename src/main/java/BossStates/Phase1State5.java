package BossStates;

import Entities.Boss;
import PrefabPattern.BaseSpawner;
import ProjectiesLogic.*;
import SpriteLoader.BulletType;
import Utils.TimedAction;
import Utils.Utility;
import javafx.scene.canvas.GraphicsContext;


import java.util.List;

public class Phase1State5 implements BossState{

    private final double DURATION = 15;

    //SPAWNER SETTINGS
    private BaseSpawner randomspawner1;
    private BaseSpawner randomspawner2;
    private BaseSpawner bulletspawner1;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State5(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(randomspawner1,randomspawner2,bulletspawner1);

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveToRandom(5)),
                new TimedAction(1, boss -> {
                    randomspawner1.StartSpawn();
                    randomspawner2.StartSpawn();
                    bulletspawner1.StartSpawn();
                }),
                new TimedAction(5, boss -> boss.moveToRandom(5)),
                new TimedAction(10, boss -> boss.moveToRandom(5)),
                new TimedAction(14, boss -> DisableAllSpawner())







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

    public void DisableAllSpawner(){
        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();
        }
    }

    private void initSpawner(BulletPool pool){
        BulletConfig bulletConfig1 = new BulletConfig(
                List.of(BulletType.ENEMY_ORB3,
                        BulletType.ENEMY_ORB4,
                        BulletType.ENEMY_BSTAR5,
                        BulletType.ENEMY_ORB6,
                        BulletType.ENEMY_BSTAR7,
                        BulletType.ENEMY_ORB8,
                        BulletType.ENEMY_BSTAR9,
                        BulletType.ENEMY_ORB10,
                        BulletType.ENEMY_ORB11,
                        BulletType.ENEMY_ORB12,
                        BulletType.ENEMY_Star7,
                        BulletType.ENEMY_Star8,
                        BulletType.ENEMY_Star9


                ),
                200,0,0,150,5,10,false,false);


        BulletPattern bulletPattern1 = new BulletPattern(2,360);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0,60,180,0,1,.1);

        randomspawner1 = new BaseSpawner(Utility.shiftCordX(-150),Utility.shiftCordY(220),32,.5,pool,false,null,1,bulletConfig1,
                bulletPattern1,spawnerConfig1);

        randomspawner2 = new BaseSpawner(Utility.shiftCordX(150),Utility.shiftCordY(220),32,.5,pool,false,null,1,bulletConfig1,
                bulletPattern1,spawnerConfig1);

        BulletConfig bulletConfig2 = new BulletConfig(
                List.of(BulletType.ENEMY_Bullet3,
                        BulletType.ENEMY_Bullet4,
                        BulletType.ENEMY_Bullet9,
                        BulletType.ENEMY_Bullet10

                ),
                0,350,350,0,0,4,false,true);

        BulletPattern bulletPattern2 = new BulletPattern(3,45);

        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0,0,0,.25,5,1);

        bulletspawner1 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig2,
                bulletPattern2,spawnerConfig2);

    }


}