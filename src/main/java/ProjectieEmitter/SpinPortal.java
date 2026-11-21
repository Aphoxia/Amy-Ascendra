package ProjectieEmitter;

import Entities.Entity;
import Pattern.BulletPattern;
import Projecties.BulletConfig;
import Projecties.BulletPool;
import Projecties.BulletSpawner;
import SpriteLoader.BulletType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class SpinPortal extends Entity implements IEmitter{

    private BulletConfig config;
    private BulletPattern pattern;
    private BulletSpawner spawner;

    private double speed;


    private Image Sprite;

    public SpinPortal (double x , double y , double visualSize , BulletPool bulletPool){
        super(x,y,visualSize,bulletPool);
        config = new BulletConfig(
                List.of( BulletType.ENEMY_ORB2 ),
                150,200,60,0,10,false,false
        );
        pattern = new BulletPattern(2 , 180);
        spawner = new BulletSpawner(bulletPool , x , y ,0 , 0 ,0 , 50 , 0 ,
                1, .5 , config , pattern);

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
