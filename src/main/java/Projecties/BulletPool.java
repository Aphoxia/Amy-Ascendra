
package Projecties;

//CHANGE THIS CLASS LATER

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class BulletPool {
    private final ArrayList<Bullet> activeBullets= new ArrayList<>();
    private final Deque<Bullet> free = new ArrayDeque<>();


    public BulletPool(int capacity) {
        for (int i = 0; i < capacity; i++) {
            free.addLast(new Bullet());
        }
    }

    public Bullet obtain() {
        Bullet bullet = free.pollLast();
        if (bullet != null && !bullet.isActive()){
            activeBullets.add(bullet);
            return bullet;
        }
            // IF NOTHING GOES WRONG EVERYTHING HERE SHOULD BE INACTIVE

        Bullet created = new Bullet();
        activeBullets.add(created);
        return created;
    }


    public void updateAll(double dt) {

        //DEBUG
       //System.out.println("Active Bullet : " + activeBullets.size());
       //System.out.println("free : " + free.size());



        for (int i = 0; i < activeBullets.size();) {
            Bullet b = activeBullets.get(i);

            if (b.isActive()) {
                b.update(dt);
            }

            if (!b.isActive()) {
                int lastIndex = activeBullets.size() - 1;
                Bullet lastBullet = activeBullets.remove(lastIndex);

                if (i < lastIndex) {
                    activeBullets.set(i, lastBullet);
                }

                free.addLast(b);
            } else {
                i++;
            }
        }
    }

    public void renderAll(GraphicsContext gc) {
        for (Bullet b : activeBullets) {
            if (b.isActive()) {
                b.render(gc);
            }
        }
    }

    public ArrayList<Bullet> getBullets() {
        return activeBullets;
    }
}
