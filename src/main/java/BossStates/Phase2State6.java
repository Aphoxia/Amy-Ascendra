package BossStates;

import Entities.Boss;
import Entities.Player;
import PrefabPattern.*;
import ProjectiesLogic.*;
import SpriteLoader.BulletType;
import SpriteLoader.SpriteLoader;
import Utils.TimedAction;
import Utils.Utility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import System.CollisionSystem;


import java.util.List;

public class Phase2State6 implements BossState{

    private final double DURATION = 4;

    //SPAWNER SETTINGS
    private BaseSpawner BigStar1;
    private BaseSpawner SmallStar1;
    private BaseSpawner SmallStar2;

    private final List<BaseSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase2State6(BulletPool pool) {

        initSpawner(pool);

        spawners = List.of(BigStar1,SmallStar1,SmallStar2);

        actions = List.of(
                new TimedAction(0.0, boss -> {
                    boss.moveToRandom(.5);
                }),


                new TimedAction( .25 , boss -> {
                    BigStar1.StartSpawn();


                }),
                new TimedAction( 1.5 , boss -> {
                    SmallStar1.StartSpawn();
                    SmallStar2.StartSpawn();
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

        Image image = SpriteLoader.loadSprite("Images/Bullets/BulletSpriteSheet.png",32,32,13,0);
        double vy = 200;
        double Size=  32;

        BulletConfig config1 = new BulletConfig(
                List.of( BulletType.ENEMY_Star3,
                        BulletType.ENEMY_Star4,
                        BulletType.ENEMY_Star5,
                        BulletType.ENEMY_Star6),
                380,200,200,0,0,10,false,false
        );
        BulletPattern pattern1 = new BulletPattern( 6, 360);

        SpawnerConfig spawnerConfig1 = new SpawnerConfig(0 , 40 , 0 , 0, 1, .25);

        SmallStar1 = new SpeedSpawner(Utility.shiftCordX(-135) ,Utility.shiftCordY(370), 64 , .5 , pool,true ,image , 270, config1 , pattern1, spawnerConfig1,0,600);
        SmallStar2 = new SpeedSpawner(Utility.shiftCordX(135) ,Utility.shiftCordY(370), 64 , .5 , pool,true ,image , 270, config1 , pattern1, spawnerConfig1,0,600);

        BulletConfig config2 = new BulletConfig(
                List.of( BulletType.ENEMY_BSTAR3,
                        BulletType.ENEMY_BSTAR4,
                        BulletType.ENEMY_BSTAR5,
                        BulletType.ENEMY_BSTAR6),
                190,120,120,0,5,10,false,false
        );
        BulletPattern pattern2 = new BulletPattern( 4, 360);
        SpawnerConfig spawnerConfig2= new SpawnerConfig(0 , 120 , 0 , 0, 1, .2);

        BigStar1 = new SpeedSpawner(Utility.shiftCordX(-300) ,Utility.shiftCordY(-180), 128 , .5 , pool,true ,image , 250, config2 , pattern2, spawnerConfig2,350,0);


    }


}
