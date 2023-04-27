<<<<<<<< HEAD:src/main/java/edu/virginia/cs/gui/HelloController.java
package edu.virginia.cs.gui;
========
package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;
>>>>>>>> c65444d4ba65a0a68b25a7cc9acdf055b0ac2f6f:src/main/java/edu/virginia/cs/hw7coursereviewkpb8hpzdc4tpwcg9ev/HelloController.java

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}