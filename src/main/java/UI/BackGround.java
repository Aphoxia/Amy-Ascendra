package UI;

import Utils.Settings;
import Utils.Utility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackGround {

    private final double width = Settings.HORIZONTAL_RESOLUTION;
    private final double height = Settings.VERTICAL_RESOLUTION;

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


        //FIX VALUE
        double DimStarScrollSpeed = 25;
        double NormalStarScrollSpeed = 50;
        double BrightStarScrollSpeed = 75;
        double BrightestStarScrollSpeed = 100;

        offsetY1 = (offsetY1 + DimStarScrollSpeed * dt) % height;
        offsetY2 = (offsetY2 + NormalStarScrollSpeed  * dt) % height;
        offsetY3 = (offsetY2 + BrightStarScrollSpeed * dt) % height;
        offsetY4 = (offsetY4 + BrightestStarScrollSpeed * dt) % height;

    }
    public void render(GraphicsContext gc){

        gc.save();

        gc.drawImage(Stars1, 0, Math.round(offsetY1), width, height);
        gc.drawImage(Stars2, 0,  Math.round(offsetY1 - height), width, height);

        gc.drawImage(Stars3, 0, Math.round(offsetY2), width, height);
        gc.drawImage(Stars4, 0,  Math.round(offsetY2 - height), width, height);

        gc.drawImage(Stars5, 0, Math.round(offsetY3), width, height);
        gc.drawImage(Stars6, 0,  Math.round(offsetY3 - height), width, height);

        gc.drawImage(Stars7, 0, Math.round(offsetY4), width, height);
        gc.drawImage(Stars8, 0,  Math.round(offsetY4 - height), width, height);

        gc.restore();
    }
}
