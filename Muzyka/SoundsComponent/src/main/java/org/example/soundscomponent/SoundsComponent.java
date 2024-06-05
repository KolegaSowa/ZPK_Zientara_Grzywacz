package org.example.soundscomponent;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SoundsComponent implements Serializable {

    private Media media;
    private MediaPlayer musicPlayer;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    private Timer timer;
    private TimerTask task;
    private double currentTime;
    private double endTime;
    private boolean running;
    private boolean disableMediaPlayer = false;
    private boolean autoPlayNext = true;

    public boolean isDisableMediaPlayer() {
        return disableMediaPlayer;
    }

    public void setDisableMediaPlayer(boolean disableMediaPlayer) {
        this.disableMediaPlayer = disableMediaPlayer;
        if (disableMediaPlayer){
            pauseSound();
        } else {
            playSound();
        }
    }

    public boolean isAutoPlayNext() {
        return autoPlayNext;
    }

    public void setAutoPlayNext(boolean autoPlayNext) {
        this.autoPlayNext = autoPlayNext;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void setMusicPlayer(MediaPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public ArrayList<File> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<File> songs) {
        this.songs = songs;
    }

    public int getSongNumber() {
        return songNumber;
    }

    public void setSongNumber(int songNumber) {
        this.songNumber = songNumber;
    }

    public int[] getSpeeds() {
        return speeds;
    }

    public void setSpeeds(int[] speeds) {
        this.speeds = speeds;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public TimerTask getTask() {
        return task;
    }

    public void setTask(TimerTask task) {
        this.task = task;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public SoundsComponent(String soundsPath) {

        songs = new ArrayList<>();
        File directory = new File(soundsPath);

        files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                songs.add(file);
            }
        }

        media = new Media(songs.get(songNumber).toURI().toString());
        musicPlayer = new MediaPlayer(media);
    }

    public void playSound() {
        if (!disableMediaPlayer){
            beginTimer();
            musicPlayer.play();
        }
    }

    public void pauseSound() {
        musicPlayer.pause();
        cancelTimer();
    }

    public void resetSound() {
        musicPlayer.seek(Duration.seconds(0));
    }

    public void previousSound() {
        if (songNumber > 0) {
            songNumber--;
            musicPlayer.stop();

            if (running) {
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            musicPlayer = new MediaPlayer(media);

            playSound();
        } else {
            songNumber = songs.size() - 1;
            musicPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            musicPlayer = new MediaPlayer(media);
            playSound();
        }
    }

    public void nextSound() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            musicPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            musicPlayer = new MediaPlayer(media);

            playSound();
        } else {
            songNumber = 0;
            musicPlayer.stop();
            media = new Media(songs.get(songNumber).toURI().toString());
            musicPlayer = new MediaPlayer(media);

            playSound();
        }
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                currentTime = musicPlayer.getCurrentTime().toSeconds();
                endTime = media.getDuration().toSeconds();

                if (currentTime / endTime == 1) {
                    if (autoPlayNext) {
                        nextSound();
                    } else {
                        cancelTimer();
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }
}
