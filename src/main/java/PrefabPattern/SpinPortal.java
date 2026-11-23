package PrefabPattern;

import Pattern.BulletPattern;
import ProjectiesLogic.BulletConfig;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.BulletSpawner;
import SpriteLoader.BulletType16;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class SpinPortal extends BaseEmitter{

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
                360,0,0,200,0,10,false,false
        );
        pattern = new BulletPattern(2 , 180);
        spawner = new BulletSpawner(bulletPool , x , y ,0 , 0 ,0 , 120 , 0 ,
                3, .25 , config , pattern);

    }

    public void update(double dt) {
        spawner.update(dt);
        spawner.setX(x);
        spawner.setY(y);

    }

    public void render(GraphicsContext gc){
        gc.save();
        gc.restore();
    }

}
