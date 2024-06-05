package org.example.projektgra.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.projektgra.Sounds;
import org.points.pointscountercomponent.PointsCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SpaceInvaders {

    private Sounds sounds = new Sounds();
    private static final Random random = new Random();
    private static final int width = 800;
    private static final int height = 600;
    private static final int playerSize = 60;
    static final Image playerImage = new Image("file:src/main/resources/images/player.png");

    static final Image[] bombsImage = {
            new Image("file:src/main/resources/images/1.png"),
            new Image("file:src/main/resources/images/2.png"),
            new Image("file:src/main/resources/images/3.png"),
            new Image("file:src/main/resources/images/4.png"),
            new Image("file:src/main/resources/images/5.png"),
            new Image("file:src/main/resources/images/6.png"),
            new Image("file:src/main/resources/images/7.png"),
            new Image("file:src/main/resources/images/8.png"),
            new Image("file:src/main/resources/images/9.png"),
            new Image("file:src/main/resources/images/10.png"),
    };
    private final int maxBombs = 10;
    private final int maxShots = maxBombs * 2;
    private boolean gameOver = false;
    private GraphicsContext gc;
    private Rocket player;
    private List<Shot> shots;
    private List<Universe> univ;
    private List<Bomb> Bombs;
    private double mouseX;
    private PointsCounter pointsCounter = new PointsCounter();

    //start
    public void start(Stage stage) {
        sounds.disableAutoPlayEffect();
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        canvas.setCursor(Cursor.MOVE);
        canvas.setOnMouseMoved(e -> mouseX = e.getX());
        canvas.setOnMouseClicked(e -> {
            if (shots.size() < maxShots) shots.add(player.shoot());
            if (gameOver) {
                gameOver = false;
                setup();
            }
        });
        setup();
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.setTitle("Space Invaders");
        stage.show();
    }

    //setup the game
    private void setup() {
        univ = new ArrayList<>();
        shots = new ArrayList<>();
        Bombs = new ArrayList<>();
        player = new Rocket(width / 2, height - playerSize, playerSize, playerImage);
        pointsCounter.resetPoints();
        IntStream.range(0, maxBombs).mapToObj(i -> this.newBomb()).forEach(Bombs::add);
    }

    //run Graphics
    private void run(GraphicsContext gc) {
        gc.setFill(Color.grayRgb(20));
        gc.fillRect(0, 0, width, height);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + pointsCounter.getPoints(), 60, 20);

        if (gameOver) {
            gc.setFont(Font.font(35));
            gc.setFill(Color.YELLOW);
            gc.fillText("Game Over \n Your Score is: " + pointsCounter.getPoints() + " \n Click to play again", width / 2, height / 2.5);
            return;
        }

        univ.forEach(u -> u.draw(gc));

        player.update();
        player.draw(gc);
        player.posX = (int) mouseX;

        Bombs.stream().peek(b -> b.update(height, (int) pointsCounter.getPoints())).peek(b -> b.draw(gc)).forEach(e -> {
            if (player.colide(e) && !player.exploding) {
                player.explode();
            }
        });

        for (int i = shots.size() - 1; i >= 0; i--) {
            Shot shot = shots.get(i);
            if (shot.posY < 0 || shot.toRemove) {
                shots.remove(i);
                continue;
            }
            shot.update();
            shot.draw((int) pointsCounter.getPoints(), gc);
            for (Bomb bomb : Bombs) {
                if (shot.colide(bomb) && !bomb.exploding) {
                    pointsCounter.addPoints();
                    bomb.explode();
                    shot.toRemove = true;
                    sounds.playEffect();
                    Sounds.effectInGame.resetSound();
                }
            }
        }

        for (int i = Bombs.size() - 1; i >= 0; i--) {
            if (Bombs.get(i).destroyed) {
                Bombs.set(i, newBomb());
            }
        }

        gameOver = player.destroyed;
        if (random.nextInt(10) > 2) {
            univ.add(new Universe(width, random));
        }
        for (int i = 0; i < univ.size(); i++) {
            if (univ.get(i).getPosY() > height) {
                univ.remove(i);
            }
        }
    }

    Bomb newBomb() {
        return new Bomb(50 + random.nextInt(width - 100), 0, playerSize, bombsImage[random.nextInt(bombsImage.length)]);
    }
}
