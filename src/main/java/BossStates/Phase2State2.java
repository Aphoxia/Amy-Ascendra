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

    private final double DURATION = 18;

    //SPAWNER SETTINGS
    private BaseSpawner ringspawner1;
    private BaseSpawner ringspawner2;
    private BaseSpawner ringspawner4;
    private BaseSpawner ringspawner3;

    private BaseSpawner bulletspawner1;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase2State2(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(ringspawner1,ringspawner2,ringspawner3,ringspawner4,bulletspawner1);

        actions = List.of(
                new TimedAction(0.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(250), 1, Interpolator.EASE_BOTH);
                }),
                new TimedAction( 2 , boss -> {
                    ringspawner1.StartSpawn();
                    ringspawner2.StartSpawn();

                    boss.moveToRandom(5);
                }), new TimedAction( 6 , boss -> {
                    ringspawner3.StartSpawn();
                    ringspawner4.StartSpawn();
                }),new TimedAction( 8 , boss -> {
                    boss.moveToRandom(5);
                }), new TimedAction( 14 , boss -> {
                    boss.moveToRandom(5);
                }), new TimedAction( 16 , boss -> {
                    bulletspawner1.StartSpawn();
                }),
                new TimedAction( 18 , boss -> {
                    DisableAllSpawner();
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

        bulletspawner1.setX(boss.getx());
        bulletspawner1.setY(boss.gety());
        ringspawner1.setX(boss.getx());
        ringspawner1.setY(boss.gety());
        ringspawner2.setX(boss.getx());
        ringspawner2.setY(boss.gety());
        ringspawner3.setX(boss.getx());
        ringspawner3.setY(boss.gety());
        ringspawner4.setX(boss.getx());
        ringspawner4.setY(boss.gety());



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
            spawner.StopObject();
        }
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
                360,120,300,20,0,10,false,false
        );

        BulletPattern pattern1 = new BulletPattern( 20, 360);
        BulletPattern pattern2 = new BulletPattern( 4, 360);
        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0 , 240 , 0 , 0, 1, 1);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(0 , 80 , 0 , 0, 1, .35);

        ringspawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(150), 60 , .5 , pool,false , null , 0, config1 , pattern1 , spawnerConfig1);
        ringspawner2 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(150), 60 , .5 , pool,false , null , 0, config2 , pattern2, spawnerConfig2);

        BulletConfig config3 = new BulletConfig(
                List.of( BulletType.ENEMY_Star9,
                        BulletType.ENEMY_Star10,
                        BulletType.ENEMY_Star15,
                        BulletType.ENEMY_Star14,
                        BulletType.ENEMY_Star13),
                360,150,150,0,20,10,false,false
        );
        SpawnerConfig spawnerConfig3 = new SpawnerConfig(0 , 240 , 0 , 0.1, 5, 1);
        SpawnerConfig spawnerConfig4 = new SpawnerConfig(0 , 120 , 0 , 0.15, 20, .5);
        ringspawner3= new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(150), 60 , .5 , pool,false , null , 0, config3 , pattern2, spawnerConfig3);
        ringspawner4= new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(150), 60 , .5 , pool,false , null , 0, config3 , pattern2, spawnerConfig4);

        BulletConfig config4 = new BulletConfig(
                List.of( BulletType.ENEMY_ORB5,
                        BulletType.ENEMY_ORB6,
                        BulletType.ENEMY_ORB3,
                        BulletType.ENEMY_ORB4,
                        BulletType.ENEMY_ORB9,
                        BulletType.ENEMY_ORB10
                ),
                0,250,250,0,0,10,false,true
        );

        BulletPattern pattern4 = new BulletPattern( 20, 180);
        SpawnerConfig spawnerConfig5 = new SpawnerConfig(0 , 0 , 0 , .2, 5, 10);

        bulletspawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(360), 60 , .5 , pool,false , null , 0, config4 , pattern4, spawnerConfig5);
    }


}