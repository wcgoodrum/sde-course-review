package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataManagerImpl implements DataManager {

    private Connection connection;
    private boolean connected = false;

    @Override
    public void connect() throws SQLException {

        String filePath = "C:\\Users\\13186\\hw7-coursereview-kpb8hp-zdc4tp-wcg9ev\\Reviews.sqlite3";

        if (connected) {
            throw new IllegalStateException("Already Connected.");
        }

        try {
//            String url = ConfigSingleton.getInstance().getDatabaseFilename();
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            connection.setAutoCommit(false);
            connected = true;
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createTables() throws SQLException {

        if(!connected) {
            throw new IllegalStateException("Manager is not connected yet.");
        }

//        routeID = 1;
        String queryToCreateStudents = "CREATE TABLE Students " +
                "(id INT PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL)";
        String queryToCreateCourses = "CREATE TABLE Courses " +
                "(id INT PRIMARY KEY AUTOINCREMENT, " +
                "department VARCHAR(255) NOT NULL, " +
                "catalog VARCHAR(255) NOT NULL)";
        String queryToCreateReviews = "CREATE TABLE Reviews " +
                "(id INT PRIMARY KEY AUTOINCREMENT, " +
                "'text' VARCHAR(255) NOT NULL, " + // MIGHT NEED TO INCREASE WORD COUNT
                "rating INT NOT NULL, " +
                "StudentID INT NOT NULL, " +
                "CourseID INT NOT NULL, " +
                " FOREIGN KEY (StudentID) REFERENCES Students(id) ON DELETE CASCADE," +
                " FOREIGN KEY (CourseID) REFERENCES Courses(id) ON DELETE CASCADE)";

        Statement statementStops = connection.createStatement();
        Statement statementBusLines = connection.createStatement();
        Statement statementRoutes = connection.createStatement();

//        if (allThreeTablesExist()) {
//            throw new IllegalStateException("The table you are trying to create already exists.");
//        }
//        else {
            statementStops.executeUpdate(queryToCreateStudents);
            statementBusLines.executeUpdate(queryToCreateCourses);
            statementRoutes.executeUpdate(queryToCreateReviews);
//        }
    }

    @Override
    public Student login(String user, String password) {
        return null;
    }

    @Override
    public Student createNewUser(String user, String password, String confirm) {
        return null;
    }

    @Override
    public boolean validCourse(String courseName, Student student) {
        return false;
    }

    @Override
    public void addReview(Student student, String courseName, String text, int rating) {

    }

    @Override
    public List<Review> getReviews(String courseName) {
        return null;
    }

    @Override
    public double getAverageRating(String courseName) {
        return 0;
    }

    @Override
    public void disconnect() throws SQLException {

    }
}