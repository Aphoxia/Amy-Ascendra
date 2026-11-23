package Entities;

import BossStates.*;
import Managers.GameManager;
import ProjectiesLogic.BulletPool;
import Utils.Settings;
import Utils.Utility;
import javafx.animation.Interpolator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Boss extends Entity{

    private BulletPool bossbulletPool;

    private final List<BossState> states;

    private final double Phase2Threshold = 0.60;
    private final double Phase3Threshold = 0.30;

    private int hp;
    private int maxHp;
    private int stateIndex = -1;
    private int prevstateIndex = -1;
    private BossState currentState;

    private final double AmpetudeSwing = 2;
    private final double AmpetudeSwingTime = 4;

    private Interpolator moveEasing;

    private double startX, startY;
    private double targetX, targetY;
    private double moveDuration, moveElapsed;
    private boolean isMoving;



    public Boss(double x , double y , double hitboxPercentage , double visualSize , BulletPool bossbulletPool){
        super(x,y,visualSize,hitboxPercentage,bossbulletPool,true);
        this.hitboxPercentage = hitboxPercentage;
        hp = Settings.BOSSHP;
        maxHp = Settings.BOSSHP;
        this.bossbulletPool = bossbulletPool;

        states = List.of(
                new Phase1State1(bossbulletPool),
                new Phase1State2(bossbulletPool),
                new Phase1State3(bossbulletPool),
                new Phase3State1(bossbulletPool)
        );

        currentState = new PHASEENTER(bossbulletPool);
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
        if(!isMoving) {
            y += (Math.sin(GameManager.instance.getGameTime() * 2)) * AmpetudeSwing / AmpetudeSwingTime ;
        }

        if (currentState.update(this, dt)) {
            advanceState();
        }

    }

    private void advanceState() {
        if (currentState != null) {
            currentState.exit(this);
        }



        while (stateIndex == prevstateIndex){
            stateIndex = Utility.RandomInt(0,3);
        }
        System.out.println(stateIndex);

        currentState = states.get(stateIndex);
        prevstateIndex = stateIndex;
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
        this.isMoving = true;

        if (duration <= 0) {
            x = targetX;
            y = targetY;
            this.isMoving = false;
        }
    }

    private void updateMovement(double dt) {
        if (!isMoving) return;
        moveElapsed = Math.min(moveElapsed + dt, moveDuration);
        double t = moveElapsed / moveDuration;
        double eased = moveEasing.interpolate(0.0, 1.0, t);

        x = startX + (targetX - startX) * eased;
        y = startY + (targetY - startY) * eased;

        if (t >= 1) {
            x = targetX;
            y = targetY;
            isMoving = false;
        }  else{
            isMoving = true;
        }
    }

    public double getHpPercent(){
        if (maxHp == 0) return 1;
        return hp / maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp(){
        return hp;
    }

    public BulletPool getbossbulletPool(){
        return bossbulletPool;
    }
}
