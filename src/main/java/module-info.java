module edu.virginia.cs.hw7.hw7_course_review {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.virginia.cs.hw7.hw7_course_review to javafx.fxml;
    exports edu.virginia.cs.hw7.hw7_course_review;
}