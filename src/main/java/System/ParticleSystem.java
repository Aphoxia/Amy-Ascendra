package System;

import ProjectiesLogic.Bullet;
import SpriteLoader.SpriteLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public final class ParticleSystem {


    public static final ParticleSystem INSTANCE = new ParticleSystem(1000);

    private final ArrayList<Flash> active = new ArrayList<>();
    private final ArrayDeque<Flash> free = new ArrayDeque<>();

    public ParticleSystem(int capacity) {
        for (int i = 0; i < capacity; i++) {
            free.addLast(new Flash());
        }
    }

    public void update(double dt) {

        for (int i = 0 ; i < active.size();) {
            Flash flash = active.get(i);
            flash.update(dt);


            if (flash.isFinished()) {
                int lastIndex = active.size() - 1;
                Flash lastFlash = active.remove(lastIndex);

                if (i < lastIndex) {
                    active.set(i, lastFlash);
                }

                free.addLast(flash);
            } else{
                i++;
            }
        }
    }

    public void render(GraphicsContext gc) {
        gc.save();

        for (Flash flash : active) {
            flash.render(gc);
        }
        gc.restore();
    }

    public Flash obtain() {
        Flash flash = free.pollLast();
        if (!flash.active){
            active.add(flash);
            return flash;
        }
        // IF NOTHING GOES WRONG EVERYTHING HERE SHOULD BE INACTIVE

        Flash created = new Flash();
        active.add(created);
        return created;
    }


    public void flashCircle(
            double x, double y, double duration) {
        Flash flash = obtain();
        flash.reset(x,y,duration);
    }

    private static final class Flash {
        double x , y ;
        double duration ;
        double age ;
        boolean active;
        private Image sprite = SpriteLoader.loadSprite("Images/Bullets/HitTestSprite.png",128, 128,0,0);

        void reset(double x , double y , double duration){
            this.x = x;
            this.y = y;
            age = 0;
            this.duration = duration;
            active = true;
        }

        void update(double dt) {

            if (!active) return;

            age += dt;
            if (age >= duration){
                active = false;
            }
        }

        boolean isFinished() {
            return age >= duration;
        }

        void render(GraphicsContext gc) {

            if (!active) return;

            gc.save();
            double t = Math.min(age / duration, 1.0);
            double alpha = flashEnvelope(age, 0.02, duration);



            gc.setImageSmoothing(false);

            double Size = alpha * 256;

            gc.drawImage(sprite,x - Size / 2, y - Size / 2,Size,Size);


            gc.restore();;
        }

        private double flashEnvelope(double age, double attack, double duration) {
            if (age <= 0 || age >= duration) return 0.0;

            if (age < attack) {
                return age / attack;
            }

            double decayT = (age - attack) / (duration - attack);
            return Math.pow(1.0 - decayT, 2); // gradual fade to 0
        }
    }


}