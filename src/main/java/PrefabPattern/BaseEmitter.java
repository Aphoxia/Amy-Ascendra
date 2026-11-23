package PrefabPattern;

import Entities.Entity;
import Pattern.BulletPattern;
import ProjectiesLogic.BulletConfig;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.BulletSpawner;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class BaseEmitter extends Entity {


        protected BulletConfig config;
        protected BulletPattern pattern;
        protected BulletSpawner spawner;
        protected Image Sprite;

        public BaseEmitter (double x , double y , double visualSize , BulletPool bulletPool){
            super(x,y,visualSize,bulletPool);
        }

        public abstract void update(double dt);

        public abstract void render(GraphicsContext gc);

        public void setConfig(BulletConfig bulletConfig){
            if (bulletConfig == null) return;
            spawner.setbConfig(bulletConfig);
        }

        public void setPattern(BulletPattern bulletpattern){
            if (bulletpattern == null) return;
            spawner.setBpattern(bulletpattern);
        }

        public void Enable(){
            spawner.start();

        }
        public void Disable(){
            spawner.stop();
        }
    }


