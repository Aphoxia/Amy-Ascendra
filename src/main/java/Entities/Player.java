package Entities;

import Managers.GameManager;
import PrefabPattern.BaseSpawner;
import ProjectiesLogic.*;
import Sound.SoundId;
import SpriteLoader.BulletType;
import UI.UltimateBar;
import Utils.Settings;
import Utils.Utility;
import input.KeyBoardInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static Utils.Utility.*;
import static java.lang.Math.max;


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

    private final double INVINCIBLE_TIME = 1;
    private final double ULTINVINCIBLE_TIME = 2;
    private double invincibleElapse;

    private int maxHitPoint = 30;
    private int hitpoint;


    private final Image[] sprites = new Image[4];
    private int spriteIndex = 0;
    private int spriteCounter = 0;
    private int animationSpeed = 15;

    private final Image HitboxSprite = loadSprite("Images/Player/Hitbox.png");


    //Effet
    private DropShadow dropShadow = new DropShadow(4, 0, 2, Color.color(0, 0, 0, 0.65));

    //PLAYER BULLET
    private BaseSpawner playerbulletspawner1;


    public Player(double x ,
                  double y ,
                  double size ,
                  double hitboxPercentage ,
                  BulletPool playerbulletpool){

        super(x,y,size,hitboxPercentage,playerbulletpool,true);
        this.hitboxPercentage = hitboxPercentage;

        sprites[0] = Utility.loadSprite("Images/Player/AmySpriteAnimation1.png");
        sprites[1] = Utility.loadSprite("Images/Player/AmySpriteAnimation2.png");
        sprites[2] = Utility.loadSprite("Images/Player/AmySpriteAnimation3.png");
        sprites[3] = Utility.loadSprite("Images/Player/AmySpriteAnimation4.png");

        left = false;
        right = false;
        up = false;
        down = false;

        shotting = false;
        focus = false;

        Instance = this;

        invincibleElapse = 0;
        hitpoint = maxHitPoint;


        BulletPattern playerBulletPattern = new BulletPattern(3 ,5);
        BulletConfig  playerbulletConfig = new BulletConfig(
                List.of(BulletType.Player_Bullet1 ,
                        BulletType.Player_Bullet2 ,
                        BulletType.Player_Bullet3 ,
                        BulletType.Player_Bullet4),
                0, 1500,1500,0,0,
                5,true,false);
        SpawnerConfig playerSpawnerConfig = new SpawnerConfig(- 90,0,0,0,1,.05);
        playerbulletspawner1 = new BaseSpawner(x,y,size,hitboxPercentage,bulletPool,true,
                null,0,playerbulletConfig,playerBulletPattern,playerSpawnerConfig);

        playerbulletspawner1.StartObject();

        playerbulletspawner1.getBulletSpawner().setSoundId(SoundId.PLAYER_SHOT);
    }

    @Override
    public void update(double dt) {

        logicUpdate(dt);
        updatePos(dt);
        shoot(dt);
        playerbulletspawner1.setX(x);
        playerbulletspawner1.setY(y);
    }


    @Override
    public void render(GraphicsContext gc) {

        gc.save();
        if(focus){
            gc.setGlobalAlpha(.5);
        }

        if (invincibleElapse > 0) {
            boolean visiblePhase = ((int)(invincibleElapse * 15) % 2) == 0;
            gc.setGlobalAlpha((visiblePhase ? 1.0 : 0.3));
        } else {
            if(focus){
                gc.setGlobalAlpha(.5);
            } else{
                gc.setGlobalAlpha(1);
            }
        }

        double drawX = x - (128 / 2);
        double drawY = y - (128 / 2);

        gc.setEffect(dropShadow);


        gc.drawImage(sprites[spriteIndex],drawX,drawY,128,128);
            spriteCounter++;

            if (spriteCounter >= animationSpeed) {
                spriteIndex++;

                // Loop back to the first image (0) after the 4th image (3)
                if (spriteIndex >= sprites.length) {
                    spriteIndex = 0;
                }

                spriteCounter = 0; // Reset counter

        }




        //HITBOX
        if (focus){
            gc.setGlobalAlpha(1);
        double coreSize = (visualSize * hitboxPercentage);
        double coreX = x - 3;
        double coreY = y - 3;

//        gc.setFill(Color.RED);
//        gc.fillOval(coreX , coreY, coreSize, coreSize);
            gc.drawImage(HitboxSprite,coreX,coreY);
    }
        gc.restore();
    }

    private void logicUpdate(double dt){
        focus = KeyBoardInput.getInstance().isHeld(KeyCode.SHIFT);
        invincibleElapse = max(0,invincibleElapse - dt);
    }

    private void updatePos(double dt) {


        if (x < Settings.HORIZONTAL_RESOLUTION / 2 - Settings.PlayAreaX / 2) x = Settings.HORIZONTAL_RESOLUTION / 2 - Settings.PlayAreaX / 2;
        if (x > Settings.HORIZONTAL_RESOLUTION / 2 + Settings.PlayAreaX / 2) x = Settings.HORIZONTAL_RESOLUTION / 2 + Settings.PlayAreaX / 2;
        if (y < Settings.VERTICAL_RESOLUTION / 2 - Settings.PlayAreaY / 2) y = Settings.VERTICAL_RESOLUTION / 2 - Settings.PlayAreaY / 2;
        if (y > Settings.VERTICAL_RESOLUTION / 2 + Settings.PlayAreaY / 2) y = Settings.VERTICAL_RESOLUTION / 2 + Settings.PlayAreaY / 2;



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

        double norm = Normalize(xDir , yDir);
        //Utilities should return 1 or .707 if I am not wrong.

        x = (x + (movementSpeed * norm * xDir * dt));
        y = (y + (movementSpeed * norm * yDir * dt));

        //TEST
        //System.out.println(Utility.Distance(movementSpeed * norm * xDir , movementSpeed * norm * yDir));

    }

    private void shoot(double dt){

        shotting = KeyBoardInput.getInstance().isHeld(KeyCode.Z);

        playerbulletspawner1.update(dt);
        playerbulletspawner1.getBulletSpawner().setActive(shotting);



        }


    public void resetInvincibleElapse(){

        this.invincibleElapse  = UltimateBar.Instance.isInUltimate() ? ULTINVINCIBLE_TIME : INVINCIBLE_TIME;
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

    public void setHitpoint(int hitpoint) {
        this.hitpoint = hitpoint;
    }

    public int getHitpoint(){
        return hitpoint;
    }

    public double getHitpointPercent(){
        return ( double)hitpoint / ( double) maxHitPoint;
    }

    public double getInvincibleElapse() {
        return invincibleElapse;
    }
}
