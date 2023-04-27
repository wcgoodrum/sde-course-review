package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;
import java.sql.SQLException;
import java.util.List;
public interface DataManager {
    /**
     *returns Student with that username
     *returns null if password is wrong
     *@throws IllegalArgumentException if the username is not in the database
     */
    Student login(String user, String password);

    /**
     *returns created Student
     *@throw IllegalArgumentException if the username already exists
     *@throw IllegalArgumantException if username or password is empty
     *@throw IllegalArgumentException if password does not equal confirm
     */
    Student createNewUser(String user, String password, String confirm);

    /**
     *returns true if the course is valid
     * @throws IllegalArgumentException if the course is valid but the Student has already made a review
     */
    boolean validCourse(String courseName, Student student);

    /**
     *adds a course to the database if the course does not exist
     *adds review to the course
     *@throw IllegalArgumentException if Student does not exist
     *@throw IllegalArgumentException if text is empty              if you care
     *@throw IllegalArgumentException if courseName is not vaild
     *@throw IllegalArgumentException if rating is not between 1-5
     *@throw IllegalArgumentException if Student already has a review in course
     */
    void addReview(Student student, String courseName, String text, int rating);

    /**
     *returns all the reviews in courseName
     *@throw IllegalArgumentException if courseName is not vaild
     *@throw IllegalArgumentException if course has no reviews/does not exist
     */
    List<Review> getReviews(String courseName);

    /**
     *returns average course rating
     *@throw IllegalArgumentException if courseName is not vaild
     *@throw IllegalArgumentException if course has no reviews/does not exist
     */
    double getAverageRating(String courseName);
}
