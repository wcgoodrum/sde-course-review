package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CommandLineCourseReview {
    private Scanner scanner = new Scanner(System.in);
    private Student student = null;
    private DataManager dataManager = new DataManagerImpl();

    public static void main(String[] args) {
        CommandLineCourseReview play = new CommandLineCourseReview();
        play.run();
    }

    public void run(){
        try {
            dataManager.setUp();
        } catch(SQLException e){
            throw new RuntimeException("!! setup failed !!");
        }
        loginScreen();
    }

    public void loginScreen() {
        for (;;) {
            System.out.print("Options: _LOGIN_ or make a _NEW_ account > ");
            String loginChoice = scanner.nextLine();
            if (loginChoice.toUpperCase().contains("LOGIN")){
                System.out.print("Enter Username > ");
                String user = scanner.nextLine();
                System.out.print("Enter Password > ");
                String password = scanner.nextLine();
                try{
                    student = dataManager.login(user, password);
                    if(student != null){
                        System.out.println("Welcome "+student.getUsername());
                        mainMenu();
                    }
                    else{
                        System.out.println("Wrong password.");
                    }
                }
                catch(IllegalArgumentException e){
                    System.out.println(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (loginChoice.toUpperCase().contains("NEW")){
                System.out.print("Enter Username > ");
                String user = scanner.nextLine();
                System.out.print("Enter Password > ");
                String password = scanner.nextLine();
                System.out.print("Confirm Password > ");
                String confirm = scanner.nextLine();
                System.out.println(user + password + confirm);

                try{
                    student = dataManager.createNewUser(user, password, confirm);
                    if(student != null){
                        System.out.println("Welcome "+student.getUsername());
                        mainMenu();
                    }
                    else{
                        System.out.println("wrong password");
                    }
                }
                catch(IllegalArgumentException e){
                    System.out.println(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                System.out.println("not valid choice");
            }
        }
    }

    public void mainMenu(){
        for(;;) {
            System.out.print("Options: _CREATE_ new review, _BROWSE_ other reviews, _LOGOUT_ of account.\n > ");
            String mainMenuChoice = scanner.nextLine().toUpperCase();
            if (mainMenuChoice.contains("CREATE")) {
                createReview();
            } else if (mainMenuChoice.contains("BROWSE")) {
                seeReviews();
            } else if (mainMenuChoice.contains("LOGOUT")) {
                loginScreen();
            } else {
                System.out.print("Invalid option, try again > ");
            }
        }
    }

    public void seeReviews(){
        System.out.print("Enter course pneumonic and number > ");
        String courseName = scanner.nextLine();
        try{
            List<Review> reviews = dataManager.getReviews(courseName);
            double rating = dataManager.getAverageRating(courseName);
            System.out.println(courseName + "\t\t\t"+rating+"/5 stars\n");
            for (int i = 0; i < reviews.size(); i++) {
                System.out.println(reviews.get(i).getText()+"\n");
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Couldn't find course, did you type it correctly?");
            System.out.println(e);
        }
        mainMenu();
    }

    public void createReview(){
        System.out.print("Enter course pneumonic and number > ");
        String courseName = scanner.nextLine();
        try {
            if (dataManager.validCourse(courseName, student)) {
                System.out.print("Enter written review:\n > ");
                String text = scanner.nextLine();
                int rating = 0;
                while (rating > 5 || rating < 1) {
                    System.out.print("Rating on scale of 1 to 5 > ");
                    try {
                        rating = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        rating = 0;
                    }
                    if (rating > 5 || rating < 1){
                        System.out.println("Rating must be between 1-5.");
                    }
                }

                dataManager.addReview(student,courseName,text,rating);
            }
        }
        catch (IllegalArgumentException e){
            System.out.println("Couldn't find course, did you type it correctly?");
            System.out.println(e);
        }
        mainMenu();
    }
}
