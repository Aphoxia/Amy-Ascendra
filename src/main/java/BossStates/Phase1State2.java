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

public class Phase1State2 implements BossState{

    private final double DURATION = 12;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner1;
    private BaseSpawner ringspawner2;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State2(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner1,ringspawner2);

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(250), .5, Interpolator.EASE_OUT)),

                new TimedAction(1, boss -> {
                    ringspawner1.StartSpawn();
                    ringspawner2.StartSpawn();
                }),
                new TimedAction(5, boss -> boss.moveToRandom(5))





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
        BulletConfig bulletConfig1 = new BulletConfig(
                List.of(BulletType.ENEMY_BSTAR7,
                        BulletType.ENEMY_BSTAR6),
                180,120,120,0,0,10,false,false);

        BulletConfig bulletConfig2 = new BulletConfig(
                List.of(BulletType.ENEMY_Star7,
                        BulletType.ENEMY_Star6),
                360,200,200,0,25,10,false,false);

        BulletPattern bulletPattern = new BulletPattern(20,360);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0,30,0,0,1,1);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0,30,0,0,1,1);

        ringspawner1 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig1,
                bulletPattern,spawnerConfig1);

        ringspawner2 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig2,
                bulletPattern,spawnerConfig2);
    }


}