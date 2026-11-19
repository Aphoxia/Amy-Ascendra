package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import States.GameState;

public class KeyBoardInput {
    //set the Starting State;
    private GameState gameState = GameState.MENU;

    public void attachTo(Scene scene) {
//        scene.addEventFilter(KeyEvent.KEY_TYPED, this::keyTyped);
//        scene.addEventFilter(KeyEvent.KEY_RELEASED, this::keyReleased);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::keyPressed);
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public GameState getGameState() {
        return gameState;
    }

//    public void keyTyped(KeyEvent e) {
//        if (gameState == GameState.MENU) {
//            // Menu text/typed handling if needed
//        } else if (gameState == GameState.PLAYING) {
//            // Game typed handling if needed
//        }
//    }
//
//    public void keyReleased(KeyEvent e) {
//        if (gameState == GameState.MENU) {
//            // Menu key-release handling
//        } else if (gameState == GameState.PLAYING) {
//            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
//                System.out.println("[PLAYING] Released UP/W");
//            }
//        }
//    }

    public void keyPressed(KeyEvent e) {
        if (gameState == GameState.MENU) {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("[MENU] Pressed ENTER -> Starting game!");
                gameState = GameState.PLAYING;
            }
        } else if (gameState == GameState.PLAYING) {
            if (e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
                System.out.println("[PLAYING] Holding/Pressing UP/W");
            } else if (e.getCode() == KeyCode.ESCAPE) {
                System.out.println("[PLAYING] Pressed ESCAPE -> Back to menu");
                gameState = GameState.MENU;
            }
        }
    }
}
