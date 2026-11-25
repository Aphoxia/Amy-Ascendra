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

public class Phase1State6 implements BossState{

    private final double DURATION = 1.5;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner3;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State6(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner3);

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveToRandom(1.5)),

                new TimedAction(0.5, boss -> ringspawner3.StartSpawn())






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
        BulletConfig bulletConfig3 = new BulletConfig(
                List.of(BulletType.ENEMY_Bullet4,
                        BulletType.ENEMY_Bullet5,
                        BulletType.ENEMY_Bullet6),
                0,300,300,0,0,5,false,false);

        BulletPattern bulletPattern2 = new BulletPattern(15,360);
        SpawnerConfig spawnerConfig3 = new SpawnerConfig(0,120,0,.1,15,20);

        ringspawner3 = new BaseSpawner(0,0,32,.5,pool,false,null,1,bulletConfig3,
                bulletPattern2,spawnerConfig3);

    }


}
