package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import javafx.fxml.FXML;
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

}
