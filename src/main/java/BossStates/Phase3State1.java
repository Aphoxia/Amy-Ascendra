package BossStates;

import Entities.Boss;
import Entities.Player;
import Managers.SoundManager;
import PrefabPattern.BaseSpawner;
import PrefabPattern.BoundSpawner;
import ProjectiesLogic.*;
import Sound.SoundId;
import SpriteLoader.BulletType;
import SpriteLoader.SpriteLoader;
import Utils.TimedAction;
import Utils.Utility;
import javafx.animation.Interpolator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import System.CollisionSystem;
import javafx.scene.paint.Color;
import System.ParticleSystem;


import java.util.List;

public class Phase3State1 implements BossState{

    private final double DURATION = 25;

    //SPAWNER SETTINGS
    private BaseSpawner edge1;
    private BaseSpawner edge2;
    private BaseSpawner edge3;

    private BaseSpawner StarSpawner1;
    private BaseSpawner StarSpawner2;
    private BaseSpawner StarSpawner3;
    private BaseSpawner StarSpawner4;
    private BaseSpawner StarSpawner5;
    private BaseSpawner StarSpawner6;
    private BaseSpawner StarSpawner7;
    private BaseSpawner StarSpawner8;
    private BaseSpawner StarSpawner9;
    private BaseSpawner StarSpawner10;
    private BaseSpawner StarSpawner11;
    private BaseSpawner StarSpawner12;
    private BaseSpawner StarSpawner13;
    private BaseSpawner StarSpawner14;
    private BaseSpawner StarSpawner15;
    private BaseSpawner StarSpawner16;
    private BaseSpawner StarSpawner17;
    private BaseSpawner StarSpawner18;
    private BaseSpawner StarSpawner19;
    private BaseSpawner StarSpawner20;
    private BaseSpawner StarSpawner21;
    private BaseSpawner StarSpawner22;
    private BaseSpawner StarSpawner23;
    private BaseSpawner StarSpawner24;
    private BaseSpawner StarSpawner25;
    private BaseSpawner StarSpawner26;
    private BaseSpawner StarSpawner27;
    private BaseSpawner StarSpawner28;
    private BaseSpawner StarSpawner29;
    private BaseSpawner StarSpawner30;
    private BaseSpawner StarSpawner31;
    private BaseSpawner StarSpawner32;


    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase3State1(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(edge1,edge2,edge3,StarSpawner1,StarSpawner2,StarSpawner3,StarSpawner4,StarSpawner5,StarSpawner6,StarSpawner8,
                StarSpawner9,StarSpawner10,StarSpawner11,StarSpawner12,StarSpawner13,StarSpawner14,StarSpawner16,StarSpawner17,StarSpawner18,StarSpawner19,
                StarSpawner20,StarSpawner21,StarSpawner22,StarSpawner24,StarSpawner25,StarSpawner26,StarSpawner27,StarSpawner28,StarSpawner29,StarSpawner30
                ,StarSpawner32);

        actions = List.of(
                new TimedAction(0.0, boss -> {

                    boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(220), 2, Interpolator.EASE_BOTH);

                }),
                new TimedAction( 2 , boss -> {
                    edge1.StartSpawn();
                    edge2.StartSpawn();
                    edge3.StartSpawn();

                }),
                new TimedAction( 3 , boss -> {
                    StarSpawner1.StartSpawn();
                    StarSpawner2.StartSpawn();
                    StarSpawner3.StartSpawn();
                    StarSpawner4.StartSpawn();
                    StarSpawner5.StartSpawn();
                    StarSpawner6.StartSpawn();
                    //StarSpawner7.StartSpawn();
                    StarSpawner8.StartSpawn();

                }),
                new TimedAction( 4 , boss -> {
                    StarSpawner9.StartSpawn();
                    StarSpawner10.StartSpawn();
                    StarSpawner11.StartSpawn();
                    StarSpawner12.StartSpawn();
                    StarSpawner13.StartSpawn();
                    StarSpawner14.StartSpawn();
                    //StarSpawner15.StartSpawn();
                    StarSpawner16.StartSpawn();

                }),
                new TimedAction( 5 , boss -> {
                    //StarSpawner23.StartSpawn();
                    StarSpawner17.StartSpawn();
                    StarSpawner18.StartSpawn();
                    StarSpawner19.StartSpawn();
                    StarSpawner20.StartSpawn();
                    StarSpawner21.StartSpawn();
                    StarSpawner22.StartSpawn();
                    StarSpawner24.StartSpawn();

                }),
                new TimedAction( 6 , boss -> {
                    StarSpawner32.StartSpawn();
                    StarSpawner25.StartSpawn();
                    StarSpawner26.StartSpawn();
                    StarSpawner27.StartSpawn();
                    StarSpawner28.StartSpawn();
                    StarSpawner29.StartSpawn();
                    StarSpawner30.StartSpawn();
                    //StarSpawner31.StartSpawn();

                }),
                new TimedAction( 23 , boss -> {
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
            if (spawner instanceof BoundSpawner boundSpawner){
                boundSpawner.ResetMove();
            }
        }
    }

    public void render(GraphicsContext gc){
        for (BaseSpawner spawner : spawners) {
            spawner.render(gc);
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
            CollisionSystem.CheckPlayerHitObject(spawner , Player.getInstance());


        }
        edge1.setX(Utility.shiftCordX(Utility.RandomDouble(-270,270)));
        edge2.setX(Utility.shiftCordX(Utility.RandomDouble(-270,270)));
        edge3.setX(Utility.shiftCordX(Utility.RandomDouble(-270,270)));


        return elapsed >= DURATION;
    }

    @Override
    public void exit(Boss boss) {
        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();

        }
    }

