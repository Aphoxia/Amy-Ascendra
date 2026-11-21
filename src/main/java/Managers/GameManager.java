package Managers;

import Utils.GameTimer;
import main.Game;

public class GameManager {

    public static final GameManager instance = new GameManager();
    private double timer;

    public GameManager(){
        timer = 0;
    }

    public void update(double dt){
        timer += dt;
        System.out.println(timer);
    }

    public static GameManager getInstance() {
        return instance;
    }
}
