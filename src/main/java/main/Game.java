


// EVERYTHING IS RENDER AND CREATED HERE


package main;

import Entities.Player;
import Utility.Utilities;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game extends AnimationTimer{
    //MAIN GAME CLASS INIT ALL BOSS PLAYER AND STUFF HERE
    private static final double TARGET_FPS = 60.0;
    private static final double FIXED_DT = 1.0 / TARGET_FPS; //like unity delta-time
    private static final double MAX_FRAME_TIME = 0.25; // IF YOUR PC SUCK

    private final Canvas canvas;
    private final GraphicsContext gc;

    private Player player; //There only 1 player lol
    //private BulletObjectPulling

    //FPS STUFF
    private long previousNanoTime = 0;
    private double accumulator = 0.0;

    private long fpsTimerNano = 0;
    private int framesThisSecond = 0;

    public Game(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        initClasses();
    }

    private void initClasses(){
        player = new Player((float) Utilities.HORIZONTAL_RESOLUTION / 2 , (float) Utilities.VERTICAL_RESOLUTION * 6 / 8);
    }


    //JAVAFX AUTO CALL THS FOR SOME REASON
    @Override
    public void handle(long now) {
        if (previousNanoTime == 0) {
            previousNanoTime = now;
            return;
        }

        double frameTime = (now - previousNanoTime) / 1_000_000_000.0;
        previousNanoTime = now;

        // IF YOUR PC SUCK
        if (frameTime > MAX_FRAME_TIME) {
            frameTime = MAX_FRAME_TIME;
        }

        accumulator += frameTime;

        // Run logic at a steady 60 FPS
        while (accumulator >= FIXED_DT) {
            update(FIXED_DT);
            accumulator -= FIXED_DT;
        }
        //render BG to player
        render();

        framesThisSecond++;
        if (now - fpsTimerNano >= 1_000_000_000L) {
            System.out.println("FPS: " + framesThisSecond);
            framesThisSecond = 0;
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
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        player.render(gc);
    }


    private void update(double dt) {

        player.update(dt);
    }

    //GETTER SETTER

    public Player getPlayer(){
        return player;
    }



}
