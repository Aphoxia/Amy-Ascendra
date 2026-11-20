package Projecties;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum BulletType {
    //PLAYER_BASIC(14.0, 5.0,   Color.WHITE),
    ENEMY_ORB1(32, 16, "Sprite-0001.png" , Color.ORANGE),
    ENEMY_ORB2(16, 8, "Test2.png" , Color.ORANGE),
    Player_Bullet1(16, 16, "Test3.png" , Color.PURPLE),
    Player_Bullet2(16, 16, "Test4.png" , Color.PURPLE);

    public final double visualSize;
    public final double hitRadius;
    public final Paint fallbackColor;
    public final Image sprite;

    BulletType(double visualSize, double hitRadius, String res_path, Color fallbackColor) {
        this.visualSize = visualSize;
        this.hitRadius = hitRadius;
        this.fallbackColor = fallbackColor;
        this.sprite = loadSprite(res_path);
    }

    private static Image loadSprite(String resourcePath) {
        String image_path = ClassLoader.getSystemResource(resourcePath).toString();
        return new Image(image_path,false);
    }


}

