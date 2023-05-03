package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

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
        loginScreen();
    }

    public void loginScreen(){
        for (;;){
            System.out.println("Type login to login or new user if new user\n");
            String loginChoice = scanner.nextLine();
            if (loginChoice.toUpperCase().equals("LOGIN")){
                System.out.println("\nEnter Username");
                String user = scanner.nextLine();
                System.out.println("\nEnter Password");
                String password = scanner.nextLine();
                try{
                    Student student = dataManager.login(user, password);
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
                }
            }
            else if (loginChoice.toUpperCase().equals("NEW USER")){
                System.out.println("\nEnter Username");
                String user = scanner.nextLine();
                System.out.println("\nEnter Password");
                String password = scanner.nextLine();
                System.out.println("\nConfirm Password");
                String confirm = scanner.nextLine();
                try{
                    Student student = dataManager.createNewUser(user, password, confirm);
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
                }
            }
            else{
                System.out.println("not valid choice");
            }
        }
    }

    public void mainMenu(){
        for(;;) {
            System.out.println("To create a review type create \nTo look at reviews type see\nTo logout type logout");
            String mainMenuChoice = scanner.nextLine().toUpperCase();
            if (mainMenuChoice.equals("CREATE")) {
                createReview();
            } else if (mainMenuChoice.equals("SEE")) {
                seeReviews();
            } else if (mainMenuChoice.equals("LOGOUT")) {
                loginScreen();
            } else {
                System.out.println("not a valid choice");
            }
        }
    }

    public void seeReviews(){
        System.out.println("What course are you look for?");
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
            System.out.println(e);
        }
        mainMenu();
    }

    public void createReview(){
        System.out.println("What course are you look for?");
        String courseName = scanner.nextLine();
        try {
            if (dataManager.validCourse(courseName, student)) {
                System.out.println("What would you like to say?");
                String text = scanner.nextLine();
                int rating = 0;
                do {
                    System.out.println("What would you rate this class 1-5 stars");
                    rating = scanner.nextInt();
                    if (rating > 5 || rating < 0){
                        System.out.println("rating must be between 1-5");
                    }
                }
                while (rating > 5 || rating < 0);
                dataManager.addReview(student,courseName,text,rating);
            }
        }
        catch (IllegalArgumentException e){
            System.out.println(e);
        }
        mainMenu();
    }
}
