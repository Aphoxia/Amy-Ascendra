package BossStates;

import Entities.Boss;
import Managers.GameManager;
import Managers.SoundManager;
import PrefabPattern.BaseSpawner;
import ProjectiesLogic.*;
import Sound.SoundId;
import SpriteLoader.BulletType;
import Utils.TimedAction;
import Utils.Utility;
import javafx.animation.Interpolator;
import javafx.scene.canvas.GraphicsContext;


import java.util.List;

public class Phase3State2 implements BossState{

    private final double DURATION = 20;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner1;
    private BaseSpawner ringspawner2;
    private BaseSpawner ringspawner3;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase3State2(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner1,ringspawner2,ringspawner3);

        actions = List.of(
                new TimedAction(0.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(0), 1, Interpolator.EASE_BOTH);
                    boss.getbossbulletPool().clearAll();
                    GameManager.getInstance().getScreenShake().trigger(.25 , 18);
                    SoundManager.instance.play(SoundId.BOSSCLEARSCREEN);
                }),
                new TimedAction( 1.5 , boss -> {
                    ringspawner1.StartSpawn();
                }),
                new TimedAction( 2 , boss -> {
                    ringspawner3.StartSpawn();
                    ringspawner2.StartSpawn();
                }),

                new TimedAction( 15 , boss -> {
                    ringspawner1.StopSpawn();
                }),
                new TimedAction( 18 , boss -> {
                    ringspawner2.StopSpawn();
                    ringspawner3.StopSpawn();
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
                        BulletType.ENEMY_BSTAR6,
                        BulletType.ENEMY_Star7,
                        BulletType.ENEMY_BSTAR8,
                        BulletType.ENEMY_BSTAR9,
                        BulletType.ENEMY_Star10,
                        BulletType.ENEMY_BSTAR11,
                        BulletType.ENEMY_Star12),
                360,300,300,-100,0,6,false,false
        );
        BulletConfig config2 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet4,
                        BulletType.ENEMY_Bullet5,
                        BulletType.ENEMY_Bullet6,
                        BulletType.ENEMY_Bullet7,
                        BulletType.ENEMY_Bullet8,
                        BulletType.ENEMY_Bullet9,
                        BulletType.ENEMY_Bullet10,
                        BulletType.ENEMY_Bullet11,
                        BulletType.ENEMY_Bullet12),
                0,70,70,0,10,10,false,false
        );

        BulletConfig config3 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet4,
                        BulletType.ENEMY_Bullet5,
                        BulletType.ENEMY_Bullet6,
                        BulletType.ENEMY_Bullet7,
                        BulletType.ENEMY_Bullet8,
                        BulletType.ENEMY_Bullet9,
                        BulletType.ENEMY_Bullet10,
                        BulletType.ENEMY_Bullet11,
                        BulletType.ENEMY_Bullet12),
                0,70,70,0,-10,10,false,false
        );


        BulletPattern pattern1 = new BulletPattern( 6, 360);
        BulletPattern pattern2 = new BulletPattern( 30, 360);
        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0 , 30 , 0 , 0, 1, .1);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0 , 0 , 0 , 0, 1, 1.5);
        ringspawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config1 , pattern1 , spawnerConfig1);
        ringspawner2 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config2 , pattern2 , spawnerConfig2);
        ringspawner3 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config3 , pattern2 , spawnerConfig2);
    }


}