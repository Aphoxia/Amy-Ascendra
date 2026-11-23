package BossStates;

import Entities.Boss;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.BulletSpawner;
import Utils.TimedAction;
import javafx.animation.Interpolator;

import java.util.List;

public class Phase1State1Test1 implements BossState{

    private final double DURATION = 5;

//    private final BulletSpawner leftSpawner;
//    private final BulletSpawner rightSpawner;
//    private final BulletSpawner ringSpawner;

    private final List<BulletSpawner> spawners;
    private final List<TimedAction> actions;

    private double elapsed;
    private int nextAction;

    public Phase1State1Test1 (BulletPool pool) {
        // Use your existing BulletSpawner constructor/configuration here.
//        leftSpawner = createLeftSpawner(pool);
//        rightSpawner = createRightSpawner(pool);
//        ringSpawner = createRingSpawner(pool);

        spawners = List.of();

        actions = List.of(
                new TimedAction(0.0, boss -> boss.moveTo(300, 180, 1.0, Interpolator.EASE_BOTH)),
                new TimedAction(2.0, boss -> boss.moveTo(500, 230, 1.0, Interpolator.EASE_BOTH))

//                new TimedAction(0.5, boss -> leftSpawner.setActive(true)),
//                new TimedAction(0.5, boss -> rightSpawner.setActive(true)),
//                new TimedAction(0.5, boss -> ringSpawner.setActive(true)),
//
//                new TimedAction(5.0, boss -> leftSpawner.setActive(false)),
//
//                new TimedAction(7.0, boss ->
//                        boss.moveTo(300, 220, 2.0, Interpolator.EASE_BOTH)),
//
//                new TimedAction(12.0, boss -> ringSpawner.setActive(false)),
//                new TimedAction(15.0, boss -> rightSpawner.setActive(false))
        );
    }

    @Override
    public void enter(Boss boss) {
        elapsed = 0;
        nextAction = 0;

        for (BulletSpawner spawner : spawners) {
            spawner.setActive(false);
            spawner.stop(); // Reset its firing timer/counters.
        }
    }

    @Override
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

    @Override
    public void exit(Boss boss) {
        for (BulletSpawner spawner : spawners) {
            spawner.setActive(false);
        }
    }

    private BulletSpawner createLeftSpawner(BulletPool pool) {
        // Construct with your left-side attack settings.
        throw new UnsupportedOperationException("Fill in your constructor");
    }

    private BulletSpawner createRightSpawner(BulletPool pool) {
        // Construct with your right-side attack settings.
        throw new UnsupportedOperationException("Fill in your constructor");
    }

    private BulletSpawner createRingSpawner(BulletPool pool) {
        // Construct with your ring attack settings.
        throw new UnsupportedOperationException("Fill in your constructor");
    }
}