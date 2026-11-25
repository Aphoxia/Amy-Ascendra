package PrefabPattern;

import ProjectiesLogic.BulletConfig;
import ProjectiesLogic.BulletPattern;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.SpawnerConfig;
import Utils.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpeedSpawner extends  BaseSpawner implements IResetMove{
    private double vx;
    private double vy;

    private double Startx;
    private double Starty;

    public SpeedSpawner(
            double x,
            double y,
            double visualSize,
            double hitboxPercentage,
            BulletPool bulletPool,
            boolean canHit,
            Image sprite,
            double visualSpinSpeed,
            BulletConfig bulletConfig,
            BulletPattern bulletPattern,
            SpawnerConfig spawnerConfig,
            double vx,
            double vy)
    {
        super(x, y, visualSize, hitboxPercentage, bulletPool, canHit, sprite, visualSpinSpeed, bulletConfig, bulletPattern, spawnerConfig);
        this.vx = vx;
        this.vy = vy;

        this.Startx = x;
        this.Starty = y;
    }

    public void update(double dt) {
        if (!active) return;
        super.update(dt);
        moveLogic(dt);
    }

    public void render(GraphicsContext gc){
        super.render(gc);
    }

    private void moveLogic(double dt){

        x += vx * dt;
        y += vy * dt;

        double offset = 200;

        if (x < -offset + (640 - 270) ) active = false;
        if (x > 640 + 270 + offset ) active = false;
        if (y < -offset ) active = false;
        if (y > Settings.PlayAreaY  + offset ) active = false;

        bulletSpawner.setX(x);
        bulletSpawner.setY(y);

    }

    public void ResetMove(){
        x = Startx;
        y = Starty;
        bulletSpawner.setX(x);
        bulletSpawner.setY(y);
    }


}


