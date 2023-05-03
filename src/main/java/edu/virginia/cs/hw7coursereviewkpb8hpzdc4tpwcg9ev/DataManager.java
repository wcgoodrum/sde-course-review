package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;
import java.sql.SQLException;
import java.util.List;
public interface DataManager {

    /**
     * Establishes the database connection. Must be called before any other
     * methods are called.
     *
     * @throws IllegalStateException if the Manager is already connected
     */
    void connect() throws SQLException;

    /**
     * Creates the tables Students, Courses, and Reviews in the database. Throws
     * an IllegalStateException if the tables already exist.
     *
     * This *does not* populate the tables Data.
     *
     * @throws IllegalStateException if the tables already exist
     * @throws IllegalStateException if the Manager hasn't connected yet
     */
    void createTables() throws SQLException;


    /**
     *returns Student with that username
     *returns null if password is wrong
     *@throws IllegalArgumentException if the username is not in the database
     */
    Student login(String user, String password) throws SQLException;

    /**
     *returns created Student
     *@throws IllegalArgumentException if the username already exists
     *@throws IllegalArgumentException if username or password is empty
     *@throws IllegalArgumentException if password does not equal confirm
     */
    Student createNewUser(String user, String password, String confirm) throws SQLException;

    /**
     *returns true if the course is valid
     * @throws IllegalArgumentException if the course is valid but the Student has already made a review
     */
    boolean validCourse(String courseName, Student student);

    /**
     *adds a course to the database if the course does not exist
     *adds review to the course
     *@throws IllegalArgumentException if Student does not exist
     *@throws IllegalArgumentException if text is empty              if you care
     *@throws IllegalArgumentException if courseName is not vaild
     *@throws IllegalArgumentException if rating is not between 1-5
     *@throws IllegalArgumentException if Student already has a review in course
     */
    void addReview(Student student, String courseName, String text, int rating);

    /**
     *returns all the reviews in courseName
     *@throws IllegalArgumentException if courseName is not vaild
     *@throws IllegalArgumentException if course has no reviews/does not exist
     */
    List<Review> getReviews(String courseName);

    /**
     *returns average course rating
     *@throws IllegalArgumentException if courseName is not vaild
     *@throws IllegalArgumentException if course has no reviews/does not exist
     */
    double getAverageRating(String courseName);

    /**
     * Commits any changes and ends the connection.
     *
     * @throws IllegalStateException if the Manager hasn't connected yet
     */
    public void disconnect() throws SQLException;

    void setUp() throws SQLException;
}
