


// EVERYTHING IS RENDER AND CREATED HERE


package main;

import Entities.Boss;
import Managers.GameManager;
import PrefabPattern.BaseSpawner;
import ProjectiesLogic.*;
import Entities.Player;
import SpriteLoader.BulletType;
import System.CollisionSystem;
import UI.BackGround;
import UI.Border;
import Utils.Settings;
import Utils.Utility;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;

import java.util.List;

public class Game extends AnimationTimer{
    //MAIN GAME CLASS INIT ALL BOSS PLAYER AND STUFF HERE
    private static final double TARGET_UPS = Settings.UPS;
    private static final double TARGET_RENDER_FPS = Settings.FPS;
    private static final double FIXED_DT = 1.0 / TARGET_UPS;
    private static final double RENDER_DT = 1.0 / TARGET_RENDER_FPS;
    private static final double MAX_FRAME_TIME = 0.25;
    private final Canvas canvas;
    private final GraphicsContext gc;

    private Player player; //There only 1 player lol

    //region TEST
    private BulletPool testBulletPool = new BulletPool(8192);;

    private BaseSpawner test1;
    private BaseSpawner test2;
    //endregion

    private BulletSpawner playerbulletSpawner;
    private BulletPool playerbulletPool;


    //FPS STUFF
    private long previousNanoTime = 0;
    private double accumulator = 0.0;
    private double renderAccumulator = 0.0;

    private long fpsTimerNano = 0;
    private int framesThisSecond = 0;
    private int updatesThisSecond = 0;

    private Bloom bloom = new Bloom(.5);

    //GRAPHIC
    private BackGround background = new BackGround();
    private Border border = new Border();

    //ENTITIES
    private Boss boss = new Boss(Utility.shiftCordX(0), Utility.shiftCordY(800) , 1.25, 32, testBulletPool);

    public Game(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        initClasses();
    }

    private void initClasses(){

        playerbulletPool = new BulletPool(1024);
        player = new Player((float) Settings.HORIZONTAL_RESOLUTION / 2 , (float) Settings.VERTICAL_RESOLUTION * 6 / 8 , 16 , .5 ,playerbulletPool);






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

        test1 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(0), 60 , .5 , testBulletPool,false , null , 0, config1 , pattern1 , spawnerConfig1);
        test2 = new BaseSpawner(Utility.shiftCordX(0) ,Utility.shiftCordY(0), 60 , .5 , testBulletPool,false , null , 0, config2 , pattern2, spawnerConfig2);

    }


    //JAVAFX AUTO CALL THS FOR SOME REASON
    @Override
    public void handle(long now) {
        if (previousNanoTime == 0) {
            previousNanoTime = now;
            fpsTimerNano = now;
            return;
        }

        double frameTime = (now - previousNanoTime) / 1_000_000_000.0;
        previousNanoTime = now;

        if (frameTime > MAX_FRAME_TIME) {
            frameTime = MAX_FRAME_TIME;
        }

        //FOR UPS
        accumulator += frameTime;
        while (accumulator >= FIXED_DT) {
            update(FIXED_DT);
            accumulator -= FIXED_DT;
            updatesThisSecond++;
        }

        //FOR FPS
        renderAccumulator += frameTime;
        if (renderAccumulator >= RENDER_DT) {
            render();
            framesThisSecond++;
            renderAccumulator %= RENDER_DT;
        }

        if (now - fpsTimerNano >= 1_000_000_000L) {
            //TEST
            //System.out.println("FPS: " + framesThisSecond + " | UPS: " + updatesThisSecond);
            framesThisSecond = 0;
            updatesThisSecond = 0;
            fpsTimerNano = now;
        }

    }
    @Override
    public void stop() {
        //STOP THE GAME ( FOR SOME REASON I NEED TO IMPLEMENT THIS )
        super.stop();
        previousNanoTime = 0;
        accumulator = 0.0;
    }

    private void render() {
        double width = canvas.getWidth();
        double height = canvas.getHeight();


        gc.save();
        gc.setImageSmoothing(false); //FOR PIXEL ART
        gc.setFill(Color.rgb(72,86,125));
        gc.fillRect(0, 0, width, height);

        gc.translate(GameManager.getInstance().getScreenShake().getX(), (GameManager.getInstance().getScreenShake().getY()));

        background.render(gc);

        player.render(gc);
        boss.render(gc);

        playerbulletPool.renderAll(gc);
        testBulletPool.renderAll(gc);

        canvas.setEffect(bloom);

        gc.restore();

        //UI
        renderUI(gc);


    }


    private void update(double dt) {


        background.update(dt);



        //MANAGER
        GameManager.getInstance().update(dt);

        player.update(dt);
        boss.update(dt);
        playerbulletPool.updateAll(dt);


        //TEST 1

        //test1.update(dt);
        //test2.update(dt);
        testBulletPool.updateAll(dt);
//        testBulletSpawner.update(dt);
//        testBulletSpawner2.update(dt);
//        double rad = Math.atan2(player.gety() , player.getx()) ;
//        //System.out.println(player.gety() + " " + player.getx() + " " + rad);
//        testBulletSpawner2.setBaseAngle(Math.toDegrees(rad));

        //TEST 2
        CollisionSystem.CheckPlayerHit(testBulletPool , player);

        CollisionSystem.CheckBossHit(playerbulletPool,boss);


    }

    //GETTER SETTER


    private void renderUI(GraphicsContext gc){
        border.render(gc);
    }



}
