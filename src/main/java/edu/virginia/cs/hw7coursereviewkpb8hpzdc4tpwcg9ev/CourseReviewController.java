package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CourseReviewController {
    private Student student;
    @FXML
    public Button crMainMenuButton, crCreateButton, lLoginButton, lNewUserButton, mmLogout, mmCreateReviewButton, mmSeeReviewsButton, srMainMenuButton, srSearchButton, srReviewBackButton, srReviewForwardButton;
    @FXML
    public TextField crCourseText, crRatingText, crMessageText, lUsernameText, lPasswordText, lConfirmText, srSearchBox;
    @FXML
    public Label crErrorLabel, lConfirmLabel, lErrorLabel, srCourseName, srReviewsLabel, srErrorLabel, srReviewLabel1, srReviewLabel2, srReviewLabel3, srReviewLabel4, srReviewLabel5, srReviewPageLabel;
    @FXML
    public void initialize(){
        resetLogin();
    }
    @FXML
    public void switchToLogin(){
        //TODO
    }
    @FXML
    public void switchToMainMenu(){
        //TODO
    }
    @FXML
    public void switchToSeeReviews(){
        //TODO
    }
    @FXML
    public void switchToCreateReview(){
        //TODO
    }
    @FXML
    public void login(){
        //TODO check stuff
        switchToMainMenu();
    }
    public void resetLogin(){
        resetNode(lLoginButton, false);
        resetNode(lNewUserButton, false);
        resetNode(lUsernameText, false);
        resetNode(lPasswordText, false);
        resetNode(lConfirmText,true);
        resetNode(lConfirmLabel,true);
        resetNode(lErrorLabel,true);
    }

    public void resetCreateReview(){
        resetNode(crMainMenuButton, false);
        resetNode(crCreateButton, false);
        resetNode(crCourseText, false);
        resetNode(crRatingText, false);
        resetNode(crMessageText, false);
        resetNode(crErrorLabel, true);
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
    }

    public void resetNode(Node node, boolean value){
        node.setVisible(!value);
        node.setDisable(value);
        if(node.getClass() == TextField.class){
            ((TextField) node).setText("");
        }
    }
}
