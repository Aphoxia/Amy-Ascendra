package main;


import Utils.Settings;
import Utils.Utility;
import input.KeyBoardInput;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    private KeyBoardInput keyBoardInput;
    private Game game;


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Thanks Bro code and Kaarin Gaming (The goats) for tutorial xD
        Stage stage = new Stage();

        Group root = new Group();
        Scene scene = new Scene(root , Settings.HORIZONTAL_RESOLUTION, Settings.VERTICAL_RESOLUTION);
        scene.setFill(Color.BLACK);

        //Canvas Setup
        //CANVAS IS IDEAL FOR GAME SO TY
        Canvas canvas = new Canvas(Settings.HORIZONTAL_RESOLUTION, Settings.VERTICAL_RESOLUTION);
        root.getChildren().add(canvas);
        game = new Game(canvas);

        //Keyboard Input Setup
        keyBoardInput = new KeyBoardInput();
        keyBoardInput.attachTo(scene);

        //Stage Settings
        stage.setScene(scene);
        stage.setTitle("Amy Ascendra Alpha 0.0.1");    //Title
        stage.setResizable(false); // So that the Sprite does not break

        //Haven't drawn lol
        //Image icon = new Image();
        //stage.getIcons().add(icon);

        stage.show();
        game.start();
        // START GAME LOOP

    }

    public static void main(String[] args) {
        launch(args);
    }
}
