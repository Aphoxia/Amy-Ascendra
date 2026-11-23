package Entities;

import ProjectiesLogic.BulletPool;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {

    protected BulletPool bulletPool;
    protected double x; //POS
    protected double y; //POS
    protected double visualSize;
    protected double hitboxPercentage;
    protected boolean canHit;

    public Entity(double x , double y , double visualSize , double hitboxPercentage , BulletPool bulletPool , boolean canHit){
        this.x = x;
        this.y = y;
        this.visualSize = visualSize;
        this.hitboxPercentage = hitboxPercentage;
        this.bulletPool = bulletPool;
        this.canHit = canHit;
    }

    public  abstract void update(double dt);

    public  abstract void render(GraphicsContext gc);

    public double getx(){
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double gety(){
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public boolean isCanHit() {
        return canHit;
    }

    public void setCanHit(boolean canHit) {
        this.canHit = canHit;
    }

    public double getHitboxPercentage() {
        return hitboxPercentage;
    }

    public void setHitboxPercentage(double hitboxPercentage) {
        this.hitboxPercentage = hitboxPercentage;
    }

    public double getVisualSize() {
        return visualSize;
    }

    public void setVisualSize(double visualSize) {
        this.visualSize = visualSize;
    }
}
