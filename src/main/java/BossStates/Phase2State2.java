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

public class Phase2State2 implements BossState{

    private final double DURATION = 15;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner1;
    private BaseSpawner ringspawner2;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase2State2(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner1,ringspawner2);

        actions = List.of(
                new TimedAction(0.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(250), 1, Interpolator.EASE_BOTH);
                }),
                new TimedAction( 2 , boss -> {
                    ringspawner1.StartSpawn();
                    ringspawner2.StartSpawn();
                })


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
        BulletConfig config1 = new BulletConfig(
                List.of( BulletType.ENEMY_Star4,
                        BulletType.ENEMY_Star5,
                        BulletType.ENEMY_Star6,
                        BulletType.ENEMY_Star7,
                        BulletType.ENEMY_Star8),
                360,75,135,0,0,10,false,false
        );

        BulletConfig config2 = new BulletConfig(
                List.of( BulletType.ENEMY_BSTAR1,
                        BulletType.ENEMY_BSTAR2,
                        BulletType.ENEMY_BSTAR3,
                        BulletType.ENEMY_BSTAR4,
                        BulletType.ENEMY_BSTAR5),
                360,10,10,60,0,10,false,false
        );

        BulletPattern pattern1 = new BulletPattern( 25, 360);
        BulletPattern pattern2 = new BulletPattern( 6, 360);
        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0 , 240 , 0 , 0, 1, 1);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0 , 80 , 0 , 0, 1, .15);

        ringspawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(150), 60 , .5 , pool,false , null , 0, config1 , pattern1 , spawnerConfig1);
        ringspawner2 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(150), 60 , .5 , pool,false , null , 0, config2 , pattern2, spawnerConfig2);
    }


}