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

public class Phase1State1 implements BossState{

    private final double DURATION = 10;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner1;
    private BaseSpawner ringspawner2;
    private BaseSpawner ringspawner3;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State1(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner1,ringspawner2,ringspawner3);

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(250), 1, Interpolator.EASE_BOTH)),

                new TimedAction(1.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), .5, Interpolator.EASE_BOTH);
                    ringspawner1.StartSpawn();
                }),
                new TimedAction(2.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,0)), Utility.shiftCordY(Utility.RandomDouble(0,200)), .5, Interpolator.EASE_BOTH)),

                new TimedAction(3.0, boss -> ringspawner2.StartSpawn()),
                new TimedAction(3.5, boss -> ringspawner3.StartSpawn()),


                new TimedAction(4.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), .5, Interpolator.EASE_BOTH)),



                new TimedAction(6.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), .5, Interpolator.EASE_BOTH)),



                new TimedAction(8.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), .5, Interpolator.EASE_BOTH))



        );

    }
    @Override
    public void enter(Boss boss) {
        elapsed = 0;
        nextAction = 0;

        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();
        }
    }

    public void render(GraphicsContext gc){
        for (BaseSpawner spawner : spawners) {
            spawner.render(gc);
        }
    }

    @Override
    public boolean update(Boss boss, double dt) {

        ringspawner1.setX(boss.getx());
        ringspawner1.setY(boss.gety());
        ringspawner2.setX(boss.getx());
        ringspawner2.setY(boss.gety());
        ringspawner3.setX(boss.getx());
        ringspawner3.setY(boss.gety());



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

    private void initSpawner(BulletPool pool){
        BulletConfig bulletConfig = new BulletConfig(
                List.of(BulletType.ENEMY_Star4,
                        BulletType.ENEMY_Star5,
                        BulletType.ENEMY_Star6,
                        BulletType.ENEMY_BSTAR4,
                        BulletType.ENEMY_BSTAR5,
                        BulletType.ENEMY_BSTAR6),
                180,120,150,0,0,6,false,false);

        BulletConfig bulletConfig2 = new BulletConfig(
                List.of(BulletType.ENEMY_Star7,
                        BulletType.ENEMY_Star8,
                        BulletType.ENEMY_Star9),
                360,180,200,0,10,6,false,false);

        BulletConfig bulletConfig3 = new BulletConfig(
                List.of(BulletType.ENEMY_Star10,
                        BulletType.ENEMY_Star11,
                        BulletType.ENEMY_Star12),
                360,200,240,0,20,6,false,false);

        BulletPattern bulletPattern = new BulletPattern(6,360);
        BulletPattern bulletPattern2 = new BulletPattern(12,360);
        SpawnerConfig spawnerConfig = new SpawnerConfig(0,30,0,0,1,.5);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0,360,0,.25,3,2);

        ringspawner1 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig,
                bulletPattern,spawnerConfig);
        ringspawner2 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig2,
                bulletPattern2,spawnerConfig2);
        ringspawner3 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig3,
                bulletPattern2,spawnerConfig2);
    }


}