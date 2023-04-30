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
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL)";
        String queryToCreateCourses = "CREATE TABLE Courses " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "department VARCHAR(255) NOT NULL, " +
                "catalog VARCHAR(255) NOT NULL)";
        String queryToCreateReviews = "CREATE TABLE Reviews " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'text' VARCHAR(255) NOT NULL, " + // MIGHT NEED TO INCREASE WORD COUNT
                "rating INT NOT NULL, " +
                "StudentID INTEGER NOT NULL, " +
                "CourseID INTEGER NOT NULL, " +
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

    private void populateStudentsTable() throws SQLException {
        String queryToAddJohn = "INSERT INTO Students (username, password) VALUES ('John Smith', 12345)";
        String queryToAddEmily = "INSERT INTO Students (username, password) VALUES ('Emily Lin', 54321)";
        String queryToAddAnna = "INSERT INTO Students (username, password) VALUES ('Anna Hugo', 13579)";

        Statement statementJohn = connection.createStatement();
        Statement statementEmily = connection.createStatement();
        Statement statementAnna = connection.createStatement();

        ResultSet johnRS = statementJohn.executeQuery(queryToAddJohn);
        ResultSet emilyRS = statementEmily.executeQuery(queryToAddEmily);
        ResultSet annaRS = statementAnna.executeQuery(queryToAddAnna);

        johnRS.close();
        emilyRS.close();
        annaRS.close();
    }

    private void populateCoursesTable() throws SQLException {
        String queryToAddCS3140 = "INSERT INTO Courses (department, catalog) VALUES ('CS', '3140')";
        String queryToAddHIST2350 = "INSERT INTO Courses (department, catalog) VALUES ('HIST', '2350')";
        String queryToAddJAPN1010 = "INSERT INTO Courses (department, catalog) VALUES ('JAPN', '1010')";

        Statement statementCS3140 = connection.createStatement();
        Statement statementHIST2350 = connection.createStatement();
        Statement statementJAPN1010 = connection.createStatement();

        ResultSet CS3140RS = statementCS3140.executeQuery(queryToAddCS3140);
        ResultSet HIST2350RS = statementHIST2350.executeQuery(queryToAddHIST2350);
        ResultSet JAPN1010 = statementJAPN1010.executeQuery(queryToAddJAPN1010);

        CS3140RS.close();
        HIST2350RS.close();
        JAPN1010.close();
    }

    private void populateReviewsTable() throws SQLException {
        String queryToGetEmilyID = "SELECT id FROM Students WHERE username = 'Emily Lin'";
        Statement statementEmily = connection.createStatement();
        ResultSet emilyRS = statementEmily.executeQuery(queryToGetEmilyID);
        Integer emilyStudentID = emilyRS.getInt(1); // WHY IS IT NEVER USED?
        emilyRS.close();

        String queryToGetCS3140ID = "SELECT id FROM Courses WHERE department = 'CS' AND catalog = '3140'";
        Statement statementCS3140 = connection.createStatement();
        ResultSet cs3140RS = statementCS3140.executeQuery(queryToGetCS3140ID);
        Integer cs3140ID = cs3140RS.getInt(1);
        cs3140RS.close();

        String queryToAddCS3140Review1 = "INSERT INTO Reviews ('text', rating, StudentID, CourseID) VALUES ('This class " +
                "is a must take if you want to become a software engineer in the future!', 4, emilyStudentID, cs3140ID)";
        String queryToAddCS3140Review2 = "INSERT INTO Reviews ('text', rating, StudentID, CourseID) VALUES ('Find friends " +
                "who are reliable to work with for this class.', 3, 3, 1)";
        String queryToAddHIST2350Review = "INSERT INTO Reviews ('text', rating, StudentID, CourseID) VALUES ('I learned " +
                "a lot about history, highly recommend!', 5, 2, 1)";
        String queryToAddJAPN1010Review = "INSERT INTO Reviews ('text', rating, StudentID, CourseID) VALUES ('There are " +
                "A LOT of work in this class.', 5, 2, 2)";

        Statement statementCS3140Review1 = connection.createStatement();
        Statement statementCS3140Review2 = connection.createStatement();
        Statement statementHIST2350 = connection.createStatement();
        Statement statementJAPN1010 = connection.createStatement();

        ResultSet CS3140RS1 = statementCS3140Review1.executeQuery(queryToAddCS3140Review1);
        ResultSet CS3140RS2 = statementCS3140Review1.executeQuery(queryToAddCS3140Review2);
        ResultSet HIST2350RS = statementHIST2350.executeQuery(queryToAddHIST2350Review);
        ResultSet JAPN1010 = statementJAPN1010.executeQuery(queryToAddJAPN1010Review);

        CS3140RS1.close();
        CS3140RS2.close();
        HIST2350RS.close();
        JAPN1010.close();
    }

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