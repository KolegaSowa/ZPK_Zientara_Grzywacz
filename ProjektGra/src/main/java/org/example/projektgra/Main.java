package org.example.projektgra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Sounds sounds = new Sounds();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StartGameWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Space Invaders");
        stage.setScene(scene);
        stage.show();

        sounds.playMusic();
    }

    public static void main(String[] args) {
        launch();
    }
}