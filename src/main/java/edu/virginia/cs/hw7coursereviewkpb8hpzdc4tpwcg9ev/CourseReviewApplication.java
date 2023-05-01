package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseReviewApplication extends Application {
    Scene login;
    Scene mainMenu;
    Scene seeReviews;
    Scene createReview;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CourseReviewApplication.class.getResource("login.fxml"));
        login = new Scene(fxmlLoader.load(), 600, 400);
        fxmlLoader = new FXMLLoader(CourseReviewApplication.class.getResource("mainMenu.fxml"));
        mainMenu = new Scene(fxmlLoader.load(), 600, 400);
        fxmlLoader = new FXMLLoader(CourseReviewApplication.class.getResource("seeReviews.fxml"));
        seeReviews = new Scene(fxmlLoader.load(), 600, 400);
        fxmlLoader = new FXMLLoader(CourseReviewApplication.class.getResource("createReview.fxml"));
        createReview = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Course Reviews");
        stage.setScene(login);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void changeScene(Stage stage, String scene){
        stage.setScene(getScene(scene));
    }

    public Scene getScene(String scene){
        if (scene.equals("login")){
            return login;
        }
        else if (scene.equals("mainMenu")){
            return mainMenu;
        }
        else if (scene.equals("seeReviews")){
            return seeReviews;
        }
        else if(scene.equals(("createReview"))){
            return createReview;
        }
        throw new IllegalArgumentException("If this is thrown than one of the scene changes is spelled wrong in the controller");
    }
}