package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CourseReviewController {
    private Student student;
    private int pageNum;
    private List<Review> reviews;
    private Stage stage;
    @FXML
    public Button crMainMenuButton, crCreateButton, lLoginButton, lNewUserButton, mmLogout, mmCreateReviewButton, mmSeeReviewsButton, srMainMenuButton, srSearchButton, srReviewBackButton, srReviewForwardButton;
    @FXML
    public TextField crCourseText, crRatingText, crMessageText, lUsernameText, lPasswordText, lConfirmText, srSearchBox;
    @FXML
    public Label crErrorLabel, lConfirmLabel, lErrorLabel, srCourseName, srReviewsLabel, srErrorLabel, srReviewLabel1, srReviewLabel2, srReviewLabel3, srReviewLabel4, srReviewLabel5, srReviewPageLabel;
    private DataManager dataManager = new DataManagerImpl();
    @FXML
    public void initialize(){
        resetLogin();
    }
    @FXML
    public void switchToLogin(Event event) throws IOException {
        student = null;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Stage stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        resetLogin();
    }
    @FXML
    public void switchToMainMenu(Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainMenu.fxml")));
        Stage stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }
    @FXML
    public void switchToSeeReviews(Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("seeReviews.fxml")));
        Stage stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        resetSeeReviews();
    }
    @FXML
    public void switchToCreateReview(Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("createReview.fxml")));
        Stage stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        resetCreateReview();
    }
    @FXML
    public void login(Event event){
        try {
            if(lConfirmText.isDisable()) {
                student = dataManager.login(lUsernameText.getText(), lPasswordText.getText());
            }
            else{
                student = dataManager.createNewUser(lUsernameText.getText(), lPasswordText.getText(), lConfirmText.getText());
            }
            if(student == null){
                resetNode(lErrorLabel, false);
                lErrorLabel.setText("Incorrect Password");
            }
            else {
                switchToMainMenu(event);
            }
        }
        catch(IllegalArgumentException e){
            resetNode(lErrorLabel, false);
            lErrorLabel.setText(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void newUser(){
        resetNode(lUsernameText, false);
        resetNode(lPasswordText, false);
        resetNode(lErrorLabel,true);
        if(lConfirmText.isDisable()) {
            resetNode(lConfirmText, false);
            resetNode(lConfirmLabel, false);
            lNewUserButton.setText("Have an account");
        }
        else{
            resetNode(lConfirmText, true);
            resetNode(lConfirmLabel, true);
            lNewUserButton.setText("New User");
        }
    }
    @FXML
    public void createReview(){
        try{
            dataManager.addReview(student, crCourseText.getText(), crMessageText.getText(), Integer.parseInt(crRatingText.getText().replace(" ","")));
        }
        catch (NumberFormatException e){
            resetCreateReview();
            resetNode(crErrorLabel, false);
            crErrorLabel.setText("Rating has to be an integer Between 1 and 5");
        }
        catch (IllegalArgumentException e){
            resetCreateReview();
            resetNode(crErrorLabel, false);
            crErrorLabel.setText(e.getMessage());
        }
    }
    @FXML
    public void Search(){
        resetSeeReviews();
        try{
            reviews = dataManager.getReviews(srSearchBox.getText());
            resetNode(srReviewsLabel, false);
            resetNode(srCourseName, false);
            resetNode(srErrorLabel, true);
            resetNode(srReviewLabel1, false);
            srReviewLabel1.setText(reviews.get(0).getText());
            if(reviews.size() > 1) {
                resetNode(srReviewLabel2, false);
                srReviewLabel2.setText(reviews.get(1).getText());
                }
            if(reviews.size() > 2) {
                resetNode(srReviewLabel3, false);
                srReviewLabel3.setText(reviews.get(2).getText());
            }
            if(reviews.size() > 3)  {
                resetNode(srReviewLabel4, false);
                srReviewLabel4.setText(reviews.get(3).getText());
            }
            if(reviews.size() > 4) {
                resetNode(srReviewLabel5, false);
                srReviewLabel5.setText(reviews.get(4).getText());
            }
            resetNode(srReviewPageLabel, false);
            resetNode(srReviewBackButton, false);
            srReviewBackButton.setDisable(true);
            resetNode(srReviewForwardButton, false);
            if(reviews.size() <= 5){
                srReviewForwardButton.setDisable(true);
            }
        }
        catch (IllegalArgumentException e){
            resetNode(srErrorLabel, false);
            crErrorLabel.setText(e.getMessage());
        }
        resetNode(srSearchBox, true);
    }
    @FXML
    public void backButton(){
        pageNum--;
        srReviewPageLabel.setText(String.valueOf(pageNum));
        srReviewLabel1.setText(reviews.get(pageNum*5-5).getText());
        srReviewLabel2.setText(reviews.get(pageNum*5-4).getText());
        srReviewLabel3.setText(reviews.get(pageNum*5-3).getText());
        srReviewLabel4.setText(reviews.get(pageNum*5-2).getText());
        srReviewLabel5.setText(reviews.get(pageNum*5-1).getText());
        if(pageNum == 1){srReviewBackButton.setDisable(true);}
        srReviewForwardButton.setDisable(false);
    }

    @FXML
    public void forwardButton(){
        pageNum++;
        srReviewPageLabel.setText(String.valueOf(pageNum));
        srReviewLabel1.setText(reviews.get(pageNum*5-5).getText());
        if(reviews.size()>pageNum*5-4)srReviewLabel2.setText(reviews.get(pageNum*5-4).getText());
        if(reviews.size()>pageNum*5-3)srReviewLabel3.setText(reviews.get(pageNum*5-3).getText());
        if(reviews.size()>pageNum*5-2)srReviewLabel4.setText(reviews.get(pageNum*5-2).getText());
        if(reviews.size()>pageNum*5-1)srReviewLabel5.setText(reviews.get(pageNum*5-1).getText());
        if(reviews.size() < pageNum*5+1) srReviewForwardButton.setDisable(true);
        srReviewBackButton.setDisable(false);
    }

    @FXML
    public void enterKey(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            Node node = (Node) event.getSource();
            if (node.equals(crCourseText)){
                crRatingText.requestFocus();
            }
            else if(node.equals(crRatingText)){
                crMessageText.requestFocus();
            }
            else if(node.equals(crMessageText)){
                createReview();
            }
            else if(node.equals(lUsernameText)){
                lPasswordText.requestFocus();
            }
            else if(node.equals(lPasswordText)){
                if(lConfirmText.isDisable()){
                    login(event);
                }
                else{
                    lConfirmText.requestFocus();
                }
            }
            else if(node.equals(lConfirmText)){
                    login(event);
            }
            else if(node.equals(srSearchBox)){
                Search();
            }

        }
    }

    //////////////////////Helper Functions///////////////////////////
    public void resetLogin(){
        resetNode(lLoginButton, false);
        resetNode(lNewUserButton, false);
        resetNode(lUsernameText, false);
        resetNode(lPasswordText, false);
        resetNode(lConfirmText,true);
        resetNode(lConfirmLabel,true);
        resetNode(lErrorLabel,true);
        if (lErrorLabel != null) lErrorLabel.setText("");
        if (lNewUserButton != null) lNewUserButton.setText("New User");
    }

    public void resetCreateReview(){
        resetNode(crMainMenuButton, false);
        resetNode(crCreateButton, false);
        resetNode(crCourseText, false);
        resetNode(crRatingText, false);
        resetNode(crMessageText, false);
        resetNode(crErrorLabel, true);
        if (crErrorLabel != null) {
            crErrorLabel.setText("");
        }
    }

    public void resetSeeReviews(){
        resetNode(srSearchButton, false);
        resetNode(srSearchBox, false);
        resetNode(srReviewsLabel, true);
        resetNode(srCourseName, true);
        resetNode(srErrorLabel, true);
        resetNode(srReviewLabel1, true);
        resetNode(srReviewLabel2, true);
        resetNode(srReviewLabel3, true);
        resetNode(srReviewLabel4, true);
        resetNode(srReviewLabel5, true);
        resetNode(srReviewPageLabel, true);
        resetNode(srReviewBackButton, true);
        resetNode(srReviewForwardButton, true);
        if(srErrorLabel != null) srErrorLabel.setText("");
        pageNum = 1;
        reviews = null;
    }

    public void resetNode(Node node, boolean value) {
        if (node != null) {
            node.setVisible(!value);
            node.setDisable(value);
            if (node.getClass() == TextField.class) {
                ((TextField) node).setText("");
            }
        }
    }
}
