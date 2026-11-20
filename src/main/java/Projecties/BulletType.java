package Projecties;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum BulletType {
    //PLAYER_BASIC(14.0, 5.0,   Color.WHITE),
    ENEMY_ORB1(16, .5, "Sprite-0001.png" ),
    ENEMY_ORB2(8, .5, "Test2.png" ),
    Player_Bullet1(16, 1, "Test3.png"),
    Player_Bullet2(16, 1, "Test4.png");

    public final double visualSize;
    public final double hitRadius;
    public final Image sprite;

    BulletType(double visualSize, double hitRadiusPercentage, String res_path) {
        this.visualSize = visualSize;
        this.hitRadius = visualSize * hitRadiusPercentage;
        this.sprite = loadSprite(res_path);
    }

    private static Image loadSprite(String resourcePath) {
        String image_path = ClassLoader.getSystemResource(resourcePath).toString();
        return new Image(image_path,false);
    }


}

