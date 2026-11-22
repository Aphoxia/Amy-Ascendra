package ProjectieEmitter;

import Entities.Entity;
import Pattern.BulletPattern;
import Projecties.BulletConfig;
import Projecties.BulletPool;
import Projecties.BulletSpawner;
import SpriteLoader.BulletType16;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class TriFanPortal extends Entity implements IEmitter{

    private BulletConfig config;
    private BulletPattern pattern;
    private BulletSpawner spawner;

    private double speed;


    private Image Sprite;

    public TriFanPortal (double x , double y , double visualSize , BulletPool bulletPool){
        super(x,y,visualSize,bulletPool);
        config = new BulletConfig(
                List.of(BulletType16.ENEMY_ORB1 , BulletType16.ENEMY_ORB2 ),
                200,200,0,0,10,false,true
        );
        pattern = new BulletPattern(3 , 50);
        spawner = new BulletSpawner(bulletPool , x , y ,0 , 0 ,0 , 0 , .5 ,
                3 , 1 , config , pattern);

    }




    public void update(double dt) {
        spawner.update(dt);
        spawner.setX(x);
        spawner.setY(y);
        spawner.update(dt);
    }

    public void render(GraphicsContext gc){
        gc.save();
        gc.restore();
    }

    public void Enable(){
        spawner.setActive(true);
    }

    public void Disable(){
        spawner.setActive(false);
    }
}
