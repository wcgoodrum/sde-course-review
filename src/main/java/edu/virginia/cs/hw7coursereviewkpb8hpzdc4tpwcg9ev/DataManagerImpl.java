package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import java.sql.*;
import java.util.List;

public class DataManagerImpl implements DataManager {

    private Connection connection;
    private boolean connected = false;

    @Override
    public void connect() throws SQLException {
        // i think i need to handle it when file base doesn't exist...?
        String filePath = "Reviews.sqlite3";

        if (connected) {
            throw new IllegalStateException("Already Connected.");
        }
        try {
//            String url = ConfigSingleton.getInstance().getDatabaseFilename();
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            connection.setAutoCommit(false);
            connected = true;
            createTables(); // needs to debug
        }
        catch (Exception e) {
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

        if (allThreeTablesExist()) {
            throw new IllegalStateException("The table you are trying to create already exists.");
        }
        else {
            statementStops.executeUpdate(queryToCreateStudents);
            statementBusLines.executeUpdate(queryToCreateCourses);
            statementRoutes.executeUpdate(queryToCreateReviews);
        }
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
        if (!connected) {
            throw new IllegalStateException("The manager isn't connected.");
        }
        connection.commit();
        connection.close();
        connected = false;
    }

    // helper functions ===============================================================

    private Boolean allThreeTablesExist() throws SQLException {
        String queryToCheckIfStudentsAlreadyExist = "SELECT count (*) FROM sqlite_master " +
                "WHERE type='table' AND name='Students'";
        String queryToCheckIfCoursesAlreadyExist = "SELECT count (*) FROM sqlite_master " +
                "WHERE type='table' AND name='Courses'";
        String queryToCheckIfReviewsAlreadyExist = "SELECT count (*) FROM sqlite_master " +
                "WHERE type='table' AND name='Reviews'";

        Statement statementStudents = connection.createStatement();
        Statement statementCourses = connection.createStatement();
        Statement statementReviews = connection.createStatement();

        ResultSet studentsRS = statementStudents.executeQuery(queryToCheckIfStudentsAlreadyExist); // should return either 1 or 0
        ResultSet coursesRS = statementCourses.executeQuery(queryToCheckIfCoursesAlreadyExist);
        ResultSet reviewsRS = statementReviews.executeQuery(queryToCheckIfReviewsAlreadyExist);

        boolean thereIsStudents = studentsRS.getInt(1) >= 1;
        boolean thereIsCourses = coursesRS.getInt(1) >= 1;
        boolean thereIsReviews = reviewsRS.getInt(1) >= 1;

        studentsRS.close();
        coursesRS.close();
        reviewsRS.close();

        return thereIsStudents || thereIsCourses || thereIsReviews;
    }

    public static void main(String args[]) throws SQLException {
        DataManager thing = new DataManagerImpl();
        thing.connect();
//        thing.createTables();
//        thing.clear();
        thing.disconnect();
    }



}