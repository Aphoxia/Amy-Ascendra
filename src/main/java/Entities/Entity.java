package Entities;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {
    protected float x;
    protected float y;

    public Entity(float x , float y){
        this.x = x;
        this.y = y;
    }

    public  abstract void update(double dt);

    public  abstract void render(GraphicsContext gc);
}
