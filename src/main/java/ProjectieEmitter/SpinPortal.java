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

public class SpinPortal extends Entity implements IEmitter{

    private BulletConfig config;
    private BulletPattern pattern;
    private BulletSpawner spawner;

    private double speed;


    private Image Sprite;

    public SpinPortal (double x , double y , double visualSize , BulletPool bulletPool){
        super(x,y,visualSize,bulletPool);
        config = new BulletConfig(
                List.of( BulletType16.ENEMY_Star4,
                        BulletType16.ENEMY_Star5,
                        BulletType16.ENEMY_Star6,
                        BulletType16.ENEMY_Star7,
                        BulletType16.ENEMY_Star8,
                        BulletType16.ENEMY_Star9,
                        BulletType16.ENEMY_Star10,
                        BulletType16.ENEMY_Star11,
                        BulletType16.ENEMY_Star12,
                        BulletType16.ENEMY_Star13),
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
