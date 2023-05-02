package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import java.io.File;
import java.sql.*;
import java.util.List;

public class DataManagerImpl implements DataManager {

    private Connection connection;
    private boolean connected = false;

    @Override
    public void setUp() throws SQLException {
        // I think I need to handle it when file base doesn't exist...?
        File file = new File ("Reviews.sqlite3");
        if (file.exists()) {
            System.out.println("Reviews.sqlite3 is in the root directory!");
        }

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
            deleteTables();
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
                "'text' VARCHAR(255) NOT NULL, " +
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

            // populate tables
            populateCoursesTable();
            populateStudentsTable();
            populateReviewsTable();
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
        // TODO:
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

        statementJohn.executeUpdate(queryToAddJohn);
        statementEmily.executeUpdate(queryToAddEmily);
        statementAnna.executeUpdate(queryToAddAnna);

    }

    private void populateCoursesTable() throws SQLException {
        String queryToAddCS3140 = "INSERT INTO Courses (department, catalog) VALUES ('CS', '3140')";
        String queryToAddHIST2350 = "INSERT INTO Courses (department, catalog) VALUES ('HIST', '2350')";
        String queryToAddJAPN1010 = "INSERT INTO Courses (department, catalog) VALUES ('JAPN', '1010')";

        Statement statementCS3140 = connection.createStatement();
        Statement statementHIST2350 = connection.createStatement();
        Statement statementJAPN1010 = connection.createStatement();

        statementCS3140.executeUpdate(queryToAddCS3140);
        statementHIST2350.executeUpdate(queryToAddHIST2350);
        statementJAPN1010.executeUpdate(queryToAddJAPN1010);

    }

    private void populateReviewsTable() throws SQLException {
        String queryToGetEmilyID = "SELECT id FROM Students WHERE username = 'Emily Lin'";
        Statement statementEmily = connection.createStatement();
        ResultSet emilyRS = statementEmily.executeQuery(queryToGetEmilyID);
        Integer emilyStudentID = emilyRS.getInt(1);
        emilyRS.close();

        String queryToGetJohnID = "SELECT id FROM Students WHERE username = 'John Smith'";
        Statement statementJohn = connection.createStatement();
        ResultSet johnRS = statementJohn.executeQuery(queryToGetJohnID);
        Integer johnStudentID = johnRS.getInt(1);
        johnRS.close();

        String queryToGetAnnaID = "SELECT id FROM Students WHERE username = 'Anna Hugo  '";
        Statement statementAnna = connection.createStatement();
        ResultSet annaRS = statementAnna.executeQuery(queryToGetAnnaID);
        Integer annaStudentID = annaRS.getInt(1); // WHY IS IT NEVER USED?
        annaRS.close();

        String queryToGetCS3140ID = "SELECT id FROM Courses WHERE department = 'CS' AND catalog = '3140'";
        Statement statementCS3140 = connection.createStatement();
        ResultSet cs3140RS = statementCS3140.executeQuery(queryToGetCS3140ID);
        Integer cs3140ID = cs3140RS.getInt(1);
        cs3140RS.close();

        String queryToGetHIST2350ID = "SELECT id FROM Courses WHERE department = 'CS' AND catalog = '3140'";
        Statement statementHIST2350 = connection.createStatement();
        ResultSet hist2350RS = statementHIST2350.executeQuery(queryToGetHIST2350ID);
        Integer hist2350ID = hist2350RS.getInt(1);
        hist2350RS.close();

        String queryToGetJAPN1010ID = "SELECT id FROM Courses WHERE department = 'CS' AND catalog = '3140'";
        Statement statementJAPN1010 = connection.createStatement();
        ResultSet japn1010RS = statementJAPN1010.executeQuery(queryToGetJAPN1010ID);
        Integer japn1010ID = japn1010RS.getInt(1);
        japn1010RS.close();

        String queryToAddCS3140Review1 = String.format("""
                INSERT INTO Reviews ('text', rating, studentID, CourseID)
                VALUES (%s, %d, %d, %d);
                """, "Take this class if you want to become a software engineer.",
                4, emilyStudentID, cs3140ID);
        String queryToAddCS3140Review2 = String.format("""
                INSERT INTO Reviews ('text', rating, StudentID, CourseID)
                VALUES (%s, %d, %d, %d);
                """, "There are many group projects in this class!",
                3, johnStudentID, cs3140ID);
        String queryToAddHIST2350Review = String.format("""
                INSERT INTO Reviews ('text', rating, StudentID, CourseID)
                VALUES (%s, %d, %d, %d);
                """, "I learned a lot about history. Highly recommend!",
                5, emilyStudentID, hist2350ID);
        String queryToAddJAPN1010Review = String.format("""
                INSERT INTO Reviews ('text', rating, StudentID, CourseID)
                VALUES (%s, %d, %d, %d);
                """, "There are homeworks due almost every day in this class.",
                3, annaStudentID, japn1010ID);

        Statement statementCS3140Review1 = connection.createStatement();
        Statement statementCS3140Review2 = connection.createStatement();
        Statement statementHIST2350Review = connection.createStatement();
        Statement statementJAPN1010Review = connection.createStatement();

        statementCS3140Review1.executeUpdate(queryToAddCS3140Review1);
        statementCS3140Review2.executeUpdate(queryToAddCS3140Review2);
        statementHIST2350Review.executeUpdate(queryToAddHIST2350Review);
        statementJAPN1010Review.executeUpdate(queryToAddJAPN1010Review);
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

    public void deleteTables() {
        if(!connected) {
            throw new IllegalStateException("Manager is not connected yet.");
        }

        try {
            String queryToDeleteStudents = "DROP TABLE Students";
            String queryToDeleteCourses = "DROP TABLE Courses";
            String queryToDeleteReviews = "DROP TABLE Reviews";

            Statement statementStudents = connection.createStatement();
            Statement statementCourses = connection.createStatement();
            Statement statementReviews = connection.createStatement();

            if (!allThreeTablesExist()) {
                throw new IllegalStateException ("The table you are trying to delete doesn't exists.");
            }
            else {
                statementStudents.executeUpdate(queryToDeleteStudents);
                statementCourses.executeUpdate(queryToDeleteCourses);
                statementReviews.executeUpdate(queryToDeleteReviews);
            }
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        DataManager thing = new DataManagerImpl();
        thing.setUp();
        thing.disconnect();
    }

}