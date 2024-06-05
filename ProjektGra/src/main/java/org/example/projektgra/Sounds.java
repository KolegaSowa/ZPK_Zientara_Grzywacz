package org.example.projektgra;

import org.example.soundscomponent.SoundsComponent;

public class Sounds {

    public static SoundsComponent musicInGame = new SoundsComponent("src/main/resources/music");
    public static SoundsComponent effectInGame = new SoundsComponent("src/main/resources/effects");

    public Sounds(){
    }

    public void playMusic(){
        musicInGame.playSound();
    }

    public void playEffect(){
        effectInGame.playSound();
    }

    public void disableAutoPlayEffect(){
        effectInGame.setAutoPlayNext(false);
    }
}
