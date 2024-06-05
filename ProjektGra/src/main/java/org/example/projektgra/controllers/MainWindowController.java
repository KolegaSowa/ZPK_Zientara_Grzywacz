package org.example.projektgra.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.projektgra.Main;
import org.example.projektgra.game.SpaceInvaders;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane mainWindow;
    @FXML
    private Button startGameButton, settingsButton;
    private Stage stage;
    private Parent root;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = new Stage();
    }

    public void startGame(){
        SpaceInvaders spaceInvaders = new SpaceInvaders();
        spaceInvaders.start(stage);
    }


    public void openSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("SettingsWindow.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
