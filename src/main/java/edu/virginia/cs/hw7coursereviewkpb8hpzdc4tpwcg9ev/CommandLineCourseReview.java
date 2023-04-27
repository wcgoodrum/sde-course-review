package edu.virginia.cs.hw7coursereviewkpb8hpzdc4tpwcg9ev;

import java.util.Scanner;

public class CommandLineCourseReview {
    private Scanner scanner;
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
                        mainMenu();
                    }
                    else{
                        System.out.print("wrong password");
                    }
                }
                catch(IllegalArgumentException e){
                    System.out.print(e);
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
                        mainMenu();
                    }
                    else{
                        System.out.print("wrong password");
                    }
                }
                catch(IllegalArgumentException e){
                    System.out.print(e);
                }
            }
            else{
                System.out.println("not valid choice")
            }
        }
    }

    public void mainMenu(){

    }
}
