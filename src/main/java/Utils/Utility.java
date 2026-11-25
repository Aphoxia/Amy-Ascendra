package Utils;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.Random;

public class Utility {

    private static final Random rand = new Random();


    //MATH FUNCTION
    public static double Normalize(int x , int y){
        if (x == 0 && y == 0) return 1;
        return (double) (1 / (Math.sqrt(Math.pow(x,2) + Math.pow(y,2))));
        // IF THE RESULT IS NEGATIVE THEN I AM DONE
    }

    public static double Distance(double x , double y){
        return (double) Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public static double RandomDouble(double x , double y){
        return (double) (x + (Math.random() * (y - x)));
    }

    public static int RandomInt(int x , int y){
        return rand.nextInt(x , y + 1);
    }

    public static Image loadSprite(String resourcePath) {
        String image_path = ClassLoader.getSystemResource(resourcePath).toString();
        return new Image(image_path,false);
    }

    public static double shiftCordX(double x){
        return ((double )Settings.HORIZONTAL_RESOLUTION / 2 + x);
    }

    public static double shiftCordY(double y){
        return ((double )Settings.VERTICAL_RESOLUTION / 2 - y);
    }

    public static double smoothStep(double t) {
        t = Math.max(0.0, Math.min(1.0, t));
        return t * t * (3.0 - 2.0 * t);
    }

    public static double easeOutCubic(double t) {
        t = Math.max(0.0, Math.min(1.0, t));
        return 1.0 - Math.pow(1.0 - t, 3.0);
    }





    //CURVE FUNCTION
}
