package ProjectiesLogic;




import SpriteLoader.BulletType;
import Utils.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Bullet {
        private boolean isPlayer;
        private boolean active;
        private double x , y ;// THE ON SCREEN POS MIDDLE DOT
        private double vx , vy; //VELOCITY
        private double currentSpeed, maxSpeed;
        private double acceleration;
        private double currentDegree, angularVelocity;
        private BulletType type;
        private double visualSpinSpeed;
        private double lifetime , age;
        private double Size;
        private double hitboxSize;

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
        visualSpinSpeed = 0;
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
        vy = Math.sin(deg) * currentSpeed * dt;
        x += vx;
        y += vy;

        //x < -50 || x > 1330 || y < -50 || y > 800 ||
        //ACTIVE CHECK
        if ( age >= lifetime) {
            active = false;
        }
    }

    public void reset(BulletType type, double visualSpinSpeed, double x, double y, double newSpeed, double newAngle , double newAcceleration , double newAngularVelocity , boolean isPlayer , double lifetime) {
        this.type = type;
        this.visualSpinSpeed = visualSpinSpeed;
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
        Size = type.visualSize;
        hitboxSize = type.hitRadiusPecentage * type.visualSize;

    }

    public void render(GraphicsContext gc) {
        if (!active) return;

        double visualRender = Size ;
        double half = visualRender * 0.5;

        gc.save();

        gc.translate(x,y);
        gc.setImageSmoothing(false);
        gc.rotate(currentDegree + (age * visualSpinSpeed));
        gc.drawImage(type.sprite,-half , -half, visualRender, visualRender);


        double hitboxRender = hitboxSize ;
        double hitboxhalf = hitboxRender * .5;

        if (Settings.DEBUG_DRAW_HITBOXES) {
            gc.setFill(Color.RED);
            gc.fillOval(-hitboxhalf , -hitboxhalf , hitboxRender , hitboxRender);
        }

        gc.restore();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getHitboxSize(){
        return hitboxSize;
    }
}

