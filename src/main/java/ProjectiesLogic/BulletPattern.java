package ProjectiesLogic;

public  class BulletPattern {

    private int bulletCount ;
    private double spreadAngle;

    public BulletPattern( int bulletCount , double spreadAngle){

        this.bulletCount = bulletCount;
        this.spreadAngle = spreadAngle;
    }


    public double getSpreadAngle() {
        return spreadAngle;
    }

    public int getBulletCount() {
        return bulletCount;
    }


}
