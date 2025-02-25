package org.example.aplikacjatesujacapunkty;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import org.points.pointscountercomponent.PointsCounter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class MemoryGameController implements Initializable {

    @FXML
    private Label pointsLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private FlowPane imagesFlowPane;
    private ArrayList<MemoryCard> cardsInGame;
    private MemoryCard firstCard, secondCard;
    private PointsCounter pointsCounter = new PointsCounter();

    @FXML
    void playAgain() {
        firstCard = null;
        secondCard = null;

        pointsCounter.resetPoints();
        pointsCounter.resetProgress();

        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();
        cardsInGame = new ArrayList<>();

        for (int i = 0; i < imagesFlowPane.getChildren().size() / 2; i++) {
            Card cardDealt = deck.dealTopCard();
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
            cardsInGame.add(new MemoryCard(cardDealt.getSuit(), cardDealt.getFaceName()));
        }
        Collections.shuffle(cardsInGame);
        flipAllCards();
        updateLabels();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pointsCounter.setMaxPoints(6f);
        pointsCounter.setDefaultNumberToAddPoints(1f);
        initializeImageView();
        playAgain();
    }

    private void initializeImageView() {
        for (int i = 0; i < imagesFlowPane.getChildren().size(); i++) {
            //"cast" the Node to be of type ImageView
            ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(i);
            imageView.setImage(new Image(Card.class.getResourceAsStream("images/back_of_card.png")));
            imageView.setUserData(i);

            //register a click listener
            imageView.setOnMouseClicked(event -> {
                flipCard((int) imageView.getUserData());
            });
        }
    }

    private void flipAllCards() {
        for (int i = 0; i < cardsInGame.size(); i++) {
            ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(i);
            MemoryCard card = cardsInGame.get(i);

            if (card.isMatched())
                imageView.setImage(card.getImage());
            else
                imageView.setImage(card.getBackOfCardImage());
        }
    }

    private void flipCard(int indexOfCard) {
        if (firstCard == null && secondCard == null)
            flipAllCards();

        ImageView imageView = (ImageView) imagesFlowPane.getChildren().get(indexOfCard);

        if (firstCard == null) {
            firstCard = cardsInGame.get(indexOfCard);
            imageView.setImage(firstCard.getImage());
        } else if (secondCard == null) {
            secondCard = cardsInGame.get(indexOfCard);
            imageView.setImage(secondCard.getImage());
            checkForMatch();
            updateLabels();
        }
    }

    private void updateLabels() {
        String formattedProgress = String.format("%.1f", pointsCounter.getProgress() * 100);

        pointsLabel.setText(formattedProgress + "%");
        progressLabel.setText(Integer.toString((int) pointsCounter.getPoints()));
    }

    private void checkForMatch() {
        if (firstCard.isSameCard(secondCard)) {
            pointsCounter.addPoints();
            pointsCounter.updateProgress();
            firstCard.setMatched(true);
            secondCard.setMatched(true);
        }
        firstCard = null;
        secondCard = null;
    }
}
