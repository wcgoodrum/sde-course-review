<<<<<<<< HEAD:src/main/java/edu/virginia/cs/gui/HelloApplication.java
package edu.virginia.cs.gui;
========
package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;
>>>>>>>> c65444d4ba65a0a68b25a7cc9acdf055b0ac2f6f:src/main/java/edu/virginia/cs/hw7coursereviewkpb8hpzdc4tpwcg9ev/HelloApplication.java

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}