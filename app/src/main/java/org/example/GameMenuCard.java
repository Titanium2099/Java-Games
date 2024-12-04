package org.example;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class GameMenuCard extends Button {

    public GameMenuCard(String text, String imagePath) {
        this.setText(text);
        this.setPrefSize(120, 156);
        Image image = new Image(imagePath);
        if (image.isError()) {
            System.out.println("Error loading image: " + imagePath);
            return;
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        Rectangle clip = new Rectangle(100, 100);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);
        StackPane imageContainer = new StackPane(imageView);
        //imageContainer.setStyle("-fx-padding: 5;");
        //add a margin-button to the imageContainer
        imageContainer.setMargin(imageView, new javafx.geometry.Insets(10, 0, 10, 0));
        imageContainer.setPrefSize(100, 100);
        this.setGraphic(imageContainer);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setStyle("-fx-background-radius: 10; -fx-background-color: lightgray; -fx-text-fill: #000000;");
        //change cursor to hand when hovering over the card
        this.setOnMouseEntered(e -> App.scene.setCursor(javafx.scene.Cursor.HAND));
        this.setOnMouseExited(e -> App.scene.setCursor(javafx.scene.Cursor.DEFAULT));
    }
}
