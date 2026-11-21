package Entities;

import Projecties.BulletPool;
import Projecties.BulletSpawner;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

    protected BulletPool bulletPool;
    protected double x;
    protected double y;
    protected double size;
    protected double hitboxPercentage;

    public Entity(double x , double y , double visualSize , BulletPool bulletPool){
        this.x = x;
        this.y = y;
        this.size = visualSize;
        this.hitboxPercentage = hitboxPercentage;
        this.bulletPool = bulletPool;
    }

    public  abstract void update(double dt);

    public  abstract void render(GraphicsContext gc);

    public double getx(){
        return x;
    }

    public double gety(){
        return y;
    }

    public double getHitboxPercentage() {
        return hitboxPercentage;
    }

    public double getSize() {
        return size;
    }
}
