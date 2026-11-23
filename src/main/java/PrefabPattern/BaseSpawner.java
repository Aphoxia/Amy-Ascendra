package PrefabPattern;

import Entities.Entity;
import Managers.GameManager;
import ProjectiesLogic.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BaseSpawner extends Entity {

    protected final Image sprite;
    protected final double visualSpinSpeed;

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

        bulletSpawner = new BulletSpawner(bulletPool,x,y,spawnerConfig,bulletConfig,bulletPattern);
    }




    public void update(double dt) {
        bulletSpawner.update(dt);
    }


    public void render(GraphicsContext gc) {

        if (sprite == null) return;

        gc.save();
        double visualRender = visualSize ;
        double half = visualRender * 0.5;

        gc.save();

        gc.translate(x,y);
        gc.setImageSmoothing(false);
        gc.rotate(GameManager.instance.getGameTime() * visualSpinSpeed);
        gc.drawImage(sprite, -half , -half, visualRender, visualRender);

        gc.restore();

    }

    public void StopSpawn(){
        bulletSpawner.stop();
    }

    public void StartSpawn(){
        bulletSpawner.start();
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
}
