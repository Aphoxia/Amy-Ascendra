package Entities;

import Utility.Utilities;
import input.KeyBoardInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;


public class Player extends Entity{

    //LOGIC
    private float movementSpeed = 200f;
    private int xDir = 0;
    private int yDir = 0;
    private boolean isMoving;
    private boolean left,right,up,down;



    public Player(float x , float y , float size , float hitboxPercentage){
        super(x,y,size,hitboxPercentage);
        left = false;
        right = false;
        up = false;
        down = false;
    }

    @Override
    public void update(double dt) {
        updatePos(dt);
    }


    @Override
    public void render(GraphicsContext gc) {
        double drawX = Math.round(x);
        double drawY = Math.round(y);


        gc.setFill(Color.CYAN);
        gc.fillOval(drawX, drawY, size,size);


        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1.5);
        gc.strokeOval(drawX, drawY, size,size);


        double coreSize = (size * hitboxPercentage);
        double coreX = x + (size - coreSize) / 2.0;
        double coreY = y + (size - coreSize) / 2.0;
        gc.setFill(Color.RED);
        gc.fillOval(Math.round(coreX), Math.round(coreY), coreSize, coreSize);

    }

    private void updatePos(double dt) {

        KeyBoardInput input = KeyBoardInput.getInstance();
        up = input.isHeld(KeyCode.W) ;
        down = input.isHeld(KeyCode.S) ;
        left = input.isHeld(KeyCode.A) ;
        right = input.isHeld(KeyCode.D);


        xDir = (right ? 1 : 0) - (left ? 1 : 0);
        yDir = (down ? 1 : 0) - (up ? 1 : 0);
        isMoving = (xDir != 0 || yDir != 0);

        if (!isMoving) return;

        float norm = Utilities.Normalize(xDir , yDir);
        //Utilities should return 1 or .707 if I am not wrong.

        x = (float) (x + (movementSpeed * norm * xDir * dt));
        y = (float) (y + (movementSpeed * norm * yDir * dt));

        //TEST
        System.out.println(Utilities.Distance(movementSpeed * norm * xDir , movementSpeed * norm * yDir));

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

    public void setMovementSpeed(float movementSpeed){
        this.movementSpeed = movementSpeed;
    }

    public float getMovementSpeed(){
        return movementSpeed;
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
