package Managers;

import Utils.GameTimer;
import main.Game;
import System.ScreenShake;

public class GameManager {

    public static final GameManager instance = new GameManager();
    private final ScreenShake screenShake = new ScreenShake();


    private double timer;

    public GameManager(){


        timer = 0;
    }

    public void update(double dt){
        timer += dt;
        screenShake.update(dt);
        //System.out.println(Math.round(timer));
    }

    public static GameManager getInstance() {
        return instance;
    }

    public ScreenShake getScreenShake() {
        return screenShake;
    }
}
