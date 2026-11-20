


// EVERYTHING IS RENDER AND CREATED HERE


package main;

import Projecties.BulletSpawner;
import Entities.Player;
import Projecties.BulletPool;
import Projecties.BulletType;
import Utils.Settings;
import Utils.Utility;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    private BulletSpawner testBulletSpawner;
    private BulletSpawner testBulletSpawner2;
    private BulletPool testBulletPool;
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

    public Game(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        initClasses();
    }

    private void initClasses(){
        playerbulletPool = new BulletPool(1024);
        player = new Player((float) Settings.HORIZONTAL_RESOLUTION / 2 , (float) Settings.VERTICAL_RESOLUTION * 6 / 8 , 16 , .5f , playerbulletPool);





        testBulletPool = new BulletPool(8192);
        testBulletSpawner = new BulletSpawner(testBulletPool,640, 100 ,0,0,90
        ,10,12,360,100,20,.1,1,.5,40 ,BulletType.ENEMY_ORB1);
        testBulletSpawner2 = new BulletSpawner(testBulletPool,0 ,0 ,0,0,0
                ,0,3,15,150,0,.2,3,1, 50 ,BulletType.ENEMY_ORB2);
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

        gc.setImageSmoothing(false); //FOR PIXEL ART
        gc.setFill(Color.rgb(0,0,0));
        gc.fillRect(0, 0, width, height);

        player.render(gc);
        playerbulletPool.renderAll(gc);
        testBulletPool.renderAll(gc);

    }


    private void update(double dt) {

        player.update(dt);
        playerbulletPool.updateAll(dt);




        testBulletPool.updateAll(dt);
        testBulletSpawner.update(dt);
        testBulletSpawner2.update(dt);
        double rad = Math.atan2(player.gety() , player.getx()) ;
        System.out.println(player.gety() + " " + player.getx() + " " + rad);
        testBulletSpawner2.setBaseAngle(Math.toDegrees(rad));
    }

    //GETTER SETTER

    public Player getPlayer(){
        return player;
    }



}
