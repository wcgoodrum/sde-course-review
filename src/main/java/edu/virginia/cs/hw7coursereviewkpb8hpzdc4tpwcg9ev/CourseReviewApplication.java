package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseReviewApplication extends Application {

    private static Student currentStudent;
    public static void setStudent(Student student) {
        currentStudent=student;
    }
    public static Student getStudent() {
        return currentStudent;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CourseReviewApplication.class.getResource("login.fxml"));
        Scene login = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Course Reviews");
        stage.setScene(login);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}