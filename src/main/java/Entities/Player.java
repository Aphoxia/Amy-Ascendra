package Entities;

import Pattern.BulletPattern;
import Pattern.PlayerShoot;
import Projecties.BulletConfig;
import Projecties.BulletPool;
import Projecties.BulletSpawner;
import SpriteLoader.BulletType;
import Utils.Settings;
import Utils.Utility;
import input.KeyBoardInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class Player extends Entity{


    public static Player Instance;
    //LOGIC
    private double normalSpeed = 300f;
    private double focusSpeed = 150f;
    private double hitboxPercentage;


    private int xDir = 0;
    private int yDir = 0;
    private boolean isMoving;
    private boolean left,right,up,down;
    private boolean shotting;
    private boolean focus;

    //PLAYER BULLET

    private BulletPattern playerBulletPattern;
    private BulletConfig playerbulletConfig;
    private ArrayList<BulletSpawner> playerbulletSpawners;


    public Player(double x , double y , double size , double hitboxPercentage , BulletPool playerbulletpool){
        super(x,y,size,playerbulletpool);
        this.hitboxPercentage = hitboxPercentage;

        left = false;
        right = false;
        up = false;
        down = false;

        shotting = false;
        focus = false;

        Instance = this;

        playerbulletSpawners = new ArrayList<>();
        playerBulletPattern = new PlayerShoot(3 ,15);
        playerbulletConfig = new BulletConfig(
                List.of(BulletType.Player_Bullet1 , BulletType.Player_Bullet2),
                3000,3000,0,0,5,true,false);

        BulletSpawner playerBulletSpawner1 = new BulletSpawner(bulletPool,0,0,0,0,- 90,0,0,1,.05,playerbulletConfig,playerBulletPattern);
        playerbulletSpawners.add(playerBulletSpawner1);


    }

    @Override
    public void update(double dt) {

        updatePos(dt);
        shoot(dt);

        focus = KeyBoardInput.getInstance().isHeld(KeyCode.SHIFT);

        for (BulletSpawner bulletSpawner : playerbulletSpawners){
            bulletSpawner.setX(x);
            bulletSpawner.setY(y);
            bulletSpawner.update(dt);
        }
    }


    @Override
    public void render(GraphicsContext gc) {

        gc.save();


        double drawX = x - (size / 2);
        double drawY = y - (size / 2);

        gc.setFill(Color.CYAN);
        gc.fillOval(drawX, drawY, size,size);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.5);
        gc.strokeOval(drawX, drawY, size,size);

        //HITBOX
        if (focus){
        double coreSize = (size * hitboxPercentage);
        double coreX = x - (coreSize / 2);
        double coreY = y - (coreSize / 2);
        gc.setFill(Color.RED);
        gc.fillOval(coreX , coreY, coreSize, coreSize);
    }
        gc.restore();
    }

    private void updatePos(double dt) {


        if (x < 0) x = 0;
        if (x > Settings.HORIZONTAL_RESOLUTION) x = Settings.HORIZONTAL_RESOLUTION;
        if ( y < 0) y = 0;
        if ( y > Settings.VERTICAL_RESOLUTION) y = Settings.VERTICAL_RESOLUTION;



        KeyBoardInput input = KeyBoardInput.getInstance();
        up = input.isHeld(KeyCode.W) || input.isHeld(KeyCode.UP) ;
        down = input.isHeld(KeyCode.S) || input.isHeld(KeyCode.DOWN) ;
        left = input.isHeld(KeyCode.A) || input.isHeld(KeyCode.LEFT) ;
        right = input.isHeld(KeyCode.D)|| input.isHeld(KeyCode.RIGHT) ;



        xDir = (right ? 1 : 0) - (left ? 1 : 0);
        yDir = (down ? 1 : 0) - (up ? 1 : 0);
        isMoving = (xDir != 0 || yDir != 0);
        double movementSpeed = (focus) ? focusSpeed : normalSpeed;


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

            for (BulletSpawner bulletSpawner : playerbulletSpawners){
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

    public static Player getInstance() {
        return Instance;
    }
}
