package main;


import input.KeyBoardInput;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Thanks Bro code and Kaarin Gaming (The goat) for turtorial xD
        Stage stage = new Stage();

        Group root = new Group();
        Scene scene = new Scene(root , 1280, 720);
        scene.setFill(Color.BLACK);

        //Keyboard Input Setup
        KeyBoardInput keyBoardInput = new KeyBoardInput();
        keyBoardInput.attachTo(scene);

        //Stage Settings
        stage.setScene(scene);
        stage.setTitle("Amy Ascendra Alpha 0.0.1");    //Title
        stage.setResizable(false); // So that the Sprite does not break

        //Haven't drawn lol
        //Image icon = new Image();
        //stage.getIcons().add(icon);

        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
