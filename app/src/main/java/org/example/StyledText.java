package org.example;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StyledText extends Text {
    public StyledText(String text) {
        super(text);
        Font quicksand = Font.loadFont(getClass().getResource("/fonts/Quicksand-Bold.ttf").toExternalForm(), 18);
        if (quicksand != null) {
            this.setFont(quicksand); // Apply font
        } else {
            System.out.println("Font could not be loaded!");
            // Fallback to Arial
            this.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        }
    }    
}
