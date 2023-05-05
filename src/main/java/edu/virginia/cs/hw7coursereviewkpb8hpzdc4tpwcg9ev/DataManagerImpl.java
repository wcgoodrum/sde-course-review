package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManagerImpl implements DataManager {

    private Connection connection;
    private boolean connected = false;

    @Override
    public void connect() throws SQLException{

        String filePath = "Reviews.sqlite3";

        if (connected) {
            return;
//            throw new IllegalStateException("Already Connected.");
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            connection.setAutoCommit(false);
            connected = true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    // Call this method for initial set up
    public void setUp() throws SQLException {
        connect();

        if (!allThreeTablesExist()) {
            System.out.println("Creating all three tables now...");
            createTables();
        }

        String queryToGetCountFromStudents = "SELECT count(*) FROM Students";
        Statement studentsStatement = connection.createStatement();

        String queryToGetCountFromCourses = "SELECT count(*) FROM Courses";
        Statement coursesStatement = connection.createStatement();

        String queryToGetCountFromReviews = "SELECT count(*) FROM Reviews";
        Statement reviewsStatement = connection.createStatement();

        ResultSet studentsRS = studentsStatement.executeQuery(queryToGetCountFromStudents);
        ResultSet coursesRS = coursesStatement.executeQuery(queryToGetCountFromCourses);
        ResultSet reviewsRS = reviewsStatement.executeQuery(queryToGetCountFromReviews);

        if (studentsRS.getInt(1) <= 0) {
            System.out.println("Populating Students table now...");
            populateStudentsTable();
        }
        if (coursesRS.getInt(1) <= 0) {
            System.out.println("Populating Courses table now...");
            populateCoursesTable();
        }
        if (reviewsRS.getInt(1) <= 0) {
            System.out.println("Populating Reviews table now...");
            populateReviewsTable();
        }

        disconnect();
    }

    @Override
    public void createTables() throws SQLException {
        if(!connected) {
            throw new IllegalStateException("Manager is not connected yet.");
        }
        String queryToCreateStudents = "CREATE TABLE Students " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "UNIQUE(username))";
        String queryToCreateCourses = "CREATE TABLE Courses " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "department VARCHAR(255) NOT NULL, " +
                "catalog VARCHAR(255) NOT NULL)";
        String queryToCreateReviews = "CREATE TABLE Reviews " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "'text' VARCHAR(255) NOT NULL, " +
                "text VARCHAR(255) NOT NULL, " +  // did you mean to use single quotes around text?
                "rating INTEGER NOT NULL, " +
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
    public Student login(String user, String password) throws SQLException{
        if (user == null || password == null) {
            throw new IllegalArgumentException("Fields are null.");
        }
        if (user.length() == 0 || password.length() == 0) {
            throw new IllegalArgumentException("Username or Password is empty.");
        }

        connect();
//        String queryUser = String.format("SELECT * FROM Students WHERE username = '%s'", user);
//        Statement statementUser = connection.createStatement();
//        ResultSet rs = statementUser.executeQuery(queryUser);
//        rs.next();
        String queryUser = String.format("SELECT * FROM Students WHERE username = '%s'", user);
        Statement stmtUser = connection.createStatement();
        ResultSet rs = stmtUser.executeQuery(queryUser);
        if (rs.next() && rs.getString("password").equals(password)) {
            int id = rs.getInt("id");
            rs.close();
            disconnect();
            return new Student(user, password, id);
        } else {
            disconnect();
            throw new IllegalArgumentException("Username not found or Password incorrect.");
        }

    }

    @Override
    public Student createNewUser(String user, String password, String passwordConfirm) throws SQLException{
        if (user == null || password == null || passwordConfirm == null) {
            throw new IllegalArgumentException("Fields are null.");
        }
        if (user.length() == 0 || password.length() == 0) {
            throw new IllegalArgumentException("Username or Password is empty.");
        }
        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        connect();
        String queryUserExists = "SELECT COUNT(*) FROM Students WHERE username = '"+user+"'";
        Statement statementUserExists = connection.createStatement();
        ResultSet rs = statementUserExists.executeQuery(queryUserExists);
        rs.next();
        if (rs.getInt(1) > 0) {
            disconnect();
            throw new IllegalArgumentException("Username already taken.");
        }
        rs.close();

        String queryCreation = "INSERT INTO Students (username, password) VALUES('"+user+"', '"+password+"')";
        Statement statementCreation = connection.createStatement();
        statementCreation.executeUpdate(queryCreation);

        String queryFindID = "SELECT id FROM Students WHERE username = '"+user+"'";
        Statement statementFindID = connection.createStatement();
        ResultSet rsStudent = statementFindID.executeQuery(queryFindID);
        int id = rsStudent.getInt(1);
        rsStudent.close();
        disconnect();
        return new Student (user, password, id);
    }

    @Override
    public boolean validCourse(String courseName, Student student) {
        String[] depNcat = courseName.split(" ");
        if (depNcat.length < 2 || !isAlpha(depNcat[0]) || !isNumeric(depNcat[1])){
            return false;
        }
        Course requestedCourse;
        try {
            requestedCourse = getCourse(courseName);
        } catch(IllegalArgumentException e){
            requestedCourse = new Course("", "", -1);
        }
        try {
            connect();
            int test1 = requestedCourse.getId();
            int test2 = student.getId();
            String queryReview = String.format("SELECT COUNT(*) FROM Reviews WHERE StudentId = '%d' AND CourseID = '%d'", student.getId(), requestedCourse.getId());
            Statement statementReview = connection.createStatement();
            ResultSet rs = statementReview.executeQuery(queryReview);
            rs.next();
            int test3 = rs.getInt(1);
            if (rs.getInt(1) > 0) {
                disconnect();
                throw new IllegalArgumentException("User already reviewed course.");
            }
            rs.close();
            return true;
        } catch(SQLException e) {
            throw new RuntimeException("SQL error");
        }
    }

//    public Student getStudent(String user) throws SQLException {  // unnecessary method
//        connect();
//        String queryStudent = String.format("SELECT * FROM Students WHERE username = '%s'", user);
//        Statement statementStudent = connection.createStatement();
//        ResultSet rs = statementStudent.executeQuery(queryStudent);
//
//        if (!rs.next()){
//            throw new IllegalArgumentException("Student couldn't be found");
//        }
//
//        Student foundStudent = new Student(rs.getString("username"), rs.getString("password"), rs.getInt("id"));
//        rs.close();
//        disconnect();
//        return foundStudent;
//    }

    private Course getCourse(String courseName) {
        try {
            String[] depNcat = courseName.split(" ");
            connect();
            String queryFindCourse = String.format("SELECT * FROM Courses WHERE department = '%s' AND catalog = '%s'", depNcat[0].toUpperCase(), depNcat[1]);
            Statement statementFindCourse = connection.createStatement();
            ResultSet rs = statementFindCourse.executeQuery(queryFindCourse);

            if (!rs.next()) {
                throw new IllegalArgumentException("Course couldn't be found");
            }

            Course foundCourse = new Course(rs.getString("department"), rs.getString("catalog"), rs.getInt("id"));
            rs.close();
            disconnect();
            return foundCourse;
        } catch(SQLException e) {
            throw new RuntimeException("SQL failure");
        }
    }

    private boolean isAlpha(String x){
        for (int i = 0; i < x.length(); i++) {
            if (!Character.isLetter(x.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumeric(String x){
        for (int i = 0; i < x.length(); i++) {
            if (!Character.isDigit(x.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private int addCourse(String department, String catalog) throws SQLException{
        connect();
        String queryCreation = String.format("INSERT INTO Courses (department, catalog) VALUES('%s', '%s')", department.toUpperCase(), catalog);
        Statement statementCreation = connection.createStatement();
        statementCreation.executeUpdate(queryCreation);

        String queryFindID = String.format("SELECT id FROM Courses WHERE department = '%s' AND catalog = '%s'", department.toUpperCase(), catalog);
        Statement statementFindID = connection.createStatement();
        ResultSet rs = statementFindID.executeQuery(queryFindID);
        int id = rs.getInt(1);
        rs.close();
        disconnect();
        return id;
    }

    @Override
    public void addReview(Student student, String courseName, String text, int rating) {
        if (rating > 5 || rating < 1) {
            throw new IllegalArgumentException("rating out of bounds");
        }
        if (text.length() == 0) {
            throw new IllegalArgumentException("text is empty");
        }
        if(!validCourse(courseName, student)){  // validCourse will throw illegalArgumentException for bad courseNames
            throw new IllegalArgumentException("illegal format for courseName");
        }

        try {
            int courseId;
            try {
                Course requestedCourse = getCourse(courseName);
                courseId = requestedCourse.getId();
            } catch(IllegalArgumentException e) {
                String[] depNcat = courseName.split(" ");
                courseId = addCourse(depNcat[0], depNcat[1]);
            }

            connect();
            String queryCreation = String.format("INSERT INTO Reviews (text, rating, studentID, courseID) VALUES('%s', '%d', '%d', '%d')",
                    text, rating, student.getId(), courseId);
            Statement statementCreation = connection.createStatement();
            statementCreation.executeUpdate(queryCreation);
            disconnect();

        } catch(SQLException e) {
            throw new RuntimeException("SQL failure");
        }
    }

    @Override
    public List<Review> getReviews(String courseName) {
        int courseId;
        try {
            courseId = getCourse(courseName).getId();
        } catch (Exception e) {
            throw new IllegalArgumentException("bad name/course doesn't exist");
        }
        try {
            connect();
            String query = String.format("SELECT * FROM Reviews WHERE courseID = %d", courseId);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            List<Review> reviews = new ArrayList<Review>();
            while(rs.next()){
                Review review = new Review(rs.getInt("id"), rs.getString("text"), rs.getInt("rating"));
                reviews.add(review);
            }
            rs.close();
            disconnect();
            return reviews;

        } catch(SQLException e){
            throw new RuntimeException("SQL failure");
        }
    }

    @Override
    public double getAverageRating(String courseName) {
        List<Review> reviews = getReviews(courseName);
        double avRating = 0;
        if (reviews.size() == 0) {
            return -1;
        }
        for (int i = 0; i < reviews.size(); i++){
            avRating += reviews.get(i).getRating();
        }
        return avRating/reviews.size();
    }

    @Override
    public void disconnect() throws SQLException {
        if (!connected) {
            return;
//            throw new IllegalStateException("The manager isn't connected.");
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

        String queryToGetAnnaID = "SELECT id FROM Students WHERE username = 'Anna Hugo'";
        Statement statementAnna = connection.createStatement();
        ResultSet annaRS = statementAnna.executeQuery(queryToGetAnnaID);
        Integer annaStudentID = annaRS.getInt(1);
        annaRS.close();

        String queryToGetCS3140ID = "SELECT id FROM Courses WHERE department = 'CS' AND catalog = '3140'";
        Statement statementCS3140 = connection.createStatement();
        ResultSet cs3140RS = statementCS3140.executeQuery(queryToGetCS3140ID);
        Integer cs3140ID = cs3140RS.getInt(1);
        cs3140RS.close();

        String queryToGetHIST2350ID = "SELECT id FROM Courses WHERE department = 'HIST' AND catalog = '2350'";
        Statement statementHIST2350 = connection.createStatement();
        ResultSet hist2350RS = statementHIST2350.executeQuery(queryToGetHIST2350ID);
        Integer hist2350ID = hist2350RS.getInt(1);
        hist2350RS.close();

        String queryToGetJAPN1010ID = "SELECT id FROM Courses WHERE department = 'JAPN' AND catalog = '1010'";
        Statement statementJAPN1010 = connection.createStatement();
        ResultSet japn1010RS = statementJAPN1010.executeQuery(queryToGetJAPN1010ID);
        Integer japn1010ID = japn1010RS.getInt(1);
        japn1010RS.close();

        String queryToAddCS3140Review1 = String.format("""
                INSERT INTO Reviews ('text', rating, StudentID, CourseID)
                VALUES ("%s", %d, %d, %d);
                """, "Take this class if you want to become a software engineer.",
                4, emilyStudentID, cs3140ID);
        String queryToAddCS3140Review2 = String.format("""
                INSERT INTO Reviews ('text', rating, StudentID, CourseID)
                VALUES ("%s", %d, %d, %d);
                """, "There are many group projects in this class!",
                3, johnStudentID, cs3140ID);
        String queryToAddHIST2350Review = String.format("""
                INSERT INTO Reviews ('text', rating, StudentID, CourseID)
                VALUES ("%s", %d, %d, %d);
                """, "I learned a lot about history. Highly recommend!",
                5, emilyStudentID, hist2350ID);
        String queryToAddJAPN1010Review = String.format("""
                INSERT INTO Reviews ('text', rating, StudentID, CourseID)
                VALUES ("%s", %d, %d, %d);
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

    @Override
    public void deleteTables() throws SQLException {
        connect();
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
                disconnect();
            }
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String args[]) throws SQLException {
//        DataManager thing = new DataManagerImpl();
////        thing.connect();
//        thing.deleteTables();
//        thing.setUp();
////        thing.disconnect();
//    }

}