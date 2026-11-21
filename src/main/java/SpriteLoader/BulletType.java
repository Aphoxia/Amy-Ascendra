package SpriteLoader;


import Utils.Settings;
import javafx.scene.image.Image;

public enum BulletType {
    //PLAYER_BASIC(14.0, 5.0,   Color.WHITE),
    ENEMY_ORB1(16, .5, "Sprite-0001.png" ),
    ENEMY_ORB2(16, .5, "Test2.png" ),
    Player_Bullet1(16, .5, "Test3.png"),
    Player_Bullet2(16, .5, "Test4.png");

    public final double visualSize;
    public final double hitRadius;
    public final Image sprite;

    BulletType(double visualSize, double hitRadiusPercentage, String res_path) {
        this.visualSize = visualSize * Settings.PIXELSCALE;
        this.hitRadius = visualSize * hitRadiusPercentage * Settings.PIXELSCALE;
        this.sprite = loadSprite(res_path);
    }

    private static Image loadSprite(String resourcePath) {
        String image_path = ClassLoader.getSystemResource(resourcePath).toString();
        return new Image(image_path,false);
    }


}

