package org.example.projektgra.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.projektgra.Main;
import org.example.projektgra.Sounds;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsWindowController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private CheckBox effectBox, musicBox;
    @FXML
    private Slider effectSlider, musicSlider;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private MediaPlayer musicMedia = Sounds.musicInGame.getMusicPlayer();
    private MediaPlayer effectMedia = Sounds.effectInGame.getMusicPlayer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (musicMedia != null) {
                musicMedia.setVolume(newValue.doubleValue() * 0.01);
            }
        });

        effectSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (effectMedia != null) {
                effectMedia.setVolume(newValue.doubleValue() * 0.01);
            }
        });
    }

    public void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("StartGameWindow.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onOrOffMusic() {
        if (!musicBox.isSelected()) {
            Sounds.musicInGame.setDisableMediaPlayer(true);
        } else {
            Sounds.musicInGame.setDisableMediaPlayer(false);
        }
    }

    public void onOrOffEffect() {
        if (!effectBox.isSelected()) {
            Sounds.effectInGame.setDisableMediaPlayer(true);
        } else {
            Sounds.effectInGame.setDisableMediaPlayer(false);
        }
    }
}
