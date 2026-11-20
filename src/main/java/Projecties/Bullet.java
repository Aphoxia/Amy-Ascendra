package Projecties;

//CHANGE THIS CLASS LATER


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {
        private boolean playerBullet;
        public boolean active = false;
        private double x, y, speed , currentSpeed, angleDeg, acceleration;

        private BulletType type;

    public void update(double dt) {
        if (!active) return;

        this.currentSpeed += (acceleration * dt);
        double rad = Math.toRadians(angleDeg);
        x += Math.cos(rad) *currentSpeed * dt;
        y += Math.sin(rad) * currentSpeed* dt;

        // Simply mark inactive to return it to the pool
        if (x < -50 || x > 1330 || y < -50 || y > 800) {
            active = false;
        }
    }

    public void reset(BulletType type, double x, double y, double speed ,double angleDeg , double acceleration ,boolean playerBullet) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.currentSpeed = speed;
        this.speed = speed;
        this.angleDeg = angleDeg;
        this.acceleration = acceleration;
        this.active = true;
        this.playerBullet = playerBullet;
    }

    public void render(GraphicsContext gc) {
        if (!active) return;

        double half = type.visualSize * 0.5;
        double dx = Math.round(x - half);
        double dy = Math.round(y - half);

        gc.setImageSmoothing(false);
        gc.drawImage(type.sprite,dx,dy, type.visualSize, type.visualSize);
//        gc.setFill(Color.CYAN);
//        double size = type.visualSize;
//        gc.fillOval(x - (size / 2), y - (size / 2), size, size);
    }



    public boolean isActive() {
        return active;
    }
}

