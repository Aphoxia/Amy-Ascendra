package Utils;

import Entities.Boss;

import java.util.function.Consumer;

public final class TimedAction {
    private final double time;
    private final Consumer<Boss> action;

    public TimedAction(double time, Consumer<Boss> action) {
        this.time = time;
        this.action = action;
    }

    public double getTime() {
        return time;
    }

    public void run(Boss boss) {
        action.accept(boss);
    }
}