package PrefabPattern;

import Entities.Entity;
import Managers.GameManager;
import ProjectiesLogic.*;
import Utils.Settings;
import Utils.Utility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import System.ParticleSystem;

public class BaseSpawner extends Entity {

    protected Image sprite;
    protected final double visualSpinSpeed;

    protected boolean active;

    protected BulletSpawner bulletSpawner;

    public BaseSpawner(double x ,
                       double y ,
                       double visualSize ,
                       double hitboxPercentage ,
                       BulletPool bulletPool ,
                       boolean canHit ,
                       Image sprite ,
                       double visualSpinSpeed,
                       BulletConfig bulletConfig,
                       BulletPattern bulletPattern,
                       SpawnerConfig spawnerConfig){
        super(x,y,visualSize,hitboxPercentage,bulletPool,canHit);
        this.sprite = sprite;
        this.visualSpinSpeed = visualSpinSpeed;
        active = false;

        bulletSpawner = new BulletSpawner(bulletPool,x,y,spawnerConfig,bulletConfig,bulletPattern);
    }




    public void update(double dt) {
        if (!active) return;
        bulletSpawner.update(dt);

    }


    public void render(GraphicsContext gc) {

        if (sprite == null || !active) return;


        double visualRender = visualSize ;
        double half = visualRender * 0.5;

        gc.save();

        gc.translate(x,y);
        gc.setImageSmoothing(false);
        gc.rotate(GameManager.instance.getGameTime() * visualSpinSpeed);
        gc.drawImage(sprite, -half , -half, visualRender, visualRender);

        double hitboxRender = visualRender * hitboxPercentage ;
        double hitboxhalf = hitboxRender * .5;

        if (Settings.DEBUG_DRAW_HITBOXES) {
            gc.setFill(Color.RED);
            gc.fillOval(-hitboxhalf , -hitboxhalf , hitboxRender , hitboxRender);
        }

        gc.restore();

    }

    public void StopSpawn(){

        bulletSpawner.stop();
    }

    public void StartSpawn(){
        StartObject();
        bulletSpawner.start();
    }

    public void StartObject() {
        if (!active) {
            active = true;
            if (sprite != null) ParticleSystem.INSTANCE.flashCircle(x, y, 1);
        }
    }

    public void StopObject() {
        bulletSpawner.stop();
        active = false;
        if (sprite != null) ParticleSystem.INSTANCE.flashCircle(x, y,  1);
    }



    public BulletSpawner getBulletSpawner() {
        return bulletSpawner;
    }


    public void setX(double x) {
        super.setX(x);
        bulletSpawner.setX(x);
    }

    public void setY(double y) {
        super.setY(y);
        bulletSpawner.setY(y);
    }

    public boolean isActive() {
        return active;
    }
}
