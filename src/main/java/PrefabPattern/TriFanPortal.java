package PrefabPattern;

import Pattern.BulletPattern;
import ProjectiesLogic.BulletConfig;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.BulletSpawner;
import SpriteLoader.BulletType16;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class TriFanPortal extends BaseEmitter{


    public TriFanPortal (double x , double y , double visualSize , BulletPool bulletPool){
        super(x,y,visualSize,bulletPool);
        config = new BulletConfig(
                List.of(BulletType16.ENEMY_ORB1 , BulletType16.ENEMY_ORB2 ),360,
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

    }

    public void render(GraphicsContext gc){
        gc.save();
        gc.restore();
    }
}
