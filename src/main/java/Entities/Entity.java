package Entities;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

    protected BulletSpawner bulletSpawner;
    protected float x;
    protected float y;
    protected float size;
    protected float hitboxPercentage;

    public Entity(float x , float y , float size , float hitboxPercentage , BulletSpawner bulletSpawner){
        this.x = x;
        this.y = y;
        this.size = size;
        this.hitboxPercentage = hitboxPercentage;
        this.bulletSpawner = bulletSpawner;
    }

    public  abstract void update(double dt);

    public  abstract void render(GraphicsContext gc);
}
