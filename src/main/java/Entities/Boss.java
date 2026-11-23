package Entities;

import BossStates.BossState;
import BossStates.Phase1State1Test1;
import BossStates.Phase1State2Test2;
import ProjectiesLogic.BulletPool;
import ProjectiesLogic.BulletSpawner;
import Utils.Settings;
import Utils.Utility;
import javafx.animation.Interpolator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Entity{

    private final List<BossState> states = List.of(new Phase1State1Test1(bulletPool),new Phase1State2Test2(bulletPool));

    private final double Phase2Threshold = 0.60;
    private final double Phase3Threshold = 0.30;

    private double hp;
    private double maxHp;
    private int stateIndex = -1;
    private BossState currentState;

    private double hitboxPercentage;

    private Interpolator moveEasing;

    private enum MovementMode {
        NONE, TWEEN, VELOCITY
    }

    private MovementMode movementMode = MovementMode.NONE;

    private double vx, vy;
    private double startX, startY;
    private double targetX, targetY;
    private double moveDuration, moveElapsed;



    public Boss(double x , double y , double hitboxPercentage , double visualSize , BulletPool bulletPool){
        super(x,y,visualSize,bulletPool);
        this.hitboxPercentage = hitboxPercentage;
        vx = 0;
        vy = 0;
        hp = Settings.BOSSHP;
        maxHp = Settings.BOSSHP;
    }

    public void render(GraphicsContext gc){

        gc.save();

        gc.setFill(Color.RED);
        gc.fillRect(x - 32, y - 32 , 64 , 64);

        gc.restore();

    }

    public void update(double dt) {
        updateMovement(dt);

        if (currentState == null) {
            advanceState();
        }

        if (currentState.update(this, dt)) {
            advanceState();
        }

    }

    private void advanceState() {
        if (currentState != null) {
            currentState.exit(this);
        }

        stateIndex = Utility.RandomInt(0,1);
        System.out.println(stateIndex);
        currentState = states.get(stateIndex);
        currentState.enter(this);

//        for (int checked = 0; checked < states.size(); checked++) {
//            stateIndex = (stateIndex + 1) % states.size();
//            BossState candidate = states.get(stateIndex);
//
//            if (!candidate.lowHpOnly() || getHpPercent() <= Phase2Threshold) {
//                currentState = candidate;
//                currentState.enter(this);
//                return;
//            }
//        }
    }

    public void moveTo(double targetX, double targetY, double duration , Interpolator moveEasing) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.startX = x;
        this.startY = y;
        this.moveDuration = duration;
        this.moveEasing = moveEasing;
        this.moveElapsed = 0;

        if (duration <= 0) {
            x = targetX;
            y = targetY;
            movementMode = MovementMode.NONE;
        } else {
            movementMode = MovementMode.TWEEN;
        }
    }

    private void updateMovement(double dt) {
        switch (movementMode) {
            case TWEEN :{
                moveElapsed = Math.min(moveElapsed + dt, moveDuration);
                double t = moveElapsed / moveDuration;
                double eased = moveEasing.interpolate(0.0, 1.0, t);

                x = startX + (targetX - startX) * eased;
                y = startY + (targetY - startY) * eased;

                if (t >= 1) {
                    x = targetX;
                    y = targetY;
                    movementMode = MovementMode.NONE;
                }
                break;
            }

            case VELOCITY : {
                x += vx * dt;
                y += vy * dt;
                break;
            }

            case NONE :{
                break;
            }
        }
    }
    public double getHpPercent(){
        if (maxHp == 0) return 1;
        return hp / maxHp;
    }
}
