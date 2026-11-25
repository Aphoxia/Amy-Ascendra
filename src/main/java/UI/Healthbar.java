package UI;

import Entities.Boss;
import Entities.Player;
import com.sun.scenario.Settings;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Healthbar {


    public void render(GraphicsContext gc){
        double hitpoint = Boss.instance.getHpPercent();
        double playerhitPoint = Player.Instance.getHitpointPercent();
        System.out.println(playerhitPoint);

        gc.save();

        gc.setFill(Color.WHITE);
        gc.fillRect(320,10,640 * hitpoint , 3);

        gc.setFill(Color.RED);
        gc.fillRect(960,120,250 * playerhitPoint , 5);


        gc.restore();
    }
}
