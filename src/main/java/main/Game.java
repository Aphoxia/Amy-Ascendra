


// EVERYTHING IS RENDER AND CREATED HERE


package main;

import Entities.Boss;
import Managers.GameManager;
import Managers.SoundManager;
import PrefabPattern.BaseSpawner;
import ProjectiesLogic.*;
import Entities.Player;
import Sound.SoundBus;
import SpriteLoader.BulletType;
import System.CollisionSystem;
import UI.BackGround;
import UI.Border;
import UI.Healthbar;
import UI.UltimateBar;
import Utils.Settings;
import Utils.Utility;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import System.ParticleSystem;

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

    private Bloom bloom = new Bloom(.95);



    //GRAPHIC
    private BackGround background = new BackGround();
    private Healthbar healthbar = new Healthbar();
    private Border border = new Border();
    private UltimateBar UltimateBar = new UltimateBar();

    //ENTITIES
    private Boss boss = new Boss(Utility.shiftCordX(0), Utility.shiftCordY(800) , 1.25, 96, testBulletPool);

    public Game(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        initClasses();
    }

    private void initClasses(){

        playerbulletPool = new BulletPool(1024);
        player = new Player((float) Settings.HORIZONTAL_RESOLUTION / 2 , (float) Settings.VERTICAL_RESOLUTION * 6 / 8 , 128 , .0625 ,playerbulletPool);
        SoundManager.instance.load();
        SoundManager.instance.setBusVolume(SoundBus.MUSIC,1);










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

    Bloom bloom1= new Bloom(0);
    Glow glow1 = new Glow(1);




    Bloom bloom2 = new Bloom(0.4);

    private void render() {
        bloom1.setInput(glow1);
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

        //gc.setEffect(bloom2);
        playerbulletPool.renderAll(gc);
        testBulletPool.renderAll(gc);

        //gc.setEffect(bloom1); // THE REASON WHY ITS LAGGY WHEN PRESS ULT ( WRITE THIS SO I DONT FORGET )
        ParticleSystem.INSTANCE.render(gc);

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
        testBulletPool.updateAll(dt);

        ParticleSystem.INSTANCE.update(dt);

        CollisionSystem.CheckPlayerHit(testBulletPool , player);
        CollisionSystem.CheckBossHit(playerbulletPool,boss);

        //UI
        updateUI(dt);


    }

    //GETTER SETTER


    private void renderUI(GraphicsContext gc){
        border.render(gc);
        healthbar.render(gc);
        UltimateBar.render(gc);

    }

    private void updateUI(double dt){
        UltimateBar.update(dt);
    }



}
