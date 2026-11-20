package Projecties;




import Utils.Settings;
import javafx.scene.canvas.GraphicsContext;


public class Bullet {
        private boolean isPlayer;
        private boolean active;
        private double x , y ;// THE ON SCREEN POS MIDDLE DOT
        private double vx , vy; //VELOCITY
        private double currentSpeed, maxSpeed;
        private double acceleration;
        private double currentDegree, angularVelocity;
        private BulletType type;
        private double lifetime , age;


    public Bullet(){
        isPlayer = false;
        active = false;
        x = 0;
        y = 0;
        vx = 0;
        vy = 0;
        currentSpeed = 0;
        acceleration = 0;
        currentDegree = 0;
        angularVelocity = 0;
        type = BulletType.Player_Bullet1; //DEFAULT
        lifetime = 0;
        age = 0;
    }

    public void update(double dt) {

        if (!active) return;
        age += dt;

        currentSpeed = currentSpeed + (dt * acceleration);
        currentDegree += (dt * angularVelocity);
        double deg = Math.toRadians(currentDegree);
        vx = Math.cos(deg) * currentSpeed * dt;
        vy = Math.sin(deg) * currentSpeed* dt;
        x += vx;
        y += vy;

        //System.out.println(currentSpeed );

        //ACTIVE CHECK
        if (x < -50 || x > 1330 || y < -50 || y > 800 || age >= lifetime) {
            active = false;
        }
    }

    public void reset(BulletType type, double x, double y, double newSpeed,double newAngle , double newAcceleration , double newAngularVelocity , boolean isPlayer , double lifetime) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.currentSpeed = newSpeed;
        this.currentDegree = newAngle;
        this.acceleration = newAcceleration;
        this.angularVelocity = newAngularVelocity;
        this.isPlayer = isPlayer;
        this.active = true;
        this.lifetime = lifetime;
        this.age = 0;

        //System.out.println("New Spees is : " + newSpeed);
    }

    public void render(GraphicsContext gc) {
        if (!active) return;

        double visualRender = type.visualSize * Settings.PIXELSCALE;
        double half = visualRender * 0.5;
        double dx = Math.round(x - half);
        double dy = Math.round(y - half);

        gc.setImageSmoothing(false);
        gc.drawImage(type.sprite,dx,dy, visualRender, visualRender);

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }
}

