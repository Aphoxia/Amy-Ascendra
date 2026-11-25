package PrefabPattern;

import Managers.SoundManager;
import ProjectiesLogic.BulletConfig;
import ProjectiesLogic.BulletPattern;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.SpawnerConfig;
import Sound.SoundId;
import Utils.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BounceSpawner extends BaseSpawner implements IResetMove {

    private double vx;
    private double vy;

    private double Startx;
    private double Starty;

    private double Startvy;
    private double Startvx;

    public BounceSpawner(
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

        this.Startvy = vy;
        this.Startvx = vx;
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

        double offset = 0;

        if (x < -offset + (640 - 270) ) {vx *= -1;
            SoundManager.instance.play(SoundId.SPAWNSFX1);}
                ;
        if (x > 640 + 270 + offset )  {vx *= -1;
            SoundManager.instance.play(SoundId.SPAWNSFX1);}
        if (y < -offset ) {vy *= -1;
            SoundManager.instance.play(SoundId.SPAWNSFX1);}
        if (y > Settings.PlayAreaY  + offset )  {vy *= -1;
            SoundManager.instance.play(SoundId.SPAWNSFX1);}

        bulletSpawner.setX(x);
        bulletSpawner.setY(y);

    }

    public void ResetMove(){
        x = Startx;
        y = Starty;

        vy = Startvy;
        vx = Startvx;

        bulletSpawner.setX(x);
        bulletSpawner.setY(y);
    }


}
