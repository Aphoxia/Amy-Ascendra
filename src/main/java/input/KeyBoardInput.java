package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.util.EnumSet;
import java.util.Set;

public class KeyBoardInput {

    private static volatile KeyBoardInput instance;

    private final Set<KeyCode> heldKeys = EnumSet.noneOf(KeyCode.class);

    public KeyBoardInput(){
        instance = this;
    }
    public static KeyBoardInput getInstance() {
        return instance;
    }

    public void attachTo(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_TYPED, this::keyTyped);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, this::keyReleased);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);
    }

    public boolean isHeld(KeyCode code) {
        return code != null && heldKeys.contains(code);
    }
    public void clearHeldKeys() {
        heldKeys.clear();
    }

    public void keyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code != null) heldKeys.remove(code);
    }
    public void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code != null) heldKeys.add(code);
    }
    public void keyTyped(KeyEvent e) {
        //CURRENTLY DOES NOTHING
    }
}
