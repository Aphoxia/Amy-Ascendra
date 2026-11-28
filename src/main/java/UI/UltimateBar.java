package UI;

import Entities.Boss;
import Managers.GameManager;
import Managers.SoundManager;
import Sound.SoundId;
import Utils.Settings;
import Utils.Utility;
import input.KeyBoardInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import static java.lang.Math.*;


public class UltimateBar {
    public static UltimateBar Instance;

    private final double MAXULTENERGY = 50;
    private double GAINULTRATE = 10;
    private double currentEnergy;

    private final double UlTDURATION = 30;
    private double UlTtimer;
    private boolean InUltimate;
    private final double drainRate;

    private Image[] UltAnimation = new Image[30];

    private int spriteIndex = 0;
    private int spriteCounter = 0;
    private int animationSpeed = 2;



    public UltimateBar(){
        currentEnergy = 0;
        UlTtimer = 0;
        InUltimate =  false;
        Instance = this;
        drainRate = -MAXULTENERGY / UlTDURATION;

        for ( int i = 0 ; i < 30 ; i++){
            String number =  String.format("%02d", i);
            UltAnimation[i] = Utility.loadSprite("Images/UltAnimation/GlowStickAnimation_"+number+".png");
        }
    }

    public void update(double dt){
        double Rate  = InUltimate ? drainRate : GAINULTRATE;
        currentEnergy = min((currentEnergy +  (Rate * dt)) , MAXULTENERGY);
        //System.out.println(currentEnergy);

        if (!InUltimate && KeyBoardInput.getInstance().isHeld(KeyCode.X) && currentEnergy >= MAXULTENERGY){
            playULTIMATE();
        }

        if (InUltimate){
            UlTtimer += dt;



            }

            if (UlTtimer >= UlTDURATION || currentEnergy <= 0){
                UlTtimer = 0;
                SoundManager.instance.playMusic("Sound/Music/BossTheme.wav");
                //System.out.println("RUN2");
                InUltimate = false;
            }
        }


    public void render(GraphicsContext gc) {
        if (!InUltimate) return;
        double ultelapse = Math.min(1.0, Math.min(UlTtimer, 30.0 - UlTtimer));
        double rightX = Utility.easeOutCubic(ultelapse);
        System.out.println(spriteIndex);
        gc.drawImage(UltAnimation[spriteIndex], Settings.HORIZONTAL_RESOLUTION - (300 * rightX), 420, 300, 300);
        gc.drawImage(UltAnimation[spriteIndex], 300 * rightX, 420, -300, 300);
        spriteCounter++;

        if (spriteCounter >= animationSpeed) {
            spriteIndex++;

            // Loop back to the first image (0) after the 4th image (3)
            if (spriteIndex >= UltAnimation.length) {
                spriteIndex = 0;
            }

            spriteCounter = 0; // Reset counter
        }
    }

    public void playULTIMATE(){
        InUltimate = true;
        SoundManager.instance.playMusic("Sound/Music/AmyTheme.wav");
        Boss.instance.getbossbulletPool().clearAll();
        GameManager.instance.getScreenShake().trigger(.25,30);
        SoundManager.instance.play(SoundId.BOSSCLEARSCREEN);
        //System.out.println("RUN");

    }

    public boolean isInUltimate() {
        return InUltimate;
    }
}
