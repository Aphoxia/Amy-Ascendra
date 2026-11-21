package Pattern;

public  class BulletPattern {
  // SPIN THE SHOOTING DIRECTION
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