    public void DisableAllSpawner(){
        for (BaseSpawner spawner : spawners) {
            spawner.StopObject();
        }
    }


    private void initSpawner(BulletPool pool){
        BulletConfig config1 = new BulletConfig(
                List.of( BulletType.ENEMY_Star1,
                        BulletType.ENEMY_Star2,
                        BulletType.ENEMY_Star3,
                        BulletType.ENEMY_Star4,
                        BulletType.ENEMY_Star5,
                        BulletType.ENEMY_Star6,
                        BulletType.ENEMY_Star7,
                        BulletType.ENEMY_Star8,
                        BulletType.ENEMY_Star9,
                        BulletType.ENEMY_Star10,
                        BulletType.ENEMY_Star11
                        ),
                360,100,100,30,0,8,false,false
        );


        BulletPattern pattern1 = new BulletPattern( 1, 0);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(90 , 0 , 10 , 0, 1, .3);

        edge1 = new BaseSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(360), 60 , .5 , pool,false , null , 0, config1 , pattern1 , spawnerConfig1);
        edge2 = new BaseSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(360), 60 , .5 , pool,false , null , 0, config1 , pattern1, spawnerConfig1);
        edge3 = new BaseSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(360), 60 , .5 , pool,false , null , 0, config1 , pattern1, spawnerConfig1);


        BulletConfig config2 = new BulletConfig(
                List.of( BulletType.ENEMY_Star6),
                180,75,75,0,0,30,false,true
        );

        BulletPattern pattern2 = new BulletPattern( 1, 360);
        SpawnerConfig spawnerConfig3 = new SpawnerConfig(0 , 30 , 0 , 0, 1, 6);

        Image image = SpriteLoader.loadSprite("Images/Bullets/BulletSpriteSheet.png",32,32,13,0);
        double vy = 200;
        double Size=  32;
        StarSpawner1 = new BoundSpawner(Utility.shiftCordX(-180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner2 = new BoundSpawner(Utility.shiftCordX(-90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner3 = new BoundSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner4 = new BoundSpawner(Utility.shiftCordX(90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner5 = new BoundSpawner(Utility.shiftCordX(180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner6 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image, 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner8 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);

        StarSpawner9 = new BoundSpawner(Utility.shiftCordX(-180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner10 = new BoundSpawner(Utility.shiftCordX(-90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner11 = new BoundSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner12 = new BoundSpawner(Utility.shiftCordX(90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner13 = new BoundSpawner(Utility.shiftCordX(180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner14 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image, 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner16 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);

        StarSpawner17 = new BoundSpawner(Utility.shiftCordX(-180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner18 = new BoundSpawner(Utility.shiftCordX(-90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner19 = new BoundSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner20 = new BoundSpawner(Utility.shiftCordX(90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner21 = new BoundSpawner(Utility.shiftCordX(180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner22 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image, 120, config2 , pattern2, spawnerConfig3,-60,vy);
        StarSpawner24 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,-60,vy);

        StarSpawner25 = new BoundSpawner(Utility.shiftCordX(-180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner26 = new BoundSpawner(Utility.shiftCordX(-90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner27 = new BoundSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner28 = new BoundSpawner(Utility.shiftCordX(90) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner29 = new BoundSpawner(Utility.shiftCordX(180) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner30 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image, 120, config2 , pattern2, spawnerConfig3,60,vy);
        StarSpawner32 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(380), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,60,vy);
    }


}