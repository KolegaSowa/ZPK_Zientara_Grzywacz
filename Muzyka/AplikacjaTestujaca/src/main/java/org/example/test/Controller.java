package org.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import org.example.soundscomponent.SoundsComponent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;

    private SoundsComponent soundsInApplication;
    private MediaPlayer mediaPlayer;
    private final int[] speeds;
    private Timer timer;

    public Controller() {
        soundsInApplication = new SoundsComponent("src/main/java/music");
        mediaPlayer = soundsInApplication.getMusicPlayer();
        speeds = soundsInApplication.getSpeeds();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        changeSongLabel();

        for (int speed : speeds) {
            speedBox.getItems().add(speed + "%");
        }

        speedBox.setOnAction(this::changeSpeed);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() * 0.01);
            }
        });
        songProgressBar.setStyle("-fx-accent: #00FF00;");
        changeProgressBar();
    }

    public void playMedia() {
        changeSpeed(null);
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        soundsInApplication.playSound();
        startTimer();
    }

    public void pauseMedia() {
        soundsInApplication.pauseSound();
        stopTimer();
    }

    public void resetMedia() {
        songProgressBar.setProgress(0);
        soundsInApplication.resetSound();
        startTimer();
    }

    public void previousMedia() {
        soundsInApplication.previousSound();
        updateMediaPlayer();
    }

    public void nextMedia() {
        soundsInApplication.nextSound();
        updateMediaPlayer();
    }

    public void changeSpeed(ActionEvent event) {
        if (speedBox.getValue() == null) {
            mediaPlayer.setRate(1);
        } else {
            mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
        }
    }

    private void changeSongLabel() {
        songLabel.setText(soundsInApplication.getSongs().get(soundsInApplication.getSongNumber()).getName());
    }

    private void changeProgressBar() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                    double totalDuration = mediaPlayer.getTotalDuration().toSeconds();
                    songProgressBar.setProgress(currentTime / totalDuration);
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        changeProgressBar();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void updateMediaPlayer() {
        stopTimer();
        mediaPlayer = soundsInApplication.getMusicPlayer();
        changeSongLabel();
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        changeSpeed(null);
        startTimer();
    }
}