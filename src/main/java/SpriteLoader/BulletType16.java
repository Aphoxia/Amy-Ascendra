package SpriteLoader;


import Utils.Settings;
import javafx.scene.image.Image;


public enum BulletType16 {

    ENEMY_ORB1( .5,   0,0 ),
    ENEMY_ORB2( .5,   1,0 ),
    ENEMY_ORB3( .5, 2,0 ),
    ENEMY_ORB4( .5, 3,0 ),
    ENEMY_ORB5(.5,  4,0 ),
    ENEMY_ORB6(.5, 5,0 ),
    ENEMY_ORB7( .5,  6,0 ),
    ENEMY_ORB8( .5,  7,0 ),
    ENEMY_ORB9( .5, 8,0 ),
    ENEMY_ORB10( .5,  9,0 ),
    ENEMY_ORB11( .5,  10,0 ),
    ENEMY_ORB12( .5,  11,0 ),
    ENEMY_ORB13(.5,  12,0 ),
    ENEMY_ORB14(.5, 13,0 ),
    ENEMY_ORB15( .5,  14,0 ),
    ENEMY_HORB1( .5,   0,1 ),
    ENEMY_HORB2( .5,   1,1 ),
    ENEMY_HORB3( .5, 2,1 ),
    ENEMY_HORB4( .5, 3,1 ),
    ENEMY_HORB5(.5,  4,1 ),
    ENEMY_HORB6(.5, 5,1 ),
    ENEMY_HORB7( .5,  6,1 ),
    ENEMY_HORB8( .5,  7,1 ),
    ENEMY_HORB9( .5, 8,1 ),
    ENEMY_HORB10( .5,  9,1 ),
    ENEMY_HORB11( .5,  10,1 ),
    ENEMY_HORB12( .5,  11,1 ),
    ENEMY_HORB13( .5,  12,1 ),
    ENEMY_HORB14( .5, 13,1 ),
    ENEMY_HORB15( .5,  14,1 ),
    ENEMY_Bullet1( .5,   0,2 ),
    ENEMY_Bullet2( .5,   1,2 ),
    ENEMY_Bullet3( .5, 2,2 ),
    ENEMY_Bullet4( .5, 3,2 ),
    ENEMY_Bullet5( .5,  4,2 ),
    ENEMY_Bullet6( .5, 5,2 ),
    ENEMY_Bullet7( .5,  6,2 ),
    ENEMY_Bullet8( .5,  7,2 ),
    ENEMY_Bullet9( .5, 8,2 ),
    ENEMY_Bullet10( .5,  9,2 ),
    ENEMY_Bullet11( .5,  10,2 ),
    ENEMY_Bullet12( .5,  11,2 ),
    ENEMY_Bullet13( .5,  12,2 ),
    ENEMY_Bullet14( .5, 13,2 ),
    ENEMY_Bullet15( .5,  14,2 ),
    ENEMY_Star1( .5,   0,3 ),
    ENEMY_Star2( .5,   1,3 ),
    ENEMY_Star3( .5, 2,3 ),
    ENEMY_Star4( .5, 3,3 ),
    ENEMY_Star5( .5,  4,3 ),
    ENEMY_Star6( .5, 5,3 ),
    ENEMY_Star7( .5,  6,3 ),
    ENEMY_Star8( .5,  7,3 ),
    ENEMY_Star9( .5, 8,3 ),
    ENEMY_Star10( .5,  9,3 ),
    ENEMY_Star11( .5,  10,3 ),
    ENEMY_Star12( .5,  11,3 ),
    ENEMY_Star13( .5,  12,3 ),
    ENEMY_Star14( .5, 13,3 ),
    ENEMY_Star15( .5,  14,3 ),
    ENEMY_HStar1( .5,   0,4),
    ENEMY_HStar2( .5,   1,4 ),
    ENEMY_HStar3( .5, 2,4 ),
    ENEMY_HStar4( .5, 3,4 ),
    ENEMY_HStar5( .5,  4,4 ),
    ENEMY_HStar6( .5, 5,4 ),
    ENEMY_HStar7( .5,  6,4 ),
    ENEMY_HStar8( .5,  7,4 ),
    ENEMY_HStar9( .5, 8,4 ),
    ENEMY_HStar10( .5,  9,4 ),
    ENEMY_HStar11( .5,  10,4 ),
    ENEMY_HStar12( .5,  11,4 ),
    ENEMY_HStar13( .5,  12,4 ),
    ENEMY_HStar14( .5, 13,4 ),
    ENEMY_HStar15( .5,  14,4 ),
    Player_Bullet1(.75 , 0,5),
    Player_Bullet2(.75 , 1,5),
    Player_Bullet3( .75, 2,5),
    Player_Bullet4( .75, 3,5);;

    public final double visualSize = 16;
    public final double hitRadiusPecentage;
    public final Image sprite;

    BulletType16(double hitRadiusPercentage , int col , int row) {
        this.hitRadiusPecentage = visualSize * hitRadiusPercentage;
        this.sprite = SpriteLoader.loadSprite("Images/Bullets/Bullet16SpriteSheet.png",(int) visualSize, (int) visualSize,col , row );
    }



}

