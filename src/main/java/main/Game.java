


// EVERYTHING IS RENDER AND CREATED HERE


package main;

import Managers.GameManager;
import ProjectieEmitter.SpinPortal;
import ProjectieEmitter.TriFanPortal;
import Projecties.BulletSpawner;
import Entities.Player;
import Projecties.BulletPool;
import System.CollisionSystem;
import UI.BackGround;
import UI.Border;
import Utils.Settings;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
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

    private TriFanPortal test1;
    private SpinPortal test2;
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

    public Game(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        initClasses();
    }

    private void initClasses(){

        playerbulletPool = new BulletPool(1024);
        player = new Player((float) Settings.HORIZONTAL_RESOLUTION / 2 , (float) Settings.VERTICAL_RESOLUTION * 6 / 8 , 16 , .5 ,playerbulletPool);





        testBulletPool = new BulletPool(8192);
        test1 = new TriFanPortal(0 , 0 , 60 , testBulletPool);
        test2 = new SpinPortal(650 , 200, 60 ,testBulletPool);

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
        playerbulletPool.renderAll(gc);
        testBulletPool.renderAll(gc);
        canvas.setEffect(bloom);

        gc.restore();

        //UI
        renderUI(gc);

//        test1.Disable();
//        test2.Disable();


    }


    private void update(double dt) {


        background.update(dt);



        //MANAGER
        GameManager.getInstance().update(dt);

        player.update(dt);
        playerbulletPool.updateAll(dt);


        //TEST 1

        test1.update(dt);
        test2.update(dt);
        testBulletPool.updateAll(dt);
//        testBulletSpawner.update(dt);
//        testBulletSpawner2.update(dt);
//        double rad = Math.atan2(player.gety() , player.getx()) ;
//        //System.out.println(player.gety() + " " + player.getx() + " " + rad);
//        testBulletSpawner2.setBaseAngle(Math.toDegrees(rad));

        //TEST 2
        CollisionSystem.CheckBullet(testBulletPool , player);
        test1.Disable();

    }

    //GETTER SETTER

    public Player getPlayer(){
        return player;
    }

    private void renderUI(GraphicsContext gc){
        border.render(gc);
    }



}
