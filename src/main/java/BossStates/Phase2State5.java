package BossStates;

import Entities.Boss;
import Entities.Player;
import PrefabPattern.*;
import ProjectiesLogic.*;
import SpriteLoader.BulletType;
import SpriteLoader.SpriteLoader;
import Utils.TimedAction;
import Utils.Utility;
import javafx.animation.Interpolator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import System.CollisionSystem;


import java.util.List;

public class Phase2State5 implements BossState{

    private final double DURATION = 20;

    //SPAWNER SETTINGS
    private BaseSpawner areaSpawner1;
    private BaseSpawner edge1;
    private BaseSpawner edge2;
    private BaseSpawner ringspawner1;
    private BaseSpawner Star1;


    private List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase2State5(BulletPool pool) {

        initSpawner(pool);



        actions = List.of(
                new TimedAction(0.0, boss -> {
                    boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(0), 1, Interpolator.EASE_BOTH);
                }),
                new TimedAction(1.0, boss -> {
                    areaSpawner1.StartSpawn();
                }),
                new TimedAction(1.5, boss -> {
                    edge1.StartSpawn();
                    edge2.StartSpawn();
                    ringspawner1.StartSpawn();
                }),
                new TimedAction(14, boss -> {
                    Star1.StartSpawn();
                }),
                new TimedAction(18, boss -> {
                    DisableAllSpawner();
                    boss.moveToRandom(1.5);
                })


        );
    }
    @Override
    public void enter(Boss boss) {
        elapsed = 0;
        nextAction = 0;

        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();
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

        edge1.setX(Utility.shiftCordX(Utility.RandomDouble(-270,270)));
        edge2.setX(Utility.shiftCordX(Utility.RandomDouble(-270,270)));


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

    public void DisableAllObject(){
        for (BaseSpawner spawner : spawners) {
            spawner.StopSpawn();
        }
    }


    private void initSpawner(BulletPool pool){

        Image image = SpriteLoader.loadSprite("Images/Bullets/BulletSpriteSheet.png",32,32,13,0);

        BulletConfig config1 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet4,
                        BulletType.ENEMY_Bullet5,
                        BulletType.ENEMY_Bullet6),
                0,400,400,0,80,3,false,false
        );
        BulletPattern pattern1 = new BulletPattern( 6, 360);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0 , 10 , 0 , 0, 1, .05);

        areaSpawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(0), 64 , .5 , pool,false ,null , 270, config1 , pattern1, spawnerConfig1);

        BulletConfig config2 = new BulletConfig(
                List.of( BulletType.ENEMY_BSTAR10,
                        BulletType.ENEMY_BSTAR11,
                        BulletType.ENEMY_BSTAR12,
                        BulletType.ENEMY_Star10,
                        BulletType.ENEMY_Star11,
                        BulletType.ENEMY_Star12),

                150,40,100,30,0,8,false,false);

        BulletPattern pattern2 = new BulletPattern( 1, 0);

        SpawnerConfig spawnerConfig2 = new SpawnerConfig(90 , 0 , 0 , 0, 1, .35);
        SpawnerConfig spawnerConfig3 = new SpawnerConfig(270 , 0 , 0 , 0, 1, .35);

        edge1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(370), 0 , .5 , pool,false ,null , 0, config2 , pattern2, spawnerConfig2);
        edge2 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(-370), 0 , .5 , pool,false ,null , 0, config2 , pattern2, spawnerConfig3);


        BulletConfig config3 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet8,
                        BulletType.ENEMY_Bullet9,
                        BulletType.ENEMY_Bullet10),
                0,70,70,0,10,20,false,false);
        BulletPattern pattern3 = new BulletPattern( 25, 360);
        SpawnerConfig spawnerConfig4 = new SpawnerConfig(270 , 10 , 0 , 0, 1, 2);

        ringspawner1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(0), 0 , .5 , pool,false ,null , 0, config3 , pattern3, spawnerConfig4);
        BulletConfig config4 = new BulletConfig(
                List.of( BulletType.ENEMY_Bullet11,
                        BulletType.ENEMY_Bullet12,
                        BulletType.ENEMY_Bullet13),
                0,0,0,200,10,5,false,false);

        BulletPattern pattern4 = new BulletPattern( 4, 360);

        SpawnerConfig spawnerConfig5 = new SpawnerConfig(90 , 150 , 0 , 0, 1, .10);
        Star1 = new SpeedSpawner((Utility.shiftCordX(-300)),Utility.shiftCordY(390), 64 , .5 , pool,true ,image , 450, config4 , pattern4, spawnerConfig5,270,360);



        spawners = List.of(areaSpawner1,edge1,edge2,ringspawner1,Star1);

    }


}
