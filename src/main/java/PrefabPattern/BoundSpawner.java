package PrefabPattern;

import ProjectiesLogic.BulletConfig;
import ProjectiesLogic.BulletPattern;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.SpawnerConfig;
import Utils.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BoundSpawner extends BaseSpawner {

    private double vx;
    private double vy;

    public BoundSpawner(
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

        double offset = 50;

        if (x < -offset + (640 - 270) ) x = 640 + 270 + offset - 10;
        if (x > 640 + 270 + offset ) x = -offset + (640 - 270) + 10;
        if (y < -offset ) y = Settings.PlayAreaY + offset - 10;
        if (y > Settings.PlayAreaY  + offset ) y = -offset + 10;

        bulletSpawner.setX(x);
        bulletSpawner.setY(y);

    }


}
