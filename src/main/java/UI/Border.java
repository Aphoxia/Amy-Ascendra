package UI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Border {
    public void render(GraphicsContext gc){

        gc.save();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,370 , 720);
        gc.fillRect(910,0,370 , 720);
        gc.restore();

    }
}
