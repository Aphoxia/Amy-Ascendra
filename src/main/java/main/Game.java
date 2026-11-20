


// EVERYTHING IS RENDER AND CREATED HERE


package main;

import Entities.BulletSpawner;
import Entities.Player;
import Projecties.BulletPool;
import Projecties.BulletType;
import Utility.Utilities;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Game extends AnimationTimer{
    //MAIN GAME CLASS INIT ALL BOSS PLAYER AND STUFF HERE
    private static final double TARGET_UPS = Utilities.UPS;
    private static final double TARGET_RENDER_FPS = Utilities.FPS;
    private static final double FIXED_DT = 1.0 / TARGET_UPS;
    private static final double RENDER_DT = 1.0 / TARGET_RENDER_FPS;
    private static final double MAX_FRAME_TIME = 0.25;
    private final Canvas canvas;
    private final GraphicsContext gc;

    private Player player; //There only 1 player lol
    private BulletSpawner bulletSpawner;
    private BulletPool bulletPool;


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
        player = new Player((float) Utilities.HORIZONTAL_RESOLUTION / 2 , (float) Utilities.VERTICAL_RESOLUTION * 6 / 8 , 15 , .5f);
        bulletPool = new BulletPool(8192);
        bulletSpawner = new BulletSpawner(bulletPool);
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
        bulletPool.renderAll(gc);

    }

    //region Test 1 Para
    private double testSpawnTimer1 = 0.0;
    private double testSpawnTimer2 = 0.0;
    private double testSpawnInterval1 = 0.35; // seconds between bursts (frequency = ~2.86 bursts/sec)
    private double testSpawnInterval2 = 0.5;
    private double ringStartAngle = 0.0;
    //endregion

    private void update(double dt) {

        player.update(dt);


        testSpawnTimer1 -= dt;
        if (testSpawnTimer1 <= 0.0) {

            bulletSpawner.spawnRing(
                    BulletType.ENEMY_ORB1,
                    Utilities.HORIZONTAL_RESOLUTION / 2,
                    0,
                    30,
                    120,
                    0 + ringStartAngle,
                    0
            );

            ringStartAngle += 25;         // rotate pattern slightly each burst
            testSpawnTimer1 += testSpawnInterval1; // keeps cadence steady even if dt is slightly off
        }

        testSpawnTimer2 -= dt;
        if (testSpawnTimer2 <= 0.0) {

            bulletSpawner.spawnRing(
                    BulletType.ENEMY_ORB2,
                    Utilities.HORIZONTAL_RESOLUTION / 2,
                    0,
                    50,
                    200,
                    0 + ringStartAngle,
                    0
            );

            ringStartAngle += 45;         // rotate pattern slightly each burst
            testSpawnTimer2 += testSpawnInterval2; // keeps cadence steady even if dt is slightly off
        }



        bulletPool.updateAll(dt);
    }

    //GETTER SETTER

    public Player getPlayer(){
        return player;
    }



}
