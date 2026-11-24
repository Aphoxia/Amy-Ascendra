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
    private BaseSpawner ringspawner;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State1(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner);

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(250), 1, Interpolator.EASE_OUT)),

                new TimedAction(1.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), .5, Interpolator.EASE_OUT);
                    ringspawner.StartSpawn();
                }),
                new TimedAction(2.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,0)), Utility.shiftCordY(Utility.RandomDouble(0,200)), 1, Interpolator.EASE_OUT)),



                new TimedAction(4.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), 1, Interpolator.EASE_OUT)),



                new TimedAction(6.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), 1, Interpolator.EASE_OUT)),



                new TimedAction(8.5, boss -> boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(0,200)), 1, Interpolator.EASE_OUT))



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

        ringspawner.setX(boss.getx());
        ringspawner.setY(boss.gety());


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
                180,120,120,0,0,10,false,false);

        BulletPattern bulletPattern = new BulletPattern(12,360);
        SpawnerConfig spawnerConfig = new SpawnerConfig(0,30,0,0,1,.5);

        ringspawner = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig,
                bulletPattern,spawnerConfig);
    }


}