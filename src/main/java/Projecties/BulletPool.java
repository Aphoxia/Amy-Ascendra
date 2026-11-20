
package Projecties;

//CHANGE THIS CLASS LATER

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class BulletPool {
    private final ArrayList<Bullet> bullets= new ArrayList<>();

    public BulletPool(int capacity) {
        for (int i = 0; i < capacity; i++) {
            bullets.add(new Bullet());
        }
    }

    public Bullet obtain() {
        for (Bullet bullet : bullets) {
            if (!bullet.isActive()) {
                return bullet;
            }
        }
        return null;
//        Bullet created = new Bullet();
//        bullets.add(created);
//        return created;
    }


    public void updateAll(double dt) {
        for (Bullet b : bullets) {
            if (b.isActive()) {
                b.update(dt);
            }
        }
    }

    public void renderAll(GraphicsContext gc) {
        for (Bullet b : bullets) {
            if (b.isActive()) {
                b.render(gc);
            }
        }
    }
}
