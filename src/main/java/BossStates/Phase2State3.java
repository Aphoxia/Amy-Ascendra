package BossStates;

import Entities.Boss;
import Entities.Player;
import Managers.SoundManager;
import PrefabPattern.BaseSpawner;
import PrefabPattern.BounceSpawner;
import PrefabPattern.BoundSpawner;
import PrefabPattern.IResetMove;
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


import java.util.List;

public class Phase2State3 implements BossState{

    private final double DURATION = 15;

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

    private BounceSpawner bouncespawner1;
    private BounceSpawner bouncespawner2;


    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase2State3(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(StarSpawner1,StarSpawner2,StarSpawner3,StarSpawner4
                ,StarSpawner5,StarSpawner6,StarSpawner7,StarSpawner8 , bouncespawner1,bouncespawner2);

        actions = List.of(
                new TimedAction(0.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(220), 2, Interpolator.EASE_BOTH);
                }),


                new TimedAction( .5 , boss -> {
                    StarSpawner1.StartObject();
                    StarSpawner2.StartObject();
                    StarSpawner3.StartObject();
                    StarSpawner4.StartObject();

                }),
                new TimedAction( 1 , boss -> {

                    StarSpawner5.StartObject();
                    StarSpawner6.StartObject();
                    StarSpawner7.StartObject();
                    StarSpawner8.StartObject();

                }),

        new TimedAction( 1.5 , boss -> {
            bouncespawner1.StartSpawn();
            SoundManager.instance.play(SoundId.SPAWNSFX1);

            boss.moveToRandom(1.5);

            }),
                new TimedAction( 2.5 , boss -> {
                    StarSpawner1.StartSpawn();
                    StarSpawner2.StartSpawn();
                    StarSpawner3.StartSpawn();
                    StarSpawner4.StartSpawn();

                    StarSpawner5.StartSpawn();
                    StarSpawner6.StartSpawn();
                    StarSpawner7.StartSpawn();
                    StarSpawner8.StartSpawn();
                }),
                new TimedAction( 3 , boss -> {
                    bouncespawner2.StartObject();
                    SoundManager.instance.play(SoundId.SPAWNSFX1);

                }),
                new TimedAction( 3 , boss -> {
                    bouncespawner2.StartSpawn();
                }),
                new TimedAction( 4.5 , boss -> {
                    bouncespawner1.StartSpawn();
                    boss.moveToRandom(1.5);}),
                new TimedAction( 5.5 , boss -> {
                    bouncespawner1.StartSpawn();
                    boss.moveToRandom(1.5);}),
                new TimedAction( 5.5 , boss -> {
                    bouncespawner1.StartSpawn();
                    boss.moveToRandom(3.5);}),
                new TimedAction( 13 , boss -> DAllSpawner())
        );
    }
    @Override
    public void enter(Boss boss) {
        elapsed = 0;
        nextAction = 0;

        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();
            spawner.StopObject();
            if (spawner instanceof IResetMove iResetMove){
                iResetMove.ResetMove();
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

    public void DAllSpawner(){
        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();
        }
    }


    private void initSpawner(BulletPool pool){


        BulletConfig config2 = new BulletConfig(
                List.of( BulletType.ENEMY_Star7,BulletType.ENEMY_Star10,BulletType.ENEMY_Star11,BulletType.ENEMY_Star12),
                380,125,125,0,0,10,false,false
        );

        BulletPattern pattern2 = new BulletPattern( 1, 60);
        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0 , 0 , 0 , 0, 1, 2);
        SpawnerConfig spawnerConfig2 = new SpawnerConfig(90 , 0 , 0 , 0, 1, 2);
        SpawnerConfig spawnerConfig3 = new SpawnerConfig(180 , 0 , 0 , 0, 1, 2);
        SpawnerConfig spawnerConfig4 = new SpawnerConfig(270 , 0 , 0 , 0, 1, 2);

        Image image = SpriteLoader.loadSprite("Images/Bullets/BulletSpriteSheet.png",32,32,13,0);
        double vy = 200;
        double Size=  32;
        StarSpawner1 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(-360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig4,90,0);
        StarSpawner2 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig2,-90,0);
        StarSpawner3 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig1,0,90);
        StarSpawner4 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(-360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,0,-90);

        StarSpawner5 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(-360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig4,-90,0);
        StarSpawner6 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig2,90,0);
        StarSpawner7 = new BoundSpawner(Utility.shiftCordX(-270) ,Utility.shiftCordY(-360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig1,0,-90);
        StarSpawner8 = new BoundSpawner(Utility.shiftCordX(270) ,Utility.shiftCordY(360), Size , .5 , pool,true ,image , 120, config2 , pattern2, spawnerConfig3,0,90);

        BulletConfig config1 = new BulletConfig(
                List.of( BulletType.ENEMY_BSTAR4,BulletType.ENEMY_ORB5,BulletType.ENEMY_BSTAR6,
                        BulletType.ENEMY_ORB7,BulletType.ENEMY_BSTAR10,BulletType.ENEMY_ORB9),
                380,150,200,0,5,5,false,false
        );

        BulletConfig config4 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet4,BulletType.ENEMY_Bullet5,BulletType.ENEMY_Bullet6,
                        BulletType.ENEMY_Bullet7,BulletType.ENEMY_Bullet10,BulletType.ENEMY_Bullet9),
                0,200,400,0,0,5,false,false
        );

        BulletPattern pattern1 = new BulletPattern( 10, 360);
        BulletPattern pattern3 = new BulletPattern( 3, 360);
        SpawnerConfig spawnerConfig5 = new SpawnerConfig(0 , 0 , 0 , 0, 1, 2);
        SpawnerConfig spawnerConfig6 = new SpawnerConfig(0 , 240 , 0 , .1, 20, 4);

        bouncespawner1 = new BounceSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(360), 64 , .5 , pool,true ,image , 360, config1 , pattern1, spawnerConfig5,200,220);
        bouncespawner2 = new BounceSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(360), 32 , .5 , pool,true ,image , 720, config4 , pattern3, spawnerConfig6,-380,440);
    }


}
