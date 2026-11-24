package BossStates;

import Entities.Boss;
import javafx.scene.canvas.GraphicsContext;

public interface BossState {
    void enter(Boss boss);
    boolean update(Boss boss, double dt); // true when this state is finished
    void exit(Boss boss);
    void render(GraphicsContext gc);

    default boolean lowHpOnly() {
        return false;
    }
}
