package UI;

import Utils.Utility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackGround {

    private final Image Stars1 = Utility.loadSprite("Images/BackGround/StarBG.png");
    private final Image Stars2 = Utility.loadSprite("Images/BackGround/StarBG.png");

    private final Image Stars3 = Utility.loadSprite("Images/BackGround/StarBG2.png");
    private final Image Stars4 = Utility.loadSprite("Images/BackGround/StarBG2.png");

    private final Image Stars5 = Utility.loadSprite("Images/BackGround/StarBG3.png");
    private final Image Stars6 = Utility.loadSprite("Images/BackGround/StarBG3.png");

    private final Image Stars7 = Utility.loadSprite("Images/BackGround/StarBG4.png");
    private final Image Stars8 = Utility.loadSprite("Images/BackGround/StarBG4.png");


    private double offsetY1;
    private double offsetY2;
    private double offsetY3;
    private double offsetY4;

    public void update(double dt){

        double DimStarScrollSpeed = 25;
        double NormalStarScrollSpeed = 50;
        double BrightStarScrollSpeed = 75;
        double BrightestStarScrollSpeed = 100;

        //if (bottomBGPosy < BOUNDY) bottomBGPosy += dt * DimStarScrollSpeed;

        offsetY1 = (offsetY1 + DimStarScrollSpeed * dt) % 720;
        offsetY2 = (offsetY2 + NormalStarScrollSpeed  * dt) % 720;
        offsetY3 = (offsetY2 + BrightStarScrollSpeed * dt) % 720;
        offsetY4 = (offsetY4 + BrightestStarScrollSpeed * dt) % 720;

    }
    public void render(GraphicsContext gc){

        gc.save();

        gc.drawImage(Stars1, 0, Math.round(offsetY1), 1280, 720);
        gc.drawImage(Stars2, 0,  Math.round(offsetY1 - 720), 1280, 720);

        gc.drawImage(Stars3, 0, Math.round(offsetY2), 1280, 720);
        gc.drawImage(Stars4, 0,  Math.round(offsetY2 - 720), 1280, 720);

        gc.drawImage(Stars5, 0, Math.round(offsetY3), 1280, 720);
        gc.drawImage(Stars6, 0,  Math.round(offsetY3 - 720), 1280, 720);

        gc.drawImage(Stars7, 0, Math.round(offsetY4), 1280, 720);
        gc.drawImage(Stars8, 0,  Math.round(offsetY4 - 720), 1280, 720);

        gc.restore();
    }
}
