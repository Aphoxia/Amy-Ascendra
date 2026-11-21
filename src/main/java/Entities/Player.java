package Entities;

import Projecties.BulletPool;
import Projecties.BulletSpawner;
import Projecties.BulletType;
import Utils.GameTimer;
import Utils.Utility;
import input.KeyBoardInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class Player extends Entity{


    //LOGIC
    private double normalSpeed = 300f;
    private double focusSpeed = 150f;
    private int xDir = 0;
    private int yDir = 0;
    private boolean isMoving;
    private boolean left,right,up,down;
    private boolean shotting;
    private boolean focus;

    private ArrayList<BulletSpawner> playerbulletSpawner;

    //TIMER
    private final double firerate = .1f;
    private final GameTimer fireCooldown = GameTimer.every(firerate);



    public Player(double x , double y , double size , double hitboxPercentage , BulletPool playerbulletpool){
        super(x,y,size,hitboxPercentage,playerbulletpool);
        left = false;
        right = false;
        up = false;
        down = false;
        shotting = false;
        focus = false;

        playerbulletSpawner = new ArrayList<>();

        BulletSpawner playerBulletSpawner1 = new BulletSpawner(bulletPool,0,0,0,0,- 90,0,3,15,1000,0,0,3,.05, 2, BulletType.Player_Bullet1);
        playerbulletSpawner.add(playerBulletSpawner1);


    }

    @Override
    public void update(double dt) {
        updatePos(dt);
        shoot(dt);

        for (BulletSpawner bulletSpawner : playerbulletSpawner){
            bulletSpawner.setX(x);
            bulletSpawner.setY(y);
            bulletSpawner.update(dt);
        }
    }


    @Override
    public void render(GraphicsContext gc) {
        double drawX = x - (size / 2);
        double drawY = y - (size / 2);

        gc.setFill(Color.CYAN);
        gc.fillOval(drawX, drawY, size,size);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.5);
        gc.strokeOval(drawX, drawY, size,size);

        //HITBOX
        double coreSize = (size * hitboxPercentage);
        double coreX = x - (coreSize / 2);
        double coreY = y - (coreSize / 2);
        gc.setFill(Color.RED);
        gc.fillOval(coreX , coreY, coreSize, coreSize);

    }

    private void updatePos(double dt) {



        KeyBoardInput input = KeyBoardInput.getInstance();
        up = input.isHeld(KeyCode.W) || input.isHeld(KeyCode.UP) ;
        down = input.isHeld(KeyCode.S) || input.isHeld(KeyCode.DOWN) ;
        left = input.isHeld(KeyCode.A) || input.isHeld(KeyCode.LEFT) ;
        right = input.isHeld(KeyCode.D)|| input.isHeld(KeyCode.RIGHT) ;



        xDir = (right ? 1 : 0) - (left ? 1 : 0);
        yDir = (down ? 1 : 0) - (up ? 1 : 0);
        isMoving = (xDir != 0 || yDir != 0);
        double movementSpeed = (input.isHeld(KeyCode.SHIFT)) ? focusSpeed : normalSpeed;


        if (!isMoving) return;

        double norm = Utility.Normalize(xDir , yDir);
        //Utilities should return 1 or .707 if I am not wrong.

        x = (x + (movementSpeed * norm * xDir * dt));
        y = (y + (movementSpeed * norm * yDir * dt));

        //TEST
        //System.out.println(Utility.Distance(movementSpeed * norm * xDir , movementSpeed * norm * yDir));

    }

    private void shoot(double dt){

        shotting = KeyBoardInput.getInstance().isHeld(KeyCode.Z);

            for (BulletSpawner bulletSpawner : playerbulletSpawner){
                bulletSpawner.setActive(shotting);
            }
        }


    //GETTER SETTER FOR TESTING (WHY I HAVE TO WRITE TEST)

    public void setDirectionX(int xDir){
        this.xDir = xDir;
        isMoving = (xDir != 0);
    }

    public int getDirectionX(){
        return xDir;
    }

    public void setDirectionY(int yDir){
        this.yDir = yDir;
        isMoving = (yDir != 0);
    }

    public int getDirectionY(){
        return yDir;
    }

    public boolean getIsMoving(){
        return isMoving;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void set_Up(boolean up) {
        this.up = up;
    }


}
