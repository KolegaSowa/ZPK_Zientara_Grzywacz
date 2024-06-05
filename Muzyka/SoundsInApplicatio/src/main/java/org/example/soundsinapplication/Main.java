package org.example.soundsinapplication;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.soundscomponent.SoundsComponent;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            SoundsComponent soundsInApplication = new SoundsComponent("src/main/java/music");
            soundsInApplication.playSound();
        } catch (Exception e) {
            System.out.println("Error initializing SoundsInApplication: " + e.getMessage());
        }
    }
}
