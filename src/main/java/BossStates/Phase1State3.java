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

public class Phase1State3 implements BossState{

    private final double DURATION = 15;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner1;
    private BaseSpawner ringspawner2;
    private BaseSpawner fanspawner1;
    private BaseSpawner fanspawner2;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State3(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner1,ringspawner2,fanspawner1,fanspawner2);

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(250), 1, Interpolator.EASE_BOTH)),
                new TimedAction(0.0, boss -> {
                    fanspawner1.StartSpawn();
                    fanspawner2.StartSpawn();
                }),

                new TimedAction(2, boss -> boss.moveTo(Utility.shiftCordX(200), Utility.shiftCordY(210), 5, Interpolator.EASE_BOTH)),

                new TimedAction(2.5, boss -> {
                    ringspawner1.StartSpawn();
                    ringspawner2.StartSpawn();
                }),
                new TimedAction(9, boss -> boss.moveTo(Utility.shiftCordX(-100), Utility.shiftCordY(180), 5, Interpolator.EASE_BOTH))






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

        ringspawner1.setX(boss.getx());
        ringspawner1.setY(boss.gety());

        ringspawner2.setX(boss.getx());
        ringspawner2.setY(boss.gety());


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
                List.of(BulletType.ENEMY_BSTAR7,
                        BulletType.ENEMY_BSTAR6),
                180,100,150,0,10,10,false,false);

        BulletConfig bulletConfig2 = new BulletConfig(
                List.of(BulletType.ENEMY_Star7,
                        BulletType.ENEMY_Star6),
                360,300,300,0,0,10,false,false);

        BulletConfig bulletConfig3 = new BulletConfig(
                List.of(BulletType.ENEMY_Star1,
                        BulletType.ENEMY_Star2,
                        BulletType.ENEMY_Star3),
                360,200,200,0,0,10,false,true);

        BulletPattern bulletPattern1 = new BulletPattern(10,360);
        BulletPattern bulletPattern2 = new BulletPattern(10,360);
        BulletPattern bulletPattern3 = new BulletPattern(3,25);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0,60,0,0,1,3);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0,30,0,0,1,1);
        SpawnerConfig spawnerConfig3 = new SpawnerConfig(0,0,0,.5,3,1);

        ringspawner1 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig1,
                bulletPattern1,spawnerConfig1);

        ringspawner2 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig2,
                bulletPattern2,spawnerConfig2);

        fanspawner1 = new BaseSpawner(Utility.shiftCordX(-270),Utility.shiftCordY(340),32,.5,pool,false,null,1,bulletConfig3,
                bulletPattern3,spawnerConfig3);

        fanspawner2 = new BaseSpawner(Utility.shiftCordX(270),Utility.shiftCordY(340),32,.5,pool,false,null,1,bulletConfig3,
                bulletPattern3,spawnerConfig3);
    }


}