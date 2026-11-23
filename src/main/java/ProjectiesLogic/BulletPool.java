
package ProjectiesLogic;

//CHANGE THIS CLASS LATER

import Utils.Settings;
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

        for (int i = 0; i < activeBullets.size();) {
            Bullet b = activeBullets.get(i);

            if (b.isActive()) {
                b.update(dt);
            }

            if (!b.isActive()) {
                int lastIndex = activeBullets.size() - 1;
                Bullet lastBullet = activeBullets.remove(lastIndex);
                //CAN LAST BULLET BE INACTVE? YEA BUT I AM TOO LAZY TO GET AND FIX IT

                if (i < lastIndex) {
                    activeBullets.set(i, lastBullet);
                }

                free.addLast(b);

            } else {
                i++;
            }
        }

        if (Settings.DEBUG_BULLET_POOLCOUNT) {
            System.out.println("Active Bullet : " + activeBullets.size());
            System.out.println("free : " + free.size());
        }

    }

    public void renderAll(GraphicsContext gc) {
        for (Bullet b : activeBullets) {
            if (b.isActive()) {
                b.render(gc);
            }
        }
    }

    public ArrayList<Bullet> getActiveBullets() {
        return activeBullets;
    }

    public Deque<Bullet> getFreeBullets(){
        return free;
    }

    public void clearAll(){
        for (Bullet b : activeBullets){
            b.setActive(false);
        }
    }
}
