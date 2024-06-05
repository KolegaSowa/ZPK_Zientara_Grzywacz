package org.example.projektgra.game;

import javafx.scene.image.Image;

public class Bomb extends Rocket {

    public Bomb(int posX, int posY, int size, Image image) {
        super(posX, posY, size, image);
    }

    public void update(int HEIGHT, int score) {
        super.update();
        if (!exploding && !destroyed) posY += (score / 5) + 2;
        if (posY > HEIGHT) destroyed = true;
    }
}
