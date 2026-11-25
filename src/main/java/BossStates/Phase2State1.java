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

public class Phase2State1 implements BossState{

    private final double DURATION = 14;

    //SPAWNER SETTINGS
    private BaseSpawner edge1;
    private BaseSpawner edge2;
    private BaseSpawner edge3;
    private BaseSpawner edge4;
    private BaseSpawner edge5;
    private BaseSpawner edge6;
    private BaseSpawner ringspawner1;
    private BaseSpawner bulletspawner1;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase2State1(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(edge1,edge2,edge3,edge4,edge5,edge6,ringspawner1,bulletspawner1);

        actions = List.of(
                new TimedAction(0.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(220), 1, Interpolator.EASE_BOTH);
                    boss.getbossbulletPool().clearAll();
                    GameManager.getInstance().getScreenShake().trigger(.25 , 18);
                    SoundManager.instance.play(SoundId.BOSSCLEARSCREEN);
                }),
                new TimedAction( 1.5 , boss -> {
                    edge1.StartSpawn();
                    edge2.StartSpawn();
                    edge3.StartSpawn();
                    edge4.StartSpawn();
                    edge5.StartSpawn();
                    edge6.StartSpawn();
                }),
                new TimedAction( 2.5 , boss -> {
                    ringspawner1.StartSpawn();
                }),
                new TimedAction(5, boss -> bulletspawner1.StartSpawn()),
                new TimedAction(6, boss -> {
                    boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(100,300)), 2, Interpolator.EASE_BOTH);
                }),
                new TimedAction(9, boss -> {
                    boss.moveTo(Utility.shiftCordX(Utility.RandomDouble(-200,200)), Utility.shiftCordY(Utility.RandomDouble(100,300)), 2, Interpolator.EASE_BOTH);
                }),
                new TimedAction(12, boss -> {
                    boss.moveToRandom(2);
                }),
                new TimedAction(13, boss -> {
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



        edge1.setY(Utility.shiftCordY(Utility.RandomDouble(-360,360)));
        edge2.setY(Utility.shiftCordY(Utility.RandomDouble(-360,360)));
        edge3.setY(Utility.shiftCordY(Utility.RandomDouble(-360,360)));
        edge4.setY(Utility.shiftCordY(Utility.RandomDouble(-360,360)));
        edge5.setY(Utility.shiftCordY(Utility.RandomDouble(-360,360)));
        edge6.setY(Utility.shiftCordY(Utility.RandomDouble(-360,360)));
        bulletspawner1.setX(boss.getx());
        bulletspawner1.setY(boss.gety());


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
        BulletConfig config1 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet11,
                        BulletType.ENEMY_Bullet12,
                        BulletType.ENEMY_Bullet13),
                0,0,20,30,0,15,false,false
        );


        BulletPattern pattern1 = new BulletPattern( 1, 0);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0 , 0 , 10 , 0, 1, .5);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(180 , 0 , 10 , 0, 1, .5);


        edge1 = new BaseSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config1 , pattern1 , spawnerConfig1);
        edge2 = new BaseSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config1 , pattern1, spawnerConfig1);
        edge3 = new BaseSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config1 , pattern1, spawnerConfig1);
        edge4 = new BaseSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config1 , pattern1, spawnerConfig2);
        edge5 = new BaseSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config1 , pattern1, spawnerConfig2);
        edge6 = new BaseSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(0), 60 , .5 , pool,false , null , 0, config1 , pattern1, spawnerConfig2);

        BulletConfig config2 = new BulletConfig(
                List.of( BulletType.ENEMY_Star5,
                        BulletType.ENEMY_Star6,
                        BulletType.ENEMY_Star7,
                        BulletType.ENEMY_Star8,
                        BulletType.ENEMY_Star9,
                        BulletType.ENEMY_Star10
                        ),
                180,200,200,0,10,6,false,false
        );

        BulletPattern pattern2 = new BulletPattern( 30, 360);
        SpawnerConfig spawnerConfig3 = new SpawnerConfig(90 , 75 , 0 , .2, 3, 2);

        ringspawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(360), 60 , .5 , pool,false , null , 0, config2 , pattern2, spawnerConfig3);

        BulletConfig config3 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet5,
                        BulletType.ENEMY_Bullet6,
                        BulletType.ENEMY_Bullet3,
                        BulletType.ENEMY_Bullet4,
                        BulletType.ENEMY_Bullet9,
                        BulletType.ENEMY_Bullet10
                ),
                0,350,350,-100,0,15,false,true
        );

        BulletPattern pattern3 = new BulletPattern( 1, 0);
        SpawnerConfig spawnerConfig4 = new SpawnerConfig(0 , 75 , 0 , .1, 5, 2);

        bulletspawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(360), 60 , .5 , pool,false , null , 0, config3 , pattern3, spawnerConfig4);
    }


}