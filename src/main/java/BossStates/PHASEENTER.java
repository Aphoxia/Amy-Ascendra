package BossStates;

import Entities.Boss;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.BulletSpawner;
import Utils.TimedAction;
import Utils.Utility;
import javafx.animation.Interpolator;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class PHASEENTER implements BossState{
    private final double DURATION = 10;

    private final List<BulletSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public PHASEENTER (BulletPool pool) {

        spawners = List.of();
        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveTo(Utility.shiftCordX(0), Utility.shiftCordY(250), 7.5, Interpolator.EASE_OUT))
        );
    }

    public void enter(Boss boss) {
        elapsed = 0;
        nextAction = 0;

        for (BulletSpawner spawner : spawners) {
            spawner.stop();
        }
    }

    public boolean update(Boss boss, double dt) {
        elapsed += dt;

        while (nextAction < actions.size()
                && elapsed >= actions.get(nextAction).getTime()) {
            actions.get(nextAction++).run(boss);
        }

        for (BulletSpawner spawner : spawners) {
            spawner.update(dt);
        }

        return elapsed >= DURATION;
    }

    public void exit(Boss boss) {
        for (BulletSpawner spawner : spawners) {
            spawner.setActive(false);
        }
    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
