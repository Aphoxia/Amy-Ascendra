package SpriteLoader;


import javafx.scene.image.Image;


public enum BulletType {

    ENEMY_ORB1( 16,.3,   0,0 ),
    ENEMY_ORB2( 16,.3,   1,0 ),
    ENEMY_ORB3( 16,.3, 2,0 ),
    ENEMY_ORB4( 16,.3, 3,0 ),
    ENEMY_ORB5(16,.3,  4,0 ),
    ENEMY_ORB6(16,.3, 5,0 ),
    ENEMY_ORB7( 16,.3,  6,0 ),
    ENEMY_ORB8( 16,.3,  7,0 ),
    ENEMY_ORB9( 16,.3, 8,0 ),
    ENEMY_ORB10( 16,.3,  9,0 ),
    ENEMY_ORB11( 16,.3,  10,0 ),
    ENEMY_ORB12( 16,.3,  11,0 ),
    ENEMY_ORB13(16,.3,  12,0 ),
    ENEMY_ORB14(16,.3, 13,0 ),
    ENEMY_ORB15( 16,.3,  14,0 ),
    ENEMY_HORB1( 16,.3,   0,1 ),
    ENEMY_HORB2( 16,.3,   1,1 ),
    ENEMY_HORB3( 16,.3, 2,1 ),
    ENEMY_HORB4( 16,.3, 3,1 ),
    ENEMY_HORB5(16,.3,  4,1 ),
    ENEMY_HORB6(16,.3, 5,1 ),
    ENEMY_HORB7( 16,.3,  6,1 ),
    ENEMY_HORB8( 16,.3,  7,1 ),
    ENEMY_HORB9( 16,.3, 8,1 ),
    ENEMY_HORB10( 16,.3,  9,1 ),
    ENEMY_HORB11( 16,.3,  10,1 ),
    ENEMY_HORB12( 16,.3,  11,1 ),
    ENEMY_HORB13( 16,.3,  12,1 ),
    ENEMY_HORB14( 16,.3, 13,1 ),
    ENEMY_HORB15( 16,.3,  14,1 ),
    ENEMY_Bullet1( 16,.3,   0,2 ),
    ENEMY_Bullet2( 16,.3,   1,2 ),
    ENEMY_Bullet3( 16,.3, 2,2 ),
    ENEMY_Bullet4( 16,.3, 3,2 ),
    ENEMY_Bullet5( 16,.3,  4,2 ),
    ENEMY_Bullet6( 16,.3, 5,2 ),
    ENEMY_Bullet7( 16,.3,  6,2 ),
    ENEMY_Bullet8( 16,.3,  7,2 ),
    ENEMY_Bullet9( 16,.3, 8,2 ),
    ENEMY_Bullet10( 16,.3,  9,2 ),
    ENEMY_Bullet11( 16,.3,  10,2 ),
    ENEMY_Bullet12( 16,.3,  11,2 ),
    ENEMY_Bullet13( 16,.3,  12,2 ),
    ENEMY_Bullet14( 16,.3, 13,2 ),
    ENEMY_Bullet15( 16,.3,  14,2 ),
    ENEMY_Star1( 16,.3,   0,3 ),
    ENEMY_Star2( 16,.3,   1,3 ),
    ENEMY_Star3( 16,.3, 2,3 ),
    ENEMY_Star4( 16,.3, 3,3 ),
    ENEMY_Star5( 16,.3,  4,3 ),
    ENEMY_Star6( 16,.3, 5,3 ),
    ENEMY_Star7( 16,.3,  6,3 ),
    ENEMY_Star8( 16,.3,  7,3 ),
    ENEMY_Star9( 16,.3, 8,3 ),
    ENEMY_Star10( 16,.3,  9,3 ),
    ENEMY_Star11( 16,.3,  10,3 ),
    ENEMY_Star12( 16,.3,  11,3 ),
    ENEMY_Star13( 16,.3,  12,3 ),
    ENEMY_Star14( 16,.3, 13,3 ),
    ENEMY_Star15( 16,.3,  14,3 ),
    ENEMY_HStar1( 16,.3,   0,4),
    ENEMY_HStar2( 16,.3,   1,4 ),
    ENEMY_HStar3( 16,.3, 2,4 ),
    ENEMY_HStar4( 16,.3, 3,4 ),
    ENEMY_HStar5( 16,.3,  4,4 ),
    ENEMY_HStar6( 16,.3, 5,4 ),
    ENEMY_HStar7( 16,.3,  6,4 ),
    ENEMY_HStar8( 16,.3,  7,4 ),
    ENEMY_HStar9( 16,.3, 8,4 ),
    ENEMY_HStar10( 16,.3,  9,4 ),
    ENEMY_HStar11( 16,.3,  10,4 ),
    ENEMY_HStar12( 16,.3,  11,4 ),
    ENEMY_HStar13( 16,.3,  12,4 ),
    ENEMY_HStar14( 16,.3, 13,4 ),
    ENEMY_HStar15( 16,.3,  14,4 ),
    Player_Bullet1(16,.75 , 0,5),
    Player_Bullet2(16,.75 , 1,5),
    Player_Bullet3( 16,.75, 2,5),
    Player_Bullet4( 16,.75, 3,5),
    ENEMY_BSTAR1( 32,.3,   8,0 ),
    ENEMY_BSTAR2( 32,.3,   9,0 ),
    ENEMY_BSTAR3( 32,.3, 10,0 ),
    ENEMY_BSTAR4( 32,.3, 11,0 ),
    ENEMY_BSTAR5(32,.3,  12,0 ),
    ENEMY_BSTAR6(32,.3, 13,0 ),
    ENEMY_BSTAR7( 32,.3,  14,0 ),
    ENEMY_BSTAR8( 32,.3,  15,0 ),
    ENEMY_BSTAR9( 32,.3, 16,0 ),
    ENEMY_BSTAR10( 32,.3,  17,0 ),
    ENEMY_BSTAR11( 32,.3,  18,0 ),
    ENEMY_BSTAR12( 32,.3,  19,0 ),
    ENEMY_BSTAR13(32,.3,  20,0 ),
    ENEMY_BSTAR14(32,.3, 21,0 ),
    ENEMY_BSTAR15( 32,.3,  22,0 );


    public double visualSize;
    public double hitRadiusPecentage;
    public final Image sprite;

    BulletType(int visualSize , double hitRadiusPercentage , int col , int row) {
        this.visualSize = visualSize;
        this.hitRadiusPecentage = hitRadiusPercentage;
        this.sprite = SpriteLoader.loadSprite("Images/Bullets/BulletSpriteSheet.png",(int) visualSize, (int) visualSize,col , row );
    }



}

