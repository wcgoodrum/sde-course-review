module edu.virginia.cs.hw7.hw7_course_review {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.virginia.cs.hw7.hw7_course_review to javafx.fxml;
    exports edu.virginia.cs.hw7.hw7_course_review;
    exports edu.virginia.cs.gui;
    opens edu.virginia.cs.gui to javafx.fxml;
    exports edu.virginia.cs.course_review;
    opens edu.virginia.cs.course_review to javafx.fxml;
}