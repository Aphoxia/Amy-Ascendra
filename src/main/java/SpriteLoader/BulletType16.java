package SpriteLoader;


import Utils.Settings;
import javafx.scene.image.Image;


public enum BulletType16 {

    ENEMY_ORB1(16, .5,   0,0 ),
    ENEMY_ORB2(16, .5,   1,0 ),
    ENEMY_ORB3(16, .5, 2,0 ),
    ENEMY_ORB4(16, .5, 3,0 ),
    ENEMY_ORB5(16, .5,  4,0 ),
    ENEMY_ORB6(16, .5, 5,0 ),
    ENEMY_ORB7(16, .5,  6,0 ),
    ENEMY_ORB8(16, .5,  7,0 ),
    ENEMY_ORB9(16, .5, 8,0 ),
    ENEMY_ORB10(16, .5,  9,0 ),
    ENEMY_ORB11(16, .5,  10,0 ),
    ENEMY_ORB12(16, .5,  11,0 ),
    ENEMY_ORB13(16, .5,  12,0 ),
    ENEMY_ORB14(16, .5, 13,0 ),
    ENEMY_ORB15(16, .5,  14,0 ),
    ENEMY_HORB1(16, .5,   0,1 ),
    ENEMY_HORB2(16, .5,   1,1 ),
    ENEMY_HORB3(16, .5, 2,1 ),
    ENEMY_HORB4(16, .5, 3,1 ),
    ENEMY_HORB5(16, .5,  4,1 ),
    ENEMY_HORB6(16, .5, 5,1 ),
    ENEMY_HORB7(16, .5,  6,1 ),
    ENEMY_HORB8(16, .5,  7,1 ),
    ENEMY_HORB9(16, .5, 8,1 ),
    ENEMY_HORB10(16, .5,  9,1 ),
    ENEMY_HORB11(16, .5,  10,1 ),
    ENEMY_HORB12(16, .5,  11,1 ),
    ENEMY_HORB13(16, .5,  12,1 ),
    ENEMY_HORB14(16, .5, 13,1 ),
    ENEMY_HORB15(16, .5,  14,1 ),
    ENEMY_Bullet1(16, .5,   0,2 ),
    ENEMY_Bullet2(16, .5,   1,2 ),
    ENEMY_Bullet3(16, .5, 2,2 ),
    ENEMY_Bullet4(16, .5, 3,2 ),
    ENEMY_Bullet5(16, .5,  4,2 ),
    ENEMY_Bullet6(16, .5, 5,2 ),
    ENEMY_Bullet7(16, .5,  6,2 ),
    ENEMY_Bullet8(16, .5,  7,2 ),
    ENEMY_Bullet9(16, .5, 8,2 ),
    ENEMY_Bullet10(16, .5,  9,2 ),
    ENEMY_Bullet11(16, .5,  10,2 ),
    ENEMY_Bullet12(16, .5,  11,2 ),
    ENEMY_Bullet13(16, .5,  12,2 ),
    ENEMY_Bullet14(16, .5, 13,2 ),
    ENEMY_Bullet15(16, .5,  14,2 ),
    ENEMY_Star1(16, .5,   0,3 ),
    ENEMY_Star2(16, .5,   1,3 ),
    ENEMY_Star3(16, .5, 2,3 ),
    ENEMY_Star4(16, .5, 3,3 ),
    ENEMY_Star5(16, .5,  4,3 ),
    ENEMY_Star6(16, .5, 5,3 ),
    ENEMY_Star7(16, .5,  6,3 ),
    ENEMY_Star8(16, .5,  7,3 ),
    ENEMY_Star9(16, .5, 8,3 ),
    ENEMY_Star10(16, .5,  9,3 ),
    ENEMY_Star11(16, .5,  10,3 ),
    ENEMY_Star12(16, .5,  11,3 ),
    ENEMY_Star13(16, .5,  12,3 ),
    ENEMY_Star14(16, .5, 13,3 ),
    ENEMY_Star15(16, .5,  14,3 ),
    Player_Bullet1(16,.75 , 0,5),
    Player_Bullet2(16,.75 , 1,5),
    Player_Bullet3(16, .75, 2,5),
    Player_Bullet4(16, .75, 3,5);;

    public final double visualSize;
    public final double hitRadiusPecentage;
    public final Image sprite;

    BulletType16(double visualSize, double hitRadiusPercentage , int col , int row) {
        this.visualSize = visualSize * Settings.PIXELSCALE;
        this.hitRadiusPecentage = visualSize * hitRadiusPercentage * Settings.PIXELSCALE;
        this.sprite = SpriteLoader.loadSprite("Images/Bullets/Bullet16SpriteSheet.png",16,16,col , row );
    }



}

