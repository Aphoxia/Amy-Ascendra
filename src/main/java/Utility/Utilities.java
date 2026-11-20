package Utility;

public class Utilities {

    //SETTINGS
    public static final int HORIZONTAL_RESOLUTION = 1280;
    public static final int VERTICAL_RESOLUTION = 720;
    public static final int FPS = 60;
    public static final int UPS = 60;
    //public static final int PIXELSCALE = 2;


    //FUNCTION
    public static float Normalize(int x , int y){
        if (x == 0 && y == 0) return 1;
        return (float) (1 / (Math.sqrt(Math.pow(x,2) + Math.pow(y,2))));
        // IF THE RESULT IS NEGATIVE THEN I AM DONE
    }

    public static float Distance(float x , float y){
        return (float) Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public static float Random(float x , float y){
        return (float) (x + (Math.random() * (y - x)));
    }
}
