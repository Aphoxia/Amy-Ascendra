
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

    public Bullet obtain(BulletType type, double x, double y, double speed ,double angleDeg , double acceleration) {
        for (Bullet bullet : bullets) {
            if (!bullet.isActive()) {
                bullet.reset(type, x, y, speed , angleDeg , acceleration);
                return bullet;
            }
        }

        Bullet created = new Bullet();
        created.reset(type, x, y, speed , angleDeg , acceleration);
        bullets.add(created);
        return created;
    }

    public void updateAll(double dt) {
        for (Bullet b : bullets) {
            if (b.active) {
                b.update(dt);
            }
        }
    }
    public void renderAll(GraphicsContext gc) {
        for (Bullet b : bullets) {
            if (b.active) {
                b.render(gc);
            }
        }
    }
}
