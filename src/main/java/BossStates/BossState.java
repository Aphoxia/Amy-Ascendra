package BossStates;

import Entities.Boss;

public interface BossState {
    void enter(Boss boss);
    boolean update(Boss boss, double dt); // true when this state is finished
    void exit(Boss boss);

    default boolean lowHpOnly() {
        return false;
    }
}
