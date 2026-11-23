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
        super.update(dt);
        moveLogic(dt);
    }

    public void render(GraphicsContext gc){
        super.render(gc);
    }

    private void moveLogic(double dt){
        x += vx;
        y += vy;

        double offset = 50;

        if (x < -offset ) x = Settings.PlayAreaX + offset;
        if (x > Settings.PlayAreaX + offset ) x = -offset;
        if (y < -offset ) y = Settings.PlayAreaY + offset;
        if (y > Settings.PlayAreaY  + offset ) y = -offset;

        bulletSpawner.setX(x);
        bulletSpawner.setY(y);

    }


}
