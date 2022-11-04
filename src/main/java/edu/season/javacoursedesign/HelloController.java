package edu.season.javacoursedesign;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author season
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}