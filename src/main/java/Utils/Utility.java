package Utils;

public class Utility {

    //MATH
    public static double Normalize(int x , int y){
        if (x == 0 && y == 0) return 1;
        return (double) (1 / (Math.sqrt(Math.pow(x,2) + Math.pow(y,2))));
        // IF THE RESULT IS NEGATIVE THEN I AM DONE
    }

    public static double Distance(double x , double y){
        return (double) Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public static double Random(double x , double y){
        return (double) (x + (Math.random() * (y - x)));
    }

    //CURVE FUNCTION
}
